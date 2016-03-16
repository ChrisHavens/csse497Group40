<%-- 
    Document   : groupjsp
    Created on : Feb 25, 2016, 4:40:18 PM
    Author     : daveyle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Group</title>
    </head>
    <body>
        <h1>${group.name}</h1>
         <form name="Info Input Form" 
                  action="${pageContext.request.contextPath}/AdminServlet"
                  method="get">
 
               
                <input type="submit" value="People" name="getAllPeopleByGroup" />
                <input type="submit" value="Locations" name="getAllLocationsByGroup" />
                <input type="submit" value="Shipments" name="getAllShipmentsByGroup" />
                <input type="submit" value="Notes" name="getAllNotesByGroup" />
                <input type="submit" value="Messages" name="getAllMessageThreadsByGroup" />
                <input type="submit" value="Checklists" name="getAllChecklistsByGroup" />
                
            </form>
                 
    </body>
</html>
