package com.example.administrator.rollcall_10.note;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/5/22.
 */

public class Note_Object extends LinkedList
{
    //存放欄位的List
    public static ArrayList<String> Title=new ArrayList<>();
    public static ArrayList<String> Content=new ArrayList<>();
    public static ArrayList<String> Time=new ArrayList<>();

    //0是未完成1是已完成
    public static ArrayList<Integer> IsFinsh=new ArrayList<>();

    //已完成跟未完成的ICON
    public static ArrayList<Integer> FinshOrNotIcon=new ArrayList<>();


}
