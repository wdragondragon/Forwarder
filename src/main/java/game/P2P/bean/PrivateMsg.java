package game.P2P.bean;

import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;

public class PrivateMsg {
    private User_info user_info;
    private String message;
    private String path;
    private EventPrivateMessage qqmsg;
    private BaseMsg wxmsg;
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
    public PrivateMsg qqmsg(EventPrivateMessage qqmsg){
        this.qqmsg = qqmsg;
        return this;
    }
    public PrivateMsg wxmsg(BaseMsg wxmsg){
        this.wxmsg = wxmsg;
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
    public Object getEvent(){
        if(user_info.getType().equals("QQ"))
            return qqmsg;
        else
            return wxmsg;
    }
}
