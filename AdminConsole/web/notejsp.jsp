<%-- 
    Document   : notejsp
    Created on : Feb 25, 2016, 8:55:00 PM
    Author     : daveyle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Note</title>
    </head>
    <body>
        <h1>${note.title}</h1>
        
            ${note.body}
            <br>
            <br>
            <br>
            ${note.date} ${note.time}
        
            
       
    </body>
</html>
