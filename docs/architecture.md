# 🧱 Arquitectura del Sistema – MiniTweet API

MiniTweet API está desarrollada con una arquitectura **n-tier (multicapa)** basada en buenas prácticas de diseño con **Spring Boot**. Esta estructura favorece la **separación de responsabilidades**, la **escalabilidad** y el **mantenimiento limpio** del código.

MiniTweet API está desarrollada con una arquitectura **n-tier (multicapa)** basada en buenas prácticas de diseño con **Spring Boot**. Esta estructura favorece la **separación de responsabilidades**, la escalabilidad y el mantenimiento limpio del código. Además, se hace uso de **Programación Orientada a Objetos (POO)** para modelar entidades y comportamientos del dominio de forma **clara y reutilizable**.

---

## 🗂️ Capas principales

MiniTweet se organiza en **cuatro capas principales**, cada una con una responsabilidad específica:

### 1️⃣ Capa de Controladores (`controllers/`)

- 📍 **Responsabilidad:** Maneja las solicitudes HTTP entrantes.
- 💬 Interactúa directamente con el usuario o cliente (frontend o app móvil).
- 🧠 Delegan la lógica a los servicios.
- 🚫 No contiene lógica de negocio.

```java
    @PostMapping("/register")
    public ResponseEntity<GeneralResponse>registerUser(@RequestBody @Valid RegisterUserDto userDto){
        try{
            userService.registerUser(userDto);

            return GeneralResponse.getResponse(HttpStatus.CREATED, "User registered successfully");
        }catch (HttpError e){
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }
```

### 2️⃣ Capa de Servicios (`services/`)

- 🛠️ **Responsabilidad:** Contiene la lógica de negocio.
- 🔄 Interactúa con los repositorios para acceder a la base de datos.
- 📦 Agrupa funcionalidades relacionadas (usuarios, publicaciones, comentarios).
- 🔐 Puede aplicar reglas de validación o seguridad.

```java
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean isTokenValid(User user, String token) {
        try {
            cleanToken(user);
            List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
            tokens.stream()
                    .map(t -> t.getContent().equals(token))
                    .findAny()
                    .orElseThrow(() -> new HttpError(HttpStatus.UNAUTHORIZED, "Token not valid"));
            return true;
        } catch (HttpError e) {
            throw e;
        }
    }

```
### 3️⃣ Capa de Repositorios (`repositories/`)
- 🗄️ **Responsabilidad:** Interactúa directamente con la base de datos.
- 🔍 Utiliza **Spring Data JPA** para realizar consultas.
- 📑 Peticiones con Hibernate Query Language (HQL).
- 💾 Realiza operaciones CRUD sobre las entidades.

```java
@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment>findAllByPost(Post post);
    Comment findByIdAndAuthor(UUID id, User authorId);
}
```

### 4️⃣ Capa de Entidades (`domain/`)
- 🏷️ **Responsabilidad:** Define las entidades del dominio.
- 🧩 Modela los objetos del negocio (usuarios, publicaciones, comentarios).
- 🔗 Utiliza anotaciones JPA para mapear a la base de datos.

```java

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    private String id;
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;
}
```
## 🛠️ Otras capas y componentes importantes

### 🧾 DTOs (Data Transfer Objects)
- 📦 Utilizados para transferir datos entre capas.
- 📝 Definen la estructura de los datos que se envían o reciben.
- 🚫 No contienen lógica de negocio.

### 🔒 Seguridad y Autenticación (Spring Security y JWT)
- 🔐 Validacion de roles y permisos.
- 🛡️ Protege los endpoints con roles y permisos.
- 📜 Configura filtros de seguridad para validar tokens.
- 🗝️ Utiliza un `UserDetailsService` para cargar usuarios desde la base de datos.
- 🔑 Genera y valida tokens JWT para acceso seguro.

### ☁️ Integración con Cloudinary
- 🌥️ Maneja la carga y gestión de imágenes.
- 📸 Utiliza la API de Cloudinary para almacenar imágenes.
- 🔗 Configuración en `application.properties` para acceder a las credenciales de Cloudinary


### 🛠️ Manejador Global de Errores
- 📦 Utiliza un `@ControllerAdvice` para manejar excepciones de forma centralizada.
- 🔍 Captura excepciones específicas y devuelve respuestas adecuadas.
- 🛡️ Protege la aplicación de filtraciones de información sensible.

```java
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(HttpError.class)
    public ResponseEntity<GeneralResponse> handleHttpError(HttpError e) {
        return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralResponse> handleGenericError(Exception e) {
        return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }
}
```

### 🛠️ Excepción Personalizada `HttpError`
- 📦 Extiende de `RuntimeException`.
- 🔍 Permite manejar errores HTTP de forma consistente.
- 🛡️ Incluye información sobre el estado y el mensaje del error.
- 📜 Facilita la creación de respuestas de error personalizadas.

```java
@Getter
public class HttpError extends RuntimeException {

    private final HttpStatus httpStatus;

    public HttpError(HttpStatus httpStatus,  String message){
        super(message);
        this.httpStatus = httpStatus;
    }
}
```

## 🧩 Resumen de la Arquitectura
MiniTweet API sigue una arquitectura limpia y modular, donde cada capa tiene una responsabilidad clara. Esto permite un desarrollo ágil, pruebas unitarias efectivas y una fácil escalabilidad. La separación de preocupaciones facilita el mantenimiento y la evolución del sistema a lo largo del tiempo.
Esta estructura modular también permite que diferentes equipos trabajen en paralelo en distintas capas sin interferencias, lo que mejora la productividad y la calidad del código.

---

## 🔄 Flujo de una petición
```css
[Cliente (Postman / App / Frontend)]
        ↓
[Controller] → [Service] → [Repository] → [Base de datos]
        ↓
[Response al Cliente]
```
🛡️ Las respuestas siguen este camino en sentido inverso.

---

## ✨ Ventajas de esta arquitectura
- 🔍 Claridad: Cada capa tiene una única responsabilidad.
- ♻️ Reutilización: Lógica de negocio centralizada y reutilizable.
- 🔒 Seguridad: Filtros y capas de autenticación aisladas.
- 🚀 Escalabilidad: Puedes agregar nuevas funcionalidades sin afectar otras capas.
- 🧪 Testabilidad: Fácil de probar cada capa de forma independiente.

---

📌 Para más detalles sobre la implementación y el uso de MiniTweet API, consulta la sección [Guía de Inicio Rápido](getting-started.md).

📚 ¿ Te interesa la seguridad del sistema? Consulta la sección [Autenticación](authentication.md).