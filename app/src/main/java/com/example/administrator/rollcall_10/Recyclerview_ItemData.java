package com.example.administrator.rollcall_10;

/**
 * Created by Administrator on 2016/8/11.
 */
public class Recyclerview_ItemData {

    private String title;

    private int itemimg;



    public Recyclerview_ItemData(String title) {
        this.title = title;
    }




    public Recyclerview_ItemData(String title,int itemimg){
        this.title=title;
        this.itemimg=itemimg;
    }


    public Recyclerview_ItemData(int itemimg){
        this.itemimg=itemimg;
    }



    public String getTitle(){
        return title;
    }




    public int getIiemimg(){
        return itemimg;
    }


}