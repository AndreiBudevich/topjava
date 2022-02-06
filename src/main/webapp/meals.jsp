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
<b><a href="addMeal?action=create">Add Meal</a></b>
<br>
<table border="2">
    <tr>
         <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach items="${mealTos}" var="mealTos">
        <tr style="color:${mealTos.isExcess() == true ? "red" : "green"}">
            <td>${f:formatLocalDateTime(mealTos.getDateTime(), 'yyyy-MM-dd hh:mm')}</td>
            <td>${mealTos.getDescription()}</td>
            <td>${mealTos.getCalories()}</td>
            <td>
                <a href="meals?action=update&id=${mealTos.getId()}">Update</a>
            </td>
            <td>
                <a href="meals?action=delete&id=${mealTos.getId()}">Delete</a>
            </td>
        </tr>
        </tr>
    </c:forEach>
</table>


</body>
</html>