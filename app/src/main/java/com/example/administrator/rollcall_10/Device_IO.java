package com.example.administrator.rollcall_10;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/8/12.
 */
public class Device_IO {







    //要寫入的文字檔
//    File file  =new File(I_File_Path.main_path + I_File_Path.Built_TextFile);//文字檔





    //****************************************************************************************寫入寫法 Start***\\\
    public void writeData(String sad,boolean append,String path){

        File seletor_File= new File(path);



        try {
           //創建資料夾
            File peoplefile = new File(I_File_Path.main_path);
            peoplefile.mkdirs();



            //寫入文字檔
            FileOutputStream fileOutputStream = new FileOutputStream(seletor_File,append);

            fileOutputStream.write(sad.getBytes());

            fileOutputStream.write("\n".getBytes());


            fileOutputStream.close();





        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //****************************************************************************************寫入寫法 End*****\\\



    //******************************************************************************************************讀出寫法 Start***\\\
    public String[] readData(File file, String x) {
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


            try {
                while (( br.readLine()) != null) {
                    anzahl++;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fis.getChannel().position(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] array = new String[anzahl];


            String line;
            int i = 0;
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
    //******************************************************************************************************讀出寫法 End******\\\


}
