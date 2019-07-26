package GUIAdmin.Bean;

import java.util.List;

public class Room {
    List<String> roommember;
    int roomnub;
    String createdata;
    String deteledata;
    int messagenum = 0;
    boolean sign = false;
    public Room(List list, String createdata, int roomnub){
        roommember = list;
        this.createdata = createdata;
        this.roomnub = roomnub;
    }
    public int getRoomnub() {
        return roomnub;
    }
    public String getCreatedata() {
        return createdata;
    }
    public String getDeteledata() {
        return deteledata;
    }
    public void setDeteledata(String deteledata) {
        this.deteledata = deteledata;
    }
    public List<String> getRoommember() {
        return roommember;
    }
    public int getMessagenum() {
        return messagenum;
    }
    public void addMessagenum() {
        messagenum++;
    }
    public boolean getSign(){
        return sign;
    }
}
