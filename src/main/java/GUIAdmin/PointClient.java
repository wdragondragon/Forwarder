package GUIAdmin;

import GUIAdmin.Bean.Room;
import GUIAdmin.Tools.listopra;
import cc.moecraft.icq.sender.IcqHttpApi;
import game.P2P.bean.PrivateMsg;
import game.P2P.bean.User_info;
import game.P2P.handle.Adapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * handleMsg 中的message为接受到的文字信息
 * 由于微信协议不同，需要将文件类信息分开接受
 * WXhandleImgMsg 接收图片
 * WXhandleVoiceMsg 接收语音
 * WXhandleFileMsg 接收文件
 */
public class PointClient extends Adapter {
    private window win = window.getInstance();
    private List<List<String>> roommembers = new ArrayList<List<String>>();//匹配排队房间
    private HashMap<Integer, Room> roommap = new HashMap<Integer, Room>();//全部房间信息
    private HashMap<String, User_info> member = new HashMap<String, User_info>();//全部用户信息
    private int roomnum = 0;//全局房间号计数
    public PointClient(IcqHttpApi httpApi){
        super(httpApi);
//        User_adr.file = "/root/coolq/data/file/";
//        User_adr.image = "/root/coolq/data/image/";
//        User_adr.viedo = "/root/coolq/data/viedo/";
//        User_adr.voice = "/root/coolq/data/record/";
//        User_adr.file = "C:\\Users\\Lenovo\\Desktop\\酷Q Pro\\data\\file\\";
//        User_adr.image = "C:\\Users\\Lenovo\\Desktop\\酷Q Pro\\data\\image\\";
//        User_adr.voice = "C:\\Users\\Lenovo\\Desktop\\酷Q Pro\\data\\record\\";
//        User_adr.viedo = "C:\\Users\\Lenovo\\Desktop\\酷Q Pro\\data\\viedo\\";
//        Fileopra.separator
    }
    //实现Controller接口
    @Override
    public void handleMsg(PrivateMsg msg){
        try {
            String message = msg.getMessage();
            User_info user_info= msg.getUser_info();
            String id = user_info.getQQnumber();
            //黑名单判断
            if (win.blacklist.contains(id))return;
            String type = user_info.getType();
            String sex = user_info.getSex();
            String logtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String log =  logtime + "\n" + type+"id:" + id + " 性别:"+ sex;
            if (!member.containsKey(id)) {
                if (message.equals("#匹配")) {
                    win.log(log + "--->匹配");
                    //该用户不在成员中且发送匹配字段时添加用户操作
                    List<String> roommember = roommembers.size()==0?null:roommembers.get(0);
                    //队列里没有匹配房间，新创一个房间并加他加入队列
                    if(roommember!=null&&(type.equals("WX")||!sex.equals(member.get(roommember.get(0)).getSex()))){
                        //成员信息中设置房间号
                        user_info.setRoomnumber(member.get(roommember.get(0)).getRoomnumber());
                        roommember.add(id);
                    }
                    else if (roommember == null||sex.equals(member.get(roommember.get(0)).getSex())){
                        roomnum++;
                        roommember = new ArrayList<>();
                        //临时匹配房间成员添加
                        roommember.add(id);
                        roommembers.add(roommember);
                        //成员信息中设置房间号
                        user_info.setRoomnumber(roomnum);
                    }
                    //成员添加
                    member.put(id, user_info);
                    SendMsgToID("正在匹配", id, type);

                    //通知关注你的人
                    if(win.carelist.containsKey(id)){
                        List<String> carelist = win.carelist.get(id);
                        for (String s : carelist) {
                            System.out.println(s);
                            SendMsgToID("你关注的人正在匹配",s,type);
                        }
                    }
                    //添加到使用过的用户名单，用于广播
                    listopra.addMemberlist(id);
                    //判断是否满人
                    if (roommember.size() == 2) {
                        //临时房间满人，放入房间map
                        int roomnumtemp = member.get(roommember.get(0)).getRoomnumber();
                        Room room = new Room(roommember,logtime,roomnumtemp);
                        roommap.put(roomnumtemp, room);
                        for (int i = 0; i < roommember.size(); i++) {
                            String sendtoid = roommember.get(i);
                            String totype = member.get(sendtoid).getType();
                            String linkfromid = roommember.get((i + 1) % 2);
                            String sendtype = member.get(linkfromid).getType();
                            String sendsex = member.get(linkfromid).getSex();
                            SendMsgToID("已配对，对方使用" + sendtype + "对方性别" + sendsex, sendtoid, totype);

                            //检测有没有互相匹配到关注的人
                            if(win.carelist.containsKey(linkfromid)){
                                List<String> carelist = win.carelist.get(linkfromid);
                                if(carelist.contains(sendtoid))
                                    SendMsgToID("偷偷告诉你，你和你关注的人匹配到一起了哦！",sendtoid,totype);
                            }
                            if(i==1){
                                win.log("------\n"+log+"\n配对\n"+totype+"id:" + linkfromid + " 性别:"+ sendsex + " 房间号:" + roomnumtemp + "\n------");
                            }
                        }
                        //临时房间删除
                        roommembers.remove(roommember);
                    }
                }
            } else if (member.containsKey(id)) {
                //获取用户所在的房间号
                int thisQQroom = member.get(id).getRoomnumber();
                //通过房间号获取房间成员
                Room room = roommap.get(thisQQroom);
                List<String> roommember1 = room.getRoommember();
                if (message.equals("#退出")) {
                    room.setDeteledata(logtime);
                    for (String sendtoid : roommember1) {//移除房间成员
                        SendMsgToID("房间中有一人退出，房间关闭", sendtoid, member.get(sendtoid).getType());
                        member.remove(sendtoid);
                    }
                    roommap.remove(thisQQroom);//移除房间
                    //日志
                    win.log(log + "-->主动退出-->" + thisQQroom + "号房间解散");
                    win.addRow(room.getRoomnub()
                            ,roommember1.get(0)
                            ,roommember1.get(1)
                            ,room.getCreatedata()
                            ,room.getDeteledata()
                            ,room.getMessagenum());
                } else if(message.equals("#关注")){
                    if(member.get(roommember1.get(0)).getType().equals("QQ")
                            &&member.get(roommember1.get(1)).getType().equals("QQ")) {
                        for (String careid : roommember1)
                            if (!careid.equals(id))
                                if(listopra.addCarelist(id, careid))SendMsgToID("关注成功",id,type);
                                else SendMsgToID("你已关注过他/她",id,type);
                    }else{
                        SendMsgToID("关注只支持QQ，请确定对方和你都为QQ端",id,type);
                    }
                } else if(message.equals("#取消关注")){
                    if(member.get(roommember1.get(0)).getType().equals("QQ")
                            &&member.get(roommember1.get(1)).getType().equals("QQ")) {
                        for (String careid : roommember1)
                            if (!careid.equals(id))
                                if(listopra.delCarelist(id, careid))SendMsgToID("取消关注成功",id,type);
                                else SendMsgToID("你未关注过他/她",id,type);
                    }else{
                        SendMsgToID("关注只支持QQ，请确定对方和你都为QQ端",id,type);
                    }
                } else {
                    roommap.get(member.get(id).getRoomnumber()).addMessagenum();
                    for (String sendtoid : roommember1) {
                        if (!sendtoid.equals(id))
                            SendMsgToID(message, sendtoid, member.get(sendtoid).getType());
                    }
                }
            }
            System.out.println(roommap);
            System.out.println(member);
        }catch (Exception e){e.printStackTrace();}
    }
    @Override
    public void WXhandleImgMsg(PrivateMsg msg) {
        User_info user_info = msg.getUser_info();
        String filepath = msg.getPath();
        String id = user_info.getQQnumber();
        if(member.containsKey(id)) {
            roommap.get(id).addMessagenum();
            //获取用户所在的房间号
            int thisQQroom = member.get(id).getRoomnumber();
            //通过房间号获取房间成员
            List<String> roommember1 = roommap.get(thisQQroom).getRoommember();
            for (String sendtoid : roommember1) {
                if (!sendtoid.equals(id)) {
                    SendWXVoiceToID(filepath, sendtoid, member.get(sendtoid).getType());
                }
            }
        }
    }
    @Override
    public void WXhandleVoiceMsg(PrivateMsg msg) {
        User_info user_info = msg.getUser_info();
        String filepath = msg.getPath();
        String id = user_info.getQQnumber();
        if(member.containsKey(id)) {
            roommap.get(id).addMessagenum();
            //获取用户所在的房间号
            int thisQQroom = member.get(id).getRoomnumber();
            //通过房间号获取房间成员
            List<String> roommember1 = roommap.get(thisQQroom).getRoommember();
            for (String sendtoid : roommember1) {
                if (!sendtoid.equals(id)) {
                    SendWXPicMsgToID(filepath, sendtoid, member.get(sendtoid).getType());
                }
            }
        }
    }
    //实现Controller接口
    @Override
    public void WXhandleFileMsg(PrivateMsg msg){
        User_info user_info = msg.getUser_info();
        String filepath = msg.getPath();
        String id = user_info.getQQnumber();
        if(member.containsKey(id)) {
            roommap.get(id).addMessagenum();
            //获取用户所在的房间号
            int thisQQroom = member.get(id).getRoomnumber();
            //通过房间号获取房间成员
            List<String> roommember1 = roommap.get(thisQQroom).getRoommember();
            for (String sendtoid : roommember1) {
                if (!sendtoid.equals(id)) {
                    SendWXFileMsgToID(filepath, sendtoid, member.get(sendtoid).getType());
                }
            }
        }
    }
}
