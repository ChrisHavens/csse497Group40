<%-- 
    Document   : index
    Created on : Feb 25, 2016, 1:07:09 PM
    Author     : daveyle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>s40 Admin Console
            
            <form name="Info Input Form" 
                  action="${pageContext.request.contextPath}/AdminServlet"
                  method="post">
                UserID: <input type="text" name="username" value="" />
                Password: <input type="password" name="password" value="" />
                <input type="submit" name="login" value="OK" />
            </form></h1>
    </body>
</html>
