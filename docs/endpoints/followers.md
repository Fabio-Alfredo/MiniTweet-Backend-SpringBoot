# 🤝 Endpoints de Seguidores (Followers)

La funcionalidad de seguidores en MiniTweet permite a los usuarios crear su propia red social, seguir a otros y ver a quién siguen o quién los sigue. Esto también influye en la privacidad y visibilidad de los posts.

---

## ➕ Seguir a un usuario

**`POST /api/follow/follow/{followedId}`**

Permite que el usuario autenticado comience a seguir a otro usuario y si ya lo sigue, el usuario dejará de seguirlo.

### 🔐 Requiere autenticación

### 🛣️ Parámetro de ruta

| Campo        | Tipo   | Descripción                        |
|--------------|--------|------------------------------------|
| `followedId` | UUID   | ID del usuario que se quiere seguir |

### ✅ Ejemplo de respuesta

```json
{
  "success": true,
  "message": "Followed user successfully",
  "data": null
}
```
### ⚠️ Notas
- El `followedId` debe ser un UUID válido de un usuario existente.
- El usuario debe tener permisos para seguir a otros usuarios.
- Si el usuario ya sigue al otro, se eliminará la relación de seguimiento.

---

## 👥 Ver seguidores de un usuario

**`GET GET /api/follow/followers/{userId}`**

Obtiene la lista de usuarios que siguen al usuario especificado.
Solo el propio usuario o sus seguidores pueden acceder a esta lista.

### 🔐 Requiere autenticación
**Si** Se requiere un token JWT valido.

### 🛣️ Parámetro de ruta

| Campo   | Tipo | Descripción                       |
|---------|------|-----------------------------------|
| `userId`| UUID | ID del usuario del que se quieren obtener los seguidores |

### ✅ Ejemplo de respuesta

```json
{
  "success": true,
  "message": "Followers retrieved successfully",
  "data": [
    {
      "id": "123e4567-e89b-12d3-a456-426614174000",
      "username": "follower1",
      "email": "follower1@example.com"
    },
    {
      "id": "123e4567-e89b-12d3-a456-426614174001",
      "username": "follower2",
      "email": "follower2@example.com"
    }
  ]
}
```
### ⚠️ Notas
- El `userId` debe ser un UUID válido de un usuario existente.
- Solo el propio usuario o sus seguidores pueden acceder a esta información.

---

## 🔎 Ver a quién sigue un usuario
**`GET /api/follow/followed/{userId}`**
Devuelve la lista de usuarios que el usuario especificado sigue.
Solo el propio usuario o sus seguidores pueden acceder a esta información.

### 🔐 Requiere autenticación
**Si** Se requiere un token JWT valido.

### 🛣️ Parámetro de ruta

| Campo   | Tipo | Descripción                       |
|---------|------|-----------------------------------|
| `userId`| UUID | ID del usuario del que se quieren obtener los seguidos |

### ✅ Ejemplo de respuesta

```json
{
  "success": true,
  "message": "Followed users retrieved successfully",
  "data": [
    {
      "id": "123e4567-e89b-12d3-a456-426614174002",
      "username": "followed1",
      "email": "followed1@example.com"
    },
    {
      "id": "123e4567-e89b-12d3-a456-426614174003",
      "username": "followed2",
      "email": "followed2@example.com"
    }
  ]
}
```
### ⚠️ Notas
- El `userId` debe ser un UUID válido de un usuario existente.
- Solo el propio usuario o sus seguidores pueden acceder a esta información.

---

## 🧭 Navegación rápida

- [Seguir a un usuario](#-seguir-a-un-usuario)
- [Ver seguidores de un usuario](#-ver-seguidores-de-un-usuario)
- [Ver a quién sigue un usuario](#-ver-a-quién-sigue-un-usuario)

---

## 📌 Notas adicionales
- Asegúrate de manejar correctamente los errores, como intentar seguir a un usuario que no existe o ya estás siguiendo.
- La funcionalidad de seguidores es fundamental para construir una comunidad activa y conectada en MiniTweet
- Considera implementar notificaciones o actualizaciones en tiempo real cuando un usuario comience a seguir a otro.

---

### Concozcamos los endpoints para gestion de usuarios: [👤 Users](users.md)