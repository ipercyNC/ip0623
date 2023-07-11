# Toolywood

Welcome! This application allows you to checkout tools and rent them for a specified amount of time.
Live on the web:
https://toolywood.onrender.com/

Current functionality:

## Checkout
Rent a specific tool (by tool code), checkout date, amount of rental days, and discount (if applicable)
This will generate a rental agreement with information about the rental. 
Tool Code, Tool Type, Tool Brand, Number of Rental Days, Checkout Date,
Due Date, Daily Rental Charge, Charge Days (Number of Days The Rental Will Charge For),
Pre-discount Charge, Discount Percent, Discount Amount, Final Charge

The rental will start the day after the checkout up until the number of 
rental days has been reached. 

Choose a tool from the selection and it will generate a rental agreement with the chosen tool 
and rental length. The rental days must be 1 or greater and the percent discount must be 
0-100. 


## How to Run
1) Clone package
2) Import DB into your local Postgres server (if wanting to manage yourself)
    a) toolywood_full.sql is the full create tables with data, toolywood_schema.sql is just the table structure (Postgres)
3) Edit the template_application.properities (main/resources) to match your local jdbc url, username, and password
4) Run with `mvn spring-boot:run`

### Run Tests
1) Run tests with `mvn test`

### Tests Information
There are tests for all of the controllers. I would like to add more but these test majority of the endpoints.
The tests for the actual tool renting are in src\test\java\com\RentToolControllerTest.java.
The main tool rental tests are:

Tool Code, Checkout Date, Rental Days, Discount Percent
1) Rent - JAKR, 9/3/15, 5, 101
2) Rent - LADW, 7/2/20, 3, 10
3) Rent - CHNS, 7/2/15, 5, 25
4) Rent - JAKD, 9/3/15, 6, 0
5) Rent - JAKR, 7/2/15, 9, 0
6) Rent - JAKR, 7/2/20, 4, 50
7) Rent - JAKR, 7/2/20, 4, -1 -> Invalid percent (not 0-100)
8) Rent - YYYY, 7/2/20, 4, 0 -> Rent tool that doesn't exist
9) Rent - JAKR, 7/2/20, 0, 50 -> Invalid rental days
10) Rent - JAKR, 7/2/21, 4, 0 -> Test July 4th on a Sunday
11) Rent - JAKR, aaaaaaa, 1, 50 -> Invalid date format

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

The main Java objects:
1) ToolType - Contains tool types
2) ToolBrand - Contains tool brands
3) ToolChoices - Contains available tools (type + brand + tool code)
4) ToolCharges - Contains charges for a particular tool type
5) RentalAgreement - Contains the rental agreement object (not currently in a DB table)

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

