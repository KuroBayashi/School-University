#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


int main() {
    pid_t pid_child;
    int status_child;

    printf("Bonjour\n");

    pid_child = fork();
    if (pid_child == -1) {
        perror("fork() impossible");
        exit(1);
    }

    if (pid_child == 0){
        sleep(1);
        printf("... je suis le fils\n");
    } else {
        printf("... je suis le pere\n");
        pid_child = wait(&status_child);    // Attente du fils pour terminer le pere
        if (pid_child == -1) {              // Cas d'erreur
            perror("wait() error");
        }
    }

    return 0;
}
