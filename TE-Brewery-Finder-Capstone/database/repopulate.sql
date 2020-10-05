BEGIN TRANSACTION;

DELETE FROM reviews;
DELETE FROM beer;
DELETE FROM breweries;
DELETE FROM users;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('brewer','$2a$10$okQpsk0Y6d.4gI6ljUQeaukzvgz2Ae8YYFnl/zWC5hzVEwGUJRi/S','ROLE_BREWER');

INSERT INTO breweries (brewery_name,contact_info,address,history,isOpen) VALUES ('Howl at the Moon Saloon','Howl Castle','667 Gravestone Avenue','We are built on an ancient burial site.',true);
INSERT INTO breweries (brewery_name,contact_info,address,history,isOpen) VALUES ('Backend Brewery','Stateless Steve','8080 Springboot Str','Its magic that were even open',false);
INSERT INTO breweries (brewery_name,contact_info,address,history,isOpen) VALUES ('The Vue','DJ Vue-X','404 Axios Avenue','We released in Febrewary 2014',true);

INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('slopz', 1, 'its sloppy, but gets Emily through the day', 'urlAAA', '.05', 'midwestern');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Howlers Growler', 1, 'I howl at the moon every time I drink this brew.', 'url_WolfmanJack', '.05', 'Amber ALe');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Shaquille ONeils Dunkelweizen', 2,'I slam this back every game day.', 'url_Lakers', '.33', 'Dunkelweizen');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Cool-thulu', 2, '...tastes like the elixer of the elder gods', 'url_AAAHHH', '.06', 'Oud Bruin');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Quiggly Gose Down Under', 3,'Literally fell head over heels for this brew.', 'url_oopsydaisey', '.06', 'Gose');

INSERT INTO reviews (user_id, title, stars, body) VALUES (16, 'YESSS', 3, 'ive ');

COMMIT TRANSACTION;