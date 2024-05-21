# Семинар 13. Кэширование

> _Поднимаем зависимости для семинара:_

```shell
docker-compose up -d
```

- postgres — `docker exec -it postgres psql -U postgres`
- redis — `docker exec -it redis redis-cli`

---

### Redis

Redis является, пожалуй, самым популярным выбором, когда заходит речь о key-value in-memory хранилищах.

Может работать как в качестве единственного контейнера, так и в распределенном режиме с десятками нод.

Вся конфигурация происходит через _redis.conf_. Для ручной работы существует _redis-cli_.

---

Справка:

- https://redis.io/docs/latest/
- https://www.tutorialspoint.com/redis/index.htm
- https://hub.docker.com/_/redis
- https://raw.githubusercontent.com/redis/redis/7.2/redis.conf

---

### redis4cats

Существует множество библиотек (написанных как на Scala, так и на Java).

Сегодня остановимся на _redis4cats_ по принципу близости к typelevel стеку.

---

- https://github.com/profunktor/redis4cats

Проинициализируйте клиент для редиса в `package object database`.

Внутри `RedisClientService` реализуйте три основные операции, которые нам сегодня понадобятся: `get`, `set`
, `set with ttl` (храним JSON объекты).

### Стратегии кэширования

https://blog.bytebytego.com/p/top-caching-strategies

Внутри контроллеров вам предстоит реализовать две стандартные стратегии для работы с кэшами:

- cache-aside
- write-through

---

А так же fallback паттерн.

Представим, что вместо базы данных мы запрашиваем данные у third-party сервиса. 
Возможна ситуация, когда сервис упал и не отвечает. 

Для таких ситуаций, мы можем сохранить предыдущие ответы в кэш с бОльшим TTL, чем обычно.
И если вдруг сервис перестанет отвечать, вернуть пользователю ответ из нашего fallback кэша.

Такой паттерн так же отлично комбинируется с паттерном circuit break.
Тогда мы можем решать, ходить ли в кэш или нет в зависимости от состояния CB.

---

Справка

- https://botpenguin.com/glossary/fallback
- https://www.codecentric.de/wissens-hub/blog/resilience-design-patterns-retry-fallback-timeout-circuit-breaker
- https://habr.com/ru/companies/oleg-bunin/articles/466295/

