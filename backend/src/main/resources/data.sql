-- ADDRESS
INSERT INTO address (id, address_1, address_2, city, state, zip_code)
VALUES (1, '1000N 4TH ST BLD 140', null, 'Fairfield', 'Iowa', '52557');

INSERT INTO address (id, address_1, address_2, city, state, zip_code)
VALUES (2, '1000N 4TH ST BLD 141', 'MR 1000', 'Fairfield', 'Iowa', '52557');

INSERT INTO address (id, address_1, address_2, city, state, zip_code)
VALUES (3, '1000N 4TH ST BLD 142', null, 'Fairfield', 'Iowa', '52557');

INSERT INTO address (id, address_1, address_2, city, state, zip_code)
VALUES (4, 'SOMEWHERE IN FL', null, 'Sarasota', 'Florida', '33133');

INSERT INTO address (id, address_1, address_2, city, state, zip_code)
VALUES (5, 'SOMEWHERE IN XXX', null, 'Sarasota', 'Florida', '33133');

INSERT INTO address (id, address_1, address_2, city, state, zip_code)
VALUES (6, 'SOMEWHERE IN YYY', null, 'Sarasota', 'Florida', '33133');

-- USERS
INSERT INTO users (id, first_name, last_name, email, password, address_id, phone_number, user_type)
VALUES (1, 'John', 'Doe', 'john@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 1, '+1 641 111 11 11', 1); --123
INSERT INTO users (id, first_name, last_name, email, password, address_id, phone_number, user_type)
VALUES (2, 'Jessica', 'Doe', 'jessica@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 2, '+1 641 222 22 22', 2); --123
INSERT INTO users (id, first_name, last_name, email, password, address_id, phone_number, user_type)
VALUES (3, 'Jerry', 'Doe', 'jerry@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 3, '+1 641 333 33 33', 3); --123

-- LISTING TYPE
INSERT INTO listing_type (id, name)
VALUES (1, 'SELL');
INSERT INTO listing_type (id, name)
VALUES (2, 'RENT');

-- PROPERTY STATUS
INSERT INTO property_status (id, name)
VALUES (1, 'SOLD');
INSERT INTO property_status (id, name)
VALUES (2, 'ACTIVE');

-- PROPERTY TYPE
INSERT INTO property_type (id, name)
VALUES (1, 'APARTMENT');
INSERT INTO property_type (id, name)
VALUES (2, 'CONDO');

-- PROPERTY
INSERT INTO property (id, description, name, price, view_count, address_id, listing_type_id, owner_id, property_status_id, property_type_id)
VALUES (1, 'PROPERTY 1 DESCRIPTION', 'PROPERTY 1 NAME', 35.5, 0, 4, 1, 2, 1, 1);
INSERT INTO property (id, description, name, price, view_count, address_id, listing_type_id, owner_id, property_status_id, property_type_id)
VALUES (2, 'PROPERTY 2 DESCRIPTION', 'PROPERTY 2 NAME', 50.5, 356, 5, 1, 2, 2, 2);
INSERT INTO property (id, description, name, price, view_count, address_id, listing_type_id, owner_id, property_status_id, property_type_id)
VALUES (3, 'PROPERTY 3 DESCRIPTION', 'PROPERTY 3 NAME', 20, 120, 6, 2, 2, 2, 2);
