# Команда для докера + необходимо датасорс в idea добавить:
docker run --name bonjour -e POSTGRES_USER=user -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=bonjour-db -d -p 5432:5432 postgres <br/>
menuItem endpoints: <br/>
GET: <br/>
http://localhost:8080/menuItems/{id} <br/>
http://localhost:8080/menuItems <br/>
POST/PUT: <br/>
http://localhost:8080/menuItems <br/>
DELETE: <br/>
http://localhost:8080/menuItems/{id} <br/>
