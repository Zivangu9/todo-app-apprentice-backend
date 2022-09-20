# To Do App: back-end

Este repo contiene el back-end de la aplicación To Do.

El back-end del sistema consiste en RESTFul API que están construidos utilizando Java y las siguientes herramientas:
- Spring boot: Framework del lado del servidor.
- Maven: Herramienta de gestión y construcción de proyectos.

El objetivo principal es servir peticiones a través de URL, por lo tanto esta aplicación no contiene vistas ni archivos de interfaz de usuario. A través de URL es como se accede a recursos y la aplicación va a responder con una 
representación en JSON dependiendo del recurso solicitado.

## Pre-requisitos
Es necesario tener instalado: 
- Java 11
- Maven 

## Instalación de la aplicación

### 1. Clonar el repositorio

```
git clone https://github.com/Zivangu9/todo-app-apprentice-backend
```
### 2. Moverse a la raíz del proyecto
```
cd todo-app-apprentice-backend
```

### 3. Construir el proyecto de Spring e instalar dependencias

```
 mvn install / mvn clean install
```

### 4. Correr la aplicación

```
 mvn spring-boot:run
```

Si todo está corriendo bien en la consola se podrá ver el logo de spring y la aplicación estará corriendo en el puerto 9090.

```
 http://localhost:9090
```

## Arquitectura del sistema

- **Controller**: es responsable de recibir y procesar peticiones REST, llama al servicio correspondiente y regresa una respuesta en forma de JSON.
    
- **Service**: es responsable de hacer la lógica de negocio, es donde valido los datos entrantes si es necesario arroja excepciones. Si todo está bien entonces manda a llamar al repository.
  
- **Repository**: es responsable de la persistencia de los datos, para este proyecto se almacenan los datos en memoria usando Java Collections

## Estructura del proyecto
```
src
├── main
│   ├── java
│   │   └── apprentice
│   │       └── ivan
│   │           └── todoappbackend
│   │               ├── controllers
│   │               ├── models
│   │               ├── repositories
│   │               └── services
│   └── resources
└── test
    └── java
        └── apprentice
            └── ivan
                └── todoappbackend
```
### Descripción de los paquetes
- **Controllers**: Todos los controladores del sistema.
- **Models**: Entidades del sistema.
- **Repositories**: Todos los repositorios del sistema.
- **Services**: Todos los servicios del sistema.