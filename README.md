Como nos conectaremos desde nuestro IDE local a la BD que esta en el servidor
  1. Necesitaremos estar conectados a la VPN
  2. Cuando ejecutemos el IDE haremos esto
  ./mvnw spring-boot:run   
  4. Una vez cargue, en el postman lo ejecutaremos de esta forma
       http://localhost:8080/api/<ruta-del-controller>
