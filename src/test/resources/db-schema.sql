SET MODE POSTGRESQL;

CREATE TABLE country (
  id        INT AUTO_INCREMENT,
  name      VARCHAR(255),
  code_name VARCHAR(255),

  PRIMARY KEY (id)
);