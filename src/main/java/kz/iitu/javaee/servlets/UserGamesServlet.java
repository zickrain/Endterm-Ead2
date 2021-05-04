package kz.iitu.javaee.servlets;

import kz.iitu.javaee.models.Game;
import kz.iitu.javaee.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/userGames")
public class UserGamesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DBConnection dbConnection = new DBConnection();
        int userId = (Integer) request.getSession(false).getAttribute("userId");
        String status = request.getParameter("status");

        List<Game> games = new ArrayList<Game>();
        List<Integer> gameIds = dbConnection.getUserGames(userId, status);
        for (Integer id : gameIds) {
            Game b=dbConnection.getGameById(id);
            games.add(b);
        }

        if (status.equals("borrowed")) {
            request.setAttribute("borrowedGames", games);
            request.getRequestDispatcher("/userBorrowed.jsp").forward(request, response);
        } else if(status.equals("returned")) {
            request.setAttribute("returnedGames", games);
            request.getRequestDispatcher("/userReturned.jsp").forward(request, response);
        }
    }
}
