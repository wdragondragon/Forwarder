package GUIAdmin;

import GUIAdmin.Listener.listListener;
import GUIAdmin.Listener.ConfiresetListener;
import GUIAdmin.Listener.MoveListener;
import GUIAdmin.Tools.Filename;
import GUIAdmin.Tools.Fileopra;
import com.alibaba.fastjson.JSONObject;
import game.P2P.bean.User_adr;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Vector;

public class window extends JFrame {
    private static window win = new window();
    public static window getInstance(){
        return win;
    }
    private window(){
        repalceUI();
        init();
        initpath();
        config();
    }
    public final CardLayout cards = new CardLayout();
    public final JPanel container = new JPanel(cards);
    public void init(){
        p1();
        p2();
        add(container);
        container.add(p1,"log");
        container.add(p2,"set");
    }
    JPanel p1 ;
    JTextArea log ;
    JScrollPane logpane;
    JButton startQQ;
    JButton startWX;
    JButton startBOTH;
    JButton resetlog;
    JButton close;
    JButton record;
    JButton set;
    JTextField postPort;
    JTextField sockerPort;
    JTextField url;
    private void p1(){
        p1 = new JPanel();
        p1.setLayout(null);
        log = new JTextArea();
        logpane = new JScrollPane(log);
        startQQ = new JButton("启动QQ");
        startWX = new JButton("启动WX");
        startBOTH = new JButton("启动BOTH");
        resetlog = new JButton("清空日志");
        close = new JButton("关闭");
        sockerPort = new JTextField("9999");
        postPort = new JTextField("5700");
        url = new JTextField("127.0.0.1");
        record = new JButton("转换表格");
        set = new JButton("设置");

        newTable();
        table.setBounds(5,5,400,400);
        logpane.setBounds(5,5,400,400);
        startQQ.setBounds(420,10,80,30);
        startWX.setBounds(420,50,80,30);
        startBOTH.setBounds(420,90,80,30);
        resetlog.setBounds(420,130,80,30);
        close.setBounds(420,170,80,30);
        postPort.setBounds(420,210,80,30);
        sockerPort.setBounds(420,250,80,30);
        url.setBounds(420,290,80,30);
        record.setBounds(420,330,80,30);
        set.setBounds(420,370,80,30);

        table.setVisible(false);
        log.setLineWrap(true);
        log.setEditable(false);
        RecordOpra recordOpra = new RecordOpra();
        startWX.addActionListener(recordOpra);
        startQQ.addActionListener(recordOpra);
        startBOTH.addActionListener(recordOpra);
        resetlog.addActionListener(recordOpra);
        KeyListener onlymath = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if(keyChar>=KeyEvent.VK_0&&keyChar<=KeyEvent.VK_9){}
                else e.consume();}
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        };
        record.addActionListener(e->{
            if(logpane.isVisible()) {
                table.setVisible(true);
                logpane.setVisible(false);
            }else{
                table.setVisible(false);
                logpane.setVisible(true);
            }
        });
        set.addActionListener(e->{
            cards.show(container,"set");
        });
        postPort.addKeyListener(onlymath);
        sockerPort.addKeyListener(onlymath);
        //添加关闭窗口的监听
        close.addActionListener(evt ->System.exit(0));

        p1.add(logpane);
        p1.add(startQQ);
        p1.add(startWX);
        p1.add(startBOTH);
        p1.add(resetlog);
        p1.add(close);
        p1.add(postPort);
        p1.add(sockerPort);
        p1.add(url);
        p1.add(table);
        p1.add(record);
        p1.add(set);
        add(p1);
    }
    private JScrollPane table;
    private JTable jtable;
    private DefaultTableModel tableM;
    private void newTable(){
        Object[] name = {"房间号", "先", "后","创建时间","关闭时间","通话数"};
        //不允许修改表格内容的同时能高亮
        tableM = new DefaultTableModel(null, name) {
            private static final long serialVersionUID = 2L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jtable = new JTable(tableM);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table = new JScrollPane(jtable);
    }
    public void addRow(int roomnum,String maleid,String remaleid,String createdata,String deteledata,int messagenum){
        Vector vRow = new Vector();
        vRow.add(roomnum);
        vRow.add(maleid);
        vRow.add(remaleid);
        vRow.add(createdata);
        vRow.add(deteledata);
        vRow.add(messagenum);
        tableM.addRow(vRow);
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

    JPanel p2;
    public JTextField file;
    public JTextField image;
    public JTextField voice;
    public JTextField viedo;
    public JTextField addlistid;
    JButton fileJB,imageJB,voiceJB,viedoJB,confireset, addlist;
    public JComboBox<String> choose;
    private JScrollPane blacktable;
    private JTable blackjtable;
    private DefaultTableModel blacktableM;
    private void p2(){
        p2 = new JPanel();
        p2.setLayout(null);
        file = new JTextField();
        image = new JTextField();
        viedo = new JTextField();
        voice = new JTextField();
        addlistid = new JTextField();

        fileJB = new JButton("file");
        imageJB = new JButton("image");
        viedoJB = new JButton("viedo");
        voiceJB = new JButton("voice");
        addlist = new JButton("确定");
        confireset = new JButton("返回");
        choose = new JComboBox<>();
        String [] Name= {"添加黑名单","删除黑名单","添加管理员","删除管理员"};
        for (String s : Name) {
            choose.addItem(s);
        }
        blacklistTable();
        managerlistTable();
        file.setBounds(10,10,200,30);
        fileJB.setBounds(220,10,90,30);
        image.setBounds(10,50,200,30);
        imageJB.setBounds(220,50,90,30);
        viedo.setBounds(10,90,200,30);
        viedoJB.setBounds(220,90,90,30);
        voice.setBounds(10,130,200,30);
        voiceJB.setBounds(220,130,90,30);
        addlistid.setBounds(10,170,200,30);
        addlist.setBounds(220,170,90,30);
        choose.setBounds(10,210,300,30);
        blacktable.setBounds(10,250,300,150);
        confireset.setBounds(420,370,80,30);

        managertable.setBounds(310,10,200,350);
        confireset.addActionListener(new ConfiresetListener());

        Filename setfilename = new Filename();
        fileJB.addActionListener(setfilename);
        imageJB.addActionListener(setfilename);
        viedoJB.addActionListener(setfilename);
        voiceJB.addActionListener(setfilename);

        listListener listListener = new listListener();
        addlist.addActionListener(listListener);

        p2.add(file);
        p2.add(fileJB);
        p2.add(image);
        p2.add(imageJB);
        p2.add(viedo);
        p2.add(viedoJB);
        p2.add(voice);
        p2.add(voiceJB);
        p2.add(confireset);
        p2.add(addlistid);
        p2.add(addlist);
        p2.add(choose);
        p2.add(blacktable);
        p2.add(managertable);
        add(p2);
    }
    public List<String> blacklist;
    private void blacklistTable(){
        blacklist = Fileopra.readBlacklist();
        Object[] name = {"黑名单(屏蔽所有信息)"};
        //不允许修改表格内容的同时能高亮
        blacktableM = new DefaultTableModel(null, name) {
            private static final long serialVersionUID = 2L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        blackjtable = new JTable(blacktableM);
        blackjtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        blacktable = new JScrollPane(blackjtable);
        addBlackListRow(blacklist);
    }
    public void addBlackListRow(List<String> blacklist){
        while (blackjtable.getRowCount()>0)
            blacktableM.removeRow(0);
        for (String s : blacklist) {
            Vector vRow = new Vector();
            vRow.add(s);
            blacktableM.addRow(vRow);
        }
    }
    private JScrollPane managertable;
    private JTable managerjtable;
    private DefaultTableModel managertableM;
    public List<String> managerlist;
    private void managerlistTable(){
        managerlist = Fileopra.readManagerlist();
        Object[] name = {"管理员名单"};
        //不允许修改表格内容的同时能高亮
        managertableM = new DefaultTableModel(null, name) {
            private static final long serialVersionUID = 2L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        managerjtable = new JTable(managertableM);
        managerjtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        managertable = new JScrollPane(managerjtable);
        addmanagerListRow(managerlist);
    }
    public void addmanagerListRow(List<String> managerlist){
        while (managerjtable.getRowCount()>0)
            managertableM.removeRow(0);
        for (String s : managerlist) {
            Vector vRow = new Vector();
            vRow.add(s);
            managertableM.addRow(vRow);
        }
    }
    private void initpath(){
        JSONObject json =  Fileopra.readfile();
        if(json==null)return;
        String filepath = json.getString("file");
        String imagepath = json.getString("image");
        String voicepath = json.getString("voice");
        String viedopath = json.getString("viedo");
        User_adr.file = filepath;
        User_adr.viedo = viedopath;
        User_adr.image = imagepath;
        User_adr.voice = voicepath;
        file.setText(filepath);
        image.setText(imagepath);
        voice.setText(voicepath);
        viedo.setText(viedopath);
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
