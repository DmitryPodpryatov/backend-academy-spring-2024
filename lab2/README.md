# Семинар 2. Базы данных

### #1 Схема и Миграции

1. Создайте схему для представления данных в страховой компании. Необходимо хранить следующие данные:

    - информация об авто `car`
    - базовые данные о клиентах `customer`
    - данные о платежах `insurance_payment`
    - информация об аварии `accident`

   Добавьте составленную схему в файл с миграциями [V1__schema.sql](./src/main/resources/db.migration/V1__schema.sql)

2. Создайте таблицы для представления отношений между сущностями выше.

    - страховой план по авто `insurance_plan`
    - владение авто `owner`
    - история аварий `accident_history`

3. Напишите еще один файл-миграцию для наполнения таблиц данными.

4. Опишите составленную схему в виде кейс-классов [/domain](./src/main/scala/lab2/domain)

Справка:
- https://documentation.red-gate.com/flyway/quickstart-how-flyway-works

### #2 Вспоминаем, как писать SQL запросы

Реализуйте [CarRepository](./src/main/scala/lab2/repository/CarRepository.scala)
и [CarService](./src/main/scala/lab2/services/CarService.scala)

- создание
- получение авто по id
- удаление

Справка:
- https://tpolecat.github.io/doobie/docs/04-Selecting.html
- https://tpolecat.github.io/doobie/docs/07-Updating.html

### #3 Агрегируем данные

Напишите SQL запросы для следующих сценариев
в [AggregateRepository](./src/main/scala/lab2/repository/AggregateRepository.scala):

1. Список всех авто, по которым платеж по страховке составляет меньше чем N руб.

2. Список всех клиентов, которые ни разу не бывали в аварии

3. Список всех клиентов, суммарная сумма по страховке за все авто не превышает N руб.

Дополните [InsuranceService](./src/main/scala/lab2/services/InsuranceService.scala)
и [InsuranceController](./src/main/scala/lab2/endpoints/InsuranceController.scala), чтобы ответ возвращался через HTTP
endpoint.

### #4 Добавим индексы на таблицы

Добавьте еще один файл-миграцию. Проанализируйте запросы выше, создайте индексы на поля, которые считаете нужными.

Справка:
- https://www.postgresql.org/docs/current/indexes.html
