
# --- !Ups

ALTER TABLE accounts ALTER COLUMN account_type TYPE character varying(11);

insert into accounts(id, account_type, email, password, first_name, last_name, sensitization_kit, language, super_admin, version, creation_date, last_update_date)
values (999999999, 'SUPERADMIN','factorx@factorx.eu','rP5V4qgQEJZ+KhhBcepCJ9yK3i7zZq0ssXv5wdmsZBGD4XlYqQQD9/PeVLy0b9UA','admin','admin','f',0,'t',0,'2015-01-01 00:00:00','2015-01-01 00:00:00');

# --- !Downs

delete from accounts where id = 999999999;
