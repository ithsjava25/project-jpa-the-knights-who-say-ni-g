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

insert into actor (first_name, last_name) values
                                              ('Tim', 'Robbins'),
                                              ('Morgan', 'Freeman'),
                                              ('Bob', 'Gunton'),
                                              ('William', 'Sadler'),

                                              ('Marlon', 'Brando'),
                                              ('Al', 'Pacino'),
                                              ('James', 'Caan'),
                                              ('Diane', 'Keaton'),

                                              ('Christian', 'Bale'),
                                              ('Heath', 'Ledger'),
                                              ('Aaron', 'Eckhart'),
                                              ('Gary', 'Oldman'),
                                              ('Michael', 'Caine'),

                                              ('John', 'Travolta'),
                                              ('Samuel L.', 'Jackson'),
                                              ('Uma', 'Thurman'),
                                              ('Bruce', 'Willis'),

                                              ('Liam', 'Neeson'),
                                              ('Ralph', 'Fiennes'),
                                              ('Ben', 'Kingsley'),

                                              ('Elijah', 'Wood'),
                                              ('Ian', 'McKellen'),
                                              ('Viggo', 'Mortensen'),
                                              ('Sean', 'Astin'),
                                              ('Andy', 'Serkis'),

                                              ('Brad', 'Pitt'),
                                              ('Edward', 'Norton'),
                                              ('Helena', 'Bonham Carter'),

                                              ('Tom', 'Hanks'),
                                              ('Robin', 'Wright'),
                                              ('Gary', 'Sinise'),

                                              ('Leonardo', 'DiCaprio'),
                                              ('Joseph', 'Gordon-Levitt'),
                                              ('Elliot', 'Page'),
                                              ('Tom', 'Hardy'),

                                              ('Keanu', 'Reeves'),
                                              ('Laurence', 'Fishburne'),
                                              ('Carrie-Anne', 'Moss'),
                                              ('Hugo', 'Weaving'),

                                              ('Ray', 'Liotta'),
                                              ('Joe', 'Pesci'),
                                              ('Robert', 'De Niro'),

                                              ('Jodie', 'Foster'),
                                              ('Anthony', 'Hopkins'),
                                              ('Scott', 'Glenn'),

                                              ('Brad', 'Pitt'),
                                              ('Morgan', 'Freeman'),
                                              ('Gwyneth', 'Paltrow'),

                                              ('Matthew', 'McConaughey'),
                                              ('Anne', 'Hathaway'),
                                              ('Jessica', 'Chastain'),

                                              ('Song', 'Kang-ho'),
                                              ('Lee', 'Sun-kyun'),
                                              ('Cho', 'Yeo-jeong'),

                                              ('Russell', 'Crowe'),
                                              ('Joaquin', 'Phoenix'),
                                              ('Connie', 'Nielsen'),

                                              ('David', 'Morse'),
                                              ('Michael Clarke', 'Duncan'),

                                              ('Vin', 'Diesel'),
                                              ('Matt', 'Damon'),

                                              ('Miyu', 'Irino'),

                                              ('Matthew', 'Broderick'),
                                              ('Jeremy', 'Irons'),
                                              ('James Earl', 'Jones');


insert into movie_actor (movie_id, actor_id) values
-- Shawshank
(1,1),(1,2),(1,3),(1,4),

-- Godfather
(2,5),(2,6),(2,7),(2,8),

-- Dark Knight
(3,9),(3,10),(3,11),(3,12),(3,13),

-- Pulp Fiction
(4,14),(4,15),(4,16),(4,17),

-- Schindler
(5,18),(5,19),(5,20),

-- LOTR
(6,21),(6,22),(6,23),(6,24),(6,25),

-- Fight Club
(7,26),(7,27),(7,28),

-- Forrest Gump
(8,29),(8,30),(8,31),

-- Inception
(9,32),(9,33),(9,34),(9,35),

-- Matrix
(10,36),(10,37),(10,38),(10,39),

-- Goodfellas
(11,40),(11,41),(11,42),

-- Silence of the Lambs
(12,43),(12,44),(12,45),

-- Se7en
(13,46),(13,47),(13,48),

-- Interstellar
(14,49),(14,50),(14,51),

-- Parasite
(15,52),(15,53),(15,54),

-- Gladiator
(16,55),(16,56),(16,57),

-- Green Mile
(17,29),(17,58),(17,59),

-- Saving Private Ryan
(18,29),(18,60),(18,61),

-- Spirited Away
(19,62),

-- Lion King
(20,63),(20,64),(20,65);

-- Select fråga för test

select m.title,
       group_concat(concat(a.first_name,' ',a.last_name) separator ', ') as actors
from movie m
         join movie_actor ma on m.item_id = ma.movie_id
         join actor a on a.actor_id = ma.actor_id
group by m.title;

