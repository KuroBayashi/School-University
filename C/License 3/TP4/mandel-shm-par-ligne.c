#include <sys/ipc.h>
#include <sys/shm.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <complex.h>
#include <math.h>
#include <semaphore.h>
#include "libimage.h"

/* Fonction d'allocation d'une image d'une taille donnée en mémoire
   partagée (shm: SHared Memory) */
image nouvelle_image_shm(int largeur, int hauteur)
{
  image im;
  int segment_id;
  int sizeoftabpoints;

  im.largeur = largeur;
  im.hauteur = hauteur;

  sizeoftabpoints = sizeof(couleur) * largeur * hauteur;

  segment_id = shmget(IPC_PRIVATE, sizeoftabpoints,
                      IPC_CREAT | IPC_EXCL | S_IRUSR | S_IWUSR);
  if (segment_id == -1) {perror("shmget"); exit(1);}
  im.tabPoints = (couleur *) shmat(segment_id, NULL, 0);
  if (im.tabPoints == (void *)-1) {perror("shmat"); exit(1);}
  shmctl(segment_id, IPC_RMID, 0);
  return im;
}

/* Fonction de destruction d'une image precedemment allouee en mémoire
   partagée (shm: SHared Memory) */
void detruire_image_shm(image im)
{
  shmdt(im.tabPoints);
}

couleur iter_to_couleur(int iter) {
  couleur res;
  double h, s, v;
  double r, g, b;

  s = .9; v = 1; h = iter % 360;
  hsv_to_rgb(h, s, v, &r, &g, &b);
  res.rouge = r;
  res.vert = g;
  res.bleu = b;
  return res;
}

/* lissage d'une image par moyennage sur (facteur*facteur) points */
image lissage(image im, int facteur)
{
  image out = nouvelle_image(im.largeur/facteur, im.hauteur/facteur);
  int i, j;
  int k, l;
  int nb;
      
  for(i = 0; i < out.largeur; i++) {
    for(j = 0; j < out.hauteur; j++) {
      nb = 0;
      couleur p = noir;
      for (k = 0; k < facteur; k++) {
        if (facteur*i + k < im.largeur) {
          for (l = 0; l < facteur; l++) {
            if (facteur*j + l < im.hauteur) {
              couleur orig = lire_couleur(im, facteur*i + k, facteur*j + l);
              p.rouge += orig.rouge;
              p.vert += orig.vert;
              p.bleu += orig.bleu;
              nb ++;
            }
          }
        }
      }
      p.rouge = p.rouge / nb;
      p.vert = p.vert / nb;
      p.bleu = p.bleu / nb;
      change_couleur(out, i, j, p);
    }
  }
  return out;
}

/* retourne le nombre d'itérations nécessaires pour sortir du cercle de
   rayon 2 (ou itermax sinon) pour la suite: z(n+1) = z(n)^2 + c (en
   partant de z(0)= 0). */
int mandeldivergence(double complex c, int itermax) {
  double complex z = 0;
  int i = 0;
  while ((cabs(z) < 2) && (i < itermax)) {
    z = z*z + c;
    i++;
  }
  return i;
}

/* remplit une ligne de l'image de l'ensemble de Mandelbrot: l'image
   préallouée est centrée sur le complexe 'centre' et il faut lui
   ajouter le complexe 'r_x' pour aller de ce centre au point le plus à
   droite de l'image */
void mandelbrot_ligne(image im,
		      double complex centre, double complex r_x, int itermax,
		      int numligne) {
  int size_x = im.largeur/2;
  int size_y = im.hauteur/2;
  double complex r_y = (r_x / size_x) * size_y * I;
  int j = numligne;

  for (int i = 0; i < im.largeur; i++) {
    double complex c;
    int iter;
    couleur coul;
    c = ((double)i-size_x)/size_x*r_x
      + (size_y - (double)j)/size_y*r_y + centre;
    iter = mandeldivergence(c, itermax);
    if (iter == itermax) {
      coul = noir;
    } else {
      coul = iter_to_couleur(iter);
    }
    change_couleur(im, i, j, coul);
  }
}

/* calcul une série de lignes dont les numéros sont fournis par le
   processus père. On utilise des sémaphores pour synchorniser le
   dialogue du père avec chacun de ses fils. */
void mandelbrot_child(int num_child,
		      sem_t * sem_fils, sem_t * sem_pere, int * ordre,
		      image im,
		      double complex center, double complex r_x, int itermax) {
  int fini = 0;
  int nb_lignes_calculees = 0;
  while (! fini) {
    /* on attend un ordre du pere */
    // fprintf(stderr, "Fils %d attend ordre\n", num_child);
    sem_wait(sem_fils);
    /* on récupère le numéro de ligne à calculer */
    int num_ligne = ordre[num_child];
    if (num_ligne >= 0 && num_ligne < im.hauteur) {
      // fprintf(stderr, "Fils %d calcule ligne %d\n", num_child, num_ligne);
      /* on calcule cette ligne */
      mandelbrot_ligne(im, center, r_x, itermax, num_ligne);
      nb_lignes_calculees++;
      /* on prévient le pere qu'on a fini */
      ordre[num_child] = -1;
      sem_post(sem_pere);
    } else if (num_ligne == -2) {
      /* on s'arrete */
      // fprintf(stderr, "Fils %d s'arrete\n", num_child);
      fini = 1;
    } else {
      fprintf(stderr, "Bug dans fils %d !!!\n", num_child);
      exit(1);
    }
  }
  /* fprintf(stderr, "Fils %d a calcule %d lignes\n",
     num_child, nb_lignes_calculees); */
  exit(0);
}


/* fonction de création et d'initialiation d'un sémaphore */
#define MY_SEM_NAME "sem-mandel"
sem_t * open_mysem(int valeur_initiale) {
  sem_t* sem;
  sem = sem_open(MY_SEM_NAME, O_CREAT | O_EXCL, 0666, valeur_initiale);
  if (sem == SEM_FAILED) {perror("sem_open"); exit(1);}
  if (sem_unlink(MY_SEM_NAME) == -1) {perror("sem_unlink"); exit(1);}
  return sem;
}

/* fonction de création des processus fils puis dialogue du père avec
   chacun des fils */
void mandelbrot_shm(int nb_children,
		    image im,
		    double complex center, double complex r_x, int itermax) {

  /* creation d'un tableau partagé d'ordres entre pere et fils */
  int segment_id = shmget(IPC_PRIVATE, sizeof(int)*nb_children,
			  IPC_CREAT | IPC_EXCL | S_IRUSR | S_IWUSR);
  if (segment_id == -1) {perror("shmget"); exit(1);}
  int * ordre = (int *) shmat(segment_id, NULL, 0);
  for (int i = 0; i < nb_children; i++) {
    ordre[i] = -1; /* le fils est en attente */ 
  }
  
  /* creation des semaphores du pere vers le fils */
  sem_t **sem_fils = calloc(nb_children, sizeof(sem_t *));
  for (int id_child = 0; id_child < nb_children; id_child++) {
    sem_fils[id_child] = open_mysem(0);
  }

  /* creation du semaphore du fils vers le pere */
  sem_t *sem_pere = open_mysem(nb_children);

  /* creation des fils */
  for (int num_child = 0; num_child < nb_children; num_child++) {
    int child_pid = fork(); if (child_pid == -1) {perror("fork"); exit(1);}
    if (child_pid == 0) {
      mandelbrot_child(num_child, sem_fils[num_child], sem_pere, ordre,
		       im, center, r_x, itermax);
    }
  }

  // fprintf(stderr, "Le pere commence à donner les ordres\n");
  
  /* envoi des ordres de calcul des lignes */
  int numligne = 0;
  /* tant qu'il reste des lignes à calculer */
  while (numligne < im.hauteur) {
    /* on attend qu'un fils soit disponible */
    sem_wait(sem_pere);
    /* on cherche ce fils */
    int num_child = 0;
    while (ordre[num_child] != -1 && num_child < nb_children) {num_child++;}
    if (num_child == nb_children) {fprintf(stderr, "Bug !!!\n"); exit(1);}
    // fprintf(stderr, "Le fils %d est libre pour %d\n", num_child, numligne);
    /* on lui donne un nouvel ordre */
    ordre[num_child] = numligne;
    sem_post(sem_fils[num_child]);
    numligne++;
  }
  /* on envoie les ordres d'arrêt */
  int nb_children_stoppes = 0;
  while (nb_children_stoppes != nb_children ) {
    /* on attend qu'un fils soit disponible */
    sem_wait(sem_pere);
    /* on cherche ce fils */
    int num_child = 0;
    while (ordre[num_child] != -1 && num_child < nb_children) {num_child++;}
    if (num_child == nb_children) {fprintf(stderr, "Bug !!!\n"); exit(1);}
    /* on lui donne l'ordre d'arrêt */
    ordre[num_child] = -2;
    sem_post(sem_fils[num_child]);
    nb_children_stoppes++;
  }
  
  /* on attend tous ses fils */
  while(wait(NULL) != -1) {};
}

/* fonction qui précise l'usage de ce programme */
void usage(char * nom_prog, char * message) {
  fprintf(stderr, "%s\n", message);
  fprintf(stderr,
	  "usage: %s <image> <largeur>x<hauteur> <lissage> <cx>,<cy> <da>:<dv> <itermax> <nbproc>\n",
	  nom_prog);
  exit(0);
}

/* lit les paramètres de la ligne de commande pour calculer une image de
   l'ensemble de Mandelbrot lissée. Les paramètres sont dans l'ordre :

   nom_image, largeur, hauteur, lissage, Cx, Cy, Da, Dv itermax

   nom_image : nom de l'image à produire (.ppm)
   largeur, hauteur : dimensions de l'image finale
   lissage : facteur de lissage (1, 2 ouy 3)
   Cx, Cy : les coordonnées du centre (Cx + I * Cy)
   Da, Dv : les coordonnées polaires de l'axe horizontale
   itermax : nombre maximal d'itérations
   nbproc: nombre de processus fils
*/

int main(int argc, char *argv[]) {

  /* on vérifie le nombre d'arguments */
  if (argc != 8) {
    usage(argv[0],"Nombre d'arguments incorrect");
  }


  /* le nom du fichier résultat */
  char * nom_image = argv[1];

  /* les dimensions de l'image */
  int taille_lissee_x, taille_lissee_y;
  int read = sscanf(argv[2], "%dx%d", &taille_lissee_x, &taille_lissee_y);
  // sscanf(argv[3], "%d", &taille_lissee_y);
  if (read != 2 || taille_lissee_x < 1 || taille_lissee_y < 1) {
    usage(argv[0], "Dimensions sous la forme 640x480");
  }
  /* le facteur de lissage */
  int facteur_lissage;
  sscanf(argv[3], "%d", &facteur_lissage);
  if (facteur_lissage < 1 || facteur_lissage > 3) {
    usage(argv[0], "Facteur de lissage entre 1 et 3");
  }
  
  /* le centre */
  double c_x, c_y;
  read = sscanf(argv[4],"%lf,%lf", &c_x, &c_y);
  if (read != 2) {
    usage(argv[0], "Centre sous la forme x,y");
  }
  double complex centre = c_x + I * c_y;
  
  /* le vecteur horozontal */
  double dx_a, dx_v;
  read = sscanf(argv[5],"%lf:%lf", &dx_a, &dx_v);
  if (read != 2) {
    usage(argv[0], "Orientation sous la forme angle:distance");
  }
  double complex dx = dx_v * cexp(dx_a/180.0*M_PI * I);
  
  /* le nombre maximum d'itération */
  int itermax;
  sscanf(argv[6], "%d", &itermax);
  if (itermax < 1 || itermax > 100000) {
    usage(argv[0], "Itération maximum entre 1 et 100000");
  }

  /* le nombre de processus fils */
  int nb_children = 0;
  sscanf(argv[7], "%d", &nb_children);
  if (nb_children < 1 && nb_children > 100) {
    usage(argv[0], "nbproc entre 1 et 100");
  }

  int taille_x = taille_lissee_x * facteur_lissage;
  int taille_y = taille_lissee_y * facteur_lissage;

  image brute = nouvelle_image_shm(taille_x, taille_y);
  mandelbrot_shm(nb_children, brute, centre, dx, itermax);
  image lissee = lissage(brute, facteur_lissage);
  ecrire_image(lissee, nom_image);
  detruire_image_shm(brute);
  detruire_image(lissee);
  return 0;
}

