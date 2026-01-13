use blockbuster_gang;
insert into movie (title, genre, price)
values ('The Shawshank Redemption', 'Drama', 79),
       ('The Godfather', 'Crime', 99),
       ('The Dark Knight', 'Action', 49),
       ('Pulp Fiction', 'Crime', 29),
       ('Schindler''s List', 'History', 79),
       ('The Lord of the Rings: The Return of the King', 'Fantasy', 99),
       ('Fight Club', 'Drama', 49),
       ('Forrest Gump', 'Romance', 29),
       ('Inception', 'Sci-Fi', 79),
       ('The Matrix', 'Sci-Fi', 49),
       ('Goodfellas', 'Crime', 29),
       ('The Silence of the Lambs', 'Thriller', 79),
       ('Se7en', 'Mystery', 49),
       ('Interstellar', 'Sci-Fi', 99),
       ('Parasite', 'Drama', 99),
       ('Gladiator', 'Action', 79),
       ('The Green Mile', 'Fantasy', 49),
       ('Saving Private Ryan', 'War', 29),
       ('Spirited Away', 'Animation', 99),
       ('The Lion King', 'Animation', 49);

# INSERT INTO customer (firstname, lastname, email)
# VALUES ('Anna', 'Andersson', 'anna.andersson@test.se');
# SET @customer_id = LAST_INSERT_ID();
#
# INSERT INTO rental (rentalDate, customer_id)
# VALUES (
#            NOW(),
#            @customer_id
#        );
#
# SET @rental_id = LAST_INSERT_ID();
#
# SELECT itemId, title FROM movie LIMIT 2;
#
# SET @movie1_id = 1;
# SET @movie2_id = 2;
#
#
# INSERT INTO movie_rental (rental_id, movie_id)
# VALUES
#     (@rental_id, @movie1_id),
#     (@rental_id, @movie2_id);
#
# select * from movie m
# join movie_rental mr on m.itemId = mr.movie_id
# join rental r on mr.rental_id = r.rentalId
# where r.customer_id = 3;
#
# delete from customer where customerId = 2;
