docker container run -it \
  --pid container:app \
  --network container:app \
  --privileged \
  ubuntu
