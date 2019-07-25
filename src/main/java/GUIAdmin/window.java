package GUIAdmin;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class window extends JFrame {
    private static window win = new window();
    public static window getInstance(){
        return win;
    }
    private window(){
        repalceUI();
        init();
        config();
    }
    JPanel p ;
    JTextArea log ;
    JScrollPane logpane;
    JButton startQQ;
    JButton startWX;
    JButton startBOTH;
    JButton resetlog;
    JButton close;
    public void init(){
        p = new JPanel();
        p.setLayout(null);
        log = new JTextArea();
        logpane = new JScrollPane(log);
        startQQ = new JButton("启动QQ");
        startWX = new JButton("启动WX");
        startBOTH = new JButton("启动BOTH");
        resetlog = new JButton("清空日志");
        close = new JButton("关闭");

        logpane.setBounds(5,5,400,400);
        startQQ.setBounds(420,10,80,30);
        startWX.setBounds(420,50,80,30);
        startBOTH.setBounds(420,90,80,30);
        resetlog.setBounds(420,130,80,30);
        close.setBounds(420,170,80,30);

        log.setLineWrap(true);
        log.setEditable(false);

        RecordOpra recordOpra = new RecordOpra();
        startWX.addActionListener(recordOpra);
        startQQ.addActionListener(recordOpra);
        startBOTH.addActionListener(recordOpra);
        resetlog.addActionListener(recordOpra);
        //添加关闭窗口的监听
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        p.add(logpane);
        p.add(startQQ);
        p.add(startWX);
        p.add(startBOTH);
        p.add(resetlog);
        p.add(close);
        add(p);
    }
    private void repalceUI(){
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible",false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closeButton(){
        startQQ.setEnabled(false);
        startWX.setEnabled(false);
        startBOTH.setEnabled(false);
    }
    public void log(String string){
        log.append(string+"\n");
        if(string==null){log.setText(null);}
    }
    private void config(){
        this.setSize(530,410);
        this.setLocationRelativeTo(null);//显示屏幕中央
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);//去除边框
        this.setResizable(false);
        this.setVisible(true);

        MoveListener moveListener = new MoveListener(this);
        this.addMouseListener(moveListener);
        this.addMouseMotionListener(moveListener);
    }
}
