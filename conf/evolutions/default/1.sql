# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint not null,
  creation_date             timestamp not null,
  last_update               timestamp not null,
  first_name                varchar(255) not null,
  last_name                 varchar(255) not null,
  email                     varchar(255) not null,
  password                  varchar(255) not null,
  constraint uq_account_email unique (email),
  constraint pk_account primary key (id))
;

create sequence account_seq;




# --- !Downs

drop table if exists account cascade;

drop sequence if exists account_seq;

