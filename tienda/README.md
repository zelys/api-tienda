# Gestión Tienda API

Este proyecto es una API REST para gestionar las operaciones de una tienda. Ofrece funcionalidades para manejar clientes, productos y ventas.

## Tecnologías

- **Java 17**: Lenguaje de programación principal.
- **Spring Boot**: Framework para simplificar la creación de aplicaciones basadas en Spring.
- **Maven**: Herramienta de gestión y comprensión de proyectos.
- **MySQL**: Base de datos utilizada para almacenar la información.
- **Lombok**: Librería para simplificar el código Java y reducir el boilerplate.

## Funcionalidades

- **Gestión de Clientes**: Agregar, actualizar, eliminar y consultar clientes.
- **Gestión de Productos**: Agregar, actualizar, eliminar y consultar productos.
- **Gestión de Ventas**: Procesar y seguir ventas.

## Instalación

1. Clona el repositorio.
   ```bash
   git clone https://github.com/zelys/api-gestion-tienda.git
   ```

2. Navega al directorio del proyecto.
   ```bash
   cd tienda
   ```

3. Compila y empaqueta el proyecto usando Maven.
   ```bash
   mvn clean package
   ```

4. Ejecuta la aplicación.
   ```bash
   mvn spring-boot:run
   ```

## Uso

La API expone los siguientes endpoints principales:

- `/clientes`: Maneja las operaciones relacionadas con los clientes.
- `/productos`: Maneja las operaciones relacionadas con los productos.
- `/ventas`: Maneja las operaciones relacionadas con las ventas.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, sigue los lineamientos de contribución y abre un pull request.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT.

