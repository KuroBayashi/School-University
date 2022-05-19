# Simple
gcc -Wall -o mandel-simple mandel-simple.c libimage.c -lm
./mandel-simple res.ppm 640x640 2 0,0 0:2 10000

# Fichier
gcc -Wall -o mandel-shm-par-ligne mandel-shm-par-ligne.c libimage.c -lm
./mandel-simple res.ppm 640x640 2 0,0 0:2 10000 4

# Tube
gcc -Wall -o mandel-shm-par-ligne mandel-shm-par-ligne.c libimage.c -lm
./mandel-simple res.ppm 640x640 2 0,0 0:2 10000 4

# SHM
gcc -Wall -o mandel-shm-par-ligne mandel-shm-par-ligne.c libimage.c -lm -pthread
./mandel-simple res.ppm 640x640 2 0,0 0:2 10000 4
