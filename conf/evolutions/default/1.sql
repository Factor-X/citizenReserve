# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table accounts (
  id                        bigint not null,
  version                   bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  deletion_date             timestamp,
  account_type              varchar(9),
  email                     varchar(255) not null,
  password                  varchar(255) not null,
  first_name                varchar(255) not null,
  last_name                 varchar(255) not null,
  provider                  varchar(255) not null,
  power_consumption_category varchar(255),
  emails_household_members  varchar(255),
  sensitization_kit         varchar(255),
  constraint ck_accounts_account_type check (account_type in ('HOUSEHOLD')),
  constraint uq_accounts_email unique (email),
  constraint pk_accounts primary key (id))
;

create table answers (
  id                        bigint not null,
  version                   bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  deletion_date             timestamp,
  account_id                bigint not null,
  question_code             varchar(2),
  period                    varchar(6),
  constraint ck_answers_question_code check (question_code in ('Q1')),
  constraint ck_answers_period check (period in ('FIRST','SECOND','THIRD')),
  constraint pk_answers primary key (id))
;

create table answervalues (
  id                        bigint not null,
  version                   bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  deletion_date             timestamp,
  answer_id                 bigint not null,
  string_value              varchar(255),
  double_value              float,
  constraint pk_answervalues primary key (id))
;

create sequence accounts_seq;

create sequence answers_seq;

create sequence answervalues_seq;

alter table answers add constraint fk_answers_account_1 foreign key (account_id) references accounts (id);
create index ix_answers_account_1 on answers (account_id);
alter table answervalues add constraint fk_answervalues_answer_2 foreign key (answer_id) references answers (id);
create index ix_answervalues_answer_2 on answervalues (answer_id);



# --- !Downs

drop table if exists accounts cascade;

drop table if exists answers cascade;

drop table if exists answervalues cascade;

drop sequence if exists accounts_seq;

drop sequence if exists answers_seq;

drop sequence if exists answervalues_seq;

