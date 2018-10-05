CREATE DATABASE SFG_DEV;
CREATE DATABASE SFG_PROD;

CREATE USER 'sfg_dev_user'@'localhost' IDENTIFIED BY '123456';
CREATE USER 'sfg_prod_user'@'localhost' identified BY '123456';

GRANT SELECT ON SFG_DEV.* TO 'sfg_dev_user'@'localhost';
GRANT UPDATE ON SFG_DEV.* TO 'sfg_dev_user'@'localhost';
GRANT DELETE ON SFG_DEV.* TO 'sfg_dev_user'@'localhost';
GRANT INSERT ON SFG_DEV.* TO 'sfg_dev_user'@'localhost';
GRANT SELECT ON SFG_PROD.* TO 'sfg_prod_user'@'localhost';
GRANT UPDATE ON SFG_PROD.* TO 'sfg_prod_user'@'localhost';
GRANT DELETE ON SFG_PROD.* TO 'sfg_prod_user'@'localhost';
GRANT INSERT ON SFG_PROD.* TO 'sfg_prod_user'@'localhost';