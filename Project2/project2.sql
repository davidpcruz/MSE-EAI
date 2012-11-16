create database EAIProj2;
CREATE USER 'DBuser'@'localhost' IDENTIFIED BY 'DBpass';

GRANT ALL PRIVILEGES ON *.* TO 'DBuser'@'localhost'  WITH GRANT OPTION;

INSERT INTO eaiproj2.user (name, username, password ) VALUES('user', 'user', 'user' );
INSERT INTO eaiproj2.backofficeuser (username, password ) VALUES('admin','admin' );

