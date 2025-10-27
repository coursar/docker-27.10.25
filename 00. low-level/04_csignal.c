#include <stdlib.h>
#include <stdio.h>
#include <signal.h>
#include <unistd.h>

void sig_handler(int signum) {
  printf("signal catched: %d\n", signum);
}

int main() {
  printf("pid: %d\n", getpid());
  signal(SIGTERM, sig_handler);

  while (1) {
    printf("wait\n");
    sleep(5);
  }

  return EXIT_SUCCESS;
}
