#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <time.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <signal.h>

void mssleep(long ms) {
    struct timespec delai;

    delai.tv_sec = ms / 1000;
    delai.tv_nsec = (ms % 1000) * 1000000;

    int res = nanosleep(&delai, NULL);

    if (res != 0) {
        perror("nanosleep");
    }
}

int flag = 1;
void handler(int sig) {
    flag = 0;
}

/*
    --Ajout de mssleep:
    Affichage aleatoire
    --Ajout des signaux:
    Probleme d'attente infini en cas d'interruption avant le "pause()"
    --Ajout du flag:
    Toujours probleme d'attente infini en cas d'interruption entre le moment ou on
    test le "if(flag)" et le "pause()".
*/
int main() {
    const int start = 1, end = 20;

    pid_t pid_fils;

    if (signal(SIGUSR1, handler) == SIG_ERR) { perror("signal"); exit(1); }
    if ((pid_fils = fork()) == -1) 	     { perror("fork");   exit(1); }

    if (pid_fils == 0){
	srand(getpid());
	
	pid_t pid_pere = getppid();	
	
	for (int i = start; i <= end; i += 2) {
            mssleep(rand() % 100);
	    printf("%d\n", i);

	    kill(pid_pere, SIGUSR1);
	    if (flag) pause();
	    flag = 1;
	}
    } else {
	srand(getpid());

        for (int i = start+1; i <= end; i += 2) {
	    if (flag) pause();
	    flag = 1;

            mssleep(rand() % 100);
	    printf("%d\n", i);

	    kill(pid_fils, SIGUSR1);
	}
    }

    return 0;
}
