<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://ru.javawebinar.topjava.model" prefix="f" %>
<html>
<head>
    <title>Список еды</title>
</head>
<body>
<p>Meals</p>
<table border="2">
    <tr>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
    </tr>
    <c:forEach items="${mealTos}" var="mealTos">
        <tr style="color:${mealTos.isExcess() == true ? "red" : "green"}">
            <td>${f:formatLocalDateTime(mealTos.getDateTime(), 'yyyy-MM-dd hh:mm')}</td>
            <td>${mealTos.getDescription()}</td>
            <td>${mealTos.getCalories()}</td>
        </tr>
        </tr>
    </c:forEach>
</table>
</body>
</html>