<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <form method="GET" action="meals" name="filterMeal">
        <input type="hidden" name="action" value="filter">
        <table>
            <tbody>
            <tr>
                <th>From date (including)</th>
                <th>Before date (including)</th>
                <th>From time (including)</th>
                <th>Before time (not including)</th>
            </tr>
            <tr>
                <td>
                    <input type="date" value="${param.startdate}" id="startdate" name="startdate"/>
                </td>
                <td>
                    <input type="date" value="${param.enddate}" id="enddate" name="enddate"/>
                </td>
                <td>
                    <input type="time" value="${param.starttime}" id="starttime" name="starttime"/>
                </td>
                <td>
                    <input type="time" value="${param.endtime}" id="endtime" name="endtime"/>
                </td>
            </tr>
            <tr>
                <th colspan="4" align="right">
                    <button type="submit">filter</button>
                    <button onclick="window.location.href ='meals'" type="button">Cancel</button>
                </th>
            </tr>
            </tbody>
        </table>
    </form>
    <br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>