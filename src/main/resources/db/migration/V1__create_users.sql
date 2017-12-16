create table users (
    emailid varchar(250) not null primary key,
    password_hash varchar(500) not null,
    password_validated boolean default false,
    name varchar(500),
    address json,
    practice_name varchar(500),
    primary_user boolean
);
create table orders (
    order_id VARCHAR(36) not null PRIMARY KEY,
    emailid VARCHAR(250)
);

create table subscription (
  id SERIAL not null primary key,
  name varchar(255) not null,
  number_of_books int not null
);

create table librabry_user (
    id SERIAL primary key,
    email varchar(250) not null,
    name varchar(500),
    subscription_id int not null references subscription(id)
);

create table book (
  id SERIAL primary key,
  name varchar(255) not null,
  total_quantity int not null default 5
);

create table user_book_issue (
  id SERIAL primary key,
  user_id int not null references librabry_user(id),
  book_id int not null references book(id),
  issue_date DATE not null,
  return_date DATE,
  issue_active boolean default true
);

create table reservation (
  id SERIAL primary key,
  book_id int not null references book(id),
  user_id int not null references librabry_user(id),
  reservation_date DATE not null
);


insert into subscription (id, name, number_of_books) values (1, 'silver', 2);
insert into subscription (id, name, number_of_books) values (2, 'gold', 4);
insert into subscription (id, name, number_of_books) values (3, 'paltinum', 6);

insert into librabry_user (id, email, name, subscription_id) values (1, 'abc@tw.com', 'abc', 1);
insert into librabry_user (id, email, name, subscription_id) values (2, 'pqr@tw.com', 'pqr', 2);
insert into librabry_user (id, email, name, subscription_id) values (3, 'lmn@tw.com', 'lmn', 3);

insert into book (id, name, total_quantity) values (1, 'clean code', 2);
insert into book (id, name, total_quantity) values (2, 'refactoring', 1);
insert into book (id, name, total_quantity) values (3, 'javascript good parts', 2);