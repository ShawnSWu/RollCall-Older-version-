package com.example.administrator.rollcall_10.device_io;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Administrator on 2016/8/12.
 */
public class Device_IO {



    public HashMap<String,String> HashMap_ReadDataFromTxt(String path){

        File seletor_File= new File(path);
        BufferedReader bufferedReader_txt;
        HashMap<String,String> txt_hashmap=new HashMap<>();
        try {

            FileReader fileReader = new FileReader(seletor_File);
            bufferedReader_txt=new BufferedReader(fileReader);


            while(bufferedReader_txt.ready()){
                String data=bufferedReader_txt.readLine();

                String []  data_split= data.split(",");

                txt_hashmap.put(data_split[1],data_split[0]);

            }


        }catch (IOException e){
            e.getMessage();
        }


        return txt_hashmap;

    }

    public ArrayList<String> ArrayList_ReadDataFromTxt(String path){

        File seletor_File= new File(path);
        BufferedReader bufferedReader_txt;
        ArrayList<String> arrayList=new ArrayList<>();
        try {

            FileReader fileReader = new FileReader(seletor_File);
            bufferedReader_txt=new BufferedReader(fileReader);


            while(bufferedReader_txt.ready()){
                String data=bufferedReader_txt.readLine();

                String []  data_split= data.split(",");

                arrayList.add(data_split[0] + " ," + data_split[1]);


            }


        }catch (IOException e){
            e.getMessage();
        }


        return arrayList;

    }



    public void Manual__WriteDataToTxt(HashMap<String,String> write_hashmap, boolean append, String path)
    {
        BufferedWriter bufferedWriter = null;

        try {

            Iterator txt_iterator = write_hashmap.entrySet().iterator();


            bufferedWriter = new BufferedWriter(new FileWriter(new File(path)));


            while (txt_iterator.hasNext()) {

                Entry entry = (Entry) txt_iterator.next();

                bufferedWriter.write(entry.getValue().toString());
                bufferedWriter.write(",");
                bufferedWriter.write(entry.getKey().toString() + "\n");
            }


            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void Auto_WriteDataToTxt(HashMap<String,String> write_hashmap, boolean append, String path)
    {

        BufferedWriter bufferedWriter = null;

        try {

            Iterator txt_iterator = write_hashmap.entrySet().iterator();


            bufferedWriter = new BufferedWriter(new FileWriter(new File(path)));


            while (txt_iterator.hasNext()) {

                Entry entry = (Entry) txt_iterator.next();

                bufferedWriter.write(entry.getValue().toString());
                bufferedWriter.write(",");
                bufferedWriter.write(entry.getKey().toString() + "\n");
            }


            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void DeleteDataFromTxt(String Delete_address,HashMap<String,String> ReadList,String pathstring){

        ReadList.remove(Delete_address);

        BufferedWriter bufferedWriter = null;

        try {

            Iterator txt_iterator = ReadList.entrySet().iterator();


            bufferedWriter = new BufferedWriter(new FileWriter(new File(pathstring)));


            while (txt_iterator.hasNext()) {

                Entry entry = (Entry) txt_iterator.next();

                bufferedWriter.write(entry.getValue().toString());
                bufferedWriter.write(",");
                bufferedWriter.write(entry.getKey().toString() + "\n");
            }


            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    public void EditDataFromTxt(String delete_DeviceAddress,String Edit_DeviceName,HashMap<String,String> ReadList,String pathstring){


        ReadList.remove(delete_DeviceAddress);

        ReadList.put(delete_DeviceAddress,Edit_DeviceName);

        BufferedWriter bufferedWriter = null;

        try {

            Iterator txt_iterator = ReadList.entrySet().iterator();

            bufferedWriter = new BufferedWriter(new FileWriter(new File(pathstring)));


            while (txt_iterator.hasNext()) {

                Entry entry = (Entry) txt_iterator.next();

                bufferedWriter.write(entry.getValue().toString());
                bufferedWriter.write(",");
                bufferedWriter.write(entry.getKey().toString() + "\n");
            }


            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
