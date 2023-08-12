# Пропиши для докера, чтоб поднять контейнер с пг:
docker run --name bonjour -e POSTGRES_USER=user -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=bonjour-db -d -p 5432:5432 postgres

