FROM openjdk
COPY ./boss-card-service/target/boss-card-service-0.0.1-SNAPSHOT.jar /app/
CMD java -jar /app/boss-card-service-0.0.1-SNAPSHOT.jar