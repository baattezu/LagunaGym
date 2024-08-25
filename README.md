# LagunaGym Microservices Project

Этот проект состоит из нескольких микросервисов, построенных с использованием Spring Boot.

## Требования
- Docker
- Docker Compose

Для выполнения команд Maven, вам не нужно устанавливать Maven вручную, так как проект использует Maven Wrapper.

## Сборка Docker-образов

Для сборки Docker-образов всех микросервисов, выполните следующий скрипт:

```powershell
.\build-services.bat
```

Сборка будет идти долго по-началу из-за доставания образа builder'а , потом все ускорится!

После же вам придется написать 
```powershell
docker-compose up -d
```
Для завершения стоит использовать 
```powershell
docker-compose down
```
Документация API лежит в пути
```http request
localhost:8765/swagger-ui.html
```


По всем вопросам писать на почту : temupxan@gmail.com 
Или в тг для друзей или знакомых : t.me/baattezu