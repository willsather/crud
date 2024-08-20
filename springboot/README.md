# Getting Started

## Create Local Database

Pull the docker image:

```zsh
docker pull postgres
```

Create a docker container via the image:

```zsh
docker run --name crud-db -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -e POSTGRES_DB=crud -p 5432:5432 -d postgres
```

Verify the container started: 

```zsh
docker ps
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.2/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.2/gradle-plugin/packaging-oci-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
