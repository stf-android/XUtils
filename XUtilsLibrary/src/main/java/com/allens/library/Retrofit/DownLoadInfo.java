package com.allens.library.Retrofit;

/**
 * Created by allens on 2017/6/15.
 */

public class DownLoadInfo {

    private String url;
    private Long length;
    private Long startLong;
    private String path;
    private int baiFen;
    private boolean isStop;

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public int getBaiFen() {
        return baiFen;
    }

    public void setBaiFen(int baiFen) {
        this.baiFen = baiFen;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getStartLong() {
        return startLong;
    }

    public void setStartLong(Long startLong) {
        this.startLong = startLong;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
