
# --- !Ups

ALTER TABLE accounts ADD COLUMN legacy_account_power_reduction double precision;

# --- !Downs

ALTER TABLE accounts DROP COLUMN legacy_account_power_reduction;

