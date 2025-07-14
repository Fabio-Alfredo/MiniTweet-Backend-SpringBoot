# 🏁 Guía para Comenzar con MiniTweet API

¿Listo para comenzar a trabajar con MiniTweet API? ¡Sigue esta guía paso a paso para tener tu entorno listo en pocos minutos!

---

## 📦 Requisitos previos

Asegúrate de tener instalado en tu máquina:

- ☕ **Java 17 o superior**
- ⚙️ **Gradle**
- 🐘 **PostgreSQL**
- 🌐 Acceso a una cuenta en [Cloudinary](https://cloudinary.com) para manejar imágenes

---

## 📁 1. Clonar el repositorio

```bash
git clone https://github.com/your-username/minitweet.git
cd minitweet
```

---

## 🛠️ 2. Instalacion de dependencias

```bash
./gradlew build
```

---

## 🗄️ 3. Configurar el entorno

Crea un archivo application.properties dentro de src/main/resources/ con la siguiente estructura:

```properties
# PostgreSQL
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
# Otros ajustes
```
🔐 **Asegúrate de mantener este archivo en secreto y no compartirlo públicamente.**

---

## 🚀 4. Iniciar la aplicación

Inicia el servidor en modo desarrollo:

```bash
./gradlew bootRun
```
La aplicación debería estar corriendo en `http://localhost:8080`.

---

## 🧪 5. Prueba endpoints
Puedes usar herramientas como [Postman](https://www.postman.com) o [Insomnia](https://insomnia.rest) para probar los endpoints de la API.

🎉 ¡Todo listo! Ahora puedes comenzar a interactuar con MiniTweet API.

---

coloca un llamado para la arquitectura

## 🏗️ Conozcamos la arquitectura

Sigue la sección [Arquitectura del sistema](architecture.md) para entender cómo está estructurada MiniTweet API y cómo puedes aprovechar sus componentes.
