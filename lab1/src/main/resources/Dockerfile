FROM openjdk:11

ENV jarname='lab1.jar'

WORKDIR /app
COPY ../../../target/scala-2.13/$jarname ./

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar ${jarname}"]
