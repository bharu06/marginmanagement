Task: Build a RESTful API for a simple margin management

#### Things Built

1. 3 apis based on the requirement. To Upload a MarginOrder csv file, Download the current data
as a fail, Find Api which takes 5 params
2. Swagger UI is hosted at    
   http://localhost:8080/swagger-ui.html
3. Validations


#### Pending Things

1. We can use java 17 and add @Min @Max validation annotation for numeric fields
2. We can process each csv Record in async mode during upload
3. BidOperator and AskOperator has * in csv, so added them as well for validation.
Are those valid inputs ? 
4. Generic Validation or use latest springboot featues


-> How Validations are handled ? 
As of now they are handled in MarginOrderValidator class, But we can think of a
generic Validator model or utilize latest spring validator framework.

-> How find is implemented ?

Custom query with parameterized inputs (@Param("instructions"))