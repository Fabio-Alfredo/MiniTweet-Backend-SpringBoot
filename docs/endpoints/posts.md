# 📝 Endpoints de Publicaciones (Posts)

La API de MiniTweet permite a los usuarios crear, ver, editar, eliminar y dar like a publicaciones conocidas como "posts". A continuación, te presentamos todos los endpoints disponibles relacionados con este recurso.

---

## ➕ Crear una publicación

**`POST /api/posts/create`**

Crea una nueva publicación disponible para todos los usuarios, dicha publicación puede contener texto y una imagen opcional.

### 🔐 Requiere autenticación

**Sí** Se require un token JWT válido.

### 📤 Body (form-data)

| Campo   | Tipo    | Requerido | Descripción                       |
| ------- | ------- | --------- | --------------------------------- |
| `text`  | string  | Opcional  | Mensaje o pensamiento a compartir |
| `image` | archivo | Opcional  | Imagen relacionada al post        |

### ✅ Ejemplo de respuesta

```json
HTTP/1.1 201 Created

{
    "message": "Post created successfully",
    "data": {
        "id": "1fe4cca5-2f75-42eb-8731-873ae5338c2e",
        "content": "2",
        "image": "https://res.cloudinary.com/dosctrwix/image/upload/v1/posts/koedzuqwdfv30cusmch4",
        "createdAt": "2025-07-15T00:24:54.124+00:00",
        "likedBy": null,
        "author": {
            "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
            "username": "hernandex2",
            "email": "fabio2@gmail.com",
            "biography": null,
            "profilePicture": null
        }
    }
}
```

### ⚠️ Notas

- El campo `text` es opcional, pero si se deja vacío, el post no será creado.
- La imagen debe ser un archivo válido y no exceder el tamaño máximo permitido por la API.
- El campo `likedBy` se inicializa como `[]` y se actualizará cuando los usuarios den like a la publicación.

---

## 👀 Ver todas las publicaciones de un usuario

**`GET /api/posts/{userId}/all`**

Devuelve todos los posts públicos del usuario o todos si es el mismo usuario autenticado.

### 🔐 Requiere autenticación

**Sí** Se requiere un token JWT válido.

### 📄 Parámetros de ruta

| Campo    | Tipo | Descripción                                          |
| -------- | ---- | ---------------------------------------------------- |
| `userId` | UUID | ID del usuario cuyas publicaciones se desean obtener |

### ✅ Ejemplo de respuesta

```json
HTTP/1.1 200 OK
{
    "message": "success",
    "data": [
        {
            "id": "1fe4cca5-2f75-42eb-8731-873ae5338c2e",
            "content": "2",
            "image": "https://res.cloudinary.com/dosctrwix/image/upload/v1/posts/koedzuqwdfv30cusmch4",
            "createdAt": "2025-07-15T00:24:54.124+00:00",
            "likedBy": [],
            "author": {
                "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
                "username": "hernandex2",
                "email": "fabio2@gmail.com",
                "biography": null,
                "profilePicture": null
            }
        },
        {
            "id": "03bde210-dc3b-400b-ac54-dfb7f17a4269",
            "content": "2",
            "image": null,
            "createdAt": "2025-07-15T00:25:43.018+00:00",
            "likedBy": [],
            "author": {
                "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
                "username": "hernandex2",
                "email": "fabio2@gmail.com",
                "biography": null,
                "profilePicture": null
            }
        }
    ]
}
```

### ⚠️ Notas

- Si el usuario no tiene publicaciones, se devolverá un array vacío.
- Si el usuario autenticado es el mismo que el `userId`, se devolverán todas sus publicaciones, independientemente de su visibilidad.

---

## 📚 Ver todos los posts (admin)

**`GET /api/posts/all`**

Muestra todas las publicaciones registradas dentro del sistema (solo para administradores).

### 🔐 Requiere autenticación

**Sí** Se requiere un token JWT válido con rol de administrador.

### ✅ Ejemplo de respuesta

```json
HTTP/1.1 200 OK
{
    "message": "success",
    "data": [
        {
            "id": "1fe4cca5-2f75-42eb-8731-873ae5338c2e",
            "content": "2",
            "image": "https://res.cloudinary.com/dosctrwix/image/upload/v1/posts/koedzuqwdfv30cusmch4",
            "createdAt": "2025-07-15T00:24:54.124+00:00",
            "author": {
                "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
                "username": "hernandex2",
                "email": "fabio2@gmail.com",
                "password": "$2a$10$mu4K78j.02JOnRaVD4lSqeSan2nd3I7.vKH31G7MYbNiopgHcf3jK",
                "biography": null,
                "profilePicture": null,
                "tokens": [
                    {
                        "code": "a306b1b4-fa3d-465e-a76a-2dad504df965",
                        "content": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWJpbzJAZ21haWwuY29tIiwiaWF0IjoxNzUyNDYzMjI3LCJleHAiOjE3NTYwNjMyMjd9.XdcoWN0VWruCjcKJszMbQS9Njcfl8srNPkCLUnJqi08",
                        "timestamp": "2025-07-14T03:20:27.116+00:00",
                        "active": true
                    },
                    {
                        "code": "34ae56a5-3e91-47e9-a219-8dad2ddaf50d",
                        "content": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWJpbzJAZ21haWwuY29tIiwiaWF0IjoxNzUyNTM5MDc5LCJleHAiOjE3NTYxMzkwNzl9.GyFyk8jpRBdQgz9t3CmGDSeOfVwp6X1ChhnipotZu44",
                        "timestamp": "2025-07-15T00:24:39.247+00:00",
                        "active": true
                    }
                ],
                "roles": [
                    {
                        "id": "USER",
                        "name": "usuario"
                    }
                ],
                "authorities": [
                    {
                        "authority": "USER"
                    }
                ],
                "accountNonExpired": false,
                "accountNonLocked": false,
                "credentialsNonExpired": false,
                "enabled": true
            },
            "likedBy": []
        },
        {
            "id": "03bde210-dc3b-400b-ac54-dfb7f17a4269",
            "content": "2",
            "image": null,
            "createdAt": "2025-07-15T00:25:43.018+00:00",
            "author": {
                "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
                "username": "hernandex2",
                "email": "fabio2@gmail.com",
                "password": "$2a$10$mu4K78j.02JOnRaVD4lSqeSan2nd3I7.vKH31G7MYbNiopgHcf3jK",
                "biography": null,
                "profilePicture": null,
                "tokens": [
                    {
                        "code": "a306b1b4-fa3d-465e-a76a-2dad504df965",
                        "content": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWJpbzJAZ21haWwuY29tIiwiaWF0IjoxNzUyNDYzMjI3LCJleHAiOjE3NTYwNjMyMjd9.XdcoWN0VWruCjcKJszMbQS9Njcfl8srNPkCLUnJqi08",
                        "timestamp": "2025-07-14T03:20:27.116+00:00",
                        "active": true
                    },
                    {
                        "code": "34ae56a5-3e91-47e9-a219-8dad2ddaf50d",
                        "content": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWJpbzJAZ21haWwuY29tIiwiaWF0IjoxNzUyNTM5MDc5LCJleHAiOjE3NTYxMzkwNzl9.GyFyk8jpRBdQgz9t3CmGDSeOfVwp6X1ChhnipotZu44",
                        "timestamp": "2025-07-15T00:24:39.247+00:00",
                        "active": true
                    }
                ],
                "roles": [
                    {
                        "id": "USER",
                        "name": "usuario"
                    }
                ],
                "authorities": [
                    {
                        "authority": "USER"
                    }
                ],
                "accountNonExpired": false,
                "accountNonLocked": false,
                "credentialsNonExpired": false,
                "enabled": true
            },
            "likedBy": []
        }
    ]
}
```

### ⚠️ Notas

- Este endpoint es exclusivo para administradores y requiere un token JWT con los permisos adecuados.
- La respuesta incluye todos los posts, independientemente de su autor o contenido.

---

## 🔎 Buscar publicación por ID

**`GET /api/posts/by-id/{postId}`**
Permite obtener una publicacion especifica a partir de su ID.

### 🔐 Requiere autenticación

**Sí** Se requiere un token JWT válido.

### 📄 Parámetros de ruta

| Campo    | Tipo | Descripción                               |
| -------- | ---- | ----------------------------------------- |
| `postId` | UUID | ID de la publicación que se desea obtener |

### ✅ Ejemplo de respuesta

```json
HTTP/1.1 200 OK
{
    "message": "success",
    "data": {
        "id": "03bde210-dc3b-400b-ac54-dfb7f17a4269",
        "content": "2",
        "image": null,
        "createdAt": "2025-07-15T00:25:43.018+00:00",
        "likedBy": [],
        "author": {
            "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
            "username": "hernandex2",
            "email": "fabio2@gmail.com",
            "biography": null,
            "profilePicture": null
        }
    }
}
```

### ⚠️ Notas

- Si el post no existe, se devolverá un error 404.
- El usuario debe tener permisos para ver la publicación, de lo contrario se devolverá un error 403.

---

## ❌ Eliminar una publicación

**`DELETE /api/posts/delete/{postId}`**
Este endpoint permite a un usuario eliminar una publicación específica creada por él.

### 🔐 Requiere autenticación

**Sí** Se requiere un token JWT válido.

### 📄 Parámetros de ruta

| Campo    | Tipo | Descripción                                |
| -------- | ---- | ------------------------------------------ |
| `postId` | UUID | ID de la publicación que se desea eliminar |

### ✅ Ejemplo de respuesta

```json
HTTP/1.1 200 OK
{
    "message": "Post deleted successfully",
    "data": null
}
```

### ⚠️ Notas

- Si el post no existe, se devolverá un error 404.
- El usuario debe ser el autor de la publicación para poder eliminarla, de lo contrario se devolverá un error 403.

---

## ❤️ Dar like a una publicación

**`POST /api/posts/like/{postId}`**
Permite a un usuario dar like a una publicación específica y si ya le dio like, se lo quita.

### 🔐 Requiere autenticación

**Sí** Se requiere un token JWT válido.

### 📄 Parámetros de ruta

| Campo    | Tipo | Descripción                               |
| -------- | ---- | ----------------------------------------- |
| `postId` | UUID | ID de la publicación a la que se desea dar like |

### ✅ Ejemplo de respuesta

```json
HTTP/1.1 200 OK
{
    "message": "Post liked successfully",
    "data": null
}
```

### ⚠️ Notas

- Si el post no existe, se devolverá un error 404.
- El usuario debe estar autenticado para poder dar like a una publicación, de lo contrario se devolverá un error 403.
- Si el usuario ya había dado like a la publicación, este se eliminará de la lista de likes.
- La lista de usuarios que han dado like a la publicación se actualizará automáticamente.

---

## 👥 Ver posts de personas que sigues

**`GET /api/posts/following/all`**
Devuelve las publicaciones de los usuarios que el usuario autenticado sigue.

### 🔐 Requiere autenticación
**Sí** Se requiere un token JWT válido.

### 📄 Parámetros de ruta
| Campo     | Tipo | Descripción                      |
| --------- | ---- | -------------------------------- |
| `createAt` | Date | Fecha para paginación (ISO 8601) |
| `size`     | int  | Cantidad de posts a devolver     |

### ✅ Ejemplo de respuesta

```json
HTTP/1.1 200 OK
{
    "message": "success",
    "data": [
        {
            "id": "1fe4cca5-2f75-42eb-8731-873ae5338c2e",
            "content": "2",
            "image": "https://res.cloudinary.com/dosctrwix/image/upload/v1/posts/koedzuqwdfv30cusmch4",
            "createdAt": "2025-07-15T00:24:54.124+00:00",
            "likedBy": [],
            "author": {
                "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
                "username": "hernandex2",
                "email": "fabio2@gmail.com",
                "biography": null,
                "profilePicture": null
            }
        }
    ]
}
```
### ⚠️ Notas
- Si el usuario no sigue a nadie, se devolverá un array vacío.
- Si no hay publicaciones recientes, se devolverá un array vacío.
- Este endpoint es útil para ver las publicaciones más recientes de las personas que sigues.

---

## ✏️ Editar una publicación
**`PUT /api/posts/update/{postId}`**

Permite al usuario actualizar el texto o imagen de una publicación existente.

### 🔐 Requiere autenticación
**Sí** Se requiere un token JWT válido.

###  📤 Body (form-data)

| Campo   | Tipo    | Requerido | Descripción                        |
| ------- | ------- | --------- | ---------------------------------- |
| `text`  | string  | Opcional  | Nuevo contenido del post           |
| `image` | archivo | Opcional  | Nueva imagen (si se desea cambiar) |

### 📄 Parámetros de ruta

| Campo    | Tipo | Descripción                                |
| -------- | ---- | ------------------------------------------ |
| `postId` | UUID | ID de la publicación que se desea editar   |

### ✅ Ejemplo de respuesta

```json
HTTP/1.1 200 OK
{
    "message": "Post updated successfully",
    "data": {
        "id": "2f82ece7-ad34-4814-a7e7-e60578f37804",
        "content": "3",
        "image": null,
        "createdAt": "2025-07-15T00:47:16.364+00:00",
        "likedBy": [],
        "author": {
            "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
            "username": "hernandex2",
            "email": "fabio2@gmail.com",
            "biography": null,
            "profilePicture": null
        }
    }
}
```
### ⚠️ Notas
- Si el post no existe, se devolverá un error 404.
- El usuario debe ser el autor de la publicación para poder editarla, de lo contrario se devolverá un error 403.
- Si no se proporciona un nuevo texto o imagen, la publicación no se actualizará y se devolverá la publicación original sin cambios.  
- La imagen debe ser un archivo válido y no exceder el tamaño máximo permitido por la API.
- Si se proporciona un nuevo texto, este reemplazará al texto original de la publicación.

---

## 🧠 Notas importantes
- Las publicaciones pueden contener texto, imagen o ambos.
- Las publicaciones solo pueden ser modificadas o eliminadas por sus creadores.
- El endpoint /following/all permite paginación con fecha para obtener posts recientes de seguidores.

### ¿Quieres ver cómo gestionar comentarios?
➡️ Próximo paso: [Gestionar comentarios](/docs/endpoints/comments.md)