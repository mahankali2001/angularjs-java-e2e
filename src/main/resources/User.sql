use contactdb;

select * from user;

CREATE TABLE User (
  uid int(11) NOT NULL AUTO_INCREMENT,
  firstName varchar(45) NOT NULL,
  lastName varchar(45) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

insert into User(firstName,lastName) values('FA','LA');
insert into User(firstName,lastName) values('FB','LB');
insert into User(firstName,lastName) values('FC','LC');
insert into User(firstName,lastName) values('FD','LD');