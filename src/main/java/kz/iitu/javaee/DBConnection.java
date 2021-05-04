package kz.iitu.javaee;

import kz.iitu.javaee.models.Game;
import kz.iitu.javaee.models.User;
import kz.iitu.javaee.models.UserAndGames;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static String url = "jdbc:postgresql://localhost:5433/anime_catalog";
    private static String driverName = "org.postgresql.Driver";
    private static String login = "postgres";
    private static String dbpassword = "12345";
    Connection connection;
    Statement statement;


    public DBConnection() {
        connect();
    }

    public void connect() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            connection = DriverManager.getConnection(url, login, dbpassword);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(String username, String password){

        String sqlString = "SELECT * FROM users WHERE username='" + username +"' AND "+"password='" + password+"'";
        return getUser(sqlString);
    }

    private User getUser(String sqlString) {
        User user = new User();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                constructUser(resultSet, user);
            }
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        return user;
    }

    public User getUserById(int userId){

        String sqlString = "SELECT * FROM users WHERE user_id=" + userId;
        return getUser(sqlString);
    }

    public int createUser(User user){

        String sqlString = "insert into users (fullname, email, username, password, role) values('"
                +user.getFullname()+"', '"
                +user.getEmail()+"', '"
                +user.getUsername()+"', '"
                +user.getPassword()+"', '"
                +user.getRole()+"')";

        int result=0;
        try{
            result = statement.executeUpdate(sqlString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Game> getGames(){

        String sqlString = "SELECT * FROM games";
        List<Game> gameList = new ArrayList<Game>();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                Game game =new Game();
                game.setGameId(resultSet.getInt("game_id"));
                game.setTitle(resultSet.getString("title"));
                game.setDescription(resultSet.getString("description"));
                game.setGenre(resultSet.getString("genre"));
                gameList.add(game);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gameList;
    }

    public int addGame(Game game){

        String sqlString = "insert into games (title, description, genre) values('"
                + game.getTitle()+"', '"
                + game.getDescription()+"', '"
                + game.getGenre()+"')";

        int result=0;
        try{
            result = statement.executeUpdate(sqlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<User> getUsers(){

        String sqlString = "SELECT * FROM users";
        List<User> userList = new ArrayList<User>();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                User user=new User();
                constructUser(resultSet, user);
                userList.add(user);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    private void constructUser(ResultSet resultSet, User user) throws SQLException {
        user.setUserId(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setFullname(resultSet.getString("fullname"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(resultSet.getString("role"));
    }

    public Game getGameById(int id){

        String sqlString = "SELECT * FROM games WHERE game_id=" + id;
        Game game = new Game();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                game.setGameId(resultSet.getInt("game_id"));
                game.setTitle(resultSet.getString("title"));
                game.setDescription(resultSet.getString("description"));
                game.setGenre(resultSet.getString("genre"));
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return game;
    }

    public int deleteGame(int id){

        String sqlString = "DELETE FROM games WHERE game_id=" + id;

        int result=0;
        try{
            result = statement.executeUpdate(sqlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int borrowGame(int userId, Game game){

        int row = hasUserGame(userId, game.getGameId());
        String sqlString;
        if(row!=0){
            sqlString = "update users_game set status='borrowed' where game_id="+ game.getGameId()+" and user_id="+userId;
        }else{
            sqlString = "insert into users_game (user_id, game_id, status) values("
                    +userId+", "
                    + game.getGameId()+", "
                    + "'borrowed' )";
        }

        int result=0;
        try{
            result = statement.executeUpdate(sqlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Integer> getUserGames(int userId, String status){

        String sqlString = "SELECT * FROM users_game where status='"+status+"' and user_id="+userId;
        List<Integer> gameIds = new ArrayList<Integer>();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                gameIds.add(resultSet.getInt("game_id"));
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gameIds;
    }

    public int hasUserGame(int userId, int gameId){

        String sqlString = "SELECT count(*) as row_count FROM users_game WHERE user_id=" + userId+" and game_id=" + gameId;
        int row_count=0;
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                row_count=resultSet.getInt("row_count");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return row_count;
    }

    public List<UserAndGames> getAllUsersGames(String status){

        String sqlString = "SELECT * FROM users_game where status='"+status+"'";
        List<UserAndGames> usersGames = new ArrayList<UserAndGames>();
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()) {
                UserAndGames userGame=new UserAndGames();
                userGame.setUserId(resultSet.getInt("user_id"));
                userGame.setGameId(resultSet.getInt("game_id"));
                userGame.setStatus(resultSet.getString("status"));
                usersGames.add(userGame);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usersGames;
    }

    public int returnGame(int userId, Game game){

        String sqlString = "update users_game set status='returned' where game_id="+ game.getGameId()+" and user_id="+userId;

        int result=0;
        try{
            result = statement.executeUpdate(sqlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
