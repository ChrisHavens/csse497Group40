<%-- 
    Document   : projectjsp
    Created on : Feb 25, 2016, 4:12:09 PM
    Author     : daveyle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project</title>
    </head>
    <body>
        <h1>${project.name}</h1>
         <form name="Info Input Form" 
                  action="${pageContext.request.contextPath}/AdminServlet"
                  method="get">
             
<script type="text/javascript"> 
function groupShow()
{
    document.getElementById("groupsprimary").style.display="block";
    document.getElementById("groupsprimary").style.visibility="visible";
    }
</script>
                <input type="button" onClick="groupShow()" value="Groups" />
               
                <input type="submit" value="People" name="getAllPeopleByProject" />
                
            </form>
                  <div id="groupsprimary" style="display: none" style="visibility: hidden">
                      <form name="Group Chooser" action="${pageContext.request.contextPath}/AdminServlet" method="get">
                          Group ID: <input type="text" name="groupID" value="" />
                          <input type="submit" value="Find Group" name="getGroupByID" />
                          <input type="submit" value="Show All Groups" name="getAllGroupsByProject" />
                      </form>
                  </div>
                          
                 
    </body>
</html>
