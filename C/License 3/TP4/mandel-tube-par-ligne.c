#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <complex.h>
#include <math.h>
#include "libimage.h"

/* lissage d'une image par moyennage sur (facteur*facteur) points.
   Important: la largeur et la hauteur de l'image d'origine doivent
   être des multiples du facteur fourni !
 */
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
      p.rouge = round((double) p.rouge / nb);
      p.vert = round((double) p.vert / nb);
      p.bleu = round((double) p.bleu / nb);
      change_couleur(out, i, j, p);
    }
  }
  return out;
}

/* transformation d'un nombre d'itération en une couleur: ici, la
   saturation est fixée à 0.9 et la valeur de la couleur est au maximum
   (1). Seule la teinte (= hue) varie de manière cyclique (un tour
   toutes les 360 itérations). */
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

/* fonction d'envoi d'une structure de données sur un tube */
void mysend(int out, void *data, int size) {
  int envoye = 0;
  while (envoye != size) {
    int reellementenvoye = write(out, data + envoye, size - envoye);
    if (reellementenvoye == -1) {perror("write"); exit(1);}
    envoye += reellementenvoye;
  }
}

/* fonction de réception d'une structure de données via un tube */
int myreceive(int in, void *data, int size) {
  int recu = 0;
  int reellementrecu = -1;
  while (reellementrecu != 0 && recu != size) {
    reellementrecu = read(in, data + recu, size - recu);
    if (reellementrecu == -1) {perror("read"); exit(1);}
    recu += reellementrecu;
  }
  if (recu != size) { // Permet de savoir que le tube a ete ferme
    return 0;
  }
  return recu;
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

/* remplit une image d'une seule ligne représentant une ligne de l'image
   globale de l'ensemble de Mandelbrot: l'image globale est centrée sur
   le complexe 'centre' et il faut lui ajouter le complexe 'r_x' pour
   aller de ce centre au point le plus à droite de l'image */
void mandelbrot_ligne(image im,
		      int largeur, int hauteur,
		      double complex centre, double complex r_x, int itermax,
		      int numligne) {
  int size_x = largeur/2;
  int size_y = hauteur/2;
  double complex r_y = (r_x / size_x) * size_y * I;
  int j = numligne;
  int i;
  
  for (i = 0; i < largeur; i++) {
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
    change_couleur(im, i, 0, coul);
  }
}

/* fonction utilisée par chaque fils pour recevoir du père une série de
   numéros de ligne à calculer, puis pour retourner le résultat du
   calcul de de cette ligne au père... jusqu'à la fermeture du tube par
   le père. */
void mandelbrot_child(int num_child,
		      int largeur, int hauteur,
		      int in, int out,
		      double complex center, double complex r_x, int itermax) {
  int num_ligne;
  int res_io;
  image imligne = nouvelle_image(largeur, 1);
  int i;
  
  printf("fils %d : debut\n", num_child);

  /* lecture de la ligne à calculer */
  res_io = myreceive(in, &num_ligne, sizeof(int));

  while (res_io != 0) {
    // printf("fils %d : calcul ligne %d\n", num_child, num_ligne);
     /* calcul de la ligne num_ligne */
    mandelbrot_ligne(imligne, largeur, hauteur,
		     center, r_x, itermax,
		     num_ligne);

    /* envoi du numero de la ligne */
    mysend(out, &num_ligne, sizeof(int));

    /* envoi de la couleur de chaque pixel de la ligne */
    for (i = 0; i < largeur; i++) {
      couleur c = lire_couleur(imligne, i, 0);
      mysend(out, &c, sizeof(c));
    }

    /* lecture de la nouvelle ligne à calculer */
    res_io = myreceive(in, &num_ligne, sizeof(int));
  }
  
  printf("fils %d : fin\n", num_child);
  exit(0);
}


/* fonction de création des fils puis de dialogue du père avec ses
   fils */
#define MAX_CHILDREN 100

void mandelbrot_tube_par_ligne(int nb_children,
			       image im,
			       double complex center,
			       double complex r_x,
			       int itermax) {

  int num_child;
  /* les tubes du pere vers les fils */
  int ptof[MAX_CHILDREN][2];
  /* les tubes des fils vers le pere */
  int ftop[MAX_CHILDREN][2];
  /* pour se souvenir des fils actifs */
  int activite[MAX_CHILDREN];
  
  int ligne_courante = 0;
  int nb_lignes_recues = 0;

  /* creation des tubes */
  for (num_child = 0; num_child < nb_children; num_child++) {
    if (pipe(ptof[num_child]) == -1) {perror("pipe"); exit(1);}
    if (pipe(ftop[num_child]) == -1) {perror("pipe"); exit(1);}
  }

  /* on cree les fils */
  for (num_child = 0; num_child < nb_children; num_child++) {
    activite[num_child] = 0;
    int child_pid = fork();
    if (child_pid == -1) {perror("fork"); exit(1);}
    if (child_pid == 0) { /* un fils */
      /* fermeture de tous les tubes sauf les deux extremites importantes */
      int num_tube;
      for (num_tube = 0; num_tube < nb_children; num_tube++) {
        close(ptof[num_tube][1]); /* fermeture cote ecriture */
        close(ftop[num_tube][0]); /* fermeture cote lecture */
        if (num_tube != num_child) {
          /* fermeture de autres cotes sauf pour ce fils */
          close(ptof[num_tube][0]);
          close(ftop[num_tube][1]);
        }
      }

      mandelbrot_child(num_child,
		       im.largeur, im.hauteur,
		       ptof[num_child][0], ftop[num_child][1],
		       center, r_x, itermax);
    }
    /* envoi d'une ligne a calculer pour ce fils */
    if (ligne_courante < im.hauteur) {
      mysend(ptof[num_child][1], &ligne_courante, sizeof(int));
      ligne_courante++;
      activite[num_child] = 1;
    }
  }

  /* le pere */
  /* fermeture de tous les mauvais cotes des tubes  */
  printf("pere: fermeture de cotes inutiles des tubes\n");
  for (num_child = 0; num_child < nb_children; num_child++) {
    close(ptof[num_child][0]); /* fermeture cote lecture */
    close(ftop[num_child][1]); /* fermeture cote ecriture */
  }

  while (nb_lignes_recues != im.hauteur) {
    /* on attend de recevoir des reponses */
    fd_set lecture;
    FD_ZERO(&lecture);
    int max = -1;
    for (num_child = 0; num_child < nb_children; num_child++) {
      if (activite[num_child]) {
        FD_SET(ftop[num_child][0], &lecture);
        if (ftop[num_child][0] > max) {
          max = ftop[num_child][0];
        }
      }
    }
    // attente...
    // printf("pere: select\n");
    int retval = select(max + 1, &lecture, NULL, NULL, NULL);

    if (retval == -1) {perror("select"); exit(1);}
    
    for (num_child = 0; num_child < nb_children; num_child++) {
      if (FD_ISSET(ftop[num_child][0], &lecture)) {
        /* on lit le numero de la ligne */
        int num_ligne_recue;
	int i;
        myreceive(ftop[num_child][0], &num_ligne_recue, sizeof(int));
        /* on lit les points de la ligne */
	for (i = 0; i < im.largeur; i++) {
	  couleur c;
	  myreceive(ftop[num_child][0], &c, sizeof(c));
	  change_couleur(im, i, num_ligne_recue, c);
	}
        nb_lignes_recues++;
        /* on envoie une nouvelle ligne a calculer */
        if (ligne_courante < im.hauteur) {
          mysend(ptof[num_child][1], &ligne_courante, sizeof(int));
          ligne_courante++;
          activite[num_child] = 1;
        } else {
          activite[num_child] = 0;
        }
      }
    }
  }

  /* fermeture de tous les tubes */
  printf("pere: fermeture de tous les tubes\n");
  for (num_child = 0; num_child < nb_children; num_child++) {
    close(ptof[num_child][1]); /* fermeture cote ecriture */
    close(ftop[num_child][0]); /* fermeture cote lecture */
  }

  /* on attend la fin de tous les fils */
  printf("pere: attente de la fin de tous les fils\n");
  while (wait(NULL) != -1) {};
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
  if (nb_children < 1 && nb_children > MAX_CHILDREN) {
    char error_msg[100];
    sprintf(error_msg, "nbproc entre 1 et %d", MAX_CHILDREN);
    usage(argv[0], error_msg);
  }

  int taille_x = taille_lissee_x * facteur_lissage;
  int taille_y = taille_lissee_y * facteur_lissage;

  image brute = nouvelle_image(taille_x, taille_y);
  mandelbrot_tube_par_ligne(nb_children, brute, centre, dx, itermax);
  image lissee = lissage(brute, facteur_lissage);
  ecrire_image(lissee, nom_image);
  detruire_image(brute);
  detruire_image(lissee);
  return 0;
}

