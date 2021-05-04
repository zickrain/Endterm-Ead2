package kz.iitu.javaee.servlets;

import kz.iitu.javaee.models.Game;
import kz.iitu.javaee.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/addGame")
public class AddGameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection dbConnection = new DBConnection();

        Game game = new Game();
        game.setTitle(request.getParameter("title"));
        game.setDescription(request.getParameter("desc"));
        game.setGenre(request.getParameter("genre"));
        int addedGame = dbConnection.addGame(game);

        if (addedGame != 0) {
            request.setAttribute("message", "Game " + game.getTitle()+" added!");
            request.getRequestDispatcher("allGames.jsp").forward(request, response);
        } else {
            request.setAttribute("errMessage", "Something went wrong. Game was not added!");
            request.getRequestDispatcher("/addGame.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
