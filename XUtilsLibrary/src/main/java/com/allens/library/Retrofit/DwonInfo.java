package com.allens.library.Retrofit;

/**
 * Created by allens on 2017/6/19.
 */

public class DwonInfo {

    private int State;

    private long length;

    private long startlength;

    private String url;

    private String filepath;

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getStartlength() {
        return startlength;
    }

    public void setStartlength(long startlength) {
        this.startlength = startlength;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }


}
