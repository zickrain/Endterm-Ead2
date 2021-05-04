package kz.iitu.javaee.servlets;

import kz.iitu.javaee.models.Game;
import kz.iitu.javaee.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/deleteGame")
public class DeleteGameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection dbConnection = new DBConnection();

        int id = Integer.parseInt(request.getParameter("id"));
        Game game = dbConnection.getGameById(id);

        if (game != null) {
            int addedGame = dbConnection.deleteGame(id);

            if (addedGame != 0) {
                request.setAttribute("message", "Game " + game.getTitle() + " deleted!");
                request.getRequestDispatcher("allGames.jsp").forward(request, response);
            }
        }
    }
}
