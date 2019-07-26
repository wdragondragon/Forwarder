package GUIAdmin.Listener;


import GUIAdmin.Tools.Fileopra;
import GUIAdmin.window;
import com.alibaba.fastjson.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfiresetListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        window win = window.getInstance();
        JSONObject jsonObject = new JSONObject();
        String filepath = win.file.getText();
        String imagepath = win.image.getText();
        String viedopath = win.viedo.getText();
        String voicepath = win.voice.getText();

        jsonObject.put("file",filepath);
        jsonObject.put("image",imagepath);
        jsonObject.put("viedo",viedopath);
        jsonObject.put("voice",voicepath);

        win.cards.show(win.container,"log");
        Fileopra.writefile(jsonObject);
    }
}
