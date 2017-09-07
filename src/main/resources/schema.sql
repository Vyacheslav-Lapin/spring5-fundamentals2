SET MODE POSTGRESQL;

CREATE TABLE country (
  id        INT AUTO_INCREMENT,
  name      VARCHAR(255),
  code_name VARCHAR(255),

  PRIMARY KEY (id)
);

CREATE TABLE user (
  id        INT AUTO_INCREMENT,
  firstname VARCHAR(20),
  lastname  VARCHAR(30),

  PRIMARY KEY (id)
);
