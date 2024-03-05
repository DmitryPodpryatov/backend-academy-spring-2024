
# Семинар 4. Обработка исключений

> _Поднимаем зависимости для семинара:_
```shell
docker-compose up
```

Нам, как разработчикам, очень часто приходится взаимодействовать с ошибками и исключениями.
Однако exception-ы могут встречаться не только в коде, но и при взаимодействии с другими системами, а так же в принципе, когда мы общаемся по сети.  

Сегодня мы погрузимся в основы обработки исключений с котами.

### #1 Возвращаем ADT в sttp.tapir

Начнем с более простых вещей. Стандартные коды ответа в тапире — 200 в случае успеха и 400 в случае ошибки.
> Unless specified otherwise, successful responses are returned with the 200 OK status code, and errors with 400 Bad Request

Естественно, в реальном мире мы не ограничиваемся только этими двумя кодами.
Чтобы указать конкретный код ответа, у нас есть `statusCode(code)`.

См. [ErrorController](src/main/scala/lab4/endpoints/ErrorController.scala).

1. Настройте эндпоинт `create` так, чтобы он всегда отдавал `204 No Content`
2. Настройте эндпоинт `error` так, чтобы он всегда отдавал `429 Too Many Requests`
3. Настройте `manyErrors` так, чтобы он возвращал полное дерево ошибок `ManyErrors`
    - добавьте сервер-логику, чтобы в зависимости от входного параметра, мы могли получить все возможные варианты ошибки

В `catchingErrors` вспоминаем, как работают методы `cats.ApplicativeError[F, E]`.
Мы никогда не хотим падать с `RuntimeException` внутри запроса, потому что тогда пользователь не получит адекватного ответа.
Вдобавок, мы не хотим отдавать `stack trace` наружу (вспоминаем мем с помощником Олегом).
Поэтому нам **всегда** нужно обрабатывать исключения перед тем, как отдавать ответ наружу.

Типичный подход при разработке метода — создавать структуру кейс-классов, которая включает в себя _запрос_, _ответ_, и все возможные _ошибки_.
Каждый элемент может являться как примитивом, так и целой системой из ADT.

4. Настройте `full`, чтобы можно было вернуть полное ADT как ошибок `FullError`, так и успешного ответа `FullResponse`
    - добавьте сервер-логику (аналогично п. 3)

Справка
- [sttp.tapir statusCode](https://tapir.softwaremill.com/en/latest/endpoint/ios.html)
- [sttp.tapir oneOf](https://tapir.softwaremill.com/en/latest/endpoint/oneof.html)
- [работа с ADT в io.circe](https://circe.github.io/circe/codecs/adt.html)

### #2 Обрабатываем ответы в sttp.client3

Mock-server находится на http://localhost:1080 (уже подставлено в клиенте)

В [initializerJson.json](mocks/mockserver/initializerJson.json) лежат ответы на запрос, выбор ответа производится по значению query параметра `in`.

См. [HttpClientService](src/main/scala/lab4/services/HttpClientService.scala)
1. В `def update` внутри `recoverWith` и `map` соответственно... 
    - обработать исключения, возникающие в связи с ошибками сети
    - обработать ошибки, возникающие из-за десериализации; вернуть ответ в соответствии с сигнатурой метода
2. тесты в [HttpClientServiceSpec](src/test/scala/lab4/services/HttpClientServiceSpec.scala) должны проходить успешно

Справка:
- [sttp.client3 exceptions](https://sttp.softwaremill.com/en/stable/responses/exceptions.html)

### #3 Обрабатываем java.sql.SQLException в doobie и не только

Оказывается, что при работе с базами данных, мы так же не защищены от проклятых ошибок.
`doobie`, в дополнение к стандартным расширениям `cats.syntax.applicativeError._`, предоставляет собственные комбинаторы для обработки ошибок.

Сегодня, мы учимся обнаруживать и обезоруживать `Unique Constraint Violation`.
Чтобы воспроизвести ошибку, вызовите `api/v1/insert` два раза и одинаковым id.

См. [PostgresClientService](src/main/scala/lab4/services/PostgresClientService.scala)
1. В `def insert`, добавьте код, который будет ловить SQL исключение, связанное с нарушением уникальности ключа.
    - выходной результат должен соответствовать сигнатуре метода

Справка:
- [doobie error handling](https://tpolecat.github.io/doobie/docs/09-Error-Handling.html)
