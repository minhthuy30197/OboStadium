FROM maven:latest
RUN mkdir /obo
WORKDIR /obo
COPY . .
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]