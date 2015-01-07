
# --- !Ups

ALTER TABLE answers DROP CONSTRAINT ck_answers_question_code;
ALTER TABLE accounts DROP CONSTRAINT ck_accounts_account_type;

# --- !Downs
