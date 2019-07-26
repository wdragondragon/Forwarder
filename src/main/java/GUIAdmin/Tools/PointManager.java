package GUIAdmin.Tools;

import GUIAdmin.window;
import cc.moecraft.icq.sender.IcqHttpApi;
import game.P2P.bean.PrivateMsg;
import game.P2P.handle.Adapter;

public class PointManager extends Adapter {

    public PointManager(IcqHttpApi httpAPI) {
        super(httpAPI);
    }
    @Override
    public void handleMsg(PrivateMsg msg) {
        String id = msg.getUser_info().getQQnumber();
        String type = msg.getUser_info().getType();
        if(!window.getInstance().managerlist.contains(id))return;
        String message = msg.getMessage();
        if(message.substring(0,2).equals("拉黑")){
            System.out.println(message);
            if(listopra.addBlacklist(message.substring(2)))
                SendMsgToID(message+"成功",id,type);
        }else if(message.substring(0,4).equals("取消拉黑")){
            System.out.println(message);
            if(listopra.delBlacklist(message.substring(4)))
                SendMsgToID(message+"成功",id,type);
        }
    }
    @Override
    public void WXhandleFileMsg(PrivateMsg msg) {

    }
    @Override
    public void WXhandleImgMsg(PrivateMsg msg) {

    }
    @Override
    public void WXhandleVoiceMsg(PrivateMsg msg) {

    }
}
