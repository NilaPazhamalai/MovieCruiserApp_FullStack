export MYSQL_DATABASE=moviedb
export MYSQL_USER=app-root
export MYSQL_PASSWORD=root1234
if [[ -z "${MYSQL_CI_URL}" ]]; then
export MYSQL_CI_URL=jdbc:mysql://localhost:3306/moviedb
fi
