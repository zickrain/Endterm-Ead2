<%@ page import="java.util.List" %>
<%@ page import="kz.iitu.javaee.models.Game" %>
<%@ page import="kz.iitu.javaee.models.Game" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- using ACTION TAG jsp:useBean--%>
<jsp:useBean id="dbConnection" class="kz.iitu.javaee.DBConnection" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="styles.css">
    <title>Games in Library</title>
</head>
<body>
<%-- using Directive include --%>
<%@ include file="header.jsp" %>

<%-- Show error message if it exists --%>
<p class="message"><%=(request.getAttribute("message") == null) ? "" : request.getAttribute("message")%></p>

<h2>Games in Library</h2>
<%
    if(session.getAttribute("role").equals("admin")){
%>
        <p><a href="addGame.jsp">Add Game</a></p>
<%}

    List<Game> games = dbConnection.getGames();
    if(dbConnection.getGames()!=null){
        for(Game game : games){
%>
    <%-- using ACTION TAG jsp:include--%>
    <%-- using ACTION TAG jsp:param--%>
    <jsp:include page="game.jsp" >
        <jsp:param name="gameId" value="<%=game.getGameId()%>" />
        <jsp:param name="gameTitle" value="<%=game.getTitle()%>" />
        <jsp:param name="gameGenre" value="<%=game.getGenre()%>" />
    </jsp:include>
<%
            }
        }
%>
</body>
</html>
