docker-compose down
docker-compose up -d
sleep 60
docker exec -i mysql bash -c 'mysql -u root -ptjdgns112 Insta < index.sql'
