FROM openjdk:11

VOLUME /tmp

#EXPOSE 8080

ENV APP_PROFILE=test
ENV MY_KEY=AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw
#ENV MYSQL_HOST=localhost
#ENV MYSQL_DATABASE=bdstoom
#ENV MYSQL_USER=root
#ENV MYSQL_PASSWORD=q1w2e3r4

ADD ./target/stoomtest-0.0.1-SNAPSHOT.jar stoomtest.jar
ENTRYPOINT ["java","-jar","/stoomtest.jar"]