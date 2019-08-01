package GUIAdmin.Tools;

import GUIAdmin.window;

import java.util.ArrayList;
import java.util.List;

public class listopra {
    public static boolean addBlacklist(String blackid){
        try {
            window win = window.getInstance();
            if (!win.blacklist.contains(blackid)) {
                win.blacklist.add(blackid);
                win.addBlackListRow(win.blacklist);
                Fileopra.writeBlacklist(win.blacklist);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean delBlacklist(String blackid){
        try {
            window win = window.getInstance();
            if (win.blacklist.contains(blackid)) {
                win.blacklist.remove(blackid);
                win.addBlackListRow(win.blacklist);
                Fileopra.writeBlacklist(win.blacklist);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public static boolean addManagerlist(String managerid){
        try {
            window win = window.getInstance();
            if(!win.managerlist.contains(managerid)) {
                win.managerlist.add(managerid);
                win.addmanagerListRow(win.managerlist);
                Fileopra.writeManagerlist(win.managerlist);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public static boolean delManagerlist(String managerid){
        try {
            window win = window.getInstance();
            if(win.managerlist.contains(managerid)) {
                win.managerlist.remove(managerid);
                win.addmanagerListRow(win.managerlist);
                Fileopra.writeManagerlist(win.managerlist);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public static boolean addCarelist(String id,String careid){
        List<String> carelist;
        window win = window.getInstance();
        if(win.carelist.containsKey(careid))
            carelist = win.carelist.get(careid);
        else carelist = new ArrayList<>();
        if(!carelist.contains(id)) {
            carelist.add(id);
            win.carelist.put(careid,carelist);
            Fileopra.writeCarelist(win.carelist);
            return true;
        }
        return false;
    }
    public static boolean delCarelist(String id,String careid){
        List<String> carelist;
        window win = window.getInstance();
        if(win.carelist.containsKey(careid))
            carelist = win.carelist.get(careid);
        else carelist = new ArrayList<>();
        if(carelist.contains(id)) {
            carelist.remove(id);
            win.carelist.put(careid,carelist);
            Fileopra.writeCarelist(win.carelist);
            return true;
        }
        return false;
    }
    public static boolean addMemberlist(String id){
        window win = window.getInstance();
        if(!win.memeberlist.contains(id)){
            win.memeberlist.add(id);
            Fileopra.writeMemberlist(win.memeberlist);
        }
        return true;
    }
}
