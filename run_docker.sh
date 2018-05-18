sudo docker login --username=vennila --password=Nila123
sudo docker run -d -p 3306:3306 --network=host --name=test-mysql-dock -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=moviedb -e MYSQL_USER=root mysql:latest
sudo docker run --name test-spring-user --network=host user-app-dock
#open another terminal
sudo docker run --name test-spring-movie --network=host movie-app-dock
