# ❗ Manejo de Errores en MiniTweet API

MiniTweet cuenta con un sistema robusto de manejo de errores que garantiza que todas las respuestas ante fallos sean claras, estructuradas y útiles tanto para desarrolladores como para usuarios.

---

## 🧠 ¿Cómo se gestionan los errores?

La API captura y transforma las excepciones más comunes mediante una clase personalizada `GlobalErrorHandler`, anotada con `@ControllerAdvice`, que intercepta automáticamente las excepciones lanzadas por los controladores.

Esto permite centralizar el tratamiento de errores y devolver siempre una estructura de respuesta amigable y consistente.

---

## 📦 Estructura de la respuesta de error

Todas las respuestas de error siguen este formato:

```json
{
  "success": false,
  "status": 400,
  "message": "El campo 'email' es obligatorio"
}
```

- success: Indica si la operación fue exitosa (siempre false en errores).
- status: Código HTTP relacionado al error.
- message: Mensaje claro sobre lo que falló.

---

📚 Excepciones personalizadas y comunes

| Excepción capturada                     | Código HTTP                 | Usada para...                              |
| --------------------------------------- | --------------------------- | ------------------------------------------ |
| `NoResourceFoundException`              | `404 Not Found`             | Cuando un recurso no existe                |
| `MethodArgumentNotValidException`       | `400 Bad Request`           | Cuando los datos enviados no son válidos   |
| `IllegalArgumentException`              | `400 Bad Request`           | Argumento inválido en una operación        |
| `IllegalStateException`                 | `400 Bad Request`           | Estado ilegal o no permitido               |
| `HttpClientErrorException.Forbidden`    | `403 Forbidden`             | Acceso denegado (sin permisos suficientes) |
| `HttpClientErrorException.Unauthorized` | `401 Unauthorized`          | Usuario no autenticado                     |
| `InternalError`                         | `500 Internal Server Error` | Errores internos del servidor              |
| `Exception` (general)                   | `409 Conflict`              | Cualquier otro tipo de error no previsto   |

---

## 🛠️ Ejemplo de validación de datos

Cuando un campo obligatorio está vacío o con formato incorrecto:

```json
POST /api/auth/register
Content-Type: application/json

{
  "username": "",
  "email": "example@domain.com",
  "password": "123"
}

```

### 📉 Respuesta de error esperada

```json
HTTP/1.1 400 Bad Request
POST /api/auth/register
Content-Type: application/json
{
    "message": "Bad Request",
    "data": {
        "username": [
            "Username is required"
        ]
    }
}
```
Esto es posible gracias a:

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<GeneralResponse> BadRequestHandler(MethodArgumentNotValidException ex) {
    return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, errorTools.mapErrors(ex.getBindingResult().getFieldErrors()));
}
```

---

## 🔐 Errores de autorización
Cuando un usuario intenta acceder a un recurso protegido sin el token adecuado:

```http
GET /api/users
Authorization: Bearer <token inválido>
```
🔐 Respuesta:
```json
HTTP/1.1 401 Unauthorized
{
  "success": false,
  "status": 401,
  "message": "Token inválido o expirado"
}
```

---

## 🧱 Estructura interna del manejo de errores

### 📌 Clase personalizada: **HttpError**
```java
@Getter
public class HttpError extends RuntimeException {
    private final HttpStatus httpStatus;

    public HttpError(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
    }
}
```
> Permite lanzar errores personalizados desde cualquier parte del sistema.

### 🧠 Clase global: **GlobalErrorHandler**
```java
@ControllerAdvice
public class GlobalErrorHandler {
    ...
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GeneralResponse> IllegalArgumentHandler(IllegalArgumentException ex) {
        return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
//continúa con otros manejadores de excepciones...
```
> Este enfoque evita la repetición de lógica de manejo de errores y mejora la mantenibilidad del sistema.

---

## ✅ Buenas prácticas
- Siempre devuelve errores claros y específicos al cliente.
- Nunca expongas detalles internos (stacktrace o rutas del servidor).
- Mantén una estructura uniforme de respuesta ante errores.
- Usa códigos de estado HTTP correctos y significativos.
- Implementa excepciones personalizadas para errores comunes.
- Documenta los posibles errores en la API para que los desarrolladores sepan cómo manejarlos.


---
### ¿Listo para probar la API?
Para más detalles sobre cómo autenticarte y manejar errores, revisa la sección de [Autenticación](authentication.md) y [Endpoints](endpoints.md).



