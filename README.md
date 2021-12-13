Esta aplicación web es el resultado de un tutorial brindado por la Institucion Egg en el curso Programador Web Full Stack Java. El mismo consistía en una serie de videos
con explicaciones paso a paso en la creación de la página web.

¿Qué se puede hacer?

Registrar un usuario.
Logear y deslogear un usuario ya registrado.
Editar el perfil del usuario registrado.
Agregar, editar y eliminar las mascotas que el usuario posea.

Características:

Aplicación web creada con Spring con el lenguaje de programación Java.
Tiene Spring Security para que cuando el usuario se logee, se cree una sesión que permita ingresar a ciertas partes del sitio, que sin una no se podria.
Utiliza el motor de plantillas Thymeleaf para que las vistas sean dinámicas.
Utiliza la API de JPA para persistir información en una base de datos.
Tiene el driver de MySQL para comunicarse con la base de datos.
Validaciones en todos los formularios.

Aclaraciones:

La aplicación no está terminada y/o tiene bugs en algunas partes.
Requiere tener instalado Spring Tool Suite 4 y MySQL Workbench para levantar el sitio.

¿Cómo usar?
1) Clonar o pullear el proyecto y abrirlo en Spring Tool Suite 4.
2) Editar el archivo "application.yml" donde dice "username" y "password" para que coincidan con el usuario y contraseña del MySQL Workbench.
3) Siguiendo en "application.yml", tener una base de datos creada con el nombre que aparece en "url" después de "3306/", o también se puede cambiar el nombre en el
archivo y crear la base de datos con ese nuevo nombre. También asegurarse de que la base de datos esté creada en una conexión de MySQL donde el nombre del host sea
"localhost" y el puerto "3306".
4) En Spring Tool Suite 4, click derecho en el proyecto > Run As > Spring Boot App.
5) Ir al navegador y entrar en "http://localhost:8080/".
6) Si todo esto no funciona, probar de ejecutar estas líneas de codigo en Workbench:

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';

GRANT ALL PRIVILEGES ON * . * TO 'root'@'localhost';

FLUSH PRIVILEGES;

(Esto ya va a dejar el usuario y contraseña como "root").
