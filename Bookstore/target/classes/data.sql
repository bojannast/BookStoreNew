insert into author (id, name) values (101, 'Rhonda Byrne');
insert into author (id, name) values (102, 'Harper Lee');
insert into author (id, name) values (103, 'George Orwell');
insert into author (id, name) values (104, 'F. Scott Fitzgerald');


insert into book (id, title, author_id) values (101, 'The secret', 101);
insert into book (id, title, author_id) values (102, 'To Kill a Mockingbird', 102);
insert into book (id, title, author_id) values (103, '1984', 103);
insert into book (id, title, author_id) values (104, 'The Great Gatsby', 104);


insert into customer (id, name, username, password) values (101, 'Bojan Nastovski', 'bojan', 'bojan');
insert into customer (id, name, username, password) values (102, 'Ilina Dimitrieva', 'ilina', 'ilina');
insert into customer_books (customers_id, books_id) values (101, 101);
insert into customer_books (customers_id, books_id) values (102, 102);