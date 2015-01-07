
# --- !Ups

CREATE TABLE action_answers
(
  id bigint NOT NULL,
  deletion_date timestamp without time zone,
  survey_id bigint NOT NULL,
  question_code character varying(5) NOT NULL,
  title character varying(255),
  power_reduction integer NOT NULL,
  start_time time without time zone NOT NULL,
  duration bigint NOT NULL,
  comment character varying(255),
  version bigint NOT NULL,
  creation_date timestamp without time zone NOT NULL,
  last_update_date timestamp without time zone NOT NULL,
  CONSTRAINT pk_action_answers PRIMARY KEY (id),
  CONSTRAINT fk_action_answers_survey_1 FOREIGN KEY (survey_id)
  REFERENCES surveys (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE action_answers
OWNER TO play;

create sequence action_answers_seq;

# --- !Downs

drop table if exists action_answers cascade;

drop sequence if exists action_answers_seq;
