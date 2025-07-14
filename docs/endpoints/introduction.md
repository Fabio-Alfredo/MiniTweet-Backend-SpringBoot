# 🧭 Navegador de Endpoints

Bienvenido a la sección más dinámica de la documentación: **los endpoints** de la MiniTweet API. Aquí encontrarás todo lo necesario para comunicarte con la API de forma clara, segura y efectiva.

---

## 🧩 ¿Qué vas a encontrar aquí?

Hemos organizado los endpoints por **módulos funcionales**, para que puedas encontrar rápidamente lo que necesitas:

- 🔒 **Autenticación**: Registro, login y gestión de sesiones.
- 💬 **Comentarios**: Añadir, editar, obtener y eliminar comentarios en las publicaciones.
- 👨‍🦲 **Followers**: Ver seguidos, seguidores, seguir y dejar de seguir usuarios.
- ✏️ **Publicaciones (Posts)**: Crear, listar, editar,  comentar y dar like a pensamientos.
- 👥 **Usuarios**: Obtener informacion, enlistar y editar perfil .

Cada sección te ofrece:

✅ **Método HTTP**  
✅ **Ruta completa**  
✅ **Descripción clara**  
✅ **Cuerpo esperado de la solicitud**  
✅ **Respuesta de ejemplo**  
✅ **Notas de uso y seguridad**

---

## 🧪 ¿Cómo probar los endpoints?

Puedes usar herramientas como:

- [Postman](https://www.postman.com/)
- [Thunder Client](https://www.thunderclient.com/)
- `curl` desde consola

Recuerda incluir el token JWT en las rutas protegidas:

```http
Authorization: Bearer <tu-token-jwt>
```

---

## 🧭 Accesos rápidos
Haz clic en los módulos para navegar directamente:

- [🔒 Autenticación](/docs/authentication.md)
- [💬 Comentarios](comments.md)
- [👨‍🦲 Followers](followers.md)
- [✏️ Publicaciones](posts.md)
- [👥 Usuarios](users.md)