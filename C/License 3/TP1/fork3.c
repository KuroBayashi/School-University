#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>


int main() {
    pid_t pid_child;

    printf("Bonjour\n");

    pid_child = fork();

    if (pid_child == -1) {
        perror("fork() impossible");
        exit(1);
    }

    if (pid_child == 0){
        sleep(1); // On endort le fils 1 seconde
        printf("... je suis le fils\n");
    } else {
        printf("... je suis le pere\n");
    }

    return 0;
}
