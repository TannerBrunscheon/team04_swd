DROP TABLE IF EXISTS addresses;

CREATE TABLE addresses
(
  addressid SERIAL PRIMARY KEY NOT NULL,
  firstname VARCHAR(15) NOT NULL,
  lastname VARCHAR(30) NOT NULL,
  email VARCHAR(30) NOT NULL,
  phonenumber VARCHAR(15) NOT NULL
);

INSERT INTO addresses (firstname,lastname,email,phonenumber)
  VALUES ('Hans','Johnson','hans-johnson@uiowa.edu','319-621-7185');

INSERT INTO addresses (firstname,lastname,email,phonenumber)
VALUES ('Hans','Johnson','hans-johnson@uiowa.edu','319-621-7185');

INSERT INTO addresses (firstname,lastname,email,phonenumber)
VALUES ('Bob','Allen','bob-allen@uiowa.edu','319-621-5555');


INSERT INTO addresses (firstname,lastname,email,phonenumber)
VALUES ('Jane','Doe','jd@uiowa.edu','444-333-5555');

INSERT INTO addresses (firstname, lastname, email, phonenumber)
    VALUES ('Santa','Claus','sc@np.com','222-333-1234');

SELECT * FROM addresses;