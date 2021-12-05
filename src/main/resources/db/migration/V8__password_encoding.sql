CREATE EXTENSION IF NOT EXISTS pgcrypto;

ALTER TABLE users ALTER COLUMN password TYPE varchar;
UPDATE users SET password = crypt(password, gen_salt('bf', 8));