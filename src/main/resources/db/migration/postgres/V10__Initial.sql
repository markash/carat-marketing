DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS positioning;
DROP TABLE IF EXISTS campaign_type;

CREATE TABLE IF NOT EXISTS category (
  id SERIAL UNIQUE PRIMARY KEY,
  name VARCHAR(100),
  created TIMESTAMP NULL,
  updated TIMESTAMP NULL,
  UNIQUE (name)
);

INSERT INTO category (name) VALUES ('Local worth more');
INSERT INTO category (name) VALUES ('Mainstream');
INSERT INTO category (name) VALUES ('Affordable');
INSERT INTO category (name) VALUES ('Opaque');
INSERT INTO category (name) VALUES ('AFB');

CREATE TABLE IF NOT EXISTS positioning (
  id SERIAL UNIQUE PRIMARY KEY,
  name VARCHAR(100),
  created TIMESTAMP NULL,
  updated TIMESTAMP NULL,
  UNIQUE (name)
);

INSERT INTO positioning (name) VALUES ('Pride in Origins');
INSERT INTO positioning (name) VALUES ('Bring us Together');
INSERT INTO positioning (name) VALUES ('Masculine  Character');

CREATE TABLE IF NOT EXISTS property (
  id SERIAL UNIQUE PRIMARY KEY,
  name VARCHAR(100),
  created TIMESTAMP NULL,
  updated TIMESTAMP NULL,
  UNIQUE (name)
);

INSERT INTO property (name) VALUES ('Music');
INSERT INTO property (name) VALUES ('Soccer');
INSERT INTO property (name) VALUES ('Rugby');
INSERT INTO property (name) VALUES ('Tradition');

CREATE TABLE IF NOT EXISTS media_type (
  id SERIAL UNIQUE PRIMARY KEY,
  name VARCHAR(100),
  created TIMESTAMP NULL,
  updated TIMESTAMP NULL,
  UNIQUE (name)
);

INSERT INTO media_type (name) VALUES ('TV');
INSERT INTO media_type (name) VALUES ('Radio');
INSERT INTO media_type (name) VALUES ('Outdoor');
INSERT INTO media_type (name) VALUES ('Print');
INSERT INTO media_type (name) VALUES ('Digital');
INSERT INTO media_type (name) VALUES ('Poster');
INSERT INTO media_type (name) VALUES ('POSM');

CREATE TABLE IF NOT EXISTS brand (
  id SERIAL UNIQUE PRIMARY KEY,
  name VARCHAR(100),
  created TIMESTAMP NULL,
  updated TIMESTAMP NULL,
  UNIQUE (name)
);

INSERT INTO brand (name) VALUES ('2M');
INSERT INTO brand (name) VALUES ('Balimi');

CREATE TABLE IF NOT EXISTS campaign_type (
  id SERIAL UNIQUE PRIMARY KEY,
  name VARCHAR(100),
  created TIMESTAMP NULL,
  updated TIMESTAMP NULL,
  UNIQUE (name)
);

INSERT INTO campaign_type (name) VALUES ('Emotional campaign');
INSERT INTO campaign_type (name) VALUES ('Intrinsics campaign');
INSERT INTO campaign_type (name) VALUES ('Sponsorship campaign');
INSERT INTO campaign_type (name) VALUES ('Innovation campaign');

CREATE TABLE IF NOT EXISTS document (
  id SERIAL UNIQUE PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  comments VARCHAR(255),
  attach_file VARCHAR(255),
  creative_claim VARCHAR(255),
  category_id INT REFERENCES category (id),
  brand_id INT REFERENCES  brand (id),
  media_type_id INT REFERENCES media_type (id),
  property_id INT REFERENCES property (id),
  campaign_type_id INT REFERENCES campaign_type (id),
  positioning_id INT references positioning (id),
  created TIMESTAMP NULL,
  updated TIMESTAMP NULL,
  UNIQUE (name)
);
