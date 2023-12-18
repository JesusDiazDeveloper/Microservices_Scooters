# SCOOTER APPLICATION INSTRUCTIONS

## TO START THE APPLICATION FOLLOW THESE STEPS.
You need to be connected to internet to start the app.
### 1st step: START MCSV-CONFG

### 2nd step: START MCSV-EUREKA

### 3rd step: START MCSV-GATEWAY

### 4th step: START MCSV-AUTH

### 5th step: START ALL REMAINING MCSV

#### CREATE USERS FOR LOGIN

Examples:

{
    "username": "super-admin",
    "password": "password",
    "roles": ["ADMIN,MAINTENANCE,USER"]
}

{
    "username": "admin",
    "password": "password",
    "roles": ["ADMIN"]
}

{
    "username": "maintenance",
    "password": "password",
    "roles": ["MAINTENANCE"]
}

{
    "username": "common-user",
    "password": "password",
    "roles": ["USER"]
}

#### LOGIN

Only use credentials.

{
    "username": "super-admin",
    "password": "password"
}


#### SWAGGER


Once the services are up and running, go to http://localhost:8761/.

Navigate to the "Instances currently registered with Eureka" section.

Click on any green link under the status column (depending on the Microservice you want to view Swagger for).

Something similar to this URL will appear in the browser bar: http://host.docker.internal:54190/actuator/info.

Simply copy the port number from that link and paste it into the following link, replacing only the number: http://localhost:54190/swagger-ui/index.html#/.

After this, you should see the Swagger documentation in your browser.


####Español:

Una vez que los servicios estan levantados , Ingresar a http://localhost:8761/ 

dirigirse a la seccion "Instances currently registered with Eureka"

click en cualquier link verde bajo la columna status (dependiendo del Microservicio que desee mirar el Swagger)

aparecera algo similar a este url en la barra del navegador : http://host.docker.internal:54190/actuator/info 

Simplemente, copie el numero de Puerto dentro de ese link y peguelo dentro de el siguiente link, reemplazando solo el numero
http://localhost:54190/swagger-ui/index.html#/


Luego de esto, deberia ver la Documentacion Swagger en su navegador