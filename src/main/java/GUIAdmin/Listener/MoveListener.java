package GUIAdmin.Listener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MoveListener implements MouseListener, MouseMotionListener {
    private Window win;
    private Point pressedPoint;
    public MoveListener(Window win){
        this.win = win;
    }
    @Override
    public void mouseDragged(MouseEvent e) { // 鼠标拖拽事件
        Point point = e.getPoint();// 获取当前坐标
        Point locationPoint = win.getLocation();// 获取窗体坐标
        int x = locationPoint.x + point.x - pressedPoint.x;// 计算移动后的新坐标
        int y = locationPoint.y + point.y - pressedPoint.y;
        win.setLocation(x, y);// 改变窗体位置
    }
    public void mousePressed(MouseEvent e) { //鼠标按下事件
        pressedPoint = e.getPoint(); //记录鼠标坐标
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
}