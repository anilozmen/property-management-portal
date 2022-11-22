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

-- USERS
INSERT INTO users (id, first_name, last_name, email, password, address_id, phone_number, user_type, deleted)
VALUES (1, 'John', 'Doe', 'john@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 1, '+1 641 111 11 11', 1, false); --123
INSERT INTO users (id, first_name, last_name, email, password, address_id, phone_number, user_type, deleted)
VALUES (2, 'Jessica', 'Doe', 'jessica@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 2, '+1 641 222 22 22', 2, false); --123
INSERT INTO users (id, first_name, last_name, email, password, address_id, phone_number, user_type, deleted)
VALUES (3, 'Jerry', 'Doe', 'jerry@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 3, '+1 641 333 33 33', 3, true); --123

-- LISTING TYPE
INSERT INTO listing_type (id, name, deleted)
VALUES (1, 'SELL', false);
INSERT INTO listing_type (id, name, deleted)
VALUES (2, 'RENT', false);

-- PROPERTY STATUS
INSERT INTO property_status (id, name, deleted)
VALUES (1, 'SOLD', false);
INSERT INTO property_status (id, name, deleted)
VALUES (2, 'ACTIVE', false);

-- PROPERTY TYPE
INSERT INTO property_type (id, name, deleted)
VALUES (1, 'APARTMENT', false);
INSERT INTO property_type (id, name, deleted)
VALUES (2, 'CONDO', false);

-- PROPERTY
INSERT INTO property (id, description, name, price, view_count, address_id, listing_type_id, owner_id, property_status_id, property_type_id, deleted)
VALUES (1, 'PROPERTY 1 DESCRIPTION', 'PROPERTY 1 NAME', 35.5, 0, 4, 1, 2, 1, 1, false);
INSERT INTO property (id, description, name, price, view_count, address_id, listing_type_id, owner_id, property_status_id, property_type_id, deleted)
VALUES (2, 'PROPERTY 2 DESCRIPTION', 'PROPERTY 2 NAME', 50.5, 356, 5, 1, 2, 2, 2, false);
INSERT INTO property (id, description, name, price, view_count, address_id, listing_type_id, owner_id, property_status_id, property_type_id, deleted)
VALUES (3, 'PROPERTY 3 DESCRIPTION', 'PROPERTY 3 NAME', 20, 120, 6, 2, 2, 2, 2, false);
