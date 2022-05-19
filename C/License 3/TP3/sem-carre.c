#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <semaphore.h>

#define SEM_PERE "pere"
#define SEM_FILS "fils"


sem_t* create_semaphore(char* name) {
    sem_t* sem;
    
    printf("creation du semaphore /dev/shm/sem.%s\n", MY_SEM_NAME);
    
    sem = sem_open(MY_SEM_NAME, O_CREAT | O_EXCL, 0666, 0);
    if (sem == SEM_FAILED) {
        perror("sem_open"); 
        exit(1);
    }
    
    printf("effacement du semaphore /dev/shm/sem.%s\n", MY_SEM_NAME);
    
    if (sem_unlink(MY_SEM_NAME) == -1) {
        perror("sem_unlink"); 
        exit(1);
    }
    
    return sem;
}

int main() {
    sem_t *semp;
    sem_t *semf;
    
    semp = create_semaphore(SEM_PERE);
    semf = create_semaphore(SEM_FILS);
    
    int child_pid = fork();
    if (child_pid == -1) {
        perror("fork"); 
        exit(1);
    }

    if (child_pid == 0) { // le fils
        sem_wait(semp);
        int val;
       
        FILE* fh = fopen(FICHIER, "r");
        if (fh == NULL) {
            perror("pb de lecture de fichier"); 
            exit(1);
        }
        fscanf(fh, "%d", &val);
        fclose(fh);
        
        while (val != 0) {
            val = val * val;
            
            fh = fopen(FICHIER, "w");
            if (fh == NULL) {
                perror("pb de lecture de fichier 2"); 
                exit(1);
            }
            fscanf(fh, "%d", val);
            fclose(fh);
            
            sem_post(semf);
            sem_wait(semp);
            
            fh = fopen(FICHIER, "r");
            if (fh == NULL) {
                perror("pb de lecture de fichier 3"); 
                exit(1);
            }
            fscanf(fh, "%d", &val);
            fclose(fh);
        }
        
        unlink(FICHIER);
        
        return 0;
    }
    else { // le père
        int val;
        int res;
        FILE* fh;
        
        scanf("%d", &val);
        
        while (val != 0) {
            fh = fopen(FICHIER, "w");
            if (fh == NULL) {
                perror("pb de lecture de fichier 4"); 
                exit(1);
            }
            fscanf(fh, "%d", &val);
            fclose(fh);
            
            sem_post(semp);
            sem_wait(semf);
            
            fh = fopen(FICHIER, "r");
            if (fh == NULL) {
                perror("pb de lecture de fichier 5"); 
                exit(1);
            }
            fscanf(fh, "%d", res);
            fclose(fh);
            
            prinf("Le carre %d est %d\n", val, res);
        }
        
        printf("pere attend mort fils\n");
        
        while (wait(NULL) != -1) {};
        
        printf("pere fin\n");
        
        return 0;
    }
}
