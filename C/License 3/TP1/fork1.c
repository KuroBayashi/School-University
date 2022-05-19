#include <stdio.h>
#include <unistd.h>


int main() {
    pid_t pid_child;

    printf("Bonjour\n");

    pid_child = fork();

    if (pid_child == 0){
        printf("... je suis le fils\n");
    } else {
        printf("... je suis le pere\n");
    }

    return 0;
}
