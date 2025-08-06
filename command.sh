docker-compose up -d

docker exec jenkins-master cat /var/jenkins_home/secrets/initialAdminPassword

docker-compose restart jenkins-agent

http://localhost:8080

docker logs jenkins-master

docker-compose down && docker-compose up -d

# Gracefully stop the agent
docker-compose stop jenkins-agent && docker-compose up -d jenkins-agent

docker-compose restart jenkins-master

docker exec -it jenkins-agent bash

docker-compose up -d --force-recreate jenkins-agent

docker-compose down

docker-compose -f docker-compose_ha.yml down # specific for yml file

docker-compose -f docker-compose_ha.yml down -v # remove volumes

docker-compose -f docker-compose_ha.yml down --rmi all -v # everything

echo "AGENT_SECRET=63c99c09ea99c88ccf9f1ffa48f3aa03a6488dd59f88131169acf547df0b93ee" > .env