CREATE DATABASE demo;
CREATE USER demo@'%' IDENTIFIED BY 'demo';
GRANT ALL ON demo.* TO demo@'%';
CREATE USER demo@localhost IDENTIFIED BY 'demo';
GRANT ALL ON demo.* TO demo@localhost;