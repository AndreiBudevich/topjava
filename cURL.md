Получить список еды (с учетом CALORIES_PER_DAY) аутентифицированного пользователя:
curl --location --request GET 'http://localhost:8081/topjava/rest/meals'

Удалить еду по id еды аутентифицированного пользователя:
curl --location --request DELETE 'http://localhost:8081/topjava/rest/meals/100005'

Применить фильтр еды (с учетом CALORIES_PER_DAY) (начальная дата, конечная дата, стартовое время, финальное время (не включено))
curl --location --request GET 'http://localhost:8081/topjava/rest/meals/filter?startDate=2020-01-30&startTime=00:00&endDate=2020-01-30&endTime=20:00'

Применить фильтр еды (с учетом CALORIES_PER_DAY) (с нулевыми значениями данных по фильтрации)
curl --location --request GET 'http://localhost:8081/topjava/rest/meals/filter'

Обновить еду по id еды аутентифицированного пользователя:
curl --location --request PUT 'http://localhost:8081/topjava/rest/meals/100004' \
--header 'Content-Type: application/json' \
--data-raw '
{
"dateTime": "2020-01-31T20:33:00",
"description": "Ужин10004",
"calories": 333
}
'
Создать еду аутентифицированного пользователя:
curl --location --request POST 'http://localhost:8081/topjava/rest/meals' \
--header 'Content-Type: application/json' \
--data-raw '  {
"dateTime": "2022-01-31T20:00:00",
"description": "Ужинновый",
"calories": 111
}
'