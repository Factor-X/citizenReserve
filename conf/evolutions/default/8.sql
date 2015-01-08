
# --- !Ups

ALTER TABLE action_answers ALTER COLUMN question_code TYPE character varying(10);


# --- !Downs

ALTER TABLE action_answers ALTER COLUMN question_code TYPE character varying(5);
