package kz.iitu.javaee.models;

public class UserAndGames implements java.io.Serializable {

    private int userId;
    private int gameId;
    private String status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserAndGames{" +
                "userId=" + userId +
                ", gameId=" + gameId +
                ", status='" + status + '\'' +
                '}';
    }
}
