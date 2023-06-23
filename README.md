# MSVC-LOGIN

Microservicio que realiza el Registro y Login de Usuarios


## Descripcion

Aplicacion de Spring Boot que crea una autenticacion con HttpOnly Cookie, JWT, Spring Security, y Spring Data MongoDB.

Con este proyecto tu sabras:

- Flujo apropiado para el inicio de sesion y el registro de usuarios con JWT
- Arquitectura Spring Boot Rest API con Spring Security
- Como configurar Spring Security para trabajar con JWT
- Como definir modelos de datos y su asociacion para el inicio de sesion y registro de usuarios
- Forma de obtener y generar Cookies para Token
- Manera de usar Spring Data MongoDB para interactuar con la base de datos MongoDB


## Pre-requisitos
Que software y herramientas necesitas tener en tu equipo para poder utilizar el microservicio.

- JDK version 17 de Java
  - [Si no lo tienes instalado, puedes descargarlo dando click aqui](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Apache Maven 3.9.2
  - [Si no lo tienes instalado puedes descargarlo dando click aqui](https://maven.apache.org/download.cgi)
- MongoDB Community Server
  - [Si no lo tienes instalado puedes descargarlo dando click aqui](https://www.mongodb.com/try/download/community)
- MondgoDB Shell
  - [Si no lo tienes instalado puedes descargarlo dando click aqui](https://www.mongodb.com/try/download/shell)
- Docker
  - [Si no lo tienes instalado puedes descargarlo dando click aqui](https://www.docker.com/products/docker-desktop/)
- IntelliJ IDEA Community Edition
  - [Si no lo tienes instalado puedes descargarlo dando click aqui](https://www.jetbrains.com/es-es/idea/download/?section=windows)
- Postman
  - [Si no lo tienes instalado puedes descargarlo dando click aqui](https://www.postman.com/downloads/)
- Git
  - [Si no lo tienes instalado puedes descargarlo dando click aqui](https://git-scm.com/download/win)


## Repositorio

Se recomienda tener una cuenta en Github para poder realizar un fork sobre el [repositorio](https://github.com/AlanKairosDS/msvc-login) y poder trabajar con el proyecto


## Base de Datos

En el microservicio se definen 2 bases de datos, una para que se pueda trabajar con las pruebas unitarias y la otra para hacer uso normal de la aplicacion.

Se deben de inicializar ambas bases de datos para poder utilizar los endpoint del microservicio.

Se debe de iniciar el servicio de MongoDB y entrar a la Shell para ejecutar los siguientes comandos:

Base de Datos Dev
- use msvc_login_dev
- db.roles.insertMany([ { name: "ROLE_USER" }, { name: "ROLE_MODERATOR" }, { name: "ROLE_ADMIN" }])

Base de Datos uso normal
- use msvc_login
- db.roles.insertMany([ { name: "ROLE_USER" }, { name: "ROLE_MODERATOR" }, { name: "ROLE_ADMIN" }])


## Docker

Para poder sacar la imagen del proyecto, primero debes de construirlo con Maven y una vez que se construye de forma exitosa debes de hacer lo siguiente:

- En una consola de git bash o power shell, de la raiz del proyecto debes moverte a la carpeta /login

cd ./login

- Una vez en esa carpeta debes de ejecutar el siguiente comando: 

docker build -t NOMBRE_IMAGEN .

- Una vez que se construyo la imagen, debes de ejecutar el siguiente comando para iniciar el contenedor:

docker run -p PUERTO:8002 --rm -d --name NOMBRE_CONTENEDOR NOMBRE_IMAGEN:latest


## Postman

Esta herramienta se utilizara para poder realizar pruebas sobre los endpoint del microservicio

Ejemplos:

http://localhost:9090/msvc-login/api/auth/registrar-usuario

{
    "username": "username",
    "email": "prueba@prueba.com",
    "password": "1234567890",
    "roles": [
        "admin",
        "mod",
        "user"
    ]
}


http://localhost:9090/msvc-login/api/auth/iniciar-sesion

{
    "username": "prueba",
    "password": "1234567890"
}
