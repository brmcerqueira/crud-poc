create sequence users_seq;

create table users(
  id bigint not null default nextval ('users_seq'),
  name varchar(50) not null,
  primary key (id)
);