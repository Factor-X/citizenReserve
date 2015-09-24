# Heroku deployment

## Login

Go to http://www.heroku.com > Log in

Use one of the following credentials depending on your needs.

| Environment | Username | Password |
|----------|-------|-------|
| PROD | fournisseurs@factorx.eu |factorx07 |
| DEV | xavier.marichal@factorx.eu | ??? |


## Download / restore a backup database

In the production environment (similarly in the dev environment).

- Select the application of interest (citizenreserve for example).
- Select the Heroku Postgres add-on of interest (Heroku Postgres :: Green, for example).
- Select 'PG Backups' on the menu at the top of the page.
- A table is displayed with all the available backups and a 'Download' button.

Note that the name of the downloaded file will be some kind of UUID. You will need to add a '.backup' extension.
Otherwise, PGAdmin will refuse to restore it.

Start PGAdmin. If not already done:

- Add a new login role: Login Roles > New Login Role ... for role name 'play' with password 'play'.
- Create a the database: New Database ... with name 'citizens-reserve' and owner 'play'.
- Restore the backup: Restore ... with filename the downloaded and renamed '.backup' file.

You should get a database with 11 tables: accounts ... surveys.

## Procfile

There is a Procfile file in the root of the project (this is the mandatory place). A Procfile declares what commands are
run by the deployed application on the Heroku platform.

The Procfile of Citizens Reserve contains the following:

web: target/universal/stage/bin/citizens "-Dhttp.port=${PORT} -DapplyEvolutions.default=true -DapplyDownEvolutions.default=false -Ddb.default.driver=org.postgresql.Driver -Ddb.default.url=${DATABASE_URL}"


The 'web' process type starts the application server

------

We can download a PostgreSQL database backup.


http://citizenreserve.herokuapp.com/#/fr/welcome

The admin access to track the statistics of the tool is accessible via
https://citizenreserve.herokuapp.com/#/fr/admin/

The login credentials for this admin access are
login: factorx@factorx.eu   password: GU57IYjGjg
The password can be modified (directly in encrpyted version) via the file: citizens-reserve/conf/evolutions/default/6.sql
