CREATE DATABASE paramount;

USE paramount;

CREATE TABLE employee(
	id INT(10) NOT NULL,
	name VARCHAR(150) NOT NULL,
	department VARCHAR(150) NOT NULL,
	salary DOUBLE NOT NULL,
	PRIMARY KEY (id)
);

INSERT INTO employee VALUES (1001,'Jose', 'Technology', 40000.67);
INSERT INTO employee VALUES (1002,'John', 'Human Resource', 20000.00);
INSERT INTO employee VALUES (1003,'Jane', 'Delivery', 80000.00);