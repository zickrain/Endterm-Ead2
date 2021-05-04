<%-- using Directive page --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav>
    <a href="index.jsp">Main</a>
    <a href="allGames.jsp">Library Games</a>
    <%-- using Scriplet statement --%>
    <%-- using Implicit Object session--%>
    <%if(session.getAttribute("role").equals("admin")){%>
        <a href="listUsers">Users</a>
        <a href="borrowedGamesReport.jsp">Borrowed Games Report</a>
    <%}%>
    <%if(session.getAttribute("role").equals("user")){ %>
        <a href="userGames?status=borrowed">Borrowed Games</a>
        <a href="userGames?status=returned">Returned Games</a>
    <%}%>
    <a href="logout">Log out</a>
</nav>
