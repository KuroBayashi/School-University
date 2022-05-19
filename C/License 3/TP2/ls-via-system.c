#include <stdio.h>
#include <stdlib.h>

int main() {
    // for (int i = 0; i < 100; ++i) {
        int res = system("/bin/ls");

        if (res == -1) {perror("system()");}
    // }

    return 0;
}
