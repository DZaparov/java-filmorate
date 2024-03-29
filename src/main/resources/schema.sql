DROP TABLE IF EXISTS mpa CASCADE;
DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS friends CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS genre CASCADE;

CREATE TABLE IF NOT EXISTS mpa (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  mpa_name varchar(10)
);

CREATE TABLE IF NOT EXISTS genre (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  genre_name varchar(20)
);

CREATE TABLE IF NOT EXISTS films (
  id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(50) NOT NULL,
  description varchar(200),
  releaseDate date NOT NULL,
  duration integer NOT NULL,
  mpa_id integer REFERENCES mpa(id)
);

CREATE TABLE IF NOT EXISTS users (
  id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  email varchar(50),
  login varchar(50) NOT NULL,
  name varchar(50),
  birthday date
);

CREATE TABLE IF NOT EXISTS likes (
  film_id bigint REFERENCES films (id),
  user_id bigint REFERENCES users (id),
  primary key(user_id, film_id)
);

CREATE TABLE IF NOT EXISTS friends (
  following_user_id bigint REFERENCES users (id),
  followed_user_id bigint REFERENCES users (id),
  primary key(following_user_id, followed_user_id)
);

CREATE TABLE IF NOT EXISTS genres (
  film_id bigint REFERENCES films (id),
  genre_id integer REFERENCES genre (id),
  primary key(film_id, genre_id)
);