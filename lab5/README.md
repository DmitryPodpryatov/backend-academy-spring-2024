
# Семинар 5. Observability

### #1 Logback

Настроить logback.xml таким образом, чтобы логи дополнительно записывались локально в файл `.logs.json` в JSON формате.

https://logback.qos.ch/manual/

### #2 Оборачиваем логгер

Напишите собственный логгер (обертку вокруг `org.typelevel.log4cats.Logger`), который конвертирует все принимаемые данные в JSON, по инстансу тайп-класса `cats.Show`.

Добавьте для кейс-класса [TopSecret](src/main/scala/lab5/domain/TopSecret.scala) инстанс `cats.Show`, который маскирует поле с секретом.

Справка:
- https://github.com/typelevel/log4cats
- https://typelevel.org/cats/typeclasses/show.html
