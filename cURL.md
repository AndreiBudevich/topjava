curl --location --request GET 'http://localhost:8081/topjava/rest/meals'

curl --location --request DELETE 'http://localhost:8081/topjava/rest/meals/100005'

curl --location --request GET 'http://localhost:8081/topjava/rest/meals/filter?startDate=2020-01-30&startTime=00:00&endDate=2020-01-30&endTime=20:00'

curl --location --request GET 'http://localhost:8081/topjava/rest/meals/filter'

curl --location --request PUT 'http://localhost:8081/topjava/rest/meals/100004' \
--header 'Content-Type: application/json' \
--data-raw '
{
"dateTime": "2020-01-31T20:33:00",
"description": "Ужин10004",
"calories": 333
}
'

curl --location --request POST 'http://localhost:8081/topjava/rest/meals' \
--header 'Content-Type: application/json' \
--data-raw '  {
"dateTime": "2022-01-31T20:00:00",
"description": "Ужинновый",
"calories": 111
}
'