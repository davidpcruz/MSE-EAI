create database EAIProj2;
CREATE USER 'DBuser'@'localhost' IDENTIFIED BY 'DBpass';

GRANT ALL PRIVILEGES ON *.* TO 'DBuser'@'localhost'  WITH GRANT OPTION;

