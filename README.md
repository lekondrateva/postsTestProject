Task: напиши тесты для покрытия CRUD эндпоинта https://jsonplaceholder.typicode.com/posts/,
(учтите, что это замоканные данные, поэтому в реальности пост не будет создаваться)

Requirements:
"userId": int, required, unique, 1-10 symbols
"id": int, required, unique, 1-10 symbols
"title": String, required, 1-30 symbols
"body": String, required, 1-200 symbols

Scenarios:
Data positive:

1. create a post //201
2. read a post //200
3. ParameterizedTest - update a post with valid params //200
4. delete a post //204

Data negative:

5. ParameterizedTest - create with invalid params //400
6. ParameterizedTest - update with invalid params //400
7. ParameterizedTest - read with invalid params (<0, 0, >10symbols)     //400
8. ParameterizedTest - delete with invalid params (<0, 0, >10symbols)   //400
9. create if id already exists //400