DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);



INSERT INTO meals (user_id, date_time, description, calories) VALUES
  (100001, '2017-07-30 10:00:00', 'Завтрак', 300),
  (100001, '2017-07-30 13:00:00', 'Обед', 700),
  (100001, '2017-07-30 20:00:00', 'Ужин', 1000),
  (100000, '2017-07-31 10:00:00', 'Завтрак', 500),
  (100000, '2017-07-31 13:00:00', 'Обед', 1000),
  (100000, '2017-07-31 20:00:00', 'Ужин', 510),
  (100000, '2017-07-01 10:00:00', 'Завтрак', 700),
  (100000, '2017-07-01 14:00:00', 'Обед', 1000),
  (100000, '2017-07-01 21:00:00', 'Ужин', 700);
