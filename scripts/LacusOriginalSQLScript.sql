CREATE database lacus;
DROP database lacus;
USE lacus;
CREATE TABLE Users (
cod_user smallint(20) auto_increment primary key ,
id_user varchar(32) ,
psw_user varchar(32),
nome varchar(32),
surname varchar(32),
email varchar(32),
user_city char(20),
user_road char(30),
user_road_number int(4)
);

/* LO STATUS DELLA CONSEGNA PUO' ESSERE: IN ATTESA DI CONFERMA, IN ATTESA DI RITIRO,IN CONSEGNA,CONSEGNATO
IN ATTESA DI CONFERMA: 0
IN ATTESA DI RITIRO: 1
IN CONSEGNA: 2
CONSEGNATO: 3
*/
CREATE TABLE shipping(
cod_shipping int(20)auto_increment ,
city varchar(20),
cod_sender smallint(20) references Users(cod_user),
road_sender varchar(20),  
cod_recipient smallint(20) references Users(cod_user),
road_recipient varchar(20),
cod_courier smallint(20) default null references Users(cod_user),
payment double,
data date,
status_payment boolean,
status_shipping int, 
check (payment>0 ),
check ( status_shipping>=0 || status_shipping<=4),
check ( cod_sender != cod_recipient),
primary key (cod_shipping),
key (cod_shipping,cod_recipient)
);



CREATE TABLE payment(cod_payment smallint(20)auto_increment primary key,
 cod_payer smallint(20) references Users(cod_user) ,
 payment double,
 status_ boolean
);
create table email(
email varchar(40)
);
 

/* ---------------------------------------------- */


INSERT INTO Users (id_user,psw_user) values ('Lorenzo','lorenzo');
INSERT into Users (id_user,psw_user) values ('ndisoa','dmsak');

SELECT id_user,psw_user FROM Users WHERE id_user='Lorenzo'AND psw_user='lorenzo';
SELECT * FROM Users;
INSERT INTO Users (id_user,psw_user,nome,surname,email,user_city,user_road,user_road_number) values ('Vins','RAM16','Vincenzo','Chiarella','vins@gmail.com','Macerata','Via Macarata','15');
INSERT INTO Users (id_user,psw_user,nome,surname,email,user_city,user_road,user_road_number) values ('Lorenzo','134','Lorenzo','Acciarri','acc@gmail.com','PSE','Via pse','15');

/*PROVE JAVA*/
SELECT id_user FROM Users WHERE cod_user=1;
SELECT id_user FROM Users WHERE id_user='ffsafs';
Select * from Users;
SELECT id_user FROM Users WHERE id_user='Lorenzo';
SELECT id_user FROM Users WHERE id_user='Lorenzo';
SELECT cod_user FROM Users WHERE(nome='Vincenzo'AND surname='Chiarella');
delete from Users where cod_user='5';
