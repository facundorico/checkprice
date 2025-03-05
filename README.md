# Check Price

Este proyecto implementa un servicio de consulta de precios utilizando **Spring Boot** y una arquitectura **hexagonal**, que permite un diseño desacoplado y flexible. Se utiliza **H2** como base de datos en memoria para facilitar las pruebas.

---

## 📂 Estructura del Proyecto

El código está organizado en varias capas siguiendo la arquitectura hexagonal:

```
📦 src/main/java/com/example/pricing
 ┣ 📂 domain                # Capa de dominio (Core del negocio)
 ┃ ┣ 📜 model
 ┃ ┃ ┗ 📜 Price.java        # Entidad de dominio
 ┃ ┣ 📜 repository
 ┃ ┃ ┗ 📜 PriceRepository.java  # Puerto de repositorio
 ┃ ┗ 📜 service
 ┃ ┃ ┗ 📜 PriceService.java      # Servicio de aplicación (caso de uso)
 ┣ 📂 application           # Capa de aplicación (Casos de uso y lógica de negocio)
 ┃ ┣ 📜 dto
 ┃ ┃ ┗ 📜 PriceResponseDto.java  # DTO para la respuesta
 ┃ ┗ 📜 service
 ┃ ┃ ┗ 📜 PriceServiceImpl.java  # Implementación del servicio
 ┣ 📂 infrastructure        # Capa de infraestructura (Adaptadores)
 ┃ ┣ 📜 repository
 ┃ ┃ ┗ 📜 PriceRepositoryImpl.java  # Implementación del repositorio JPA
 ┃ ┣ 📜 config
 ┃ ┃ ┗ 📜 DatabaseConfig.java  # Configuración de H2
 ┃ ┗ 📜 entity
 ┃ ┃ ┗ 📜 PriceEntity.java      # Entidad JPA
 ┗ 📂 api                   # Capa de entrada (Adaptador de controlador)
   ┣ 📜 controller
   ┃ ┗ 📜 PriceController.java  # API REST para consultas de precios
   ┣ 📜 request
   ┗ ┗ 📜 PriceRequestDto.java  # DTO para la petición
```

---

## 📌 Descripción de las Capas

### 1️⃣ **Capa de Dominio (`domain`)**
Contiene el núcleo de la lógica de negocio sin dependencias externas.
- **`model/Price.java`** → Representa la entidad de negocio de los precios.
- **`repository/PriceRepository.java`** → Define el puerto de acceso a los datos.
- **`service/PriceService.java`** → Interfaz que define las reglas del negocio.

### 2️⃣ **Capa de Aplicación (`application`)**
Contiene los casos de uso y la transformación de datos.
- **`dto/PriceResponseDto.java`** → DTO para la respuesta del servicio.
- **`service/PriceServiceImpl.java`** → Implementación del servicio de negocio.

### 3️⃣ **Capa de Infraestructura (`infrastructure`)**
Incluye la implementación de la persistencia y configuraciones.
- **`repository/PriceRepositoryImpl.java`** → Implementación concreta del repositorio JPA.
- **`config/DatabaseConfig.java`** → Configuración de la base de datos en memoria (H2).
- **`entity/PriceEntity.java`** → Entidad JPA que mapea la tabla en la base de datos.

### 4️⃣ **Capa de Entrada (`api`)**
Contiene los controladores que exponen la API REST.
- **`controller/PriceController.java`** → Controlador que maneja las peticiones HTTP.
- **`request/PriceRequestDto.java`** → DTO para recibir parámetros en la consulta.

### 5️⃣ **Capa de Pruebas (`test`)**
Incluye pruebas unitarias y de integración para validar el servicio.
- **`integration/PriceControllerTest.java`** → Pruebas de integración de la API REST.
- **`unit/PriceServiceTest.java`** → Pruebas unitarias del servicio.

---

## 🚀 Instalación y Ejecución

### 📌 Requisitos Previos
- Java 17+
- Gradle

### 📌 Configuración y Ejecución

📌 2. Compilar el proyecto
Si usas Gradle Wrapper (recomendado):

sh
Copiar
Editar
./gradlew build   # En macOS/Linux
gradlew build     # En Windows (CMD o PowerShell)
Si tienes Gradle instalado globalmente:

sh
Copiar
Editar
gradle build
Esto generará el JAR del proyecto en build/libs/.

📌 3. Ejecutar la aplicación
🔹 Opción 1: Usar Gradle para ejecutar la app directamente
sh
Copiar
Editar
./gradlew bootRun
Este comando usa el plugin de Spring Boot para compilar y ejecutar la aplicación en un solo paso.

🔹 Opción 2: Ejecutar el JAR generado
Si prefieres ejecutar el JAR manualmente, después de la compilación, haz lo siguiente:

sh
Copiar
Editar
java -jar build/libs/tu-app.jar
(Sustituye tu-app.jar por el nombre real del JAR generado en build/libs/).

---

## 📖 API Endpoints

### 🔹 **Consultar precio de un producto**
**Endpoint:** `GET /api/prices`

**Parámetros:**
| Nombre       | Tipo   | Descripción                           |
|-------------|--------|--------------------------------------|
| date        | String | Fecha en formato `yyyy-MM-dd HH:mm:ss` |
| productId   | Long   | Identificador del producto           |
| brandId     | Long   | Identificador de la cadena (marca)   |

**Ejemplo de petición:**
```sh
curl -X GET "http://localhost:8080/api/prices?date=2020-06-14 16:00:00&productId=35455&brandId=1"
```

**Ejemplo de respuesta:**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45
}
```

---

## 🧪 Pruebas
Ejecutar las pruebas unitarias y de integración:
```
Abre una terminal y ve a la carpeta raíz de tu proyecto:
cd /ruta/de/tu/proyecto

./gradlew test   # En macOS/Linux
gradlew test     # En Windows (CMD o PowerShell)
```

---

## 🏗️ Futuras Mejoras
- Agregar autenticación y autorización con JWT.
- Documentar la API con Swagger.
- Implementar caché para mejorar el rendimiento.

---