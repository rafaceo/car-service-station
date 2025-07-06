Приветствую вас, здесь я покажу как правильно запускать проект.
1. Для начала включить DOCKER для запуска docker-compose.
2. Также нужно будет создать bd под названием car_service_station, username kaspi password kaspi. И убедится что он подключен
2. Перейти в файл docker-compose.yml и нажать на кнопку с 2 запусками (для сборки keycloak, kafka, zookeeper)
3. После создания вам следует импортировать из проекта окружение kc (realm-export), в админке по localhost:8081 
4. username admin, password admin, в realm settings нажимаем action там будет import
5. когда у вас будет готовый kc, поднялись 3 контейнера (docker ps) можно запускать проект.
6. Для создания заявки нам нужно пройти регистрацию. Пример:
7. curl --location 'http://localhost:7777/user/registration' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "username": "user",
   "password": "user",
   "confirmPassword": "user",
   "email": "user@gmail.com",
   "firstName": "User",
   "lastName": "User"
   }
   '
8. После этого можно приступать к созданию заявки. Пример:
   curl --location 'http://localhost:7777/request/create' \
   --header 'Content-Type: application/json' \
   --data '{
   "username": "user",
   "password": "user"
   }'
9. В бд появится новая заявка, я не стал использовать spring security для упрощения тестирования
10. Для поиска по имени нужен пользователь с ролью Employee, обычный клиент не должен иметь возможность смотреть какие заявки у других
11. curl --location 'http://localhost:7777/request/findByName' \
    --header 'Content-Type: application/json' \
    --data '{
    "username": "employee",
    "password": "employee",
    "searchName":"user"
    }'
12. По этому запросу вернется ответ JSON формата с заявками по имени юзера. Аналогично со статусом
13. curl --location 'http://localhost:7777/request/findByStatus' \
    --header 'Content-Type: application/json' \
    --data '{
    "username": "employee",
    "password": "employee",
    "status":"CREATED"
    }'
14. Как и требовалось, в обновлении статуса, используется kafka
15. curl --location 'http://localhost:7777/request/updateRequest' \
    --header 'Content-Type: application/json' \
    --data '{
    "username" : "employee",
    "password" : "employee",
    "uuid" : "310d5bc3-41fa-4dac-a723-014a8c481d36",
    "status" : "COMPLETED",
    "description" : "yes"
    }'
Логи кастомные, уведомление поместил в лог, в случае статуса COMPLETED. Так же дал возможность обновления только работникам
Пользователи хранятся в KC, заявки в БД. Мы можем увидеть кто изменил, когда и почему. 