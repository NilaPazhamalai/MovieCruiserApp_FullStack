sudo docker stop $(docker ps -a -q)
sudo docker rm $(docker ps -a -q)
# Delete ALL images:
docker rmi $(docker images -q)
