# Типовые кейсы

## Работа с контейнерами через Docker CLI

### Запуск ("длинный" вариант)

Скачивание образа с тегом `latest` (не рекомендуется для Production)
```sh
docker image pull postgres
```

Скачивание образа с фиксированным тегом `18.0-trixie`
```sh
docker image pull postgres:18.0-trixie
```

Скачивание образа с фиксированным хешом `sha256:41fc5342eefba6cc2ccda736aaf034bbbb7c3df0fdb81516eba1ba33f360162c`
```sh
docker image pull postgres@sha256:41fc5342eefba6cc2ccda736aaf034bbbb7c3df0fdb81516eba1ba33f360162c
```

Просмотр списка образов:
```sh
docker image ls --digests
```

**Важно**: необходимо предварительно создать каталоги:
* pgdata
* docker-entrypoint-initdb.d

Создание контейнера из образа (обратите внимание, что установка переменных окружения, volume mapping и port publishing происходят при создании контейнера):
```sh
docker container create \
      -e POSTGRES_DB=productsdb \
      -e POSTGRES_USER=productsapp \
      -e POSTGRES_PASSWORD=secret \
      -v "$PWD/pgdata":/var/lib/postgresql/18/docker:rw \
      -v "$PWD/docker-entrypoint-initdb.d":/docker-entrypoint-initdb.d:ro \
      -p 15432:5432 \
      postgres:18.0-alpine3.22 \
      postgres -c log_statement=all
```
где:
* `-e KEY=VALUE` - environment variables 
* `-v HOST_PATH:CONTAINER_PATH:rw` - volume mapping for `rw` or `ro`
* `-p HOST_PORT:CONTAINER_PORT` - port publishing
* `image:tag@hash` - image
* `cmd` - command

В ответ будет выведен хэш, который нужно использовать дальше (можно было задать имя через `--name` )

Запуск контейнера:
```sh
docker container start -a CONTAINER_ID
```

Просмотр работающих контейнеров:
```sh
docker container ls
```

Просмотр всех контейнеров:
```sh
docker container ls -a
```

### Запуск ("короткий" вариант)

Есть сокращённая версия: `docker image pull` + `docker container create` + `docker container start`:

```sh
docker run \
      -e POSTGRES_DB=productsdb \
      -e POSTGRES_USER=productsapp \
      -e POSTGRES_PASSWORD=secret \
      -v "$PWD/pgdata":/var/lib/postgresql/18/docker:rw \
      -v "$PWD/docker-entrypoint-initdb.d":/docker-entrypoint-initdb.d:ro \
      -p 15432:5432 \
      postgres:18.0-alpine3.22 \
      postgres -c log_statement=all
```

### Выполнение команд внутри контейнера

Можно выполнять команды (запускать процессы) внутри контейнера:
```sh
docker exec -it CONTAINER_ID psql -U productsapp -d productsdb
```
где:
* `-i` - interactive, оставляет STDIN открытым
* `-t` - tty, предоставляет псевдо-терминал

Если интерактивное взаимодействие не нужно, то можно `-it` не указывать:
```sh
docker exec CONTAINER_ID psql -U productsapp -d productsdb -c "SELECT NOW();"
```

### Top

Можно смотреть Top внутри контейнера:
```sh
docker container top CONTAINER_ID
```

### Logs

Можно просматривать логи контейнера через:
```sh
docker container logs CONTAINER_ID
```

Или:
```sh
docker container logs -f CONTAINER_ID
```

### Остановка и запуск контейнера

Остановить контейнер можно командой (SIGTERM, SIGKILL после таймаута):

```sh
docker container stop CONTAINER_ID
```

**Важно**: после остановки контейнера данные **НЕ** удаляются

"Убить" контейнер (SIGKILL) можно через:
```sh
docker container kill CONTAINER_ID
```

Отправить другой сигнал можно через:
```sh
docker container kill -s SIGHUP CONTAINER_ID
```

Если заново запустить контейнер, то данные в нём будут те же, что на момент остановки:

```sh
docker container start -a CONTAINER_ID
```

**Важно**: `docker run` каждый раз делает `docker create`, поэтому не путайте `docker run` и `docker container start`

### Удаление контейнера

Для удаления контейнера можно воспользоваться командой:

```sh
docker container rm CONTAINER_ID
```

**Важно**: после этого данные контейнера будут удалены

Достаточно часто при выполнении `docker run` сразу указывают флаг `--rm`, который приведёт к удалению контейнера после завершения его работы
