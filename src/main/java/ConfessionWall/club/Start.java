package ConfessionWall.club;

import GUIAdmin.window;
import java.util.Date;

/**
 * 创建管理窗体
 * 创建qq机器人和微信机器人对象
 * @return
 */
public class Start {
    public static void main(String []args){
        Date date = new Date();
        System.out.println(date.getMonth());
        if(date.getMonth()+1>=8)return;
        // 创建管理窗体
        window win = window.getInstance();
   }
}