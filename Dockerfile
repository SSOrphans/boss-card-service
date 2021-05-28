FROM openjdk
COPY ./boss-card/target/boss-card-0.0.1-SNAPSHOT.jar /app/
CMD java -jar /app/boss-card-0.0.1-SNAPSHOT.jar