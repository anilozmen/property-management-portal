-- ADDRESS
INSERT INTO address (id, address_1, address_2, city, state, zip_code, deleted)
VALUES (1, '1000N 4TH ST BLD 140', null, 'Fairfield', 'Iowa', '52557', false);

INSERT INTO address (id, address_1, address_2, city, state, zip_code, deleted)
VALUES (2, '1000N 4TH ST BLD 141', 'MR 1000', 'Fairfield', 'Iowa', '52557', false);

INSERT INTO address (id, address_1, address_2, city, state, zip_code, deleted)
VALUES (3, '1000N 4TH ST BLD 142', null, 'Fairfield', 'Iowa', '52557', false);

INSERT INTO address (id, address_1, address_2, city, state, zip_code, deleted)
VALUES (4, 'SOMEWHERE IN FL', null, 'Sarasota', 'Florida', '33133', false);

INSERT INTO address (id, address_1, address_2, city, state, zip_code, deleted)
VALUES (5, 'SOMEWHERE IN XXX', null, 'Sarasota', 'Florida', '33133', false);

INSERT INTO address (id, address_1, address_2, city, state, zip_code, deleted)
VALUES (6, 'SOMEWHERE IN YYY', null, 'Sarasota', 'Florida', '33133', false);

INSERT INTO address (id, address_1, address_2, city, state, zip_code, deleted)
VALUES (7, 'SOMEWHERE IN ZZZ', null, 'Sarasota', 'Florida', '33133', false);

SELECT pg_catalog.setval('public.address_id_seq', 7, true);

-- USERS
INSERT INTO users (id, first_name, last_name, email, password, address_id, phone_number, user_type, deleted)
VALUES (1, 'John', 'Doe', 'john@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 1,
        '+1 641 111 11 11', 'ADMIN', false); --123
INSERT INTO users (id, is_email_verified, first_name, last_name, email, password, address_id, phone_number, user_type,
                   deleted)
VALUES (2, true, 'Jessica', 'Doe', 'jessica@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 2,
        '+1 641 222 22 22', 'OWNER', false); --123
INSERT INTO users (id, first_name, last_name, email, password, address_id, phone_number, user_type, deleted)
VALUES (3, 'Jerry', 'Doe', 'jerry@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 3,
        '+1 641 333 33 33', 'CUSTOMER', true); --123

INSERT INTO users (id, first_name, last_name, email, password, address_id, phone_number, user_type, deleted,
                   is_email_verified)
VALUES (4, 'Madhav', 'Thapa', 'madhav@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 3,
        '+1 641 333 33 33', 'OWNER', false, true); --123

INSERT INTO users (id, first_name, last_name, email, password, address_id, phone_number, user_type, deleted,
                   is_email_verified)
VALUES (5, 'Anil', 'Oz', 'anil@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 3,
        '+1 641 333 33 33', 'CUSTOMER', false, true); --123


SELECT pg_catalog.setval('public.users_id_seq', 5, true);

-- -- LISTING TYPE
-- INSERT INTO listing_type (id, name, deleted)
-- VALUES (1, 'SELL', false);
-- INSERT INTO listing_type (id, name, deleted)
-- VALUES (2, 'RENT', false);

-- SELECT pg_catalog.setval('public.listing_type_id_seq', 2, true);

-- -- PROPERTY STATUS
-- INSERT INTO property_status (id, name, deleted)
-- VALUES (1, 'SOLD', false);
-- INSERT INTO property_status (id, name, deleted)
-- VALUES (2, 'ACTIVE', false);
-- SELECT pg_catalog.setval('public.property_status_id_seq', 2, true);

-- PROPERTY TYPE
-- INSERT INTO property_type (id, name, deleted)
-- VALUES (1, 'APARTMENT', false);
-- INSERT INTO property_type (id, name, deleted)
-- VALUES (2, 'CONDO', false);
--
-- SELECT pg_catalog.setval('public.property_type_id_seq', 2, true);

-- PROPERTY
INSERT INTO property (id, description, name, price, view_count, address_id, listing_type, owner_id, property_status,
                      property_type, deleted)
VALUES (1, 'PROPERTY 1 DESCRIPTION', 'PROPERTY 1 NAME', 35.5, 0, 4, 'SALE', 2, 'AVAILABLE', 'HOUSE', false);
INSERT INTO property (id, description, name, price, view_count, address_id, listing_type, owner_id, property_status,
                      property_type, deleted)
VALUES (2, 'PROPERTY 2 DESCRIPTION', 'PROPERTY 2 NAME', 50.5, 356, 5, 'SALE', 2, 'AVAILABLE', 'CONDO', false);
INSERT INTO property (id, description, name, price, view_count, address_id, listing_type, owner_id, property_status,
                      property_type, deleted)
VALUES (3, 'PROPERTY 3 DESCRIPTION', 'PROPERTY 3 NAME', 20, 120, 6, 'RENT', 2, 'AVAILABLE', 'APARTMENT', false);

SELECT pg_catalog.setval('public.property_id_seq', 3, true);


INSERT INTO offer (id, message, status, amount, customer_id, property_id)
VALUES (1, 'I would like to buy this please', 'CREATED', 35, 3, 1);
INSERT INTO offer (id, message, status, amount, customer_id, property_id)
VALUES (2, 'I would like to buy this please', 'CANCELLED', 35, 3, 1);
INSERT INTO offer (id, message, status, amount, customer_id, property_id)
VALUES (3, 'I would like to buy this please', 'REJECTED', 35, 3, 1);
INSERT INTO offer (id, message, status, amount, customer_id, property_id)
VALUES (4, 'I would like to buy this please', 'APPROVED', 35, 3, 3);
INSERT INTO offer (id, message, status, amount, customer_id, property_id)
VALUES (5, 'CUSTOMER, I am. Buy this, I want to.', 'CREATED', 35, 4, 3);

SELECT pg_catalog.setval('public.offer_id_seq', 5, true);


-- MESSAGES
INSERT INTO messages (id, message,reply, property_id, receiver_id, sender_id)
VALUES (1, 'message from customer anil to madhav property 1','message back to anil from madhav property 1', 1, 4, 5);
INSERT INTO messages (id, message, property_id, receiver_id, sender_id)
VALUES (2, 'message to owner madhav from anil customer property 1', 1, 4, 5);

INSERT INTO messages (id, message,reply, property_id, receiver_id, sender_id)
VALUES (3, 'message from customer anil to madhav property 2','message back to anil from madhav property 2', 2, 4, 5);
INSERT INTO messages (id, message, property_id, receiver_id, sender_id)
VALUES (4, 'message to owner madhav from anil customer property 2', 2, 4, 5);

SELECT pg_catalog.setval('public.property_id_seq', 3, true);
