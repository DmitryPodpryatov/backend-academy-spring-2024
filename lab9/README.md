# Семинар 8. Kafka Transactions

> _Поднимаем зависимости для семинара:_

```shell
docker-compose up
```

- kafka-ui — http://localhost:9090

### #1 Transactional Producer

https://fd4s.github.io/fs2-kafka/docs/transactions

В ситуациях, когда мы читаем данные из одного топика и записываем результат вычисления сразу в несколько топиков,
полезно иметь гарантии того, что данные дошли сразу до всех топиков.

Именно для таких случаев Kafka предоставляет транзакционные продьюсеры.

---

Создайте сразу несколько топиков — один для чтения и пару для записи

- читаем заявки из `operations`
- отправляем события в `accounts` и `payments`

### #2 Kafka Testcontainers

- [testcontainers](https://github.com/testcontainers/testcontainers-scala/)
- [testcontainers kafka модуль](https://github.com/testcontainers/testcontainers-scala/blob/master/modules/kafka/src/main/scala/com/dimafeng/testcontainers/KafkaContainer.scala)

Поднимите контейнеры для интеграционного тестирования кафка консьюмера.

Напишите тест-кейс, который проверяет, что данные записываются в оба топика.
