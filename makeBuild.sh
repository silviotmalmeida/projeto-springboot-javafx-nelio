#!/bin/bash

echo "Definindo permissoes da pasta de código-fonte..."
sudo chmod 777 -R app/
sleep 1

echo "Criando a build do projeto..."
cd app/; sudo ./mvnw package;
sleep 1