#!/bin/bash

echo "Definindo permissoes da pasta de código-fonte..."
sudo chmod 777 -R app/
sleep 1

echo "Iniciando o app..."
cd app; java -jar target/app-0.0.1-SNAPSHOT.jar
