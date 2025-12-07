# Book Discount Price – TDD Kata (Spring Boot 4, Java 25)
A fully test-driven implementation of the Development Books Discount Kata, built using Spring Boot 4, Java 25, Mockito, WebMvcTest, and modern records, Lombok, and clean architecture principles.

The project follows professional standards including:

✔ TDD workflow

✔ Clean separation of layers

✔ Enum-driven domain model

✔ DTO responses

✔ Mapping layer

✔ Global exception handling

✔ REST API with OpenAPI/Swagger

✔ 100% controller + service test coverage

# Project Overview
This kata calculates the total price of books bought from a predefined list (Development Books collection). Discounts apply depending on the number of distinct books purchased.

✔ Core Features

Fetch the list of available books (GET /api/books/getbooks)

Calculate price with discount (POST /api/books/price/calculate)

Use Java 25 records for immutable DTOs

Enum-based fixed book catalog

Input validation

Global exception handling (@RestControllerAdvice)

Extensive unit + controller tests

Follows Test-Driven Development principles

-------------------------------
# Requirement
There is a series of books about software development that have been read by a lot of developers who want to improve their development skills. Let’s say an editor, in a gesture of immense generosity to mankind (and to increase sales as well), is willing to set up a pricing model where you can get discounts when you buy these books. The available books are :

1. Clean Code (Robert Martin, 2008)
2. The Clean Coder (Robert Martin, 2011)
3. Clean Architecture (Robert Martin, 2017)
4. Test Driven Development by Example (Kent Beck, 2003)
5. Working Effectively With Legacy Code (Michael C. Feathers, 2004)

## Rules
One copy of the five books costs 50 EUR.

- If, however, you buy two different books from the series, you get a 5% discount on those two books.
- If you buy 3 different books, you get a 10% discount.
- If you buy 4 different books, you get a 20% discount.
- If you go for the whole hog, and buy all 5, you get a huge 25% discount.
- Note that if you buy, say, 4 books, of which 3 are different titles, you get a 10% discount on the 3 that form part of a set, but the 4th book still costs 50 EUR.

## Functional case
If the shopping basket contains the below books.

- 2 copies of the “Clean Code” book
- 2 copies of the “Clean Coder” book
- 2 copies of the “Clean Architecture” book
- 1 copy of the “Test Driven Development by Example” book
- 1 copy of the “Working effectively with Legacy Code” book

We can avail the discounts for above shopping basket containing 8 books by grouping [5,3] or [4,4] or [3,3,2], etc. Output should be 320 EUR as the best price by applying [4,4] as below.

- (4 * 50 EUR) - 20% [first book, second book, third book, fourth book]
- (4 * 50 EUR) - 20% [first book, second book, third book, fifth book]

= (160 EUR + 160 EUR) => 320 EUR

# Discount Rules
Distinct-Books	 ------------    Discount

1 book -----------------------	0%

2 books -----------------------	5%

3 books -----------------------	10%

4 books -----------------------	20%

5 books -----------------------	25%

Each book has a fixed price of EUR 50.

# API Endpoints
## 1. Get All Books

- GET /api/books/getbooks

Response :
`[
  {
  "id": 1,
  "title": "Clean Code",
  "author": "Robert Martin",
  "year": 2008,
  "price": 50.0
  },
  ...
  ]`

## 2. Calculate Price

- POST /api/books/price/calculate

Request:
`{
"bookList": [
{ "title": "Clean Code", "quantity": 1 },
{ "title": "The Clean Coder", "quantity": 1 }
]
}`

Response: `{
"totalPrice": 95.0
}`

*********************************************
API Documentation (Swagger UI)
=============================================
Once the app is running:

Swagger UI :
http://localhost:8081/swagger-ui.html

OpenAPI Spec :
http://localhost:8081/v3/api-docs

## Development Approach
1️. Red – Write failing test

2️. Green – Implement minimum code

3. Refactor – Improve design & remove duplication

Applied across:

- Book merging logic

- Discount application

- Controller-level validations

- Enum-to-DTO mapping

## How to build the application
Clone this repository
 
- https://github.com/tamalyaahoo/book-discount-price-tdd-v2.git

You can build the project and run the tests by running `mvn clean install`

## How to run the application

By default the application will start on port 8080. If you want the application to run on different port 8081, you can pass additional parameter --server.port=8082 while starting the application (or) you can update the server.port in application.properties

Once successfully built, you can run the service by one of this commands:

   
    java -jar target\develop-project-v2-1.0.0-SNAPSHOT.jar

							(or)
							
	java -jar target\develop-project-v2-1.0.0-SNAPSHOT.jar --server.port=8081
Once the application runs you should see below message in console log


        :: Spring Boot ::                (v4.0.0)
        
        2025-12-05T08:46:19.151+05:30  INFO 22936 --- [book-discount-price] [           main] c.b.k.b.p.BookDiscountPriceApplication   : Starting BookDiscountPriceApplication using Java 25 with PID 22936 (D:\My-WorkSpace\IntelliJ-WorkStation\book-discount-price-tdd-v2\target\classes started by ADMIN in D:\My-WorkSpace\IntelliJ-WorkStation\book-discount-price-tdd-v2)
        2025-12-05T08:46:19.153+05:30  INFO 22936 --- [book-discount-price] [           main] c.b.k.b.p.BookDiscountPriceApplication   : No active profile set, falling back to 1 default profile: "default"
        2025-12-05T08:46:20.149+05:30  INFO 22936 --- [book-discount-price] [           main] o.s.boot.tomcat.TomcatWebServer          : Tomcat initialized with port 8081 (http)
        2025-12-05T08:46:20.162+05:30  INFO 22936 --- [book-discount-price] [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
        2025-12-05T08:46:20.163+05:30  INFO 22936 --- [book-discount-price] [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/11.0.14]
        2025-12-05T08:46:20.206+05:30  INFO 22936 --- [book-discount-price] [           main] b.w.c.s.WebApplicationContextInitializer : Root WebApplicationContext: initialization completed in 1006 ms
        2025-12-05T08:46:20.736+05:30  INFO 22936 --- [book-discount-price] [           main] o.s.boot.tomcat.TomcatWebServer          : Tomcat started on port 8081 (http) with context path '/'
        2025-12-05T08:46:20.742+05:30  INFO 22936 --- [book-discount-price] [           main] c.b.k.b.p.BookDiscountPriceApplication   : Started BookDiscountPriceApplication in 1.985 seconds (process running for 2.373)
        2025-12-05T08:46:20.745+05:30  WARN 22936 --- [book-discount-price] [           main] o.s.core.events.SpringDocAppInitializer  : SpringDoc /v3/api-docs endpoint is enabled by default. To disable it in production, set the property 'springdoc.api-docs.enabled=false'
        2025-12-05T08:46:20.745+05:30  WARN 22936 --- [book-discount-price] [           main] o.s.core.events.SpringDocAppInitializer  : SpringDoc /swagger-ui.html endpoint is enabled by default. To disable it in production, set the property 'springdoc.swagger-ui.enabled=false'

 ## How to access the application

Once the application started successfully, you can access the application by launching the below url in the browser:
    
    http://localhost:8081/

		(or)
		
	http://localhost:<PORT>/




