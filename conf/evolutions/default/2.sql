
# --- !Ups

ALTER TABLE accounts ADD COLUMN legacy_account_power_reduction double precision;
ALTER TABLE accounts ADD COLUMN password_to_change boolean;

# --- !Downs

ALTER TABLE accounts DROP COLUMN legacy_account_power_reduction;
ALTER TABLE accounts DROP COLUMN password_to_change;

