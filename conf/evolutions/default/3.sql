
# --- !Ups

CREATE TABLE shedding_risks
(
  id bigint NOT NULL,
  deletion_date timestamp without time zone,
  risk_date date,
  mail_sending_date date,
  version bigint NOT NULL,
  creation_date timestamp without time zone NOT NULL,
  last_update_date timestamp without time zone NOT NULL,
  CONSTRAINT pk_shedding_risks PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE shedding_risks
OWNER TO play;

CREATE TABLE shedding_risks_answers
(
  id bigint NOT NULL,
  deletion_date timestamp without time zone,
  risk_id bigint,
  account_id bigint,
  answer boolean,
  version bigint NOT NULL,
  creation_date timestamp without time zone NOT NULL,
  last_update_date timestamp without time zone NOT NULL,
  CONSTRAINT pk_shedding_risks_answers PRIMARY KEY (id),
  CONSTRAINT fk_shedding_risks_answers_acco_7 FOREIGN KEY (account_id)
  REFERENCES accounts (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_shedding_risks_answers_risk_6 FOREIGN KEY (risk_id)
  REFERENCES shedding_risks (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE shedding_risks_answers
OWNER TO play;

create sequence shedding_risks_seq;

create sequence shedding_risks_answers_seq;

# --- !Downs

drop table if exists shedding_risks cascade;

drop table if exists shedding_risks_answers cascade;

drop sequence if exists shedding_risks_seq;

drop sequence if exists shedding_risks_answers_seq;
