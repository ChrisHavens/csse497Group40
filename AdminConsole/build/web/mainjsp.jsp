<%-- 
    Document   : mainjsp
    Created on : Feb 25, 2016, 3:16:08 PM
    Author     : daveyle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main</title>
    </head>
    <body>
        
        <h1>Welcome back, ${person.name}!</h1>
        <div id="topleveltabs" style="display: block" style="visibility: visible">
        <form name="Info Input Form" 
                  action="${pageContext.request.contextPath}/AdminServlet"
                  method="get">
             <script type="text/javascript"> 
function projectsShow()
{
    document.getElementById("projectsprimary").style.display="block";
    document.getElementById("projectsprimary").style.visibility="visible";
    document.getElementById("groupsprimary").style.display="none";
    document.getElementById("groupsprimary").style.visibility="invisible";
    document.getElementById("peopleprimary").style.display="none";
    document.getElementById("peopleprimary").style.visibility="invisible";
    }
 function groupsShow()
{
    document.getElementById("projectsprimary").style.display="none";
    document.getElementById("projectsprimary").style.visibility="invisible";
    document.getElementById("groupsprimary").style.display="block";
    document.getElementById("groupsprimary").style.visibility="visible";
    document.getElementById("peopleprimary").style.display="none";
    document.getElementById("peopleprimary").style.visibility="invisible";
    }
    
 function personShow()
{
    document.getElementById("projectsprimary").style.display="none";
    document.getElementById("projectsprimary").style.visibility="invisible";
    document.getElementById("peopleprimary").style.display="block";
    document.getElementById("peopleprimary").style.visibility="visible";
    document.getElementById("groupsprimary").style.display="none";
    document.getElementById("groupsprimary").style.visibility="invisible";
    }
 
</script>
                <input type="button" onClick="projectsShow()" value="Projects" />
               
                <input type="button" onClick="groupsShow()" value="Groups" />
                <input type="button" onClick="personShow()" value="People" />
            </form>
        </div>
                  <div id="projectsprimary" style="display: none" style="visibility: hidden">
                      <form name="Project Chooser" action="${pageContext.request.contextPath}/AdminServlet" method="get">
                          Project ID: <input type="text" name="projectID" value="" />
                          <input type="submit" value="Find Project" name="getProjectByID" />
                          <input type="submit" value="Show All Projects" name="getAllProjects" />
                      </form>
                  </div>
                          <div id="groupsprimary" style="display: none" style="visibility: hidden">
                      <form name="Group Chooser" action="${pageContext.request.contextPath}/AdminServlet" method="get">
                          Group ID: <input type="text" name="groupID" value="" />
                          <input type="submit" value="Find Group" name="getGroupByID" />
                          <input type="submit" value="Show All Groups" name="getAllGroups" />
                      </form>
                  </div>
                           <div id="peopleprimary" style="display: none" style="visibility: hidden">
                      <form name="People Chooser" action="${pageContext.request.contextPath}/AdminServlet" method="get">
                          Person ID: <input type="text" name="personID" value="" />
                          <input type="submit" value="Find Person" name="getPersonByID" />
                          <input type="submit" value="Show All People" name="getAllPeople" />
                      </form>
                  </div>
        
    </body>
</html>
