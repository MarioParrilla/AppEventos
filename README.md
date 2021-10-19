#### CICLO FORMATIVO DE GRADO SUPERIOR

### DESARROLLO DE APLICACIONES MULTIPLATAFORMA

##### ACCESO A DATOS - 2º CURSO

![logo-IESGP-FP](./readmeAssets/logo-IESGP-FP.png)



# AppEventos

###### Curso: 2021/22

###### AUTOR: Mario Parrilla Maroto

------

# Introducción

## Motivación y objetivos

He decidido realizar una aplicacion de eventos para dar solucion a las personas que quieres quedar o pedir cita para un evento o situacion, como por ejemplo realizar una reunión, quedar alguien para contratarle...

## Descripción de la aplicación

Esta aplicacion, permitirá al usuario solicitar eventos a otros usuarios, ademas de aceptar eventos que le pidan otros usuarios. Los eventos podran ser presencialmente dando la ubicación del lugar de quedada/evento, o remotamente atraves de un enlace de una plataforma de videoconferencia como zoom, google meets,... Además, los eventos tendras una duración determinada. El usuario al cual le estan citando, podrá cancelar eventos.

# Especificación de requisitos

## Requisitos funcionales

RQ1 - Login Activity: En esta actividad nos encontrarémos un formulario de login donde los usuarios iniciarán sesión. (Para tener cuenta de usuario en la app, se tendrá que dar de alta desde el CMS).

RQ2 - Main Activity: En esta actividad, encontraremos los fragmentos home, search y profile. Además, encontrarémos dos botones con los que se podrá acceder a las actividades Settings y Chats.

RQ3 - Gestion de Eventos: En los diferentes fragmentos de main activity, podremos hacer CRUD con la información de los eventos.

RQ4 - Settings Activity: En esta activity, encontraremos diferentes funcionalidades como acerca de, soporte y cerrar sesion del usuario actual.

RQ5 - Chat Activity: En esta activity, encontraremos los diferentes chats bluetooth con otros usuarios. ⚠️🏗️[En desarrollo]🏗️⚠️

RQ6 - Home Fragment: En este fragment, encontraremos las cardview de los eventos del dia actual con datos de importacia, donde el usuario a citados a otros usuarios. Si pinchamos sobre la tarjeta de un evento de esta ventana, se nos abrirá un pop up (una nueva actividad), donde podremos ver los detalles al completo del evento.

RQ7 - Search Fragment: En este fragment, encontraremos una barra de busqueda donde podremos filtrar las cardviews por los nombres de los usuarios de la app. Además, veremos las cardview de los usuarios con su username y su descripción. Cuando hagamos click sobre una cardview, se abrirá un pop up (una nueva actividad), donde podremos ver el perfil del usuario con su información y todos sus eventos creados. Si pinchamos sobre un evento del perfil de este usuario, se nos motrará un pop up (una nueva actividad), con la información detallada de ese evento con la opción de citar este evento.

RQ8 - Profile Fragment: En este fragment, encontraremos la informacion de nuestro usuario, ademas de las cardviews de nuestros eventos ya creados con la información más impotante. Si pinchamos sobre estos eventos, podremos ver su información más detallada, ademas de poder modificar su información y poder eliminar el evento. También, podremos crear nuevos eventos atraves de un fab el cual contendrá otros dos fab, donde podremos o crear un evento presencial, donde se podrá crear el evento con su información precisa o el crear un evento meeting, donde se podrá crear el evento con su información precisa.

![casosDeUso](./readmeAssets/Diagramas/casosDeUso.png)

## Requisitos no funcionales

Seguridad: El usuario necesitará un usuario con su información para poder accerder a la aplicación.

Conectividad: El dispositivo movil, necesitará de conexión wifi, bluetooth y gps.

Tipos de dispositivos: Smartphones y tablets android en principio con versiones de android apartir de 5.0 .

Memoria: Los dispositivos necesitarán minimo 512MB de memoria RAM, aunque se recominenda 2Gb.

Procesador: Minimo se necesitará un procesador con Quad Core a 1.2GHz.

Almacenamiento: se necesitará minimo de un 1Gb de almacenamiento.

## Wireframe

LOGIN:
El usuario entrará con su usuario en la aplicación pudiendo guarda la sesión. Ádemas, podrá recordar la contraseña si no la recuerda.

![login](./readmeAssets/wireframe/login.png)

REGISTRO:
El usuario se podrá registrar en la aplicación y crear su propio usuario.

![registro](./readmeAssets/wireframe/registro.png)

RECORDAR CONTRASEÑA:
Aquí el usuario podrá recordar su contraseña recibiendo un correo a su email con un codigo concreto.

![recordarContrasenna](./readmeAssets/wireframe/recordarContrasenna.png)

NUEVA CONTRASEÑA: 
Aquí el usuario podrá crear una nueva contraseña para su cuenta.

![nuevaContrasenna](./readmeAssets/wireframe/nuevaContrasenna.png)

INICIO:
Este es el menu inicial donde podrá encontrar los eventos que tiene ese dia, de lo contrario, podrá ir a buscar eventos.

![inicio](./readmeAssets/wireframe/inicio.png)

BUSQUEDA:
Aquí el usuario buscará a los usuarios a los cuales desea citar un evento.

![busqueda](./readmeAssets/wireframe/busqueda.png)

NOTIFICACIONES:
Aqui se mostrarán las notificaciones de la aplicación.

![notificaciones](./readmeAssets/wireframe/notificaciones.png)

PERFIL PERSONAL:
Aquí el usuario verá sus eventos puediendo modificarlos a su elección.

![perfilPersonal](./readmeAssets/wireframe/perfilPersonal.png)

PERFIL AJENO:
Aquí el usuario podrá elegir el dia y la hora disponible para citar un evento a otro usuario.

![perfilAjeno](./readmeAssets/wireframe/perfilAjeno.png)

AJUSTES:
Aquí encontrará la selección del idioma de la aplicación, ademas de otros datos de interés.

![ajustes](./readmeAssets/wireframe/ajustes.png)

EVENTO:
Aquí el usuario citador podrá ver la información de un evento puediendo cancelar su cita.

![evento](./readmeAssets/wireframe/evento.png)

CITAR EVENTO:
El usuario podrá seleccionar los datos de la cita.

![citarEvento](./readmeAssets/wireframe/citarEvento.png)


MODIFICAR EVENTO:
El usuario podrá editar sus eventos e incluso cancelarlos.

![modificarEvento](./readmeAssets/wireframe/modificarEvento.png)




# Análisis Funcional

## Interfaz gráfico

App:

<img src="./readmeAssets/DisennoGrafico/app.gif" width="300" height="600">

Login:

<img src="./readmeAssets/DisennoGrafico/login.jpg" width="300" height="600">

Inicio: Aquí podemos ver como se ven las cardviews meeting, presencial y cuando no se tengan eventos disponibles.

<img src="./readmeAssets/DisennoGrafico/inicio.jpg" width="300" height="600">

Info Evento Meeting: 

<img src="./readmeAssets/DisennoGrafico/infoEventoMeeting.jpg" width="300" height="600">

Info Evento Presencial:

<img src="./readmeAssets/DisennoGrafico/infoEventoPresencial.jpg" width="300" height="600">

Ajustes:

<img src="./readmeAssets/DisennoGrafico/ajustes.jpg" width="300" height="600">

Chats:

<img src="./readmeAssets/DisennoGrafico/chats.jpg" width="300" height="600">

Buscar:

<img src="./readmeAssets/DisennoGrafico/buscar.jpg" width="300" height="600">

Perfil Externo:

<img src="./readmeAssets/DisennoGrafico/perfilExterno.jpg" width="300" height="600">

Citar Evento Meeting:

<img src="./readmeAssets/DisennoGrafico/citarEventoMeeting.jpg" width="300" height="600">

Citar Evento Presencial:

<img src="./readmeAssets/DisennoGrafico/citarEventoPresencial.jpg" width="300" height="600">

Perfil Personal:

<img src="./readmeAssets/DisennoGrafico/perfil.jpg" width="300" height="600">

Perfil Personal Opciones: 

<img src="./readmeAssets/DisennoGrafico/perfil2.jpg" width="300" height="600">

Crear Evento Meeting:

<img src="./readmeAssets/DisennoGrafico/crearEventoMeeting.jpg" width="300" height="600">

Crear Evento Presencial:

<img src="./readmeAssets/DisennoGrafico/crearEventoPresencial.jpg" width="300" height="600">

Modificar/Borrar Evento Meeting:

<img src="./readmeAssets/DisennoGrafico/modificarEventoMeeting.jpg" width="300" height="600">

Modificar/Borrar Evento Presencial:

<img src="./readmeAssets/DisennoGrafico/modificarEventoPresencial.jpg" width="300" height="600">


## Diagrama de clases

Se debe incluir un diagrama de clases y la descripción de las mismas.

## Diagrama E/R

### TABLA -- USUARIO

Todos los datos de esta tabla son Not Null, no pueden ser nulos

userID: Es la clave principal de la tabla, que será el identificador del usuario, que es de tipo bigint.

username: Es el nombre de usuario, que es de tipo varchar.

email: Es el correo electronico del usuario, que es de tipo varchar.

password: Es la contraseña del usuario, que es de tipo varchar.

phonenumber: Es el numero de telefono del usuario, que es de tipo varchar.

enabled: Nos servirá para saber si el usuario puede utilizarse o no, que es de tipo bit que en verdad es un boolean.

### TABLA -- EVENTO

eventID: Es la clave principal de la tabla, que será el identificador del evento, que es de tipo bigint. NotNull.

username: Es el nombre de evento, que es de tipo varchar. NotNull.

start_time: Es la fecha y hora del inicio del evento, que es de tipo Datetime. NotNull.

end_time: Es la fecha y hora de final del evento, que es de tipo Datetime. NotNull.

event_preference: Con este dato, segun su valor, si es 0 será un evento presencial y si no, será un evento meeting y con esto trabajaremos con diferentes datos según este valor, es de tipo bit, que en realidad es un boolean. NotNull.

coordinates: Son las coordenadas de la localizacion del lugar de quedada del evento, es de tipo varchar.

videomeeting: Es el enlace de la videoconferencia del evento, es de tipo varchar.

available: Con este dato sabremos si el evento esta activado o no según su valor (0 = desahabilitado / 1 = habilitado), es de tipo bit, pero en realidad es de tipo boolean. NotNull.

user_owner_id_user: es la id del usuario que ha creado el evento, es de tipo bigint. NotNull.

user_summoner_id_user: es la id del usuario que ha citado al creador el evento, es de tipo bigint.

![entidadRealacion](./readmeAssets/Diagramas/entidadRelacion.png)

## Plan de pruebas

Define los casos de prueba que se deben realizar para comprobar el correcto funcionamiento de la aplicación móvil.



# Diseño Técnico

## Diagrama de paquetes y de componentes

Se debe incluir un diagrama de paquetes, de componentes, etc.

También se puede incluir la estructura y descripción de los ficheros que forman parte del proyecto. Descripción 

de algoritmos, etc.

## Arquitectura del sistema

Describir la arquitectura del sistema (diagrama de despliegue).

## Entorno de desarrollo, librerías y servicios

En este punto se explicarán las diferentes tecnologías utilizadas para la realización del proyecto, así como los elementos más importantes que permitan entender el funcionamiento del sistema.

## Instrucciones para la compilación, ejecución y despliegue de la aplicación

Describe los pasos a seguir para poder compilar el proyecto y ejecutarlo.

## Informe de pruebas

### Pruebas en emuladores y dispositivos reales

Incluye pantallazos de la ejecución de los casos de prueba realizadas en los emuladores y dispositivos reales.

### Pruebas remotas

Incluye pantallazos de la ejecución de los casos de prueba realizadas en dispositivos reales remotos.



# Conclusiones

## Conocimientos adquiridos

Reflexiona sobre el trabajo realizado durante el desarrollo de la aplicación móvil y sobre los conocimientos adquiridos, problemas encontrados, etc.

## Mejoras futuras

Incluye aspectos y nueva funcionalidad que incluirías en la aplicación en un futuro próximo. 
