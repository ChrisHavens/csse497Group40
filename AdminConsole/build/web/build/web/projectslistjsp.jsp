<%-- 
    Document   : projectslistjsp
    Created on : Feb 25, 2016, 3:48:45 PM
    Author     : daveyle
--%>

<%@page import="objects.Project"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Projects</h1>

        <TABLE id="projectsTable">
        <TR>
                <TD>ID</TD>
                <TD>Name</TD>
        </TR>
        
        <c:forEach value="projects" var="item">
            <TR>
                <TD>${item.id}</td>
                <td>${item.name}</td>
            </TR>

        </c:forEach>
</TABLE>

    </body>
</html>
