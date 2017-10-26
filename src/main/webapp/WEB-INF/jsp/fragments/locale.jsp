<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<li class="dropdown">
    <a class="dropdown-toggle" href="#" data-toggle="dropdown">${pageContext.response.locale}<b class="caret"></b></a>
    <ul class="dropdown-menu" >
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">English</a></li>
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru">Russian</a></li>
    </ul>
</li>
