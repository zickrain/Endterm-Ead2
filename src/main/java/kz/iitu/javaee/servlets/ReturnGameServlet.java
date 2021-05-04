package kz.iitu.javaee.servlets;

import kz.iitu.javaee.models.Game;
import kz.iitu.javaee.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/returnGame")
public class ReturnGameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection dbConnection = new DBConnection();

        int gameId = Integer.parseInt(request.getParameter("id"));
        Game game = dbConnection.getGameById(gameId);

        if (game != null) {
            int userId = (Integer) request.getSession(false).getAttribute("userId");
            int returned = dbConnection.returnGame(userId, game);

            if (returned != 0) {
                request.setAttribute("message", "Game "+ game.getTitle()+" returned successfully!");
                request.getRequestDispatcher("allGames.jsp").forward(request, response);
            }
        }
    }
}
