#!/bin/bash

cd MovieCruiserAuthenticationService
source ./env-variable.sh
mvn clean install package
cd ..
cd MovieCruiserAppService
source ./env-variable.sh
mvn clean install package
cd ..
cd MovieCruiserAppClient
ng build
cd ..
