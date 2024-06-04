# Семинар 15. Streaming. Batch operations.

### Batch/Bulk Operations

Делаем REST методы для:

- batch insert
- batch update

Принимаем целый список пользователей и делаем только один запрос в бд.

---

- https://tpolecat.github.io/doobie/docs/07-Updating.html

### Стриминг

1. Пишем REST метод, чтобы можно было загрузить картинку в базу. 

2. Добавляем sttp клиент, чтобы загружать картинки по URL

https://httpbin.org/#/Images — GET /image

---

- https://tapir.softwaremill.com/en/latest/endpoint/streaming.html
- https://tapir.softwaremill.com/en/latest/server/http4s.html#streaming


- https://sttp.softwaremill.com/en/stable/requests/streaming.html
- https://sttp.softwaremill.com/en/stable/backends/fs2.html
