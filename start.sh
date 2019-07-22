echo "============> Build server"
mvn --batch-mode package -f ./core/pom.xml -P prod
docker build -f ./core/Dockerfile -t social-server .
echo "============> Build frontend"
docker build -f ./web/Dockerfile -t social-web .
echo "============> Down container"
docker-compose -f docker-compose-prod.yml down
echo "============> Start container"
docker-compose -f docker-compose-prod.yml up -d --force-recreate
