# Семинар 10. Интеграции и разные архитектуры

> _Поднимаем зависимости для семинара:_

```shell
docker-compose up
```

- postgres — http://localhost:5432
- minio (S3) console — http://localhost:9001
- kafka-ui — http://localhost:9090

---

Сегодня мы попытаемся соединить сразу несколько различных интеграций вместе.

Примерный план нашей архитектуры:

1. Отправляем руками в kafka topic событие со ссылкой на файл в S3 хранилище
2. Событие обрабатывается консьюмером, файл скачивается из S3 и сохраняется в базу данных
3. Клиент вызывает HTTP метод и получает файл из базы данных

---

Справка:

- https://yandex.cloud/ru/docs/glossary/s3
- https://min.io
    - https://min.io/docs/minio/container/index.html
    - http://www.sefidian.com/2022/04/08/deploy-standalone-minio-using-docker-compose/
- https://aws.amazon.com/sdk-for-java/
