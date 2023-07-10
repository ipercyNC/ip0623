# Toolywood

Welcome! This application allows you to checkout tools and rent them for a specified amount of time. Current functionality:

## Checkout
Rent a specific tool (by tool code), checkout date, amount of days, and discount (if applicable)



## How to Run
1) Clone package
2) Import DB into your local Postgres server (if wanting to manage yourself)
    a) toolywood_full.sql is the full create tables with data, toolywood_schema.sql is just the table structure (Postgres)
3) Edit the template_application.properities (main/resources) to match your local jdbc url, username, and password
4) Run with `mvn spring-boot:run`

### Run Tests
1) Run tests with `mvn test`

### General Notes and Development Progress:
1) 

#### Next Steps:
1) ~~Finalize checkout path + add the test cases~~
2) ~~Create more unit tests~~
3) ~~Add formatting to rental agreement~~
4) ~~Create RentalAgreement object~~
5) ~~Add frontend~~
6) ~~Convert from MySQL (current DB to PostgreSQL)~~
7) ~~Add rental validation frontend + backend~~
8) Create simple DB diagram
9) Add SQL files to repo + documentation
10) ~~Add in logging~~
11) ~~Create JAR + deploy to Render (with hosted Postgres)~~
12) ~~Improve frontend and redeploy~~

#### Stretch Goals:
1) Add RentalAgreement to DB
2) Add Users
3) Add ability to add tools, brands, choices, charges
4) Add download as PDF option for agreement
5) Show agreement history for users
6) Fix mapping of nested objects
7) Add testing coverage sheet
8) Improve Holiday handling to make it dymamic

