package game.P2P.bean;

public class PrivateMsg {
    private User_info user_info;
    private String message;
    private String path;
    public PrivateMsg userinfo(User_info user_info){
        this.user_info = user_info;
        return this;
    }
    public PrivateMsg message(String message){
        this.message = message;
        return this;
    }
    public PrivateMsg path(String path){
        this.path = path;
        return this;
    }
    public User_info getUser_info() {
        return user_info;
    }
    public String getMessage() {
        return message;
    }
    public String getPath() {
        return path;
    }
}
