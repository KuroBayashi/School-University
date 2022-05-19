#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <complex.h>
#include <math.h>
#include "libimage.h"


/*
*  Lissage d'une image par moyennage sur (facteur*facteur) points.
*  
*  Important: la largeur et la hauteur de l'image d'origine doivent
*  être des multiples du facteur fourni !
*/
image lissage(image im, int facteur) {

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

/* 
* Transformation d'un nombre d'itération en une couleur: ici, la
* saturation est fixée à 0.9 et la valeur de la couleur est au maximum (1). 
* Seule la teinte (= hue) varie de manière cyclique (un tour toutes les 
* 360 itérations). 
*/
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

/* 
* Retourne le nombre d'itérations nécessaires pour sortir du cercle de
* rayon 2 (ou itermax sinon) pour la suite: z(n+1) = z(n)^2 + c 
* (en partant de z(0) = 0).
*/
int mandeldivergence(double complex c, int itermax) {

  double complex z = 0;
  int i = 0;
  
  while ((cabs(z) < 2) && (i < itermax)) {
    z = z*z + c;
    i++;
  }
  
  return i;
}

/* 
* Remplit une ligne de l'image de l'ensemble de Mandelbrot: l'image
* préallouée est centrée sur le complexe 'centre' et il faut lui
* ajouter le complexe 'r_x' pour aller de ce centre au point le plus à
* droite de l'image 
*/
void mandelbrot_ligne(image im,double complex centre, double complex r_x, int itermax, int numligne) {

  int size_x = im.largeur/2;
  int size_y = im.hauteur/2;
  double complex r_y = (r_x / size_x) * size_y * I;
  int j = numligne;

  for (int i = 0; i < im.largeur; i++) {
    double complex c = ((double)i-size_x)/size_x*r_x + (size_y - (double)j)/size_y*r_y + centre;
    int iter = mandeldivergence(c, itermax);
    couleur coul = (iter == itermax) ? noir : iter_to_couleur(iter);
    
    change_couleur(im, i, j, coul);
  }
}


/* 
* Calcul une image de l'ensemble de Mandelbrot : l'image préallouée est
* centrée sur le complexe 'centre' et il faut lui ajouter le complexe
* 'r_x' pour aller de ce centre au point le plus à droite de l'image 
*/
void mandelbrot(image im, double complex centre, double complex r_x, int itermax) {

  for (int j = 0; j < im.hauteur; j++) {
    mandelbrot_ligne(im, centre, r_x, itermax, j);
  }
}

/* 
* La fonction d'un processus fils 
*/
void mandelbrot_fils(image im, double complex centre, double complex r_x, int itermax, int nbchild, int numchild) {
  
  char imagefn[255];
  
  for (int j = numchild; j < im.hauteur; j += nbchild) {
    mandelbrot_ligne(im, centre, r_x, itermax, j);
  }
  
  sprintf(imagefn, "mandel-im-%03d.ppm", numchild);
  ecrire_image(im, imagefn);
  
  exit(0);
}

/* 
* Calcul une image de l'ensemble de Mandelbrot : l'image préallouée est
* centrée sur le complexe 'centre' et il faut lui ajouter le complexe
* 'r_x' pour aller de ce centre au point le plus à droite de l'image
* par création de processus enfants qui calculment chaun une partie de l'image
*/
void mandelbrot_par_fichier(image im, double complex centre, double complex r_x, int itermax, int nbchild) {

  for (int numchild = 0;  numchild < nbchild; ++numchild) {
    int pid_child = fork();
    
    if (pid_child < 0) {
      perror("fork:"); 
      exit(1);
    }
    
    if (pid_child == 0) {
      mandelbrot_fils(im, centre, r_x, itermax, nbchild, numchild);
    }
  }

  /* attente de la terminaison de tous les fils */
  while (wait(NULL) != -1) {};

  /* lire les images produites par les fils et les assembler */
  for (int numchild = 0; numchild < nbchild; numchild++) {
    char imagefn[255];
    
    sprintf(imagefn, "mandel-im-%03d.ppm", numchild);

    image im_child = lire_image(imagefn);

    for (int ligne = numchild; ligne < im.hauteur; ligne += nbchild) {
      for (int i = 0; i < im.largeur; i++) {
	      couleur c = lire_couleur(im_child, i, ligne);
	      
	      change_couleur(im, i, ligne, c);
      }
    }

    detruire_image(im_child);
    unlink(imagefn);
  }
}

/* fonction qui précise l'usage de ce programme */
void usage(char * nom_prog, char * message) {

  fprintf(stderr, "%s\n", message);
  fprintf(stderr,
	  "usage: %s <image> <largeur>x<hauteur> <lissage> <cx>,<cy> <da>:<dv> <itermax> <nbprocessus>\n",
	  nom_prog
	);
  exit(0);
}

/* lit les paramètres de la ligne de commande pour calculer une image de
   l'ensemble de Mandelbrot lissée. Les paramètres sont dans l'ordre :

   nom_image, largeur, hauteur, lissage, Cx, Cy, Da, Dv itermax nbprocessus

   nom_image : nom de l'image à produire (.ppm)
   largeur, hauteur : dimensions de l'image finale
   lissage : facteur de lissage (1, 2 ouy 3)
   Cx, Cy : les coordonnées du centre (Cx + I * Cy)
   Da, Dv : les coordonnées polaires de l'axe horizontale
   itermax : nombre maximal d'itérations
   nbprocessus : nombre de processus enfants
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
  if (itermax < 1 || itermax > 10000) {
    usage(argv[0], "Itération maximum entre 1 et 10000");
  }

  /* le nombre de processus enfants */
  int nbchild;
  sscanf(argv[7], "%d", &nbchild);
  if (nbchild < 1 || nbchild > 100) {
    usage(argv[0], "Nb de processus enfants entre 1 et 100");
  }

  
  int taille_x = taille_lissee_x * facteur_lissage;
  int taille_y = taille_lissee_y * facteur_lissage;

  image brute = nouvelle_image(taille_x, taille_y);
  mandelbrot_par_fichier(brute, centre, dx, itermax, nbchild);
  image lissee = lissage(brute, facteur_lissage);
  ecrire_image(lissee, nom_image);
  detruire_image(brute);
  detruire_image(lissee);
  
  return 0;
}
