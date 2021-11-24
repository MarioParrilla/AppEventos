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

RQ1 - Control de Acceso: Se controlará mediante una ventana con un formulario de login donde los usuarios iniciarán sesión. (Para tener cuenta de usuario en la app, se tendrá que dar de alta desde el CMS). Además desde la ventana de ajustes, se podrá cerrar la sesion del usuario actual.

RQ2 - Gestión de Eventos: En las diferentes ventanas encontraremos las diferentes funcionalidades donde podremos hacer los eventos de CRUD, ademas de ver mapas segun el evento. Además, en esta app al trabajar con los eventos se modificará la base de datos local creada previamente por la app con los datos recibidos del CMS en la sincronización.

RQ2.1 - Ventana de Ajustes: En esta ventana, encontraremos diferentes funcionalidades como acerca de, soporte y cerrar sesion del usuario actual.

RQ2.2 - Ventana de Inicio: En esta ventana, encontraremos las cardview de los eventos del dia actual con datos de importacia, donde el usuario a citados a otros usuarios. Si pinchamos sobre la tarjeta de un evento de esta ventana, se nos abrirá un pop up (una nueva ventana), donde podremos ver los detalles al completo del evento.

RQ2.3 - Ventana de Busqueda: En esta ventana, encontraremos una barra de busqueda donde podremos filtrar las cardviews por los nombres de los usuarios de la app. Además, veremos las cardview de los usuarios con su username y su descripción. Cuando hagamos click sobre una cardview, se abrirá un pop up (una nueva ventana), donde podremos ver el perfil del usuario con su información y todos sus eventos creados. Si pinchamos sobre un evento del perfil de este usuario, se nos motrará un pop up (una nueva ventana), con la información detallada de ese evento con la opción de citar este evento.

RQ2.4 - Ventana de Perfil: En esta ventana, encontraremos la informacion de nuestro usuario, ademas de las cardviews de nuestros eventos ya creados con la información más impotante. Si pinchamos sobre estos eventos, podremos ver su información más detallada, ademas de poder modificar su información y poder eliminar el evento. También, podremos crear nuevos eventos atraves de un fab el cual contendrá otros dos fab, donde podremos o crear un evento presencial, donde se podrá crear el evento con su información precisa o el crear un evento meeting, donde se podrá crear el evento con su información precisa. Por último, si pinchamos en el telefono de usuario podremos llamarlo o mandar un sms.

RQ3 - Chat: Chat será bluetooth con el cual se podrá hablar con otros usuarios. A este requirimiento se podrá acceder desde una ventana de chat.

RQ4 - Sincronización: El CMS le enviará peticiones a la app para que sincronice los datos de la base de datos local de la app con la base de datos del CMS.

![casosDeUso](./readmeAssets/Diagramas/casosDeUso.png)

## Requisitos no funcionales

Seguridad: El usuario necesitará un usuario con su información para poder accerder a la aplicación.

Conectividad: El dispositivo movil, necesitará de conexión wifi, bluetooth y gps.

Tipos de dispositivos: Smartphones y tablets android en principio con versiones de android apartir de 5.0 .

Memoria: Los dispositivos necesitarán minimo 512MB de memoria RAM, aunque se recominenda 2Gb.

Procesador: Minimo se necesitará un procesador con Quad Core a 1.2GHz.

Almacenamiento: se necesitará minimo de un 1Gb de almacenamiento.

Version de Android: El dispositivo necesitará una versión minima de android 8.0 Oreo

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

![gifApp](./readmeAssets/DisennoGrafico/app.gif)

Login:

![login](./readmeAssets/DisennoGrafico/login.jpg)

Inicio: Aquí podemos ver como se ven las cardviews meeting, presencial y cuando no se tengan eventos disponibles.

![inicio](./readmeAssets/DisennoGrafico/inicio.jpg)

Info Evento Meeting: 

![infoEventoMeeting](./readmeAssets/DisennoGrafico/infoEventoMeeting.jpg)

Info Evento Presencial:

![infoEventoPresencial](./readmeAssets/DisennoGrafico/infoEventoPresencial.jpg)

Ajustes:

![ajustes](./readmeAssets/DisennoGrafico/ajustes.jpg)

Chats:

![chats](./readmeAssets/DisennoGrafico/chats.jpg)

Buscar:

![buscar](./readmeAssets/DisennoGrafico/buscar.jpg)

Perfil Externo:

![perfilExterno](./readmeAssets/DisennoGrafico/perfilExterno.jpg)

Citar Evento Meeting:

![citarEventoMeeting](./readmeAssets/DisennoGrafico/citarEventoMeeting.jpg)

Citar Evento Presencial:

![citarEventoPresencial](./readmeAssets/DisennoGrafico/citarEventoPresencial.jpg)

Perfil Personal:

![perfil](./readmeAssets/DisennoGrafico/perfil.jpg)

Perfil Personal Opciones: 

![perfil2](./readmeAssets/DisennoGrafico/perfil2.jpg)

Crear Evento Meeting:

![crearEventoMeeting](./readmeAssets/DisennoGrafico/crearEventoMeeting.jpg)

Crear Evento Presencial:

![crearEventoPresencial](./readmeAssets/DisennoGrafico/crearEventoPresencial.jpg)

Modificar/Borrar Evento Meeting:

![modificarEventoMeeting](./readmeAssets/DisennoGrafico/modificarEventoMeeting.jpg)

Modificar/Borrar Evento Presencial:

![modificarEventoPresencial](./readmeAssets/DisennoGrafico/modificarEventoPresencial.jpg)


## Diagrama de clases

![diagramaPaqueteMain](./readmeAssets/Diagramas/diagramaPaqueteMain.png)

Clase Evento: En esta clase se usará para trabajar con los eventos de la aplicación.

Clase Usuario: En esta clase se usará para trabajar con los usuarios de la aplicación.

Clase Chat: Esta clase se usa para trabajar con la información de los chats en la aplicación.

Clase MensajeChat: Esta clase se utiliza para trabajar con la información de cada mensaje de los diferentes chats de la aplicación.

Clase SettingsFragment: Esta clase trabaja con la vista de ajustes de la aplicación.

Clase MainActivity: Esta clase es la clase donde se maneja los fragmentos en la primera carga de la aplicación.

Clase MyEventAppActivity: Esta es la clase principal de la aplicación donde se manejarán la primeras cargas de datos desde el servidor cms y se realizarán varias comprobaciones como comprobar si hay un usuario logueado actualmente.


![diagramaPaqueteLogin](./readmeAssets/Diagramas/diagramaPaqueteLogin.png)

Clase LoginActivity: Esta clase se encarga de realizar las comprobaciones relacionadas con el logueo en la aplicación de los usuarios.

Clase LoginModel: Esta clase se encarga de recuperar datos para pasarselos al LoginActivity para que pueda realizar sus operaciones correctamente.

![diagramaPaqueteHome](./readmeAssets/Diagramas/diagramaPaqueteHome.png)

Clase Homefragment: Esta clase se encarga de cargar los eventos los cuales el usuario logeado tiene citados.

Clase Homemodel: Esta clase se encarga de recuperar los datos para homefragment para que trabaje correctamente.

Clase Adaptadorproximoevento: Esta clase se encarga de cargar el layaout y los datos de los eventos en la vista.

![diagramaPaqueteBuscar](./readmeAssets/Diagramas/diagramaPaqueteBuscar.png)

Clase Buscarfragment: Esta clase se encarga de buscar los usuarios de la plataforma para poder ver sus perfiles para citar sus eventos.

Clase Buscarmodel: Esta clase proporciona los datos a buscarfragment.

Clase Adaptadorbusqueda: Esta clase se encarga de cargar el layaout y los datos lo de los usuarios en la vista.

![diagramaPaqueteExternalProfile](./readmeAssets/Diagramas/diagramaPaqueteExternalProfile.png)

Clase ExternalProfileActivity: Esta clase se encarga de mostrar los datos y eventos disponibles del perfil del usuario buscado en el fragmento de buscar .

Clase ExternalProfileModel: Esta clase se encarga de pasar los datos del usuario buscado a la clase ExternalProfileActivity para su correcta funcionalidad.

Clase AdaptadorEventoExternalProfile: Esta clase se encarga de cargar el layaout y los datos del usuario y de sus eventos disponibles.

![diagramaPaquetePerfil](./readmeAssets/Diagramas/diagramaPaquetePerfil.png)

Clase Perfilfragment: Esta clase se encarga de cargar el perfil del usuario el cual esta logeado en la aplicacion y poder llamar a los popups para agregar, modificar y eliminar sus propios eventos.

Clase Perfilmodel: Esta clase proporciona los datos del usuario logeado al perfilfragment.

Clase Adaptadorevento: Esta clase se encarga de cargar el layaout y los datos del usuario logueado y sus eventos.

![diagramaPaquetePopUpEventos](./readmeAssets/Diagramas/diagramaPaquetePopUpEventos.png)

Clases PopupCrearEventoPresencial y PopupCrearEventoMeeting: Estas clases se encargan de crear un evento propio, verificando que los datos estén correctamente puestos.

Clases PopupModificiarEventoPresencial y PopupModificiarEventoMeeting: Estas clases se encargan de modificiar un evento propio, verificando que los datos estén correctamente puestos y si este evento ya esta citado, no se permitirá modificarlo, además de poder eliminar estos eventos.

Clases PopupCitarEventoPresencial y PopupCitarEventoMeeting: Estas clases se encargan de mostrar la información de un evento, y permitir al usuario citador citar el evento.

Clases PopupInfoEventoPresencial y PopupInfoEventoMeeting: Estas clases se encargan de mostrar la información de un evento.

Clase PopupMostrarUbicación: Esta clase se encarga de mostrar un mapa en el cual se mostrará la ubicacion del evento.

![diagramaPaqueteChats](./readmeAssets/Diagramas/diagramaPaqueteChats.png)

Clase Chatsfragment: Esta clase se encarga de utilizar el bluetooth del smartphone y poder acceder a los chats individuales con los dispositivos bluetooth.

Clase Adaptadorchat: Esta clase se encarga de cargar el layaout y los datos de los dispositivos bluetooth encontrados.

Clase ChatActivity: Esta clase se encarga de cargar el historial del chat del dispositivo, realizar una conexion bluetooth con este dispositivo y poder realizar una conversación con este.

Clase AdaptardorChatIndividual: Se encarga de cargar los layaouts correspondiestes con sus datos de los mensajes de la conversación.

Clase Chatutils: Se encarga de las conexiones, informar de los estados de las conexiones y cambios de la conexion y enviar y recibir los mensajes del chat.

![diagramaPaqueteCore](./readmeAssets/Diagramas/diagramaPaqueteCore.png)

Clase FunctionsDatabase: Esta clase se encarga de interactuar con la base de datos en el smartphone y realizar sus respectivas acciones. Esta clase se utiliza en clases externas al paquete para que esta clase le envie la información que necesiten de la base de datos.

Clase Corefunctions: Esta clase se encarga de varias funcionalidades importantes de la aplicación como prevenir la inyecciones SQL, comprobar si el smartphone esta rooteado...


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

Prueba 1: Comprobar Existencia del un Usuario en el login: Se pasaran como parametros un usuario y una contraseña y si el usuario existe, nos devolverá un true, si no, nos devolverá false.

Prueba 2: Comprobar usuario agregado: Se pasará un objeto usuario y si el usuario se agrega correctamente, nos devolverá un true, si no, nos devolverá un flase.

🏗️⚠️PLAN DE PRUEBAS EN DESARROLLO⚠️🏗️

# Diseño Técnico

## Diagrama de paquetes y de componentes

Se debe incluir un diagrama de paquetes, de componentes, etc.

También se puede incluir la estructura y descripción de los ficheros que forman parte del proyecto. Descripción 

de algoritmos, etc.

## Arquitectura del sistema

![diagramaDespliegue](./readmeAssets/Diagramas/diagramaDespliegue.png)

CMS:  El CMS esta desplegado en la plataforma de Heroku. Este CMS esta desarrollado con Spring boot.

MyEventsApp: Esta es la aplicación android desarrolladoa con java.

Navegador: Se trata de un cliente web como chrome, firefox... 

Base de datos: La base de datos esta hosteada en amazon ya que se utiliza un plugin en heroku que te la hostea ahí.

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
