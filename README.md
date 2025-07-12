# ✉️MiniTweet API

MiniTweet es una solucion diseñada para entreter y relacionar a las personas por medio de una red social, donde los usuario pueden publicar sus post, en forma de pensamientos o momentos que deseen compartir con imagees o mensajes positivos, ademas permite crear una red de amigos mediante los cuales se pueden compartir comentarios o likes en las publicaciones.

Esta solucion permite el control de los usuario, el control de los post y la privacidad de los mismos, donde las personas deciden quienes pueden ver las publicaciones y quienes no.

Con el fin de garantizar la seguridad y el control de los accesso a la palicacion se cuenta con autenticacion mediante tokens y proteccion de ruta en base a roles asi como porteccion de publicaciones y usuario en base a followers, permitiendo un mejor control y una mejor experiencia dentro del sistema.

## 📦 Tecnologías Utilizadas

- ✅ **Spring Boot** - Framework principal
- 🔐 **Spring Security** - Seguridad y autenticación
- 💾 **Spring Data JPA** - Persistencia de datos
- 🐘 **PostgreSQL** - Base de datos relacional
- 🔐 **JJWT** - JSON Web Tokens para login seguro
- 🧰 **Lombok** - Reduce la verbosidad del código
- 🔁 **ModelMapper** - Mapeo entre entidades y DTOs
- ☁️ **Cloudinary** - Almacenamiento de imágenes

## ✅ Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- [Java 17+](https://adoptium.net/)
- [Gradle](https://gradle.org/)
- [PostgreSQL](https://www.postgresql.org/download/)

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/your-username/minitweet.git
```

### 2. Dirigirse al directorio del proyecto:

```bash
cd minitweet
```

### 3. Intalacion de dependencias

```bash
./gradlew build
```

### 4. Configuracion de variables de entorno

Crea un archivo _application.properties_ en src/main/resources:

```bash
# PostgresSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/your-database
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=your-jwt-secret
jwt.expiration=86400000

# Cloudinary
cloudinary.cloud_name=your-cloud-name
cloudinary.api_key=your-api-key
cloudinary.api_secret=your-api-secret
```

🛠️ Asegúrate de reemplazar los valores por los de tu entorno local

## ▶️ Como ejecutar la aplicacion

Inicia el servidor con :

```bash
./gradlew bootRun
```

📍 La aplicación estará disponible en http://localhost:8080.

## 🧪 Pruebas con Postman o ThunderClient

Puedes hacer pruebas utilizando herramientas como Postman o la extensión Thunder Client en VSCode.
No olvides incluir el token JWT en las peticiones protegidas con:

```bash
Authorization: Bearer <tu-token-jwt>
```

## 📌 Endpoints disponibles

Los principales endpoints de la aplicacion se detallas a continuacion

### Endpoinst para autenticacion

#### Registro de usuario

- **Method:** `POST`
- **Path:** `/api/auth/register`
- **Description:** `Este endpoint permite a los usuario poder registrarse dentro de la aplicacion con sus nuevas credenciales`
- **Auth Required:** `No`

#### Ejemplo de solicitud

```json
{
  "username": "testuser",
  "email": "testuser@example.com",
  "password": "password123"
}
```

#### Ejemplo de respuesta

Success Response (201 CREATED):

```json
{
  "status": "CREATED",
  "message": "User registered successfully",
  "data": null
}
```

Error Response (400 BAD REQUEST):

```json
{
  "status": "BAD_REQUEST",
  "message": "Username already exists",
  "data": null
}
```

---

#### Login de usuario

- **Method:** `POST`
- **Path:** `/api/auth/login`
- **Description:** `Endpoint permite a los usuario poder inciar sesion proporcionando sus credenciales. Al inciar sesion se genera un token unico para acceder a diferentes rutas`
- **Auth Required:** `No`

#### Ejemplo de solicitud

```json
{
  "identifier": "testuser",
  "password": "password123"
}
```

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "Login successful",
  "data": {
    "token": "your-jwt-token"
  }
}
```

Error Response (401 UNAUTHORIZED):

```json
{
  "status": "UNAUTHORIZED",
  "message": "Invalid credentials",
  "data": null
}
```

---

### Endpoints para comentarios

#### Crear comentario

- **Method:** `POST`
- **Path:** `/api/comments/create`
- **Description:** `Este endpoint permite a los usuario crear un comentario en una publicacion especifica`
- **Auth Required:** `Yes, es necesario estar autenticado para crear un comentario y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de solicitud

```json
{
  "postId": "post-id",
  "content": "This is a comment."
}
```

#### Ejemplo de respuesta

Success Response (201 CREATED):

```json
{
  "status": "CREATED",
  "message": "Comment created successfully",
  "data": null
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "Post not found",
  "data": null
}
```

---

#### Encontrar todos los comentarios de un post

- **Method:** `GET`
- **Path:** `/api/comments/all/{postId}`
- **Description:** `Este endpoint permite a los usuario encontrar todos los comentarios de una publicacion especifica`
- **Auth Required:** `Yes, es necesario estar autenticado para acceder a los comentarios y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "success",
  "data": [
    {
      "id": "comment-id",
      "content": "This is a comment.",
      "createdAt": "2023-10-27T10:00:00.000Z",
      "user": {
        "id": "user-id",
        "username": "testuser"
      }
    }
  ]
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "Post not found",
  "data": null
}
```

---

#### Editar un comentario

- **Method:** `PUT`
- **Path:** `/api/comments/update`
- **Description:** `Updates an existing comment.`
- **Auth Required:** `Yes, es necesario estar autenticado para editar un comentario y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de solicitud

```json
{
  "commentId": "comment-id",
  "content": "This is an updated comment."
}
```

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "Comment updated successfully",
  "data": null
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "Comment not found",
  "data": null
}
```

---

### Endpoints para seguidores

#### Seguir a un usuario

- **Method:** `POST`
- **Path:** `/api/follower/follow/{followedId}`
- **Description:** `Este endpoint permite a un usuario seguir a otro usuario especifico`
- **Auth Required:** `Yes, es necesario estar autenticado para seguir a un usuario y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "Followed user successfully",
  "data": null
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "User not found",
  "data": null
}
```

---

#### Encontrar seguidores de un usuario

- **Method:** `GET`
- **Path:** `/api/follower/followers/{userId}`
- **Description:** `Encuentra todos los seguidores de un usuario específico.`
- **Auth Required:** `Yes, es necesario estar autenticado para acceder a los seguidores y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "success",
  "data": [
    {
      "id": "user-id",
      "username": "followeruser"
    }
  ]
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "User not found",
  "data": null
}
```

---

#### Encontrar usuarios que un usuario está siguiendo

- **Method:** `GET`
- **Path:** `/api/follower/followed/{userId}`
- **Description:** `Encuentra todos los usuarios que un usuario específico está siguiendo.`
- **Auth Required:** `Yes, es necesario estar autenticado para acceder a esta información y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de respuesta

```json
{
  "status": "OK",
  "message": "success",
  "data": [
    {
      "id": "user-id",
      "username": "followeduser"
    }
  ]
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "User not found",
  "data": null
}
```

---

### Endpoints para publicaciones

#### Crear una nueva publicación

- **Method:** `POST`
- **Path:** `/api/posts/create`
- **Description:** `Creates a new post.`
- **Auth Required:** `Yes, es necesario estar autenticado para crear una publicación y debe enviar el token JWT en la cabecera de la solicitud.`
- **Request Body (form-data):**
  - content: este es un nuevo post.
  - image: (optional) image file

#### Ejemplo de solicitud

```json
{
  "content": "This is a new post.",
  "image": "image-file.jpg"
}
```

#### Ejemplo de respuesta

success Response (201 CREATED):

```json
{
  "status": "CREATED",
  "message": "Post created successfully",
  "data": {
    "id": "post-id",
    "content": "This is a new post.",
    "imageUrl": "http://example.com/image.jpg",
    "createdAt": "2023-10-27T10:00:00.000Z",
    "likes": 0,
    "comments": 0,
    "user": {
      "id": "user-id",
      "username": "testuser"
    }
  }
}
```

Error Response (400 BAD REQUEST):

```json
{
  "status": "BAD_REQUEST",
  "message": "Content is required",
  "data": null
}
```

---

#### Encontrar publicaciones por usuario

- **Method:** `GET`
- **Path:** `/api/posts/{userId}/all`
- **Description:** `Encuentra todas las publicaciones de un usuario específico.`
- **Auth Required:** `Yes, es necesario estar autenticado para acceder a las publicaciones y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "success",
  "data": [
    {
      "id": "post-id",
      "content": "This is a post.",
      "imageUrl": "http://example.com/image.jpg",
      "createdAt": "2023-10-27T10:00:00.000Z",
      "likes": 0,
      "comments": 0,
      "user": {
        "id": "user-id",
        "username": "testuser"
      }
    }
  ]
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "User not found",
  "data": null
}
```

---

#### Encontrar todas las publicaciones

- **Method:** `GET`
- **Path:** `/api/posts/all`
- **Description:** `Encuentra todas las publicaciones de los usuarios que sigo.`
- **Auth Required:** `Yes, es necesario estar autenticado para acceder a las publicaciones y debe enviar el token JWT en la cabecera de la solicitud, ya que este endpoint requiere roles de administrador.`

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "success",
  "data": [
    {
      "id": "post-id",
      "content": "This is a post.",
      "imageUrl": "http://example.com/image.jpg",
      "createdAt": "2023-10-27T10:00:00.000Z",
      "likes": 0,
      "comments": 0,
      "user": {
        "id": "user-id",
        "username": "testuser"
      }
    }
  ]
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "No posts found",
  "data": null
}
```

---

#### Encontrar publicación por ID

- **Method:** `GET`
- **Path:** `/api/posts/by-id/{postId}`
- **Description:** `Encuentra una publicación por su ID.`
- **Auth Required:** `Yes, es necesario estar autenticado para acceder a la publicación y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de respuesta

```json
{
  "status": "OK",
  "message": "success",
  "data": {
    "id": "post-id",
    "content": "This is a post.",
    "imageUrl": "http://example.com/image.jpg",
    "createdAt": "2023-10-27T10:00:00.000Z",
    "likes": 0,
    "comments": 0,
    "user": {
      "id": "user-id",
      "username": "testuser"
    }
  }
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "Post not found",
  "data": null
}
```

---

#### Eliminar una publicación

- **Method:** `DELETE`
- **Path:** `/api/posts/delete/{postId}`
- **Description:** `Elimina una publicación por su ID.`
- **Auth Required:** `Yes, es necesario estar autenticado para acceder a la publicación y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "Post deleted successfully",
  "data": null
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "Post not found",
  "data": null
}
```

---

#### Like a post

- **Method:** `POST`
- **Path:** `/api/posts/like/{postId}`
- **Description:** `Likes a post.`
- **Auth Required:** `Yes, es necesario estar autenticado para acceder a la publicación y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "Post liked successfully",
  "data": null
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "Post not found",
  "data": null
}
```

---

#### buscar publicaciones de usuarios que sigo

- **Method:** `GET`
- **Path:** `/api/posts/following/all`
- **Description:** `Encuentra todas las publicaciones de los usuarios que sigo.`
- **Auth Required:** `Yes, es necesario estar autenticado para acceder a las publicaciones y debe enviar el token JWT en la cabecera de la solicitud.`
- **Query Parameters:**
  - `createAt`: (optional) date-time to fetch posts from
  - `size`: (optional) number of posts to fetch

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "success",
  "data": [
    {
      "id": "post-id",
      "content": "This is a post from a followed user.",
      "imageUrl": "http://example.com/image.jpg",
      "createdAt": "2023-10-27T10:00:00.000Z",
      "likes": 0,
      "comments": 0,
      "user": {
        "id": "followed-user-id",
        "username": "followeduser"
      }
    }
  ]
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "No posts found",
  "data": null
}
```

---

#### Actualizar una publicación

- **Method:** `PUT`
- **Path:** `/api/posts/update/{postId}`
- **Description:** `Este endpoint permite actualizar una publicación existente.`
- **Auth Required:** `Yes, es necesario estar autenticado para actualizar una publicación y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de solicitud

```json
{
  "content": "This is an updated post.",
  "image": "image-file.jpg"
}
```

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "Post updated successfully",
  "data": {
    "id": "post-id",
    "content": "This is an updated post.",
    "imageUrl": "http://example.com/image.jpg",
    "createdAt": "2023-10-27T10:00:00.000Z",
    "likes": 0,
    "comments": 0,
    "user": {
      "id": "user-id",
      "username": "testuser"
    }
  }
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "Post not found",
  "data": null
}
```

---

### Endpoints para usuarios

#### Obtener información del usuario

- **Method:** `GET`
- **Path:** `/api/user/me`
- **Description:** `Obtiene la información del usuario autenticado.`
- **Auth Required:** `Yes, es necesario estar autenticado para acceder a la información del usuario y debe enviar el token JWT en la cabecera de la solicitud.`

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "success",
  "data": {
    "id": "user-id",
    "username": "testuser",
    "email": "testuser@example.com",
    "roles": ["USER"]
  }
}
```

Error Response (401 UNAUTHORIZED):

```json
{
  "status": "UNAUTHORIZED",
  "message": "Authentication required",
  "data": null
}
```

---

### Obtener todos los usuarios

- **Method:** `GET`
- **Path:** `/api/user/all`
- **Description:** `Obtiene todos los usuarios (solo para administradores).`
- **Auth Required:** `Yes, es necesario estar autenticado como administrador para acceder a esta información y debe enviar el token JWT en la cabecera de la solicitud, ya que este endpoint requiere roles de administrador.`

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "success",
  "data": [
    {
      "id": "user-id",
      "username": "testuser",
      "email": "testuser@example.com",
      "roles": ["USER"]
    }
  ]
}
```

Error Response (403 FORBIDDEN):

```json
{
  "status": "FORBIDDEN",
  "message": "Access denied",
  "data": null
}
```

---

#### Obtener usuario por ID

- **Method:** `GET`
- **Path:** `/api/user/by-id/{id}`
- **Description:** `Obtiene un usuario por su ID.`
- **Auth Required:** `Yes, es necesario estar autenticado para acceder a la información del usuario y debe enviar el token JWT en la cabecera de la solicitud.`
- **Path Parameters:**
  - `id`: ID del usuario a buscar

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "success",
  "data": {
    "id": "user-id",
    "username": "testuser",
    "email": "testuser@example.com",
    "roles": ["USER"]
  }
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "User not found",
  "data": null
}
```

---

#### Actualizar roles de un usuario

- **Method:** `PUT`
- **Path:** `/api/user/update/{userId}`
- **Description:** `Actualiza el rol de un usuario (solo para administradores).`
- **Auth Required:** `Yes, es necesario estar autenticado como administrador para acceder a esta información y debe enviar el token JWT en la cabecera de la solicitud, ya que este endpoint requiere roles de administrador.`

#### Ejemplo de solicitud

```json
{
  "roles": ["ADMIN"]
}
```

#### Ejemplo de respuesta

Success Response (200 OK):

```json
{
  "status": "OK",
  "message": "success",
  "data": null
}
```

Error Response (404 NOT FOUND):

```json
{
  "status": "NOT_FOUND",
  "message": "User not found",
  "data": null
}
```
