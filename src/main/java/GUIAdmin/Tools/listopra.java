package GUIAdmin.Tools;

import GUIAdmin.window;

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
}
