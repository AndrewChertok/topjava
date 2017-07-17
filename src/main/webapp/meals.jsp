<%@ page contentType="text/html;charset=UTF-8" language="java" import="ru.javawebinar.topjava.web.MealServlet, javax.servlet.Servlet.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>

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
            color:#000000;
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




    </style>
</head>
<body>
<h2>Meals</h2>



<c:if test="${!empty meals}">
    <table class ="tg">
    <tr>
    <th width="120">Дата/Время</th>
    <th width="120">Описание</th>
    <th width="120">Калории</th>
    </tr>
    <c:forEach items = "${meals}" var = "meal">
        <tr style="${ meal.isExceed() ? "background-color:#ff6666" : "background-color:#99ffbb"}">
            <td>${meal.getDateTime().toString().replace('T', ' ')}</td>
            <td>${meal.getDescription()} </td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>
    </table>
</c:if>

</body>
</html>
