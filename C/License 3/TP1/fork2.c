#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>


int main() {
    pid_t pid_child;

    printf("Bonjour\n");

    pid_child = fork();

    // Ajout : Gestion de l'erreur en cas de fork() impossible.
    if (pid_child == -1) {
        perror("fork() impossible");
        exit(1);
    }

    if (pid_child == 0){
        printf("... je suis le fils\n");
    } else {
        printf("... je suis le pere\n");
    }

    return 0;
}
