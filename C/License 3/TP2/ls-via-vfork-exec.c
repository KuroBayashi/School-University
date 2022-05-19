#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


int main() {
    pid_t pid_child;
    int status_child;

    // for (int i = 0; i < 100; ++i) {
        pid_child = vfork();    // Fork virtuel, donc pas de duplication des ressources, attente du execv pour savoir quoi faire

        if (pid_child == -1) {
            perror("fork() impossible");
            exit(1);
        }

        if (pid_child == 0){
            char *argv[] = {"/bin/ls", NULL};
            char *envp[] = {NULL};

            int res = execve("/bin/ls", argv, envp);

            if (res == -1) {
                perror("execve");
            }
        } else {
            pid_child = wait(&status_child);

            if (pid_child == -1) {
                perror("wait() error");
            }
        }
    // }

    return 0;
}
