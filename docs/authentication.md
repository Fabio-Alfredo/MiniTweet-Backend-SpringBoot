# 🔐 Autenticación en MiniTweet API

MiniTweet utiliza **Spring Security** junto con **JWT (JSON Web Tokens)** para autenticar a los usuarios y proteger los recursos de la API.

Así como hace uso de los roles y permisos para controlar el acceso a diferentes funcionalidades de la aplicación.

Esta configuración permite que solo los usuarios autenticados y con los permisos adecuados puedan acceder a ciertas rutas, realizar publicaciones o modificar datos.

---

## 🧠 ¿Cómo funciona?

1. 🔒 Un usuario se **registra** o inicia sesión con su email y contraseña.
2. 🧾 Si las credenciales son válidas, la API genera un **JWT**.
3. 📦 Este token se envía al cliente y debe guardarse localmente.
4. 🔐 Para acceder a rutas protegidas, el cliente debe enviar el token en el encabezado `Authorization`.
5. 🔍 La API valida el token y verifica los roles del usuario.
6. ✅ Si el token es válido y el usuario tiene los permisos necesarios, se permite el acceso a la ruta solicitada.

---

## 🛡️ ¿Qué protege Spring Security?

- Todas las rutas sensibles, como:  
  `/posts`, `/users`, `/comments`, etc.

- Verifica automáticamente si el token JWT:
  - Es válido
  - No ha expirado
  - Corresponde a un usuario real
  - Comprueba los roles y permisos del usuario para cada solicitud.
  - Permite el acceso solo a usuarios autenticados y autorizados.

---

## 🗂️ Dependencias clave

Tu proyecto utiliza las siguientes dependencias para manejar la seguridad:

```groovy
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'io.jsonwebtoken:jjwt-api:0.12.5'
implementation 'io.jsonwebtoken:jjwt-impl:0.12.5'
implementation 'io.jsonwebtoken:jjwt-jackson:0.12.5'
```

---

## 📜 Flujo de autenticación
1. **Registro de usuario**: El usuario envía sus datos (email, contraseña) a la API.
2. **Generación de token**: Si el registro es exitoso, se genera un JWT que contiene la información del usuario y sus roles.
3. **Almacenamiento del token**: El cliente almacena el token en el almacenamiento local (localStorage, sessionStorage, etc.).
4. **Acceso a rutas protegidas**: El cliente envía el token en el encabezado `Authorization` en cada solicitud a rutas protegidas.
5. **Validación del token**: La API verifica el token y extrae la información del usuario.
6. **Autorización**: La API comprueba los roles del usuario para determinar si tiene permiso para acceder a la ruta solicitada.
7. **Respuesta**: Si el token es válido y el usuario tiene los permisos necesarios, la API procesa la solicitud y devuelve la respuesta correspondiente.

```mermaid
sequenceDiagram
    participant Cliente
    participant API
    Cliente->>API: POST /auth/login (email, contraseña)
    API-->>Cliente: JWT Token
    Cliente->>API: GET /posts (con token en header)
    API-->>Cliente: Lista de posts (si el token es válido)
    Cliente->>API: POST /posts (con token en header)
    API-->>Cliente: Post creado (si el token es válido y tiene permisos)
```

---

## ✍️ Registro de usuarios
**metodo**: `POST`
**ruta**: `/auth/register`
Los usuarios pueden registrarse enviando una solicitud POST a `/auth/register` con los siguientes datos:

```json
{
    "username": "username",
    "email": "email@example.com",
    "password": "password"
}
```

### ✅ Respuesta exitosa
```json
HTTP/1.1 201 Created
{
    "message": "User registered successfully",
    "data": null
}
```

### ❌ Respuestas posibles errores

```json
HTTP/1.1 400 Bad Request
{
    "message": "Invalid input",
    "data": {
        "username": "Username is required",
        "email": "Email is required",
        "password": "Password is required"
    }
}
```

```json
HTTP/1.1 409 Conflict
{
    "message": "User already exists, username or email is already taken",
    "data": null
}
```

```json
HTTP/1.1 500 Internal Server Error
{
    "message": "An unexpected error occurred",
    "data": null
}
```

---

## 🔑 Inicio de sesión
**metodo**: `POST`
**ruta**: `/auth/login`
Los usuarios pueden iniciar sesión enviando una solicitud POST a `/auth/login` con los siguientes datos:
```json 
{
    "identifier":"hernandex2",
    "password":"12345"
}
```
### ✅ Respuesta exitosa
```json
HTTP/1.1 200 OK
{
    "message": "Login successful",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWJpbzJAZ21haWwuY29tIiwiaWF0IjoxNzUyNDYzMjI3LCJleHAiOjE3NTYwNjMyMjd9.XdcoWN0VWruCjcKJszMbQS9Njcfl8srNPkCLUnJqi08"
    }
}
```

### ❌ Respuestas posibles errores

```json
HTTP/1.1 400 Bad Request
{
    "message": "Invalid input",
    "data": {
        "identifier": "Identifier is required",
        "password": "Password is required"
    }
}
```

```json
HTTP/1.1 401 Unauthorized
{
    "message": "Invalid credentials",
    "data": null
}
```

```json
HTTP/1.1 500 Internal Server Error
{
    "message": "An unexpected error occurred",
    "data": null
}
```

---

## 🧪 Acceder a rutas protegidas
Para acceder a rutas protegidas, el cliente debe enviar el token JWT en el encabezado `Authorization` de la siguiente manera:

```http
Authorization: Bearer <token>
```

---


## 📆 Duración del token
Los tokens se configuran para expirar automáticamente después de cierto tiempo (ej. 24 horas).

Se define en application.properties
```properties
jwt.expiration=86400000 # 24 horas en milisegundos
```

---

## 🔐 Roles y permisos
MiniTweet usa control por roles para limitar el acceso:

| Rol   | Descripción                            |
| ----- | -------------------------------------- |
| USER  | Usuario normal, puede ver y publicar   |
| ADMIN | Acceso total: puede gestionar usuarios |

> Los roles son validados automáticamente por Spring Security con anotaciones como @PreAuthorize("hasRole('ADMIN')").

---

## 📌 Notas importantes
✅ Guarda el token localmente (por ejemplo, en localStorage si usas frontend).
❌ No compartas tu token ni lo expongas en URLs.
🔁 Si el token expira, el usuario debe volver a iniciar sesión.

---


### ¿Te gustaría ver cómo manejar errores de autenticación o cómo construir filtros personalizados?
➡️ Revisa la sección de [Manejo de Errores](error-handling.md)