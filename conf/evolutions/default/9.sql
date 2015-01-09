
# --- !Ups

ALTER TABLE accounts ADD COLUMN power_consumption double precision;

# --- !Downs

ALTER TABLE accounts DROP COLUMN power_consumption;


