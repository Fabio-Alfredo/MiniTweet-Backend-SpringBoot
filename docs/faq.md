# ❓ Preguntas Frecuentes (FAQ) - MiniTweet API

Bienvenido a la sección de preguntas frecuentes. Aquí respondemos las dudas más comunes que podrías tener sobre el uso, configuración y consumo de la MiniTweet API.

---

## 🔑 ¿Cómo me autentico en la API?

Debes iniciar sesión con tu correo y contraseña mediante el endpoint de login:

```http
POST /api/auth/login
Content-Type: application/json
{
  "identifier": "tu_correo@example.com",
  "password": "tu_contraseña"
}
```


Recibirás un token JWT que debes usar en cada solicitud protegida:

```http
Authorization: Bearer tu_token_jwt
```

---

## 📥 ¿Cómo creo una publicación con imagen?

Debes enviar una solicitud `POST` al endpoint de publicaciones con el siguiente formato:

```http
POST /api/posts/create
Content-Type: multipart/form-data
```
Incluye el campo `content` para el texto y `image` para la imagen:

```json
{
  "content": "¡Hola, MiniTweet!",
  "image": "ruta/a/tu/imagen.jpg"
}
```

---

## 👁️ ¿Quién puede ver mis publicaciones?
Tus publicaciones serán visibles solo para ti y tus seguidores. El sistema valida si el usuario autenticado es seguidor tuyo antes de mostrar la información.

---

## 🧹 ¿Puedo eliminar un comentario o publicación?
Sí, puedes eliminar tus propios comentarios y publicaciones. Utiliza los siguientes endpoints:
```http
DELETE /api/comments/delete/{commentId}
DELETE /api/posts/delete/{postId}
```

---

## 🧾 ¿Puedo ver publicaciones de otros usuarios?
Sí, pero únicamente si los sigues. También puedes ver tus propias publicaciones a través de:

```http
GET /api/posts/user/{userId}
```

---

## 👥 ¿Cómo sigo a otro usuario?
Para seguir a un usuario, utiliza el siguiente endpoint:
```http
POST /api/follow/{followedId}

```
Esto te permite seguir a otro usuario, y así podrás ver sus publicaciones si son privadas.

---

## 🧪 ¿Puedo probar la API con Postman?
Sí, puedes importar la colección de Postman proporcionada en el repositorio de MiniTweet. Asegúrate de configurar las variables de entorno necesarias, como la URL base y el token JWT.

---

## 🔄 ¿Puedo deshacer un like?
Sí. El endpoint de like funciona como un toggle: si ya diste like, al volver a llamar el endpoint lo eliminarás.

---

## 🚫 ¿Qué sucede si intento acceder a un recurso sin token?
Si intentas acceder a un recurso protegido sin un token JWT válido, recibirás un error 401 Unauthorized. Asegúrate de incluir el token en el encabezado de autorización de tus solicitudes.

---

## 🧠 ¿Qué hacer si tengo una duda no cubierta aquí?
Pudes:
- Revisar la documentación completa.
- Abrir una issue en el repositorio de GitHub.
- Contactar con el equipo de desarrollo.

> ¡Tu experiencia es importante para nosotros! Si tienes ideas para mejorar MiniTweet, no dudes en contribuir 🎉

---
## 📜 ¿Dónde puedo encontrar más información?
Para más detalles sobre los endpoints, consulta la sección de [Endpoints](endpoints/introduction.md). 
