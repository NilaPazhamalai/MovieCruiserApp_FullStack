FROM java:8-jre
WORKDIR usr/src
ENV MYSQL_DATABASE=moviedb
ENV MYSQL_USER=app-root
ENV MYSQL_PASSWORD=root1234
ENV MYSQL_CI_URL=jdbc:mysql://localhost:3306/moviedb
ADD ./target/MovieCruiserAppService-0.0.1-SNAPSHOT.jar /usr/src/MovieCruiserAppService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","MovieCruiserAppService-0.0.1-SNAPSHOT.jar"]
