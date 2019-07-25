package game.P2P.handle;
import cc.moecraft.icq.event.IcqListener;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;

/**
 *
 */
public class WXandQQListener extends IcqListener implements IMsgHandlerFace {
    @Override
    public String textMsgHandle(BaseMsg msg) {
        return null;
    }
    @Override
    public String picMsgHandle(BaseMsg msg) {
        return null;
    }
    @Override
    public String voiceMsgHandle(BaseMsg msg) {
        return null;
    }

    @Override
    public String viedoMsgHandle(BaseMsg msg) {
        return null;
    }
    @Override
    public String nameCardMsgHandle(BaseMsg msg) {
        return null;
    }
    @Override
    public void sysMsgHandle(BaseMsg msg) {
    }
    @Override
    public String verifyAddFriendMsgHandle(BaseMsg msg) {
        return null;
    }
    @Override
    public String mediaMsgHandle(BaseMsg msg) {
        return null;
    }
}
