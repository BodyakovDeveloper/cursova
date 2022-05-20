DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

CREATE TABLE roles (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY ,
    name VARCHAR(32) unique,
    primary key (id)
);

CREATE TABLE users (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY,
    birthday DATE,
    email VARCHAR(32) unique,
    firstName VARCHAR(32),
    lastName VARCHAR(32),
    login VARCHAR(32) unique,
    password VARCHAR(100),
    roleEntity_id INTEGER,
    primary key (id),
    FOREIGN KEY (roleEntity_id) REFERENCES roles
);

INSERT INTO roles (id, name) values (default, 'ADMIN');
INSERT INTO roles (id, name) values (default, 'USER');

INSERT INTO users (id, birthday, email, firstName, lastName, login, password, roleEntity_id)
VALUES (default, '2010-01-01', 'emailadmin@gmail.com', 'Bohdan', 'Koval', 'admin', '$2a$12$J9cUMHWgREfgm/KYyvbug.zthJu1rq0L/N9G.DXXXWJjdq4pyxn1y', 1);

INSERT INTO users (id, birthday, email, firstName, lastName, login, password, roleEntity_id)
VALUES (default, '2010-01-01', 'batsunov@gmail.com', 'Dima', 'Batsunov', 'batsunov', '$2a$12$4mzYUggiLflK82KMK5yB4uwO5BUj.pCTVoIBdtmEq.OtY9VmvRuSa', 2);

INSERT INTO users (id, birthday, email, firstName, lastName, login, password, roleEntity_id)
VALUES (default,'2010-01-01', 'poberezhnik@gmail.com', 'Andriy', 'Poberezhnik', 'poberezhnik', '$2a$12$4mzYUggiLflK82KMK5yB4uwO5BUj.pCTVoIBdtmEq.OtY9VmvRuSa', 2);

INSERT INTO users (id, birthday, email, firstName, lastName, login, password, roleEntity_id)
VALUES (default, '2010-01-01', 'user@gmail.com', 'Putin', 'Huylo', 'user', '$2a$12$4mzYUggiLflK82KMK5yB4uwO5BUj.pCTVoIBdtmEq.OtY9VmvRuSa', 2);
