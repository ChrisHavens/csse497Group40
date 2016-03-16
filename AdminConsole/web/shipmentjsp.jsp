<%-- 
    Document   : shipmentjsp
    Created on : Feb 25, 2016, 8:41:33 PM
    Author     : daveyle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shipment</title>
    </head>
    <body>
        <h1>${shipment.name}</h1>
        
            Contents: ${shipment.contents}
            <br>
            Status: ${shipment.status}
            <br>
            From: ${shipment.from}
            <br>
            To: ${shipment.to}
        
            
           
    </body>
</html>
