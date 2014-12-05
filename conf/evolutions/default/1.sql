# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table accounts (
  id                        bigint not null,
  deletion_date             timestamp,
  account_type              varchar(9) not null,
  email                     varchar(255) not null,
  password                  varchar(255) not null,
  first_name                varchar(255) not null,
  last_name                 varchar(255) not null,
  zip_code                  varchar(255),
  power_provider            varchar(255),
  power_comsumer_category   varchar(255),
  other_email_adresses      varchar(255),
  sensitization_kit         varchar(255),
  super_admin               boolean default false not null,
  version                   bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  constraint ck_accounts_account_type check (account_type in ('HOUSEHOLD')),
  constraint uq_accounts_email unique (email),
  constraint pk_accounts primary key (id))
;

create table answers (
  id                        bigint not null,
  deletion_date             timestamp,
  survey_id                 bigint not null,
  question_code             varchar(5) not null,
  period                    varchar(6),
  version                   bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  constraint ck_answers_question_code check (question_code in ('Q1300','Q1400','Q1500','Q1110','Q1120','Q1130','Q1600','Q1210','Q1900','Q1160','Q1220','Q1230','Q1700','Q1750','Q1800','Q2010','Q2020','Q2030','Q2040','Q1235','Q1140','Q1150','Q3210','Q3211','Q3110','Q3120','Q3130','Q3310','Q3320','Q3330','Q3410','Q3420','Q3510','Q3530','Q3610','Q3620','Q3630','Q3631','Q3640','Q3810','Q3710','Q3711','Q3720','Q3730','Q3740','Q3741','Q3750','Q3760','Q9999')),
  constraint ck_answers_period check (period in ('FIRST','SECOND','THIRD')),
  constraint pk_answers primary key (id))
;

create table answervalues (
  id                        bigint not null,
  deletion_date             timestamp,
  answer_id                 bigint not null,
  string_value              varchar(255),
  double_value              float,
  boolean_value             boolean,
  version                   bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  constraint pk_answervalues primary key (id))
;

create table batchresult (
  id                        bigint not null,
  reduction_type            varchar(9),
  nb_surveys                integer,
  nb_participants           integer,
  nb_errors                 integer,
  constraint ck_batchresult_reduction_type check (reduction_type in ('POTENTIAL','EFFECTIVE')),
  constraint pk_batchresult primary key (id))
;

create table batchresultitem (
  id                        bigint not null,
  day                       varchar(4),
  period                    varchar(6),
  power_reduction           float,
  batch_result_id           bigint,
  constraint ck_batchresultitem_day check (day in ('DAY1','DAY2','DAY3','DAY4')),
  constraint ck_batchresultitem_period check (period in ('FIRST','SECOND','THIRD')),
  constraint pk_batchresultitem primary key (id))
;

create table batch_result_set (
  id                        bigint not null,
  deletion_date             timestamp,
  potential_bach_id         bigint,
  effective_bach_id         bigint,
  version                   bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  constraint pk_batch_result_set primary key (id))
;

create table surveys (
  id                        bigint not null,
  deletion_date             timestamp,
  account_id                bigint not null,
  version                   bigint not null,
  creation_date             timestamp not null,
  last_update_date          timestamp not null,
  constraint pk_surveys primary key (id))
;

create sequence accounts_seq;

create sequence answers_seq;

create sequence answervalues_seq;

create sequence batchresult_seq;

create sequence batchresultitem_seq;

create sequence batch_result_set_seq;

create sequence surveys_seq;

alter table answers add constraint fk_answers_survey_1 foreign key (survey_id) references surveys (id);
create index ix_answers_survey_1 on answers (survey_id);
alter table answervalues add constraint fk_answervalues_answer_2 foreign key (answer_id) references answers (id);
create index ix_answervalues_answer_2 on answervalues (answer_id);
alter table batchresultitem add constraint fk_batchresultitem_batchResult_3 foreign key (batch_result_id) references batchresult (id);
create index ix_batchresultitem_batchResult_3 on batchresultitem (batch_result_id);
alter table batch_result_set add constraint fk_batch_result_set_potentialB_4 foreign key (potential_bach_id) references batchresult (id);
create index ix_batch_result_set_potentialB_4 on batch_result_set (potential_bach_id);
alter table batch_result_set add constraint fk_batch_result_set_effectiveB_5 foreign key (effective_bach_id) references batchresult (id);
create index ix_batch_result_set_effectiveB_5 on batch_result_set (effective_bach_id);
alter table surveys add constraint fk_surveys_account_6 foreign key (account_id) references accounts (id);
create index ix_surveys_account_6 on surveys (account_id);



# --- !Downs

drop table if exists accounts cascade;

drop table if exists answers cascade;

drop table if exists answervalues cascade;

drop table if exists batchresult cascade;

drop table if exists batchresultitem cascade;

drop table if exists batch_result_set cascade;

drop table if exists surveys cascade;

drop sequence if exists accounts_seq;

drop sequence if exists answers_seq;

drop sequence if exists answervalues_seq;

drop sequence if exists batchresult_seq;

drop sequence if exists batchresultitem_seq;

drop sequence if exists batch_result_set_seq;

drop sequence if exists surveys_seq;

