1) Отримання всіх даних
   GET http://localhost:8082/api/v1.0/contacts

2) Отримання даних за id
   GET http://localhost:8082/api/v1.0/contacts/1

3) Створення даних
   POST http://localhost:8082/api/v1.0/contacts

   {
       "id": 5,
       "name": "John",
       "phone": "+380192759385"
   }

   Якщо якогось поля не буде - створення не відбудеться

4) Оновлення даних за id
   PUT http://localhost:8082/api/v1.0/contacts/1

    {
        "phone": "+380192759385"
    }

    або

    {
        "name": "John"
    }

    або

    {
        "name": "John"
        "phone": "+380192759385"
    }

    При оновленні данних не обов'язково оновлювати все одразу

5) Видалення даних за id
   DELETE http://localhost:8082/api/v1.0/contacts/1