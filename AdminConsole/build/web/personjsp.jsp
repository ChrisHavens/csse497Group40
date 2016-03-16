<%-- 
    Document   : personjsp
    Created on : Feb 25, 2016, 6:32:10 PM
    Author     : daveyle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Person</title>
    </head>
    <body>
        <h1>${person.name}</h1>
        
            Email: ${person.email}
            <br>
            Phone: ${person.phoneNumber}
            
       
    </body>
</html>
