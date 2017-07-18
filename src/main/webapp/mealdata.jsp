<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>



<html>
<head>
    <title>MealData</title>

    <style type="text/css">
        .tg{
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }
        .tg td{
            font-family:Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color:#fff;
        }

        .tg th{
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color:#f0f0f0;
        }

        .tg .tg-4eph{
            background-color:#f9f9f9;
        }
    </style>
</head>
<body>

<h1 align="center">Meal Details</h1>

<form method="POST" action='meals' name="frmAddMeal">

    Meal ID : <input type="text" readonly="readonly" name="mealId"
                     value="<c:out value="${meal.getId()}" />" /> <br />
    Date : <input
        type="datetime-local" name="dateTime"
        value="<c:out value="${meal.getDateTime()}" />" /> <br />
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.getDescription()}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.getCalories()}" />" /> <br />
     <input type="submit" value="Submit" />
</form>

</body>
</html>