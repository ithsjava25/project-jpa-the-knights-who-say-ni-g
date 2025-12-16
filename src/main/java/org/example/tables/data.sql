use
blockbuster_gang;
insert into customer (firstName, lastName, email)
values ("bob", "bobson", "bobatbobmail");
INSERT INTO customer (firstName, lastName, email)
VALUES ('Bob', 'Bobson', 'bob.bobson@email.com'),
       ('Anna', 'Svensson', 'anna.svensson@email.com'),
       ('Erik', 'Johansson', 'erik.johansson@email.com'),
       ('Maria', 'Lindberg', 'maria.lindberg@email.com');


INSERT INTO movie (title, genre, duration, price, total_stock, available_stock)
VALUES ('Inception', 'Sci-Fi', 148, 39.90, 10, 10),
       ('The Dark Knight', 'Action', 152, 34.90, 8, 8),
       ('Interstellar', 'Sci-Fi', 169, 44.90, 5, 5),
       ('The Godfather', 'Crime', 175, 29.90, 6, 6),
       ('Pulp Fiction', 'Crime', 154, 27.90, 7, 7);


insert into inventory (price, quantity, movie_id, rental_id)
VALUES (200, 5, 1, 1);
