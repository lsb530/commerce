# FROM ubuntu
#
# FROM --platform=linux/x86_64 mysql
#
# ENV MYSQL_ROOT_PASSWORD="rootroot"
# ENV MYSQL_DATABASE="commerce"
# ENV MYSQL_CONTAINER_NAME="commerce_db"
# ENV MYSQL_USER="user"
# ENV MYSQL_PASSWORD="password"
# COPY db_migration.sql /docker-entrypoint-initdb.d
# EXPOSE 3306

FROM openjdk:11
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# FROM openjdk:11
# COPY ./commerce-application /commerce-application
# WORKDIR /commerce-application
# CMD ["./gradlew", "bootRun"]