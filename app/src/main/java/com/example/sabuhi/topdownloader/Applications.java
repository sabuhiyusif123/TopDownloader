package com.example.sabuhi.topdownloader;

/**
 * Created by SABUHI on 7/29/2016.
 */
public class Applications {

    private String appName;
    private String appArtist;
    private String appReleaseDate;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppArtist() {
        return appArtist;
    }

    public void setAppArtist(String appArtist) {
        this.appArtist = appArtist;
    }

    public String getAppReleaseDate() {
        return appReleaseDate;
    }

    public void setAppReleaseDate(String appReleaseDate) {
        this.appReleaseDate = appReleaseDate;
    }

    @Override
    public String toString() {
        return "Name: "+getAppName()+"\n"+
                "Artist: "+getAppArtist()+"\n"+
                "Release Date: "+ getAppReleaseDate()+"\n";
    }
}
