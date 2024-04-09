# Семинар 8. Kafka

> _Поднимаем зависимости для семинара:_

```shell
docker-compose up
```

- kafka-ui — http://localhost:9090

Работаем с [fs2-kafka](https://fd4s.github.io/fs2-kafka/docs/overview)

### #1 Producer

https://fd4s.github.io/fs2-kafka/docs/producers

1. Написать поднятие продьюсера при запуске приложения
2. Продьюсер записывает данные в топик `applications`, ключом является `userId`, значением — JSON представление
   заявления
3. При запросе `POST /request`, данные должны отправляться в топик

### #2 Consumer

https://fd4s.github.io/fs2-kafka/docs/consumers

1. Написать поднятие консьюмера при запуске приложения, consumer поднимается вместе с HTTP сервером в отдельном файбере
2. Консьюмер подписывается на топик `applications`, читает оттуда события, обрабатывает их (достаточно просто
   логировать)
