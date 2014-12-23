
# --- !Ups

ALTER TABLE accounts ADD COLUMN password_to_change boolean;

# --- !Downs

ALTER TABLE accounts DROP COLUMN password_to_change;

