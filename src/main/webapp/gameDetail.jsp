<%-- using Directive page --%>
<%@ page import="java.util.List" %>
<%@ page import="kz.iitu.javaee.models.Game" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- using ACTION TAG jsp:useBean--%>
<jsp:useBean id="dbConnection" class="kz.iitu.javaee.DBConnection" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>

<%-- using Scriplet statement --%>
<%-- using Implicit Object request--%>
<%
    int gameId = Integer.parseInt(request.getParameter("id"));
    Game game = dbConnection.getGameById(gameId);
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="styles.css">
    <%-- using Scriplet expression --%>
    <title><%=game.getTitle()%></title>
</head>
<body>

<%-- using Directive include --%>
<%@ include file="header.jsp" %>
<div>
    <h3>
        <%=game.getTitle()%>
    </h3>
    <p>
        <b>Description:</b> <%=game.getDescription()%>
    </p>
    <p>
        <b>Genre:</b> <%=game.getGenre()%>
    </p>
    <%-- using Implicit Object session--%>
    <%
        if(session.getAttribute("role").equals("admin")){
    %>
    <p>
        <button><a href="deleteGame?id=<%=game.getGameId()%>">Delete</a></button>
    </p>
    <%
        }
    %>
    <%-- using Implicit Object session--%>
    <%
        if(session.getAttribute("role").equals("user")){
            int userId = (int) session.getAttribute("userId");
            List<Integer> gameIds = dbConnection.getUserGames(userId, "borrowed");
            if(!gameIds.contains(game.getGameId())){
    %>
                <p>
                    <a href="borrowGame?id=<%=game.getGameId()%>">Borrow</a>
                </p>
    <%-- using Implicit Object out--%>
    <%
            }else{
                out.print("<p><a href='returnGame?id="+ game.getGameId()+"'>Return</a></p>");
            }
        }
    %>
</div>
</body>
</html>
