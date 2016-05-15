<%-- 
    Document   : locationjsp
    Created on : Feb 25, 2016, 8:38:03 PM
    Author     : daveyle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Location</title>
    </head>
    <body>
        <h1>${location.name}</h1>
        
            Lat: ${location.lat}
            <br>
            Lng: ${location.lng}
            
       
    </body>
</html>
