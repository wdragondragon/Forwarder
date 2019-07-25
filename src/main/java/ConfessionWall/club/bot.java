package ConfessionWall.club;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.sender.IcqHttpApi;
import cn.zhouyafeng.itchat4j.Wechat;
import GUIAdmin.PointClient;
import game.P2P.handle.WXandQQListener;

import java.io.File;

public class bot implements botAdapter{
    IcqHttpApi httpApi = null;
    @Override
    public boolean openWXbot() {
        //微信机器人
        String qrPath = System.getProperty("user.dir")+ File.separator +"login";
        WXandQQListener controller = new PointClient(httpApi);
        Wechat wechat = new Wechat(controller, qrPath); // 【注入】
        wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
        return true;
    }
    @Override
    public boolean openQQbot() {
        //创建机器人对象 ( 传入配置 )
        PicqBotX bot = new PicqBotX(new PicqConfig(9999).setDebug(true));
        // 添加一个机器人账户 ( 名字, 发送URL, 发送端口 )
        bot.addAccount("Bot01", "127.0.0.1", 5700);
        httpApi = bot.getAccountManager()
                .getNonAccountSpecifiedApi();
        WXandQQListener pointClient = new PointClient(httpApi);
        // 注册事件监听器, 可以注册多个监听器
        bot.getEventManager().registerListener(pointClient);
        // 启动机器人, 不会占用主线程
        bot.startBot();
        return true;
    }
    @Override
    public boolean openBoth() {
        PicqBotX bot = new PicqBotX(new PicqConfig(9999).setDebug(true));
        bot.addAccount("Bot01", "127.0.0.1", 5700);
        httpApi = bot.getAccountManager()
                .getNonAccountSpecifiedApi();
        WXandQQListener controller = new PointClient(httpApi);
        bot.getEventManager().registerListener(controller);
        bot.startBot();
        String qrPath = System.getProperty("user.dir")+ File.separator +"login";
        Wechat wechat = new Wechat(controller, qrPath); // 【注入】
        wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
        return true;
    }
}
