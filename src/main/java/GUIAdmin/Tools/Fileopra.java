package GUIAdmin.Tools;
import com.alibaba.fastjson.JSONObject;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fileopra {
    public static String getFile(){
        JFileChooser jfc;
        java.io.File file;
        jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showDialog(new JLabel(), "选择");
        file=jfc.getSelectedFile();
        if(file!=null){
            if(file.isDirectory()){
                System.out.println("文件夹:"+file.getAbsolutePath());
            }else if(file.isFile()){
                System.out.println("文件:"+file.getAbsolutePath());
            }
            //            str = str.replace("\\", "/");
//            System.out.println("选择文件:"+str);
            return file.getAbsolutePath();
        }
        return null;
    }
    public static void writefile(JSONObject jsonObject){
        try {
            FileOutputStream fos = new FileOutputStream("set.ini",false);
            ObjectOutputStream writer = new ObjectOutputStream(fos);
//            BufferedWriter bufferWriter = new BufferedWriter(writer);
            writer.writeObject(jsonObject);
            writer.flush();
            writer.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static JSONObject readfile(){
        JSONObject jsonObject = null;
        try{
            FileInputStream in = new FileInputStream("set.ini");
            ObjectInputStream reader = new ObjectInputStream(in);
//            Object boj;
            if((jsonObject = (JSONObject) reader.readObject())!=null){
                System.out.println(jsonObject);
            }
            reader.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static void writeBlacklist(List<String> blacklist){
        try{
            FileOutputStream out = new FileOutputStream("blacklist.ini",false);
            ObjectOutputStream writer = new ObjectOutputStream(out);
            writer.writeObject(blacklist);
            writer.flush();
            writer.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static  List<String> readBlacklist(){
        List<String> blacklist = new ArrayList<>();
        try{
            FileInputStream in = new FileInputStream("blacklist.ini");
            ObjectInputStream reader = new ObjectInputStream(in);
            if(null != (blacklist = (List<String>) reader.readObject())){
                System.out.println(blacklist);
            }
            reader.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return blacklist;
    }
    public static void writeManagerlist(List<String> managerlist){
        try{
            FileOutputStream out = new FileOutputStream("managerlist.ini",false);
            ObjectOutputStream writer = new ObjectOutputStream(out);
            writer.writeObject(managerlist);
            writer.flush();
            writer.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<String> readManagerlist(){
        List<String> manager = new ArrayList<>();
        try{
            FileInputStream in = new FileInputStream("managerlist.ini");
            ObjectInputStream reader = new ObjectInputStream(in);
            if(null != (manager = (List<String>) reader.readObject())){
                System.out.println(manager);
            }
            reader.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return manager;
    }
    public static HashMap readCarelist(){
        HashMap<String,List<String>> carelist = new HashMap<>();
        try{
            FileInputStream in = new FileInputStream("carelist.ini");
            ObjectInputStream reader = new ObjectInputStream(in);
            if(null != (carelist = (HashMap<String,List<String>>) reader.readObject())){
                System.out.println(carelist);
            }
            reader.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return carelist;
    }
    public static void writeCarelist(HashMap carelist){
        try{
            FileOutputStream out = new FileOutputStream("carelist.ini",false);
            ObjectOutputStream writer = new ObjectOutputStream(out);
            System.out.println("关注名单"+carelist);
            writer.writeObject(carelist);
            writer.flush();
            writer.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<String> readMemberlist(){
        List<String> memberlist = new ArrayList<>();
        try{
            FileInputStream in = new FileInputStream("memberlist.ini");
            ObjectInputStream reader = new ObjectInputStream(in);
            if(null != (memberlist = (List<String>) reader.readObject())){
                System.out.println(memberlist);
            }
            reader.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return memberlist;
    }
    public static void writeMemberlist(List memberlist){
        try{
            FileOutputStream out = new FileOutputStream("memberlist.ini",false);
            ObjectOutputStream writer = new ObjectOutputStream(out);
            System.out.println("广播名单"+memberlist);
            writer.writeObject(memberlist);
            writer.flush();
            writer.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
