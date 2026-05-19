Como nos conectaremos desde nuestro IDE local a la BD que esta en el servidor
  1. Necesitaremos estar conectados a la VPN
  2. Cuando ejecutemos el IDE haremos esto
  mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.db.username=ptincatering --spring.db.password=ptincatering"   
  4. Una vez cargue, en el postman lo ejecutaremos de esta forma
       http://localhost:8080/api/<ruta-del-controller>
