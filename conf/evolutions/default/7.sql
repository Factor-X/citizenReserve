
# --- !Ups

ALTER TABLE accounts ADD COLUMN organization_name character varying(255);


# --- !Downs

ALTER TABLE accounts DROP COLUMN organization_name;
