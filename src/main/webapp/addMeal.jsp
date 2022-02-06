<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://ru.javawebinar.topjava.model" prefix="f" %>
<html>
<title>addMeal</title>
</head>
<p><b>add Meal</b></p>
<body>
<form method="POST" action="meals?action=createMeal" name="createMeal">
    <table>
        <tbody>
        <tr>
            <td>DateTime:</td>
            <td>
                <input type="datetime-local" id="localdate" name="date"/>
            </td>
        </tr>
        <tr>
            <td>Description:</td>
            <td>
                <input type="text" id="description" name="description" placeholder="add description">
            </td>
        </tr>
        <tr>
            <td>calories:</td>
            <td>
                <input type="text" id="calories" name="calories" placeholder="add calories">
            </td>
        </tr>
        </tbody>
    </table>
        <input type="Submit" value="Save"/></form>
<form method="GET" action="meals" name="postListMeals"><input type="Submit" value="Cancel"/></form>

</body>
</html>

