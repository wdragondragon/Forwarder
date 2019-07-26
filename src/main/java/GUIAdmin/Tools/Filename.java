package GUIAdmin.Tools;

import GUIAdmin.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Filename implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        window win = window.getInstance();
        String source = e.getActionCommand();
        String filename = Fileopra.getFile();
        switch (source) {
            case "file":
                win.file.setText(filename);
                break;
            case "image":
                win.image.setText(filename);
                break;
            case "viedo":
                win.viedo.setText(filename);
                break;
            case "voice":
                win.voice.setText(filename);
                break;
        }
    }
}
