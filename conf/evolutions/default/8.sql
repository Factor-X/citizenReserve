
# --- !Ups

ALTER TABLE action_answers ALTER COLUMN question_code TYPE character varying(10);


# --- !Downs

-- dont do it...
-- ALTER TABLE action_answers ALTER COLUMN question_code TYPE character varying(5);
