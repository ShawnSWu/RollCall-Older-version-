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
     * 自動編號寫法
     * Created by Shawn Wu on 2016/09/20.
     * 架構有點爛 要在想一下
     */

    public void Imperfect_writeData(ArrayList<String> address, ArrayList<String> name ,boolean append ,String path){

        File seletor_File= new File(path);



        try {
            //創建資料夾
            File peoplefile = new File(I_File_Path.main_path);
            peoplefile.mkdirs();


            for(int i=0; i<address.size();i++) {
                //寫入文字檔
                FileOutputStream fileOutputStream = new FileOutputStream(seletor_File, append);

                fileOutputStream.write(name.get(i).getBytes());

                fileOutputStream.write("  ,  ".getBytes());

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

///****

    public String[] Imperfect_readData(File file, String x) {
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
     * 手動編號寫法
     * Created by Shawn Wu on 2016/06/19.
     * 第一版測試版寫法
     */

    //****************************************************************************************寫入寫法 Start***\\\
    public void manual_writeData(String name,String address,boolean append,String path){

        File seletor_File= new File(path);



        try {
            //創建資料夾
            File peoplefile = new File(I_File_Path.main_path);
            peoplefile.mkdirs();



            //寫入文字檔
            FileOutputStream fileOutputStream = new FileOutputStream(seletor_File, append);

            fileOutputStream.write(name.getBytes());

            fileOutputStream.write("  ,  ".getBytes());

            fileOutputStream.write(address.getBytes());

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
    public String[] address_readData(File file, String x) {
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



    public void name_writeData(String deta,boolean append,String path){

        File seletor_File= new File(path);



        try {
            //創建資料夾
            File peoplefile = new File(I_File_Path.main_path);
            peoplefile.mkdirs();



            //寫入文字檔
            FileOutputStream fileOutputStream = new FileOutputStream(seletor_File, append);

            fileOutputStream.write(deta.getBytes());

            fileOutputStream.write("\n".getBytes());


            fileOutputStream.close();





        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }













































    /**
     * Created by Shawn Wu on 2016/08/21.
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
     * Created by Shawn Wu on 2016/08/21.
     * *正式版名稱方法,未完成
     */


    public void AutowriteData(ArrayList<String> sad, boolean append, String path){

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
