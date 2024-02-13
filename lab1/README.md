
# Семинар 1. Интеграции

Сетап:

> Представим, что нам предстоит реализовать фичу — банковский перевод по номеру телефона.
> На вход мы получаем `userId` клиента-отправителя, номер телефона `phoneNumber` клиента-получателя, и сумму перевода `amount`. 
> 
> Мы уже разбили задачу на последовательность API вызовов к банковским системам (не соответствует действительности, придумано для упражнения):
> 1. получить информацию по счету клиента-отправителя — `/graphql getAccount`
> 2. узнать, достаточно ли на балансе клиента средств; вернуть ошибку в противном случае
> 3. найти `userId` клиента-получателя по номеру телефона — `/graphql getAccountByPhoneNumber`
> 4. провести комплаенс-контроль перевода — `POST /api/v1/compliance/check/payment`
> 5. перевести деньги с одного счета на другой — `POST /accounts/api/v1/transfer`

### #1 OpenAPI

Основываясь на данных, описанных выше, составить OpenAPI спецификацию для нашего нового метода.

Уже есть частично реализованный [openapi.yaml](./src/main/resources/openapi.yaml), нужно добавить метод.

Документация [OpenAPI](https://swagger.io/specification/).

### #2 REST over JSON

Реализовать клиент для вызова метода "перевода с одного счета на другой" — `POST /accounts/api/v1/transfer`.
См. сигнатуру метода в [openapi.yaml](./src/main/resources/openapi.yaml).

1. Добавить необходимые кейс-классы для сериализации/десериализации в/из JSON.<br/>Используем [io.circe](https://circe.github.io/circe/codecs/semiauto-derivation.html)
3. Добавить вызов метода в [TransferClient.scala](./src/main/scala/lab1/integrations/TransferClient.scala). <br/>Используем [sttp.client3](https://sttp.softwaremill.com/en/stable/requests/basics.html)
4. Добавить вызов метода в [PaymentsService.scala](./src/main/scala/lab1/payments/PaymentsService.scala) (можно замокать необходимые данные).

Можно поднять моки методов, чтобы потыкать методы руками `http://localhost:1080/path-to-your-method`:

```shell
docker-compose up
http://localhost:1080/mockserver/dashboard
```

### #3 XML

Реализовать клиент для вызова метода "проведение комплаенс-контроля платежа" — `/api/v1/compliance/check/payment`.
См. [compliance.wsdl](./src/main/resources/compliance.wsdl) для описания XML схемы.

Аналогично #2, реализовать клиент для вызова метода, добавить в [PaymentsService.scala](./src/main/scala/lab1/payments/PaymentsService.scala).

Для сериализации/десериализации XML используем либу `phobos-core` — [дока](https://github.com/Tinkoff/phobos/wiki/Basic-guide).

Можно также поднять моки методов, чтобы потыкать методы руками `http://localhost:1080/path-to-your-method`:

```shell
docker-compose up
http://localhost:1080/mockserver/dashboard
```

### #4 GraphQL

Реализовать клиент для вызова запросов в GraphQL - `http://localhost:4444/graphql`.
У GraphQL есть свой [язык запросов](https://graphql.org/learn/), рекомендую сначала написать GraphQL запрос через UI (или схему),
потом переносить его в скалу.

Запросы в GraphQL через HTTP посылаются либо в query параметре, либо как POST запрос с JSON body, подробности можно узнать ниже
- https://graphql.org/learn/serving-over-http/
- А это может пригодиться, чтобы сжать JSON в одну строку https://datafetcher.com/graphql-json-body-converter

GraphQL схема счетов находится в [schema.graphql](./mocks/graphql/schema.graphql), необходимо реализовать два запроса —
один на поиск счета по `userId`, второй по `phoneNumber`.

Можно поднять мок GraphQL клиента, чтобы потыкать в красивый UI.
Правда придется [установить](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm) `npm` и `node`.

```shell
cd mocks/graphql/
npx gqlmocks serve --config gqlmocks.config.cjs --fake
```

Клиент находится на `http://localhost:4444/client`, http

### Бонус #1

`tapir` может генерировать OpenAPI документацию по коду — см. https://tapir.softwaremill.com/en/v0.17.9/docs/openapi.html.

В качестве задачи со звездочкой, предлагаю добавить эту фичу в наш проект.
Acceptance criteria — открывается swagger файл на `http://localhost:8080/swagger`
