# Toolywood

Welcome! This application allows you to checkout tools and rent them for a specified amount of time.
Live on the web:
https://toolywood.onrender.com/

Current functionality:

## Checkout
Rent a specific tool (by tool code), checkout date, amount of rental days, and discount (if applicable)



## How to Run
1) Clone package
2) Import DB into your local Postgres server (if wanting to manage yourself)
    a) toolywood_full.sql is the full create tables with data, toolywood_schema.sql is just the table structure (Postgres)
3) Edit the template_application.properities (main/resources) to match your local jdbc url, username, and password
4) Run with `mvn spring-boot:run`

### Run Tests
1) Run tests with `mvn test`

### General Notes and Development Progress:
The application uses a React UI + SpringBoot + PostgreSQL backend. When starting out I was using a default JPA 
repository classes from Springboot, but changed to using JDBC queries that allowed me to be explicit with SQL.
All of the objects from the database currently have a service/controller that will allow them to be interacted
with (for future feature additions). There are some improvements that I would like to make and I listed
some of them below in the stretch goals. 

Initially I was going to use Heroku for the deploy, but they no longer offer a free version. I found a similar 
provider, Render.com, which has a free tier and a hosted PostgreSQL DB. Both the production and the test
DB (schema only) exist on this web hosted DB server. The application was containerized with mvn package and then deployed
via a docker + static React files from the npm build. 

#### Next Steps:
1) ~~Finalize checkout path + add the test cases~~
2) ~~Create more unit tests~~
3) ~~Add formatting to rental agreement~~
4) ~~Create RentalAgreement object~~
5) ~~Add frontend~~
6) ~~Convert from MySQL (current DB to PostgreSQL)~~
7) ~~Add rental validation frontend + backend~~
8) ~~Add SQL files to repo + documentation~~
9) ~~Add in logging~~
10) ~~Create JAR + deploy to Render (with hosted Postgres)~~
11) ~~Improve frontend and redeploy~~

#### Stretch Goals:
1) Add RentalAgreement to DB + create a DB diagram
2) Add Users
3) Add ability to add tools, brands, choices, charges
4) Add download as PDF option for agreement
5) Show agreement history for users
6) Fix mapping of nested objects
7) Add testing coverage sheet
8) Improve Holiday handling to make it more dynamic

