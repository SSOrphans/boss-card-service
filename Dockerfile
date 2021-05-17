FROM maven as stage1
COPY ./ /app/boss-card-service
RUN cd /app/boss-card-service && mvn -q clean package

FROM openjdk
COPY --from=stage1 /app/boss-card-service /app/boss-card-service
CMD java -jar /app/boss-card-service/boss-card-service/target/boss-card-service-0.0.1-SNAPSHOT.jar