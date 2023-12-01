DROP DATABASE IF EXISTS laboration02;
CREATE DATABASE laboration02;
USE laboration02;

DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS place;
DROP TABLE IF EXISTS user;

CREATE TABLE category (
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    symbol varchar(255) CHARSET utf8mb4 COLLATE utf8mb4_bin,
    description varchar(255)
);

CREATE TABLE user (
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(255) NOT NULL
);

CREATE TABLE place (
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    category_id int NOT NULL,
    user_id int NOT NULL,
    status ENUM('private', 'public') NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    description varchar(255),
    coordinates GEOMETRY NOT NULL SRID 4326,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO category (name, symbol, description)
VALUES
    ('Svampställen', '🍄', 'Mina favorit svampställen i de svenska skogarna'),
    ('Blåbär', '🫐', 'Det blåa guldet!'),
    ('Snabbmat', '🥡', 'Bästa snabbmatställena i Dalarna!'),
    ('Turistmål','🗺️', 'Inte äta turisterna, titta på roliga grejer');

INSERT INTO user (name)
VALUES
    ('Kråkan'),
    ('Bengan'),
    ('Burre'),
    ('Mackan');

INSERT INTO place (name, category_id, user_id, status, description, coordinates)
VALUES
    ('Orsa Kantareller', (SELECT id FROM category WHERE category.name = 'Svampställen'), (SELECT id FROM user WHERE user.name = 'Mackan'), 'private', 'Kantareller i Orsa', ST_GeomFromText('POINT(61.168742 14.797812)', 4326)),
    ('Orsa Flugvvamp', (SELECT id FROM category WHERE category.name = 'Svampställen'), (SELECT id FROM user WHERE user.name = 'Burre'), 'public', 'Flugsvamp i Orsa', ST_GeomFromText('POINT(61.175571 14.859953)', 4326)),
    ('Orsa Blåbär', (SELECT id FROM category WHERE category.name = 'Blåbär'), (SELECT id FROM user WHERE user.name = 'Kråkan'), 'public', 'Blåbär som fan', ST_GeomFromText('POINT(61.190464 14.871283)', 4326)),
    ('Orsa Blåbär', (SELECT id FROM category WHERE category.name = 'Blåbär'), (SELECT id FROM user WHERE user.name = 'Kråkan'), 'public', 'Inte så mycket men helt ok', ST_GeomFromText('POINT(61.204855 14.812317)', 4326)),
    ('Orsa Blåbär', (SELECT id FROM category WHERE category.name = 'Blåbär'), (SELECT id FROM user WHERE user.name = 'Kråkan'), 'private', 'Bästa stället, super hemligt!', ST_GeomFromText('POINT(61.194311 14.771633)', 4326)),
    ('Hellvetesfallet', (SELECT id FROM category WHERE category.name = 'Turistmål'), (SELECT id FROM user WHERE user.name = 'Bengan'), 'public', 'Fintställe, anpassat till personer med funktionsvariationer', ST_GeomFromText('POINT(61.203367 14.736786)', 4326)),
    ('Stor Stupet', (SELECT id FROM category WHERE category.name = 'Turistmål'), (SELECT id FROM user WHERE user.name = 'Bengan'), 'public', 'Fintställe, anpassat till personer med funktionsvariationer', ST_GeomFromText('POINT(61.209774 14.737988)', 4326)),
    ('Stenslips museét', (SELECT id FROM category WHERE category.name = 'Turistmål'), (SELECT id FROM user WHERE user.name = 'Burre'), 'public', 'Helt ok, ganska ok mat', ST_GeomFromText('POINT(61.184094 14.771461)', 4326)),
    ('Amores Pizzeria', (SELECT id FROM category WHERE category.name = 'Snabbmat'), (SELECT id FROM user WHERE user.name = 'Burre'), 'public', 'Har vegan pizza och kebab, bra ställe!', ST_GeomFromText('POINT(61.118703 14.619348)', 4326)),
    ('Max', (SELECT id FROM category WHERE category.name = 'Snabbmat'), (SELECT id FROM user WHERE user.name = 'Mackan'), 'public', 'Max är Max', ST_GeomFromText('POINT(61.006615 14.605186)', 4326));

