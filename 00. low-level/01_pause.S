.text                      # секция кода
.globl _start
_start:                    # точка входа в программу (отсюда начнётся исполнение)
  movq    $34, %rax        # syscal linux (34 - pause)
  syscall                  # системный вызов

  movq    $60, %rax        # syscal linux (60 - exit)
  movq    $0, %rdi         # exit code (0 - success)
  syscall                  # системный вызов
