package ly.remote.medmanager.models;

/**
 * Created by Risen on 4/19/2018.
 */

public class UserModel {
    private String Username;
    private String UserBio;

    public UserModel(String username, String userBio) {
        Username = username;
        UserBio = userBio;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserBio() {
        return UserBio;
    }

    public void setUserBio(String userBio) {
        UserBio = userBio;
    }
}
