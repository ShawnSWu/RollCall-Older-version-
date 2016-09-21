package com.example.administrator.rollcall_10.device_io;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/12.
 */
public class Device_IO {

    //要寫入的文字檔
//    File file  =new File(I_File_Path.main_path + I_File_Path.Built_TextFile);//文字檔


///******************************************************************************************************************* shawn 2016/09/20

    /**
     * Created by Shawn Wu on 2016/09/20.
     * 暫時版,EditText讀出寫法
     * 架構有點爛 要在想一下
     */
    public String[] Temporary_List_ReadData(File file, String x) {
        {
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(file);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String test="";
            int readline = 0;

            int lines = 0;

            try {
                while (( br.readLine()) != null) {
                    readline++;

                }


            } catch (IOException e) {
                e.printStackTrace();
            }



            try {
                fis.getChannel().position(0);
            } catch (IOException e) {
                e.printStackTrace();
            }


            String[] array = new String[readline];


            String line;
            int i = 0;
            //**判斷是否2個為一組
            int countline=0;

            try {
                while ((line = br.readLine()) != null) {
                    array[i] = line;
                    i++;


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return array;
        }
    }



///******************************************************************************************************************* shawn 2016/09/20



















    /**
     * Created by Shawn Wu on 2016/08/21.
     * 測試版寫法
     * name and address同時寫入測試寫法
     */

    public String[] name_and_address_readData(File file, String x) {
        {
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(file);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String test="";
            int anzahl = 0;

            int lines = 0;

            try {
                while (( br.readLine()) != null) {
                    anzahl++;

                    if(anzahl%2==0){
                        lines++;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fis.getChannel().position(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] array = new String[lines];


            String line;
            int i = 0;
            //**判斷是否2個為一組
            int a=0;

            try {
                while ((line = br.readLine()) != null) {
               a++;

                    if(a%2==0) {
                        array[i] = line;
                        i++;

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return array;
        }
    }



    public void name_and_address_writeData(ArrayList<String> address, ArrayList<String> name ,boolean append ,String path){

        File seletor_File= new File(path);



        try {
            //創建資料夾
            File peoplefile = new File(I_File_Path.main_path);
            peoplefile.mkdirs();


            for(int i=0; i<address.size();i++) {
                //寫入文字檔
                FileOutputStream fileOutputStream = new FileOutputStream(seletor_File, append);

                fileOutputStream.write(name.get(i).getBytes());

                fileOutputStream.write("\n".getBytes());

                fileOutputStream.write(address.get(i).getBytes());

                fileOutputStream.write("\n".getBytes());

                fileOutputStream.close();

            }



        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

















    /**
     *  Created by Shawn Wu on 2016/06/19.
     *  正式版 手動編號寫法(Manual)  (未完成)
     *
     *  第一版 只寫入address(已完成)
     *  正式版要寫入name與address
     *  正式版名稱把Temporary拿掉就好
     */

    //*******************************************************************************************\\\
    public void Temporary_Manual_WriteData(String name,String address,boolean append,String path){

        File seletor_File= new File(path);



        try {
            //創建資料夾
            File peoplefile = new File(I_File_Path.main_path);
            peoplefile.mkdirs();



            //寫入文字檔
            FileOutputStream fileOutputStream = new FileOutputStream(seletor_File, append);


            fileOutputStream.write(address.getBytes());

            fileOutputStream.write("\n".getBytes());

            fileOutputStream.close();





        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    //*********************************************************************************************\\\











    /**
     * Created by Shawn Wu on 2016/08/21.
     * 正式版 自動編號寫法(Auto)  (未完成)
     * *正式版名稱要取"Auto_WriteData"
     * *第一版 只寫入address(已完成)
     * 正式版要寫入name與address
     * 正式版名稱把Temporary拿掉就好
     */


    //**暫時版本,先只寫入address
    public void Temporary_Auto_WriteData(ArrayList<String> sad, boolean append, String path){

        File seletor_File= new File(path);



        try {
            //創建資料夾
            File peoplefile = new File(I_File_Path.main_path);
            peoplefile.mkdirs();


            for(int i=0; i<sad.size();i++) {
                //寫入文字檔
                FileOutputStream fileOutputStream = new FileOutputStream(seletor_File, append);

                fileOutputStream.write(sad.get(i).getBytes());

                fileOutputStream.write("\n".getBytes());


                fileOutputStream.close();

            }



        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }





}
