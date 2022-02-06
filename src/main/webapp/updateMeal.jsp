<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://ru.javawebinar.topjava.model" prefix="f" %>
<html>
<title>update Meal</title>
</head>
<p><b>update Meal</b></p>
<body>
<form method="POST" action="meals?action=updateMeal&id=${meal.getId()}" name="createMeal">
    <table>
        <tbody>
        <tr>
            <td>DateTime:</td>
            <td>
                <input type="datetime-local" name="date"
                       value="<c:out value="${meal.getDateTime()}" />"/>
            </td>
        </tr>
        <tr>
            <td>Description:</td>
            <td>
                <input type="text" name="description"
                       value="<c:out value="${meal.getDescription()}" />"/>
            </td>
        </tr>
        <tr>
            <td>calories:</td>
            <td>
                <input type="text" name="calories"
                       value="<c:out value="${meal.getCalories()}" />"/>
            </td>
        </tr>
        </tbody>
    </table>
    <input type="Submit" value="Save"/></form>
<form method="GET" action="meals" name="postListMeals"><input type="Submit" value="Cancel"/></form>
</body>
</html>

