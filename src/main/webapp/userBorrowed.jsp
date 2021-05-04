<%@ page import="java.util.List" %>
<%@ page import="kz.iitu.javaee.models.Game" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="kz.iitu.javaee.models.Game" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="styles.css">
    <title><%=session.getAttribute("username")%>'s borrowed games</title>
</head>
<body>
<%-- using Directive include --%>
<%@ include file="header.jsp" %>

<%-- using Implicit Object session--%>
<%-- using Implicit Object request--%>
<h2><%=session.getAttribute("username")%>'s borrowed games</h2>
<%
    List<Game> games = (ArrayList<Game>)request.getAttribute("borrowedGames");
    if(games !=null){
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
