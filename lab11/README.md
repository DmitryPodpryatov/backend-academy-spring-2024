# Lab 11. Модульный sbt project. Docker

### 1. Общие модули

- https://www.scala-sbt.org/1.x/docs/Multi-Project.html

---

Создайте модуль `core`, в котором будут лежать общие классы.

Вынесите общую модель `Event` в `core`.

Скомпилируйте приложение.

### 2. sbt-native-packager x docker

- https://github.com/sbt/sbt-native-packager
- https://www.scala-sbt.org/sbt-native-packager/introduction.html
- https://www.scala-sbt.org/sbt-native-packager/formats/docker.html

---

Добавьте сборку docker image для всех модулей кроме `core`.

Соберите образы.

---

Добавьте полученные образы в `docker-compose-services.yml`

Запустите сначала `-infra`, потом `-services`.

### 3. Healthchecks

- https://docs.docker.com/compose/startup-order/
- https://docs.docker.com/compose/compose-file/05-services/#depends_on
- https://docs.docker.com/compose/compose-file/05-services/#healthcheck

Контейнеры сами по себе никого не ждут, чтобы запуститься, а если мы и прописали `depends_on` то ждут только запуска
самого контейнера.

В итоге может быть такое, что контейнер с базой данных запустился, но сама бд пока не готова принимать соединения.

Аналогичная ситуация с кафкой: вероятно, что кластер с топиками пока не сформировался, а приложение уже стучится.

Для таких случаев мы и пишем healthchecks, чтобы контролировать порядок запуска сервисов на более глубоком уровне.

---

Поочереди перенесите сервисы из `docker-compose-infra.yml` файла в `-services`:

3.1 Напишите healthcheck для postgres

3.2 Напишите healthcheck для kafka
