INSERT INTO authority(
    id, timestamp_created, timestamp_modified, name)
VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2004-10-19 10:23:54', '2004-10-19 10:23:54', 'ROLE_USER');

INSERT INTO authority(
    id, timestamp_created, timestamp_modified, name)
VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380b98', '2004-10-19 10:23:54', '2004-10-19 10:23:54', 'ROLE_ADMIN');

INSERT INTO users(
    id, timestamp_created, timestamp_modified, email, first_name, last_name, password)
VALUES ('2ef3b543-af2a-47e4-9dec-97a8e10eeaec', '2021-11-04 14:23:30', '2021-11-04 14:23:30', 'admin', 'Admin', 'KMS Connect', '$2a$10$WcU8924k3irn3/3zdfoJZe0rcPgVHmIVCQKfoJjeymBH2CTfhUjVe');

INSERT INTO authority_role(user_id, authority_id)
VALUES ('2ef3b543-af2a-47e4-9dec-97a8e10eeaec', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
       ('2ef3b543-af2a-47e4-9dec-97a8e10eeaec', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380b98');