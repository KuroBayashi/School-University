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
        srand(getpid());    // Init rand
        sleep(rand() % 3);  // sleep aleatoire
        printf("... je suis le fils\n");
    } else {
        srand(getpid());    // Init rand
        sleep(rand() % 3);  // sleep aleatoire
        printf("... je suis le pere\n");
        pid_child = wait(&status_child);
        if (pid_child == -1) {
            perror("wait() error");
        }
    }

    return 0;
}
