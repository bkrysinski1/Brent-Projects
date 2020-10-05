BEGIN TRANSACTION;

DROP TABLE IF EXISTS brewers;
DROP TABLE IF EXISTS reviews;
DROP SEQUENCE IF EXISTS seq_review_id;
DROP TABLE IF EXISTS beer;
DROP SEQUENCE IF EXISTS seq_beer_id;
DROP TABLE IF EXISTS breweries;
DROP SEQUENCE IF EXISTS seq_brewery_id;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;

--USER TABLE 
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('brewer','$2a$10$okQpsk0Y6d.4gI6ljUQeaukzvgz2Ae8YYFnl/zWC5hzVEwGUJRi/S','ROLE_BREWER');
INSERT INTO users (username,password_hash,role) VALUES ('Punky Brewster','$2a$10$.j5.Z50XUC3FvAi3IBhTIOaASGffmWze3cNkFO/H62LUnLNPIdYvq','ROLE_BREWER');
INSERT INTO users (username,password_hash,role) VALUES ('Homer Simpson','$2a$10$XtwDiGALXC6JqM.0GxjcDue82pNNTBz.cXTKXUckZAFeA6W.5l1ym','ROLE_USER');

--BREWERY TABLE
CREATE SEQUENCE seq_brewery_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE TABLE breweries (
	brewery_id int DEFAULT nextval('seq_brewery_id'::regclass) NOT NULL,
	brewery_name varchar(50) NOT NULL,
	contact_info varchar(100),
	open_time varchar(25),
	close_time varchar(25),
	address varchar(100),
	history varchar(1000),
	isOpen boolean,
	CONSTRAINT PK_brewery PRIMARY KEY (brewery_id)
);

INSERT INTO breweries (brewery_name,contact_info,address,history,isOpen,open_time,close_time) VALUES ('Howl at the Moon Saloon','Howling Mad Murdock','667 Gravestone Avenue','We offer the ambiance of a true graveyard because we''re built on one',true,'23:00','8:00');
INSERT INTO breweries (brewery_name,contact_info,address,history,isOpen,open_time,close_time) VALUES ('Backend Brewery','Stateless Steve','8080 Springboot Str','It is magic we''re even open',false,'12:34','5:36');
INSERT INTO breweries (brewery_name,contact_info,address,history,isOpen,open_time,close_time) VALUES ('The Vue','DJ Vue-X','404 Axios Avenue','We released in Febrewary 2014',true,'9:54','20:21');
INSERT INTO breweries (brewery_name,contact_info,address,history,isOpen,open_time,close_time) VALUES ('Brew Gentlemen','Mr. G. Brewman','512 Braddock Ave. Braddock, PA 15104','Brew Gentlemen aims to create soft, balanced and elegant beers and meaningful experiences while helping revitalize the historic steel town of Braddock, Pennsylvania.',true,'15:00','19:00');
INSERT INTO breweries (brewery_name,contact_info,address,history,isOpen,open_time,close_time) VALUES ('Voodoo Brewery','voodoobrewery.com','205 E. 9th Avenue, Homestead, PA 15120','Mardi Gras for your mouth.',true,'16:00','22:00');
INSERT INTO breweries (brewery_name,contact_info,address,history,isOpen) VALUES ('Weyerbacher Brewing Co.','weyerbacher.com','905-G Line Street, Easton, PA 18042','This is a place we call home. Some call it Homestead.',true);
INSERT INTO breweries (brewery_name,contact_info,address,history,isOpen) VALUES ('Brent''s Beers','ogbrent.com','235 Oak Hill Dr Pittsburgh, PA, 15239','Brent''s Beers embodies bodacious bouquets and bodes the best imbibable brews in the ''burg',true);

--BEER TABLE
CREATE SEQUENCE seq_beer_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE TABLE beer (
	beer_id int DEFAULT nextval('seq_beer_id'::regclass) NOT NULL,
	brewery_id int NOT NULL,
	beer_name varchar(100) NOT NULL,
	description varchar(300) NOT NULL,
	url varchar(100) NOT NULL,
	abv varchar(5) NOT NULL,
	beer_type varchar(25),
	CONSTRAINT PK_beer PRIMARY KEY (beer_id),
	CONSTRAINT FK_brewery FOREIGN KEY (brewery_id) REFERENCES breweries (brewery_id)
);

INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Slopz', 2, 'It is sloppy, but gets you through the day', 'urlAAA', '15%', 'midwestern');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Howlers Growler', 1,  'Drinking this Ale will make you want to howl at the moon.', 'url_WolfmanJack', '6.5%', 'Amber Ale');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Shaquille ONeil''s Dunkelweizen', 3, 'A beer you can slam back every game day.', 'url_Lakers', '3.3%', 'Dunkelweizen');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Cool''Thulu', 1, '...tastes like the elixer of the elder gods', 'url_AAAHHH', '99%', 'Oud Bruin');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Quiggly Gose Down Under', 3, 'You will literally fall head over heels for this brew.', 'url_oopsydaisey', '10.0%', 'Gose');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('General Braddock''s', 4, 'Soft balanced beer you can come back to.', 'none selected', '6.8%', 'American IPA');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Summertime Squeeze', 4, 'A refreshing seasonal beer', 'none selected', '8.2%', 'Double IPA');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Garden Party', 4, 'Best described as crisp, clean, and fresh. Drink it on a hot day, or sip it with a nice dinner', 'none selected', '5.2%', 'Cucumber Wheat Beer');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Legendary Weapons', 4, 'Big creamy, orange citrus armoa that continues to the taste.', 'none selected', '6.1%', 'American IPA');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Killapilz', 5, 'An Imperial Lager with attitude', 'none selected', '7.5%', 'Imperial Lager');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Voodoo Love Child', 5, 'Aged on passion fruit, cherries and raspberries, this ale is inspired by fun, flavor, and a passion for brewing.', 'none selected', '9.2%', 'Gran Met');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Hoodoo', 5, 'This brew will insight your dark side and open a portal to your hoppier senses.', 'none selected', '7.3%', 'IPA');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Sexy Motherpucker', 6, 'Inspired by a classic blend of ice tea and lemonade, this is a sour ale with bright citrus notes.', 'none selected', '7.5%', 'Wild Ale');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Funky Monks', 6, 'Crisp, dry tartness blends harmoniously with notes of subtle spice, ripe pear and banana.', 'none selected', '9.3%', 'Wild Ale');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Hoptimus Prime', 1, 'Beer lovers have come all the way from Cybertron in order to enjoy these floral, citrusy hops.', 'url_prime', '7%', 'IPA');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Go Pens Porter', 1, 'This dark porter beer will get you through the cold winter while cheering on your hometown hockey team', 'url_pensporter', '8.7%', 'Porter');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Heimer Hefeweizen ', 3, 'Guten tag to you with this German Hefeweizen with hints of banana and clove', 'url_hefe', '6%', 'Hefeweizen');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Steve''s Stout', 2, 'Be like Steve and drink his Stout', 'url_steve', '8%', 'Stout');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Audrey Hopburn', 2, 'An IPA that is classy and elegant like the lady herself', 'url_audrey', '7%', 'IPA');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Red Head Red Ale', 3, 'With notes of toasted caramel, buttery toffee, and some malty sweetness, you cannot go wrong', 'url_red', '7%', 'Red Ale');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Harry Porter', 2, 'Butterbeer has nothing on this magical Porter from the wizard of Hogwarts', 'url_harry', '7%', 'Porter');
INSERT INTO beer (beer_name, brewery_id, description, url, abv, beer_type) VALUES ('Mr. Tea', 1, 'A brew with bite and a splash of tea to make your hair stand up straight.', 'url_mrtea', '5.5%', 'Stout');


COMMIT TRANSACTION;

--REVIEW TABLE
BEGIN TRANSACTION;

CREATE SEQUENCE seq_review_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE TABLE reviews (
	review_id int DEFAULT nextval('seq_review_id'::regclass) NOT NULL,
	beer_id int NOT NULL,
	user_id int,
	title varchar(60) NOT NULL,
	stars int NOT NULL,
	body varchar(1000),
    CONSTRAINT PK_review_id PRIMARY KEY (review_id),
	CONSTRAINT FK_beer FOREIGN KEY (beer_id) REFERENCES beer (beer_id)
);

INSERT INTO reviews (beer_id, title, stars, body, user_id) VALUES (1, 'Yes', 4, ' never had such a delightful bevvie in all my days',1);
INSERT INTO reviews (beer_id, title, stars, body, user_id) VALUES (2, 'eh', 1, ' if there was less hop & malt flavors it would have been better',1);
INSERT INTO reviews (beer_id, title, stars, body, user_id) VALUES (2, 'Yum', 4, ' good matliness + a sharp hop finish',1);
INSERT INTO reviews (beer_id, title, stars, body) VALUES (2, 'Great fall brew', 4, 'bruh');
INSERT INTO reviews (beer_id, title, stars, body) VALUES (5, 'Oy!', 4, 'Yinz better try this brew. So smooove.');
INSERT INTO reviews (beer_id, title, stars, body) VALUES (7, 'Drank this over Easter', 4, 'Man, dis beer like the Easter Bunny. Its full of hops.');

--BREWERS TABLE: Relational BREWERIES & USERS
CREATE TABLE brewers (
        brewery_id int NOT NULL,
        user_id int,
        CONSTRAINT FK_brewery_id FOREIGN KEY (brewery_id) REFERENCES breweries (brewery_id),
        CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

INSERT INTO brewers (brewery_id, user_id) VALUES (1,3);

COMMIT TRANSACTION;
--commented to change 
