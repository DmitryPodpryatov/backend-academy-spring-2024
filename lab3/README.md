
# Семинар 3. Аутентификация и Авторизация

Реализовываем упрощенную схему `OAuth 2.0 client credentials flow`.
- проверяем права пользователя по `Access Control List (ACL)`
- в качестве токена будем подписывать `Json Web Token (JWT)` по `HS256`

Справка:
- [oauth2-client-creds-grant-flow](https://learn.microsoft.com/en-us/entra/identity-platform/v2-oauth2-client-creds-grant-flow)
- [jwt](https://jwt.io/introduction)

### #1 Моделирование

1. Смоделируйте структуру JWT в виде кейс-классов:
- [JsonWebToken](./src/main/scala/lab3/domain/auth/JsonWebToken.scala)

Токен состоит из трех частей - Header, Payload, Signature.
Для `payload` используем только следующие имена из [registered claims](https://datatracker.ietf.org/doc/html/rfc7519#section-4.1)
- `iss`
- `iat`
- `exp`

В [JwtSignature](./src/main/scala/lab3/domain/auth/JwtSignature.scala) храним `secret`, который позже будем брать из конфига.

### #2 Конфигурация

1. Добавьте кейс-классы для конфигурации ACL `security.clients`
2. Добавьте в `application.conf` тестового клиента и ключ для подписи

### #3 Валидируем ACL

1. Реализуйте метод `validateClient` в [AuthService](./src/main/scala/lab3/services/AuthService.scala), который проверяет наличие реквизитов пользователя в ACL.
Кидаем ошибку `AuthError` в случае несовпадения.

_Hint: используйте `ApplicativeError.raiseError`, у вас уже есть инстанс `MonadThrow` в scope_

### #4 Генерируем токен

1. Реализуйте следующие методы в [JwtService](./src/main/scala/lab3/services/JwtService.scala):
- `def encodeBase64`, `def decodeBase64` - Base64 URL-кодирование входной строки
- `def sign` - `HMACSHA256` [подпись](https://jwt.io/introduction) токена
- `def buildJwt` - собираем токен из трех элементов

2. В итоге у вас должен получиться рабочий метод `def generateToken`
3. Напишите unit-тесты на каждый из методов выше (кроме `sign`)

Справка:
- https://en.wikipedia.org/wiki/HMAC
- https://en.wikipedia.org/wiki/SHA-2

### #5 Валидируем токен

1. Реализуйте следующие методы в [JwtService](./src/main/scala/lab3/services/JwtService.scala):
- `def decomposeJwt` - получаем на вход JWT из `Bearer` хэдера, необходимо получить из него Base64 URL-закодированные `header` и `payload`,
а также `signature`
- `def validateSignature` - провалидировать JWT подпись
- `def validateDuration` - провалидировать Claims, в нашем случае делаем только проверку по времени (по комбинации `iat` и `exp`)

2. В итоге у вас должен получиться рабочий метод `def validateToken`
3. Напишите unit-тесты на каждый из методов выше