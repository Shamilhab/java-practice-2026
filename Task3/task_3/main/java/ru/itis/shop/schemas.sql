create table Product(
    id serial unique not null,
    name char(20) not null,
    price integer check(price > 0)
);
insert into Product (name, price) values ('cake', 150);
insert into Product (name, price) values ('fish', 150);
insert into Product (name, price) values ('juice', 150);
select * from Product