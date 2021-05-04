<%-- using Directive page --%>
<%@ page import="kz.iitu.javaee.DBConnection" %>
<%@ page import="kz.iitu.javaee.models.Game" %>
<%@ page import="kz.iitu.javaee.models.UserAndGames" %>
<%@ page import="kz.iitu.javaee.models.User" %>
<%@ page import="kz.iitu.javaee.models.Game" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- using ACTION TAG jsp:useBean--%>
<jsp:useBean id="dbConnection" class="kz.iitu.javaee.DBConnection" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="styles.css">
    <title>Borrowed Games Report</title>
</head>
<body>
<%-- using Scriplet declaration --%>
<%!
    User user;
    Game game;
%>
<%-- using Directive include --%>
<%@ include file="header.jsp" %>

<h2>Borrowed Games Report</h2>
<table width="60%">
    <tr>
        <th>User Full Name</th>
        <th>Username</th>
        <th>Game ID</th>
        <th>Game Title</th>
        <th>Borrow Status</th>
        <th>Info</th>
    </tr>
    <%-- using Scriplet statement --%>
    <%
        for(UserAndGames userAndGame : dbConnection.getAllUsersGames("borrowed")){
            user = dbConnection.getUserById(userAndGame.getUserId());
            game = dbConnection.getGameById(userAndGame.getGameId());
    %>
        <tr>
            <%-- using Scriplet expression --%>
            <td><%=user.getFullname()%></td>
            <td><%=user.getUsername()%></td>
            <td><%=game.getGameId()%></td>
            <td><%=game.getTitle()%></td>
            <td><%=userAndGame.getStatus()%></td>
            <td><a href="gameDetail.jsp?id=<%=game.getGameId()%>">Game details</a></td>
        </tr>
    <%
        }
    %>
</table>
</body>
</html>
