<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://ru.javawebinar.topjava.model" prefix="f" %>
<html>
<h3><a href="index.html">Home</a></h3>
<hr>
<head>
    <title>Meals</title>
</head>
<body>
<b><a href="meals?action=create">Add Meal</a></b>
<br>
<table border="2">
    <tr>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach items="${mealTos}" var="mealTo">
        <tr style="color:${mealTo.excess ? "red" : "green"}">
            <td>${f:formatLocalDateTime(mealTo.dateTime)}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td>
                <a href="meals?action=update&id=${mealTo.id}">Update</a>
            </td>
            <td>
                <a href="meals?action=delete&id=${mealTo.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>