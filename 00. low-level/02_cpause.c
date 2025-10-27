#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

int main() {
  printf("%d\n", getpid());
  pause();
  return EXIT_SUCCESS;
}
