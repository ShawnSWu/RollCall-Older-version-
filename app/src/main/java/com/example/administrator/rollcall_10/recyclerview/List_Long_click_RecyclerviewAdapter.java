package com.example.administrator.rollcall_10.recyclerview;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.device_io.I_File_Path;
import com.example.administrator.rollcall_10.navigationdrawer.mainview_fragmentlayout_EditList;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/11.
 */
public class List_Long_click_RecyclerviewAdapter extends RecyclerView.Adapter<List_Long_click_RecyclerviewAdapter.ViewHolder> {

    File selected;

    private Context mContext;
    private ArrayList<File> files;
    RollCall_Dialog rollCall_dialog;

    String [] name;
    String [] address;

    String[] longclick_item;
    int[] image;


    public List_Long_click_RecyclerviewAdapter(String[] item, int[] image,File file,Context mContext,RollCall_Dialog rollCall_dialog) {
        this.longclick_item=item;
        this.image=image;
        this.selected=file;
        this.mContext=mContext;
        this.rollCall_dialog=rollCall_dialog;
    }


    @Override
    public List_Long_click_RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_listlongclick_item_layout, parent, false);
        ViewHolder viewholder = new ViewHolder(view);


        return viewholder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Longclick_Item.setText(longclick_item[position]);
        holder.image.setImageResource(image[position]);

    }




    @Override
    public int getItemCount() {
        return longclick_item.length;
    }





    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView Longclick_Item;
        public ImageView image;


        public ViewHolder(View v)
        {
            super(v);
            Longclick_Item = (TextView) v.findViewById(R.id.longclick_item);
            image =(ImageView)v.findViewById(R.id.item_image);



            v.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    switch (getAdapterPosition()){
                        case 0:
                            rename(v);
                            break;

                        case 1:
                            delate(v);

                            break;


                    }

                }
            });

        }

        public  void rename(View v){

            rollCall_dialog.dismiss();

            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            final View layout = inflater.inflate(R.layout.dialog_list_rename_layout, null);


            final EditText rename_edittext = (EditText)layout.findViewById(R.id.rename_edit);
            rename_edittext.setHint(selected.getName());


            final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(layout.getContext());
            rollCall_dialog.setView(layout);
            rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
            rollCall_dialog.setCancelable(false);
            rollCall_dialog.setCancelable(true);



            //**重新命名確定鍵
            Button btn_ok =(Button)layout.findViewById(R.id.btn_ok);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                                         * 重新命名code
                                        */


                    String Rename_EditText=rename_edittext.getText().toString();


                    //**修改的名字
                    Log.e("shawn",Rename_EditText);
                    //**路境名稱
                    Log.e("shawn",selected.getPath());





                    if(Rename_EditText.contains(I_File_Path.Slash) || Rename_EditText.startsWith(" ") ||Rename_EditText.endsWith(" ") || Rename_EditText.contains(I_File_Path.Slash2)) {

                        Toast.makeText(v.getContext(), v.getResources().getText(R.string.RollCall__NewFile_Dialog__Error_Messages), Toast.LENGTH_SHORT).show();


                    }
                    else if(Rename_EditText.length() ==0){

                        Toast.makeText(v.getContext(),v.getResources().getText(R.string.RollCall__NewFile_Dialog__Error_Messages2), Toast.LENGTH_SHORT).show();
                    }








                    //***重新載入一次 suck code
                    FragmentManager fragmentManager = ((Activity) mContext).getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, new mainview_fragmentlayout_EditList())
                            .commitAllowingStateLoss();
//                //***重新載入一次 suck code

                }
            });

            ///**關閉dialog
            Button btn_close =(Button)layout.findViewById(R.id.btn_close);
            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rollCall_dialog.dismiss();
                }
            });



            rollCall_dialog.show();



        }









        public  void delate(View v){

            rollCall_dialog.dismiss();


            if(selected.length() !=0){

                RollCall_Dialog rollCall_dialog = new RollCall_Dialog(v.getContext());
                rollCall_dialog.setTitle(v.getContext().getResources().getString(R.string.RollCall_List_Delete_Title_therearestilldata));
                rollCall_dialog.setMessage(v.getContext().getResources().getString(R.string.RollCall_List_Delete_Message_therearestilldata));
                rollCall_dialog.setIcon(R.mipmap.garbagecan128);
                rollCall_dialog.setCancelable(false);
                rollCall_dialog.setButton(DialogInterface.BUTTON_POSITIVE, v.getContext().getResources().getString(R.string.RollCall_List_Delete_Button_yes), Delete_List);
                rollCall_dialog.setButton(DialogInterface.BUTTON_NEGATIVE,v.getContext().getResources().getString(R.string.RollCall_List_Delete_Button_close), close);


                rollCall_dialog.show();

                TextView messageText = (TextView) rollCall_dialog.findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER_HORIZONTAL);

            }else {


                RollCall_Dialog rollCall_dialog = new RollCall_Dialog(v.getContext());
                rollCall_dialog.setTitle(R.string.RollCall_List_Delete_Title);
                rollCall_dialog.setMessage(v.getContext().getResources().getString(R.string.RollCall_List_Delete_Message) + "  " + selected.getName() + "  " + v.getContext().getResources().getString(R.string.RollCall_List_Delete_Message2));

                rollCall_dialog.setIcon(R.mipmap.garbagecan128);
                rollCall_dialog.setCancelable(false);
                rollCall_dialog.setButton(DialogInterface.BUTTON_POSITIVE, v.getContext().getResources().getString(R.string.RollCall_List_Delete_Button_yes), Delete_List);
                rollCall_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, v.getContext().getResources().getString(R.string.RollCall_List_Delete_Button_close), close);


                rollCall_dialog.show();

                //**dilaog文字置中
                TextView messageText = (TextView) rollCall_dialog.findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER_HORIZONTAL);
            }


        }


        DialogInterface.OnClickListener Delete_List = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                selected.delete();


                //***重新載入一次 suck code
                FragmentManager fragmentManager = ((Activity) mContext).getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, new mainview_fragmentlayout_EditList())
                        .commitAllowingStateLoss();
//                //***重新載入一次 suck code
//            }


            }

        };


        DialogInterface.OnClickListener close = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {




            }

        };



    }
}
