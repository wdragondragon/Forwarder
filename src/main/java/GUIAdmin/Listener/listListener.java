package GUIAdmin.Listener;

import GUIAdmin.Tools.Fileopra;
import GUIAdmin.Tools.listopra;
import GUIAdmin.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class listListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        window win  = window.getInstance();
        String operation = Objects.requireNonNull(win.choose.getSelectedItem()).toString();
        String id = win.addlistid.getText();
        switch (operation){
            case "添加黑名单":
                listopra.addBlacklist(id);
                break;
            case "删除黑名单":
                listopra.delBlacklist(id);
                break;
            case "添加管理员":
                listopra.addManagerlist(id);
                break;
            case "删除管理员":
                listopra.delManagerlist(id);
        }

    }
}
