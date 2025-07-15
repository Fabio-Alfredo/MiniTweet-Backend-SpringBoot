# 👤 Endpoints de Usuarios

La API de MiniTweet permite consultar la información del usuario autenticado, buscar otros usuarios, listar todos los usuarios (para administradores) y asignar roles.

---

## 👁️ Obtener información del usuario autenticado

**`GET /api/users/me`**

Devuelve los datos del usuario actualmente autenticado.

### 🔐 Requiere autenticación
**Si** Se requiere un token JWT valido.

### ✅ Ejemplo de respuesta

```json
{
    "message": "success",
    "data": {
        "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
        "username": "hernandex2",
        "email": "fabio2@gmail.com",
        "biography": null,
        "profilePicture": null,
        "accountNonExpired": false,
        "accountNonLocked": false,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "credentialsNonExpired": false,
        "enabled": true
    }
}
```
### ⚠️ Notas
- El usuario debe estar autenticado para acceder a esta información.

---

## 📋 Listar todos los usuarios (solo Admin)

**`GET /api/users/all`**

Devuelve una lista de todos los usuarios en el sistema.

### 🔐 Requiere autenticación
**Si** Se requiere un token JWT valido y tener el rol de ADMIN.

### ✅ Ejemplo de respuesta

```json
{
    "message": "success",
    "data": [
        {
            "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
            "username": "hernandex2",
            "email": "fabio2@gmail.com",
            "biography": null,
            "profilePicture": null,
            "accountNonExpired": false,
            "accountNonLocked": false,
            "authorities": [
                {
                    "authority": "USER"
                }
            ],
            "credentialsNonExpired": false,
            "enabled": true
        }
    ]
}
```
### ⚠️ Notas
- Solo los administradores pueden acceder a esta información.
- El usuario debe tener el rol de ADMIN para acceder a esta ruta.

---

## 🔍 Buscar usuario por ID
**`GET /api/users/by-id/{id}`**
Devuelve la información de un usuario específico por su ID.

### 🔐 Requiere autenticación
**Si** Se requiere un token JWT valido y tener el rol de ADMIN.

### 🛣️ Parámetro de ruta

| Campo | Tipo | Descripción             |
| ----- | ---- | ----------------------- |
| `id`  | UUID | ID del usuario a buscar |

### ✅ Ejemplo de respuesta

```json
{
    "message": "success",
    "data": {
        "id": "1f547782-79fc-4acf-9ab6-0a16bf1f0450",
        "username": "hernandex2",
        "email": "fabio2@gmail.com",
        "biography": null,
        "profilePicture": null,
        "accountNonExpired": false,
        "accountNonLocked": false,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "credentialsNonExpired": false,
        "enabled": true
    }
}
```
### ⚠️ Notas
- El `id` debe ser un UUID válido de un usuario existente.
- Solo los administradores pueden acceder a esta información.

---

## 🔁 Actualizar roles de un usuario (solo Admin)
**`PUT PUT /api/users/update/{userId}`**
Permite modificar los roles de un usuario. Este endpoint solo puede ser utilizado por un administrador.

### 🔐 Requiere autenticación
**Si** Se requiere un token JWT valido y tener el rol de ADMIN.

### 🛣️ Parámetro de ruta
| Campo   | Tipo | Descripción                       |
|---------|------|-----------------------------------|
| `userId`| UUID | ID del usuario al que se le quieren actualizar los roles |

### 📌 Acciones posibles
| Acción | Descripción |
|-------|-------------|
| `ADD` | Agrega un nuevo rol al usuario. |
| `REMOVE` | Elimina un rol existente del usuario. |

### 📥 Cuerpo de la solicitud

```json
{
    "roleId":"ADMIN",
    "action":"ADD"
}
```

### ✅ Ejemplo de respuesta

```json
HTTP/1.1 200 OK
{
    "message": "success",
    "data": null
}
```

### ⚠️ Notas
- El `userId` debe ser un UUID válido de un usuario existente.
- Solo los administradores pueden acceder a esta información.
- El usuario debe tener permisos para modificar los roles de otros usuarios.
- El `roleId` debe ser un rol válido definido en el sistema (por ejemplo, `ADMIN`, `USER`).
- El `action` puede ser `ADD` o `REMOVE` para agregar o eliminar un rol respectivamente.
- Si se intenta agregar un rol que ya existe, no se realizará ninguna acción.
- Si se intenta eliminar un rol que no existe, no se realizará ninguna acción.

---

## 🛡️ Notas de Seguridad
- El endpoint /me devuelve únicamente los datos del usuario autenticado.
- El endpoint /all solo está disponible para usuarios con rol ADMIN.
- Asegúrate de no exponer información sensible como contraseñas u otros datos privados en las respuestas.

### ¿ Como autenticar un usuario?
Para ello tenemos la seccion de autenticación:
[Autenticación](../authentication.md)