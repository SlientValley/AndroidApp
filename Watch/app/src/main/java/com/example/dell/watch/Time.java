package com.example.dell.watch;

/**
 * Created by 醉恋 on 2017/3/27.
 */

public class Time {

    public long baseTime = 1800;
    public long addTime = 15;

    public long getBaseTime(){
        return baseTime;
    }

    public void setBaseTime(String baseTime){
        this.baseTime = Long.parseLong(baseTime)*60;

    }
    public void setBaseTime(){ baseTime = 1800;}

    public long getAddTime(){
        return addTime;
    }

    public void setAddTime(String addTime){
        this.addTime = Long.parseLong(addTime);
    }
    public void setAddTime(){
        addTime = 15;
    }
}
