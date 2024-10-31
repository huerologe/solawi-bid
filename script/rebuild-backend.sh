cd ..
./gradlew solawi-bid-backend:clean && \
./gradlew solawi-bid-backend:build && \
./gradlew solawi-bid-backend:buildFatJar && \
docker-compose down --remove-orphans && \
docker-compose up -d --build solawi-bid_backend