DROP TABLE email IF EXISTS;

CREATE TABLE email  (
    to_address VARCHAR(30),
    subject VARCHAR(50), 
    email_body VARCHAR(500)
);
