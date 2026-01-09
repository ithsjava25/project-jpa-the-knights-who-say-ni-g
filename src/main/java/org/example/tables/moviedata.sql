use blockbuster_gang;
insert into movie(title, genre)
values ('The Shawshank Redemption', 'Drama'),
       ('The Godfather', 'Crime'),
       ('The Dark Knight', 'Action'),
       ('Pulp Fiction', 'Crime'),
       ('Schindler''s List', 'History'),
       ('The Lord of the Rings: The Return of the King', 'Fantasy'),
       ('Fight Club', 'Drama'),
       ('Forrest Gump', 'Romance'),
       ('Inception', 'Sci-Fi'),
       ('The Matrix', 'Sci-Fi'),
       ('Goodfellas', 'Crime'),
       ('The Silence of the Lambs', 'Thriller'),
       ('Se7en', 'Mystery'),
       ('Interstellar', 'Sci-Fi'),
       ('Parasite', 'Drama'),
       ('Gladiator', 'Action'),
       ('The Green Mile', 'Fantasy'),
       ('Saving Private Ryan', 'War'),
       ('Spirited Away', 'Animation'),
       ('The Lion King', 'Animation');

INSERT INTO customer (firstname, lastname, email)
VALUES ('Anna', 'Andersson', 'anna.andersson@test.se');
SET @customer_id = LAST_INSERT_ID();

INSERT INTO rental (rentalDate, customer_id)
VALUES (
           NOW(),
           @customer_id
       );

SET @rental_id = LAST_INSERT_ID();

SELECT itemId, title FROM movie LIMIT 2;

SET @movie1_id = 1;
SET @movie2_id = 2;


INSERT INTO movie_rental (rental_id, movie_id)
VALUES
    (@rental_id, @movie1_id),
    (@rental_id, @movie2_id);

select * from movie m
join movie_rental mr on m.itemId = mr.movie_id
join rental r on mr.rental_id = r.rentalId
where r.customer_id = 3;

delete from customer where customerId = 2;
