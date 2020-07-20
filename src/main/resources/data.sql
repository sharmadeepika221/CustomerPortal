
Drop TABLE customer if exists;
Drop TABLE address if exists;

create table customer
(
id varchar(255) not null auto_increment,
firstname varchar(255) not null,
lastname varchar(255) not null,
age integer not null,
address_id varchar(255) not null,
primary key(id)
);
create table address
(
id varchar(255) not null auto_increment,
flat_no varchar(255) ,
street_name varchar(255) ,
city varchar(255) ,
postal_code varchar(255),
country varchar(255),
primary key(id)
);