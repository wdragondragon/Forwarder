package ConfessionWall.club;

public interface botAdapter {
    public boolean openWXbot();
    public boolean openQQbot(int sockerPort,String postUrl,int postPort);
    public boolean openBoth(int sockerPort,String postUrl,int postPort);
}
