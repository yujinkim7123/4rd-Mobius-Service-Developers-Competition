package com.lifefriends.onem2m;

/**
 * Created by araha on 2016-09-20.
 */
public class AE {
    public String appos = "";
    public String appId = "";
    public String aeId = "";
    public String appName = "";

    public void setAEid(String aeid){
        this.aeId = aeid;
    }
    public void setAppName(String appname){
        this.appName = appname;
    }
    public String getAEid() {
        return this.aeId;
    }
    public String getappName(){
        return this.appName;
    }
}