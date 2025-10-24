# Daniel_Beltran_Java_flux

##  Descripción

Proyecto desarrollado como parte de presentacion prueba técnica.  
Se implementa una arquitectura hexagonal en Java con Spring Boot, MySQL y Docker, permitiendo la gestión de franquicias, sucursales y productos mediante el uso de API REST.

## Estructura del proyecto
El proyecto está estructurado en módulos principales y carpetas siguiendo una arquitectura limpia y organizada (Arq hexagonal):

## Módulos principales

Branches -> Sucursales

Franchises -> Franquicias

Products -> Productos

Cada módulo está dividido en las siguientes carpetas:

1. **Application:**
   Contiene la lógica de aplicación y se divide en dos subcarpetas:

**port:** Contiene las interfaces de entrada y salida (puertos).

**services:** Implementación de los servicios del módulo.

2. **Domain:**
   Contiene los modelos o DTOs (Data Transfer Objects) que representan las entidades del dominio.

3. **Infrastructure:**
   Se encarga de la infraestructura y está dividida en:

**in:**
Contiene el controlador REST (la capa que expone los endpoints).

**out:**
Contiene varios paquetes relacionados con la persistencia y manejo de datos:

**entity:** Clases que representan las tablas de la base de datos con sus respectivos campos.

**mapper:** Clases encargadas de mapear la información necesaria tanto para guardar como para mostrar.

**repository:** Implementación de la lógica de cada método para manejar la persistencia.

**repository interface:** Interfaces que extienden JPA para realizar transacciones y consultas necesarias para alimentar la lógica.

4. **Shared:**
   Contiene clases y recursos compartidos entre módulos, como: Excepciones personalizadas y clases de manejo de respuesta.
5. **Base de Datos:** Se utilizo base de datos MySql en la nube.
6. Se utilizo pruebas test con jUnit5 las cuales arrojaron 100% de aceptacion.
7. Se configuro una URL base para las apis la cual se difinio asi: /api/v1
7. Los endpoint son los siguientes:

    * **Servicio guardar franquicias:** http://localhost:8080/api/v1/franquicias/create
        * Payload para guardar franquicia:{
          "nombre": "Franquicia - 002"
          }
    * **Servicio para actualizar nombre franquicia:** http://localhost:8080/api/v1/franquicias/update
        * Payload para actualizar nombre es necesario enviar los siguientes datos: {
          "id": 1,
          "nombre": "Franquicia - 001"
          }
    * **Servicios para guardar sucursal:** http://localhost:8080/api/v1/sucursales/create
        * Payload para guardar sucursal: {
          "nombre": "Sucursal -002, franquicia 2",
          "franquiciaId": 2
          }
    * **Servicio para actualizar nombre de sucursal:** http://localhost:8080/api/v1/sucursales/update
        * Payload para actualizar nombre: {
          "id": 1,
          "nombre": "Sucursal -001, franquicia 1"
          }
    * **Servicio para guardar producto:** http://localhost:8080/api/v1/productos/create
        * Payload para guardar producto: {
          "nombre": "Producto - 003 - Suc004",
          "stock": 30,
          "sucursalId": 4
          }
    * **Servicio para actualizar nombre del prooducto**: http://localhost:8080/api/v1/productos/update/{idProducto}
        * Payload para actualizar producto: {
          "nombre": "Producto - 001 - Suc001"
          }
    * **Servicio para actualizar Stock producto:** http://localhost:8080/api/v1/productos/update/{idProducto}/stock
        * Payload para actualizar stock: {
          "stock": 23
          }
    * **Servicio Para eliminar producto de una sucursal:** http://localhost:8080/api/v1/productos/sucursal/{idSucursal}/producto/{idProducto}
    * **servicio para listar productos con mas stock de cada sucursal por franquicia:** http://localhost:8080/api/v1/productos/top-stock/franquicia/{idFranquicia}
9. **Dockerizacion Local:** Para poder dockerizar el proyecto se siguen los siguientes pasos.
    * Crear y configurar un archivo .env para configurar las variables de entorno como lo son
      host de conexion a base de datos, usuario, contraseña debido que son datos sencibles, importante este archivo
      se ingnora al subir al repositorio compartire esta informacion de conexio via correo.
    * Crear y configurar el dockerfile para contruir la imagen.
    * Crear y configurar el docker-compose.yml para ejecutar el contedor.
    * Construir o recontruir las iamgen en un contedor con el comando docker-compose up --build
    * Ejecutar el contenedor creado anteriormente con el comando docker-compose up --build
10. Para desplegarlo en un servidor en la nube se realizan los siguientes pasos.
    * Se sube el proyecto a un repositorio, cabe resaltar que se sube sin las variables de entorno.
    * Se verifica que en el servidor cuente con la configuracion necesaria ejemplo docker, puertos y demas.
    * Se clona el repositorio.
    * se configuran las variables de entorno y se modican los archivos de docker en el proyecto.
    * Se corre el proyecto finalmente.
11. Por ultimo se utilizaron algunas validaciones en el proyecto.

## Correr el proyecto
Para poder correr el proyecto dockerizado debe seguir los siguientes pasos:

Clonar el repositorio
git clone url-repo
Ingresar al repositorio pero desde la consola
cd path-proyect
Luego de estar posicionado en la raiz del proyecto debes ejecutar el siguiente comando, recuerda que los **env** se envia en el correo para que lo anexes a la ruta principal del proyecto luego ejecutamos el siguiente comando
docker compose up -d
y lo que se hace es correr nuestor archivo de configuración docker-compose.yml que tenemos en nuestro proyecto.