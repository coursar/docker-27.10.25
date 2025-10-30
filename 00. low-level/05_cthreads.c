#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <threads.h>

int run(void *arg) {
  printf("thread pid: %d", getpid());
  pause();
  return EXIT_SUCCESS;
}

int main() {
  thrd_t thrd;
  thrd_create(&thrd, run, NULL);

  int result;
  thrd_join(thrd, &result);

  return EXIT_SUCCESS;
}
