# API RESTful de Productos

Esta API RESTful permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre productos utilizando Spring Boot y MongoDB.

## Configuración y Ejecución

### Prerrequisitos
- Java 17 o superior
- MongoDB ejecutándose en localhost:27017
- Maven 3.6 o superior

### Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación se ejecutará en `http://localhost:8080`

## Endpoints de la API

### 1. Obtener todos los productos
- **GET** `/api/productos`
- **Respuesta**: Lista de todos los productos

### 2. Obtener producto por ID
- **GET** `/api/productos/{id}`
- **Parámetros**: `id` - ID del producto
- **Respuesta**: Producto encontrado o 404 si no existe

### 3. Crear nuevo producto
- **POST** `/api/productos`
- **Content-Type**: `application/json`
- **Body**:
```json
{
  "nombre": "Producto Ejemplo",
  "descripcion": "Descripción del producto",
  "precio": 99.99,
  "cantidad": 10,
  "categoria": "Categoría A"
}
```

### 4. Actualizar producto
- **PUT** `/api/productos/{id}`
- **Content-Type**: `application/json`
- **Body**: Mismo formato que para crear

### 5. Eliminar producto
- **DELETE** `/api/productos/{id}`
- **Parámetros**: `id` - ID del producto
- **Respuesta**: 204 No Content si se elimina correctamente

### 6. Buscar productos por nombre
- **GET** `/api/productos/buscar?nombre={nombre}`
- **Parámetros**: `nombre` - Nombre parcial del producto

### 7. Buscar productos por categoría
- **GET** `/api/productos/categoria/{categoria}`
- **Parámetros**: `categoria` - Categoría exacta

## Ejemplos de uso con cURL

### Crear un producto
```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Laptop Gaming",
    "descripcion": "Laptop para videojuegos de alta gama",
    "precio": 1599.99,
    "cantidad": 5,
    "categoria": "Electrónicos"
  }'
```

### Obtener todos los productos
```bash
curl http://localhost:8080/api/productos
```

### Obtener producto por ID
```bash
curl http://localhost:8080/api/productos/{id}
```

### Actualizar un producto
```bash
curl -X PUT http://localhost:8080/api/productos/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Laptop Gaming Pro",
    "descripcion": "Laptop mejorada para videojuegos",
    "precio": 1799.99,
    "cantidad": 3,
    "categoria": "Electrónicos"
  }'
```

### Eliminar un producto
```bash
curl -X DELETE http://localhost:8080/api/productos/{id}
```

## Validaciones

- `nombre`: Obligatorio, no puede estar vacío
- `precio`: Obligatorio, debe ser mayor o igual a 0
- `cantidad`: Obligatorio, debe ser mayor o igual a 0
- `descripcion`: Opcional
- `categoria`: Opcional

## Códigos de respuesta HTTP

- **200 OK**: Operación exitosa
- **201 Created**: Recurso creado exitosamente
- **204 No Content**: Recurso eliminado exitosamente
- **404 Not Found**: Recurso no encontrado
- **400 Bad Request**: Datos de entrada inválidos