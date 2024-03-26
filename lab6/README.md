
# Семинар 5. Prometheus

https://prometheus.io/docs/introduction/overview/

> _Поднимаем зависимости для семинара:_
```shell
docker-compose up
```

- Prometheus UI — http://localhost:9090/
- Swagger — http://localhost:8080/swagger
- Postgres — http://localhost:5432

### #1 Сбор метрик

1. Написать эндпоинт для сбора метрик

Prometheus собирает метрики по pull-модели.
Поэтому нам необходимо реализовать эндпоинт, с которого сервер Прометея будет забирать данные.

По дефолту, это `/metrics`. Но естественно, prometheus предоставляет возможность [настраивать](https://prometheus.io/docs/prometheus/latest/configuration/configuration/) всё через `.yaml` конфиг.

2. Настроить prometheus-конфиг на сбор метрик вашего приложения

<details><summary>Hint</summary>
У докер контейнеров может не быть доступа до вашей машины (localhost).
Используйте host.docker.internal.
</details>

### #2 Метрики JVM

Чтобы отдавать метрики, нам нужно сначала собрать эти метрики.
Обычно для этого используется регистр коллекторов `io.prometheus.client.CollectorRegistry.defaultRegistry`

1. Инициализируйте регистр коллекторов в [Server](src/main/scala/lab6/Server.scala)

2. Задайте список регистров и зарегистрируйте их в [Metrics](src/main/scala/lab6/metrics/Metrics.scala) 

3. Проверьте, что указанные вами метрики отдаются в `/metrics`

Справка:
- https://github.com/prometheus/client_java
- https://javadoc.io/doc/io.prometheus/simpleclient_hotspot/latest/io/prometheus/client/hotspot/package-summary.html

### #3 RED метрики на HTTP клиентах

[HttpClientService](src/main/scala/lab6/services/HttpClientService.scala)

1. Настроить `PrometheusBackend`, чтобы можно было собирать клиентские метрики

2. Проверьте, что метрики отдаются в `/metrics`

Справка:
- https://sttp.softwaremill.com/en/stable/backends/wrappers/prometheus.html
- вот здесь можно так же посмотреть, как можно собирать [метрики HTTP сервера](https://tapir.softwaremill.com/en/latest/server/observability.html)

### #4 Метрики подключения к базе данных — Hikari Pool

При взаимодействии с базой данных, мы пользуемся `Hikari Connection Pool`.
При инициализации пула соединений, мы так же можем указать `metricsTrackerFactory` для сбора метрик.

1. В [database.makeTransactor](src/main/scala/lab6/database/package.scala) добавить сбор метрик с пула соединений с базой данных

2. Проверьте, что метрики отдаются в `/metrics`

Справка
- https://github.com/brettwooldridge/HikariCP
- https://docs.datadoghq.com/integrations/hikaricp/

### #5 Разделяем вычисления и композируем всё вместе

С накоплением критической массы фичей в ваших сервисах, вы можете заметить, что большинство ваших методов имеют примерно следующий вид:

```scala
def doSomething = {
  writeLogs() *> 
    writeMetrics() *>
    doStuff() *>
    writeToCache() *>
    writeLogs()
}
```

К сожалению, при таком подходе теряется readability вашего кода и становится сложнее разбираться и модифицировать его.
К счастью, с помощью tagless final подхода, мы можем изолировать все *middleware* друг от друга.

В этой [статье](https://github.com/tofu-tf/tofu/blob/master/docs/mid.md) описываются структуры `Pre`, `Post`, `Mid`, которые позволяют прокидывать 

1. В [PostgresClientService](src/main/scala/lab6/services/PostgresClientService.scala) реализуйте класс, который логирует инициализацию запроса и сигнализирует об (не)успешности, в зависимости от результата вычисления 

2. Реализуйте метод `attach`, который "схлопывает" вычисление `impl` и ваше middlware для логирования.

3. Реализуйте еще один middleware, который инкрементирует метрики-счетчики `postgres_insert_total` и `postgres_insert_error_total`, в зависимости от результата

4. Реализуйте метод `combine`, который "схлопывает" вычисления различных middleware в один `mid`

Справка:
- https://typelevel.org/cats-tagless/
- https://typelevel.org/cats-tagless/typeclasses.html
- https://github.com/tofu-tf/tofu/blob/master/docs/mid.md
