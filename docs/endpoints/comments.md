# 💬 Endpoints de Comentarios

Los comentarios permiten a los usuarios interactuar con las publicaciones de forma más cercana y dinámica. En esta sección encontrarás cómo crear, visualizar y editar comentarios en MiniTweet.

---

## 📝 Crear un comentario

**`POST /api/comments/create`**

Permite a los usuarios añadir un nuevo comentario a una publicación específica.

### 🔐 Requiere autenticación
**Si** Se requiere un token JWT valido.

### 📤 Body (JSON)
```json
{
  "postId": "b7e3f6b8-1234-45c8-9ef0-dae4a389bcaf",
  "content": "¡Qué buen post!"
}
```

### ✅ Respuesta de éxito
```json
HTTP/1.1 201 Created
{
    "message": "Comment created successfully",
    "data": null
}
```

### ⚠️ Notas
- El `postId` debe ser un UUID válido de una publicación existente.
- El `content` debe tener entre 1 y 500 caracteres.
- El usuario debe tener permisos para comentar en la publicación.

---
## 👀 Ver comentarios de una publicación
**`GET /api/comments/all/{postId}`**

Devuelve todos los comentarios asociados a una publicación específica.

### 🔐 Requiere autenticación
**Si** Se requiere un token JWT valido.

### 📤 Parámetros de ruta

| Campo    | Tipo | Descripción                                          |
| -------- | ---- | ---------------------------------------------------- |
| `postId` | UUID | ID de la publicación cuyas comentarios se desean obtener |

### ✅ Respuesta de éxito
```json
HTTP/1.1 200 OK
{
    "message": "success",
    "data": [
        {
            "id": "132c4021-54eb-496e-bf26-0805356062f0",
            "content": "segund comentario",
            "author": {
                "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
                "username": "hernandex2",
                "email": "fabio2@gmail.com",
            },
            "post": {
                "id": "0c72c1b2-277b-4481-85ff-74aa2ee340cc",
                "content": "2",
                "image": null,
                "createdAt": "2025-07-15T02:33:17.432+00:00",
                "author": {
                    "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
                    "username": "hernandex2",
                    "email": "fabio2@gmail.com",
                },
                "likedBy": []
            }
        }
    ]
}
```

### ⚠️ Notas
- Si no hay comentarios, se devuelve un array vacío.
- El `postId` debe ser un UUID válido de una publicación existente.
- El usuario debe tener permisos para ver los comentarios de la publicación.

---

## ✏️ Editar un comentario

**`PUT /api/comments/update`**

Permite al usuario autenticado editar un comentario que haya realizado previamente.

### 🔐 Requiere autenticación
**Si** Se requiere un token JWT valido.

### 📤 Body (JSON)
```json
{
  "postId": "b7e3f6b8-1234-45c8-9ef0-dae4a389bcaf",
  "content": "¡Qué buen post!"
}
```

### ✅ Respuesta de éxito
```json
HTTP/1.1 200 OK
{
    "message": "Comment updated successfully",
    "data": null
}
``` 
### ⚠️ Notas
- El `postId` debe ser un UUID válido de una publicación existente.
- El `content` debe tener entre 1 y 500 caracteres.
- El usuario debe ser el autor del comentario para poder editarlo. 
- Si el comentario no existe, se devuelve un error 404.

---

## 📌 Notas adicionales
- Solo el autor del comentario puede editarlo.
- Los comentarios se muestran en orden cronológico por defecto.
- Puedes mostrar los comentarios en tu cliente ordenados por fecha o relevancia.

### Conozcamos un poco  de los seguidores de los usuarios en la siguiente sección: [👨‍🦲 Followers](followers.md)
