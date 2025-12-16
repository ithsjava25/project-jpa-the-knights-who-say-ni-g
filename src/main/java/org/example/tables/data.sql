use
    blockbuster_gang;

-- 1. CUSTOMER
INSERT INTO customer (firstName, lastName, email)
VALUES ('Sara', 'Johansson', 'sara.j@test.com'),
       ('Erik', 'Andersson', 'erik.a@test.com');

-- 2. DIRECTOR (Regissör)
INSERT INTO director (firstName, lastName)
VALUES ('Christopher', 'Nolan'),
       ('Greta', 'Gerwig'),
       ('Lana', 'Wachowski');

-- 3. ACTOR (Skådespelare)
INSERT INTO actor (firstName, lastName, dateOfBirth)
VALUES ('Ryan', 'Gosling', '1980-11-12'),
       ('Margot', 'Robbie', '1990-07-02'),
       ('Keanu', 'Reeves', '1964-09-02');

-- 4. MOVIE (Film)
INSERT INTO movie (title, duration, genre, total_stock, available_stock) VALUES
                                               ('The Matrix', 136, 'Sci-Fi/Action', 7,3),
                                               ('Interstellar', 169, 'Sci-Fi/Adventure',3,3),
                                               ('Barbie', 114, 'Comedy/Fantasy',4,3),
                                               ('Dunkirk', 106, 'War/Drama',3,3);



-- 5. MOVIE_DIRECTOR (Kopplar Film till Regissör)
-- M:M-relationen kräver denna kopplingstabell

INSERT INTO movie_director (movie_id, director_id) VALUES
                                                       (1, 3),  -- The Matrix (Lana Wachowski)
                                                       (2, 1),  -- Interstellar (Nolan)
                                                       (3, 2),  -- Barbie (Gerwig)
                                                       (4, 1);  -- Dunkirk (Nolan)

-- 6. MOVIE_ACTOR (Kopplar Film till Skådespelare)

INSERT INTO movie_actor (movie_id, actor_id) VALUES
                                                 (1, 3),  -- The Matrix (Keanu Reeves)
                                                 (3, 1),  -- Barbie (Ryan Gosling)
                                                 (3, 2);  -- Barbie (Margot Robbie)

-- 7. INVENTORY (Lager/Exemplar, om ni använder Inventory-tabellen)
-- Kopplar exemplar till film-ID.
-- OBS: Denna tabell användes inte i din Movierental-design, men är bra för test:
/*
INSERT INTO inventory (movie_id, status) VALUES
(2, 'Available'),  -- Interstellar exemplar A
(2, 'Rented'),     -- Interstellar exemplar B
(3, 'Available');  -- Barbie exemplar A
*/


-- 8. RENTAL (Huvudtransaktion)

-- Sara (ID 1) hyr filmer idag
INSERT INTO rental (customer_id, rentalDate, returnDate) VALUES
    (1, NOW(), '2025-12-23 10:00:00');
-- Denna hyra får ID 1

-- 9. MOVIE_RENTAL (Kopplar hyran till filmer)

-- Sara hyr The Matrix (ID 1) och Interstellar (ID 2) i samma transaktion (Rental ID 1)
INSERT INTO movie_rental (movie_id, rental_id) VALUES
                                                   (1, 1),
                                                   (2, 1);


