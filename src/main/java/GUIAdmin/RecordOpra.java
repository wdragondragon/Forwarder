package GUIAdmin;

import ConfessionWall.club.bot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordOpra extends bot implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String type = e.getActionCommand();
        window win = window.getInstance();
        if ("启动QQ".equals(type)) {
            win.log(type);
            if(super.openQQbot()) {
                win.log("传话筒QQ部分启动成功");
                win.closeButton();
            }else {
                win.log("传话筒QQ部分启动失败");
            }
        } else if ("启动WX".equals(type)) {
            win.log(type);
            if (super.openWXbot()) {
                win.log("传话筒WX部分启动成功");
                win.closeButton();
            }else {
                win.log("传话筒WX部分启动失败");
            }
        } else if("启动BOTH".equals(type)){
            win.log(type);
            if(super.openBoth()){
                win.log("传话筒启动成功");
                win.closeButton();
            }else{
                win.log("传话筒启动失败");
            }
        }else if ("清空日志".equals(type)) {
            win.log(null);
        }
    }
}
