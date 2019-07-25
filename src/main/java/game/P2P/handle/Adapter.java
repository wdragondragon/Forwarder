package game.P2P.handle;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.event.events.request.EventFriendRequest;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RStrangerInfo;
import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.beans.RecommendInfo;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import cn.zhouyafeng.itchat4j.utils.tools.DownloadTools;
import game.P2P.Tools.DownloadMsg;
import game.P2P.bean.PrivateMsg;
import game.P2P.bean.User_adr;
import game.P2P.bean.User_info;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Adapter extends WXandQQListener implements Controller{
    private Logger LOG = Logger.getLogger(WXandQQListener.class);
    private boolean startup_status = false;//适配器开关
    @Override
    public abstract void handleMsg(PrivateMsg msg);
    @Override
    public abstract void WXhandleFileMsg(PrivateMsg msg);
    @Override
    public abstract void WXhandleImgMsg(PrivateMsg msg);
    @Override
    public abstract void WXhandleVoiceMsg(PrivateMsg msg);
    private IcqHttpApi httpAPI;
    public Adapter(IcqHttpApi httpAPI){
        super();
        this.httpAPI = httpAPI;
    }
    //从QQ发送文本信息
    public void SendMsgToID(String message,
                            String id,
                            String type){
        if(type.equals("QQ")){
            httpAPI.sendPrivateMsg(Long.valueOf(id),message);
        }else if(type.equals("WX")){
            int imageindex = message.indexOf("url=");
            int recordindex = message.indexOf("record,file=");
            //向微信发送图片
            if(imageindex!=-1){
                message = message.substring(imageindex+4,message.length()-1);
                String filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())+".jpg";
                String image = User_adr.image + filename;
                DownloadMsg.downloadMsg(message,image);
                MessageTools.sendPicMsgByUserId(id,image);
            }else if(recordindex!=-1){
                //向微信发送语音
                String filename = message.substring(recordindex+12,message.length()-1);
                    String voice = User_adr.voice + filename;
                MessageTools.sendFileMsgByUserId(id,voice);
            } else {
                MessageTools.sendMsgById(message,id);
            }
        }
    }
    //从微信发送图片消息
    public void SendWXPicMsgToID(String filepath,
                                 String id,
                                 String type){
        if(type.equals("QQ")){
            httpAPI.sendPrivateMsg(Long.valueOf(id),"[CQ:image,file=" + filepath + "]");
        }else if(type.equals("WX")){
            String image = User_adr.image + filepath;
            MessageTools.sendPicMsgByUserId(id,image);
        }
    }
    //从微信发送WX语音消息
    public void SendWXVoiceToID(String filepath,
                                String id,
                                String type){
        if(type.equals("QQ")){
            httpAPI.sendPrivateMsg(Long.valueOf(id),"[CQ:record,file=" + filepath + "]");
        }else if(type.equals("WX")){
            MessageTools.sendFileMsgByUserId(id,filepath);
        }
    }
    //从微信转发其他文件，由于其他文件无法从微信转发到QQ，只转发微信
    public void SendWXFileMsgToID(String filepath,
                                  String id,
                                  String type){
        if(type.equals("QQ")){
//            httpAPI.sendPrivateMsg(Long.valueOf(id),"[CQ:image,file=" + filepath + "]");
        }else if(type.equals("WX")){
            MessageTools.sendFileMsgByUserId(id,filepath);
        }
    }


    @EventHandler
    public void friend(EventFriendRequest event){
        event.accept();
        event.getBot().getAccountManager().refreshCache();
    }
    /**
     *  继承QQ信息的接口
     * @param event
     */
    @EventHandler
    public void Carry(EventPrivateMessage event){
        String message = event.getMessage();
        if(message.equals("-启动")){
            startup_status = true;
            event.respond("已启动");
        } else if(message.equals("-关闭")){
            startup_status = false;
        }
        if (!startup_status)return;
        RStrangerInfo userinfo = event.getSender().getInfo();
        String username = userinfo.getNickname();
        String QQnumber = userinfo.getUserId().toString();
        String usersex = userinfo.getSex();
        long userage = userinfo.getAge();
        User_info user_info = new User_info(QQnumber,username)
                .sex(usersex)
                .age(userage)
                .type("QQ");

        handleMsg(new PrivateMsg()
                .message(message)
                .userinfo(user_info)
        );
    }

    /**
     * 继承接受微信文本信息的接口
     * @param msg
     * @return
     */
    @Override
    public String textMsgHandle(BaseMsg msg) {
        if(msg.isGroupMsg())return null;
        String message = msg.getText();
        if(message.equals("-启动")){
            startup_status = true;
        } else if(message.equals("-关闭")){
            startup_status = false;
        }
        if (!startup_status)return null;
        handleMsg(new PrivateMsg()
                .message(message)
                .userinfo(getWXUserInfo(msg)));
        return null;
    }

    /**
     * 继承重写微信图片信息的接口
     * @param msg
     * @return
     */
    @Override
    public String picMsgHandle(BaseMsg msg) {
        if(msg.isGroupMsg())return null;
        else if(!startup_status)return null;
        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())+ ".jpg";// 这里使用收到图片的时间作为文件名
        String picPath = User_adr.image + fileName ; // 调用此方法来保存图片
        DownloadTools.getDownloadFn(msg, MsgTypeEnum.PIC.getType(), picPath); // 保存图片的路径
        System.out.println("图片保存成功");
        WXhandleImgMsg(new PrivateMsg()
                .userinfo(getWXUserInfo(msg))
                .path(fileName));
        return null;
    }

    /**
     * 微信接受语音信息的接口
     * @param msg
     * @return
     */
    @Override
    public String voiceMsgHandle(BaseMsg msg) {
        if(msg.isGroupMsg())return null;
        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".mp3";
        String voicePath = User_adr.voice  + fileName;
        DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), voicePath);
        System.out.println( "声音保存成功");
        WXhandleVoiceMsg(new PrivateMsg()
                .userinfo(getWXUserInfo(msg))
                .path(fileName));
        return null;
    }

    /**
     * 继承重写微信接收视频的接口
     * @param msg
     * @return
     */
    @Override
    public String viedoMsgHandle(BaseMsg msg) {
        if(msg.isGroupMsg())return null;
        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String viedoPath = User_adr.viedo + fileName + ".mp4";
        DownloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType(), viedoPath);
        System.out.println("视频保存成功");
        WXhandleFileMsg(new PrivateMsg()
                .userinfo(getWXUserInfo(msg))
                .path(viedoPath));
        return null;
    }

    /**
     * 继承重写微信接收文件
     * @param msg
     * @return
     */
    @Override
    public String mediaMsgHandle(BaseMsg msg) {
        if(msg.isGroupMsg())return null;
        String fileName = msg.getFileName();
        String filePath = User_adr.file + fileName; // 这里是需要保存收到的文件路径，文件可以是任何格式如PDF，WORD，EXCEL等。
        DownloadTools.getDownloadFn(msg, MsgTypeEnum.MEDIA.getType(), filePath);
        System.out.println("文件" + fileName + "保存成功");
        WXhandleFileMsg(new PrivateMsg()
                .userinfo(getWXUserInfo(msg))
                .path(filePath));
        return null;
    }

    /**
     * 继承重写微信接收到名片信息的接口
     * @param msg
     * @return
     */
    @Override
    public String nameCardMsgHandle(BaseMsg msg) {
        return null;
    }

    /**
     * 继承重写微信接受到系统信息的接口
     * @param msg
     */
    @Override
    public void sysMsgHandle(BaseMsg msg) {
        String text = msg.getContent();
        LOG.info(text);
    }

    /**
     * 继承重写微信接受到添加好友信息的借口
     * @param msg
     * @return
     */
    @Override
    public String verifyAddFriendMsgHandle(BaseMsg msg) {
        MessageTools.addFriend(msg, true); // 同意好友请求，false为不接受好友请求
        RecommendInfo recommendInfo = msg.getRecommendInfo();
        String nickName = recommendInfo.getNickName();
        return "感谢添加传话筒 "+nickName;
    }

    /**
     * 封装微信userinfo
     * @param msg
     * @return
     */
    private User_info getWXUserInfo(BaseMsg msg){
        String id = msg.getFromUserName();
        String name = "未知";
        return new User_info(id,name)
                .age(0)
                .sex(name)
                .type("WX");
    }
}
