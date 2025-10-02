# TaskManager 📝

TaskManager es una aplicación backend desarrollada con **Spring Boot** y
**MySQL**, que permite gestionar tareas de manera sencilla.\
Incluye operaciones CRUD (crear, leer, actualizar y eliminar) y está
diseñada para poder conectarse a distintos frontends (web, móvil o de
escritorio).

------------------------------------------------------------------------

## 🚀 Tecnologías utilizadas

-   **Java 17+**
-   **Spring Boot**
-   **Spring Data JPA**
-   **Hibernate**
-   **MySQL**
-   **Maven**
-   **Lombok**

------------------------------------------------------------------------

## 📂 Estructura del proyecto

    src/main/java/com/example/TaskManager/
    ├── controller/     -> Controladores REST (@RestController)
    ├── dto/            -> Clases DTO (Data Transfer Object)
    ├── model/          -> Entidades JPA (@Entity)
    ├── repository/     -> Interfaces Repository (extienden JpaRepository)
    ├── mapper/         -> Conversión Entity ↔ DTO
    └── TaskManagerApplication.java -> Clase principal

------------------------------------------------------------------------

## ⚙️ Configuración de la base de datos

El proyecto está configurado para usar **MySQL**.\
En el archivo `src/main/resources/application.properties` define tus
credenciales:

``` properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
spring.jpa.hibernate.ddl-auto=update
```

------------------------------------------------------------------------

## ▶️ Ejecución del proyecto

### Opción 1: Desde el IDE (IntelliJ, Eclipse, VS Code)

-   Abre el proyecto y ejecuta la clase `TaskManagerApplication.java`.

### Opción 2: Desde la terminal

``` bash
./mvnw spring-boot:run
```

La API quedará disponible en `http://localhost:8080/api/tareas`

------------------------------------------------------------------------

## 📌 Endpoints principales

  Método   Endpoint             Descripción
  -------- -------------------- ----------------------------
  GET      `/api/tareas`        Listar todas las tareas
  POST     `/api/tareas`        Crear nueva tarea
  GET      `/api/tareas/{id}`   Obtener tarea por ID
  PUT      `/api/tareas/{id}`   Actualizar tarea existente
  DELETE   `/api/tareas/{id}`   Eliminar tarea

Ejemplo de JSON para crear una tarea:

``` json
{
  "titulo": "Estudiar Spring Boot",
  "descripcion": "Repasar DTOs y validaciones",
  "prioridad": "Alta",
  "completada": false
}
```

------------------------------------------------------------------------

## ✅ Próximos pasos / Mejoras

-   Añadir autenticación con **Spring Security (JWT)**.
-   Documentación automática con **Swagger/OpenAPI**.
-   Tests unitarios con **JUnit y Mockito**.
-   Despliegue en servidor remoto o Docker.

------------------------------------------------------------------------

## 👤 Autor

**Jason Murray Carqué**\
📧 jasonmurraytrabajo@gmail.com\
📌 Proyecto desarrollado como práctica de **Spring Boot + MySQL**.
