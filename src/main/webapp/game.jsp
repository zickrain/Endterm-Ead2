<%-- using Directive page --%>
<%@ page import="kz.iitu.javaee.models.Game" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- using ACTION TAG jsp:useBean--%>
<jsp:useBean id="dbConnection" class="kz.iitu.javaee.DBConnection" />

    <%-- using Scriplet statement --%>
    <%-- using Implicit Object request--%>
    <%
        int gameId = Integer.parseInt(request.getParameter("gameId"));
        String gameTitle = request.getParameter("gameTitle");
        String gameGenre = request.getParameter("gameGenre");
    %>
    <div>
        <h3>
            <%-- using Scriplet expression --%>
            <%=gameTitle%>
        </h3>
        <p>
            <b>Genre:</b> <%=gameGenre%>
        </p>
        <p>
            <a href="gameDetail.jsp?id=<%=gameId%>">Game details</a>
        </p>
        <%-- using Implicit Object session--%>
        <%
            if(session.getAttribute("role").equals("admin")){
        %>
        <p>
            <button><a href="deleteGame?id=<%=gameId%>">Delete</a></button>
        </p>
        <%-- using Implicit Object session--%>
        <%
            }
            if(session.getAttribute("role").equals("user")){
                int userId = (int) session.getAttribute("userId");
                List<Integer> gameIds = dbConnection.getUserGames(userId, "borrowed");
                if(!gameIds.contains(gameId)){
        %>
        <p>
            <a href="borrowGame?id=<%=gameId%>">Borrow</a>
        </p>

        <%-- using Implicit Object out--%>
        <%
                }else{
                    out.print("<p><a href='returnGame?id="+gameId+"'>Return</a></p>");
                }
            }
        %>
    </div>
