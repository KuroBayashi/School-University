#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <time.h>
#include <sys/types.h>
#include <sys/wait.h>

void mssleep(long ms) {
    struct timespec delai;

    delai.tv_sec = ms / 1000;
    delai.tv_nsec = (ms % 1000) * 1000000;

    int res = nanosleep(&delai, NULL);

    if (res != 0) {
        perror("nanosleep");
    }
}

int main() {
    const int start = 1, end = 20;

    pid_t pid_child;

    pid_child = fork();

    if (pid_child == -1) {
        perror("fork() impossible");
        exit(1);
    }

    if (pid_child == 0){
	srand(getpid());

	for (int i = start; i <= end; i += 2) {
	    printf("%d\n", i);
            mssleep(rand() % 100);
	}
    } else {
	srand(getpid());

        for (int i = start+1; i <= end; i += 2) {
	    printf("%d\n", i);
            mssleep(rand() % 100);
	}

        pid_child = wait(NULL);
	if (pid_child == -1) 
	    perror("wait error");
    }



    return 0;
}
