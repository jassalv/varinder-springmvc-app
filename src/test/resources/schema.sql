drop table transactions if exists;

create table transactions(id int auto_increment,
name varchar(50),
amount double,
type char(9),
added_date varchar(70),
update_date varchar(70),
primary key(id));