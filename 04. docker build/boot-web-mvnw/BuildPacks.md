[Paketo Buildpacks](https://paketo.io):
- Apache Http Server, Nginx
- Java, Java Native Image
- Go
- Node.js
- PHP
- Python
- и т.д.

```sh
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=boot-buildpack:1.0.0
```