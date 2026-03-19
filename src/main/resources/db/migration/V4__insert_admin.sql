INSERT INTO user (uid, password)
VALUES (
    'admin',
    '{bcrypt}$2a$10$RynkqY4gXZKs4eC9.sqfoeqbdlBVZ7wmrPVw.VA2NoGHx3aVJcfBC'
);

SET @admin_id = LAST_INSERT_ID();

INSERT INTO user_roles (user_id, role)
VALUES (
    @admin_id,
    'ROLE_ADMIN'
);