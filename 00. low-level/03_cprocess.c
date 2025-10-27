#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv, char**envp) {
  pid_t pid = fork();
  if (pid == -1) {
    return EXIT_FAILURE;
  }

  if (pid == 0) {
    printf("%d", getpid());
    execve("./02_cpause", argv, envp);
    exit(EXIT_SUCCESS);
  }

  printf("%d", getpid());
  pause();

  return EXIT_SUCCESS;
}
