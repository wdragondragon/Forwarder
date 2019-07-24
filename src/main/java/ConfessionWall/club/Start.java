package ConfessionWall.club;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cn.zhouyafeng.itchat4j.Wechat;
import game.P2P.PointClient;

public class Start {
    public static void main(String []args){
        // 创建机器人对象 ( 传入配置 )
        PicqBotX bot = new PicqBotX(new PicqConfig(9999).setDebug(true));

        // 添加一个机器人账户 ( 名字, 发送URL, 发送端口 )
        bot.addAccount("Bot01", "127.0.0.1", 5700);
        PointClient pointClient = new PointClient(bot.getAccountManager().getNonAccountSpecifiedApi());

        // 注册事件监听器, 可以注册多个监听器
        bot.getEventManager().registerListeners(
                pointClient
        );
        // 启动机器人, 不会占用主线程
        bot.startBot();

        //微信机器人
        String qrPath = System.getProperty("user.dir")+"/login";
//                "D://itchat4j//login"; // 保存登陆二维码图片的路径，这里需要在本地新建目录
//        IMsgHandlerFace msgHandler = new PointClient(); // 实现IMsgHandlerFace接口的类
        Wechat wechat = new Wechat(pointClient, qrPath); // 【注入】
        wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
    }
}