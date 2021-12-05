INSERT INTO users (name, login, password)
VALUES ('admin', 'admin', '${admin_password}');

INSERT INTO user_roles
VALUES ((SELECT user_id FROM users WHERE login = 'admin'), 1);