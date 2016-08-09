package com.example.administrator.rollcall_10;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/9.
 */
public class Home_RecyclerAdapter extends RecyclerView.Adapter<Home_RecyclerAdapter.ViewHolder> {
    private Context context;
    FragmentManager manager;
    ManualAdd_BLE_MainActivity manualAdd_ble_mainActivity =new ManualAdd_BLE_MainActivity();
    MainActivity mainActivity =new MainActivity();

    public Home_RecyclerAdapter(Context context) {

     this.context=context;
    }


    private String[] titles = {"點名",
            "查看清單",
            "設置藍芽裝置",
            "記事本",
            "未開放",
            "未開放",
            "未開放",
        };

    private String[] details = {
            "開始點名",
            "可以查看目前要掃描的清單",
            "設定您的藍芽追蹤器",
            "可以記錄下代辦事項",
            "修復中,近期開放!",
            "修復中,近期開放!",
            "修復中,近期開放!",
           };

    private int[] images = {

            R.mipmap.rollcallicon256,
            R.mipmap.setlist256,
            R.mipmap.bluetoothdevice128,
            R.mipmap.memorandum256,
            R.mipmap.worker256,
            R.mipmap.worker256,
            R.mipmap.worker256,

       };



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_recyclerview_cardview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images[i]);
        FragmentManager manager;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }






    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();



                    switch (getAdapterPosition()){
                        case 0:

                            Intent intent = new Intent();
                            intent.setClass(v.getContext(), BLE_MainActivity.class);
                            v.getContext().startActivity(intent);
                            break;


                        case 1:
                            WatchList(v);
                            break;

                        case 2:
                            Snackbar.make(v, "尚未開放" ,
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            break;
                        case 3:
                            Snackbar.make(v, "尚未開放" ,
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            break;

                        case 4:
                            Snackbar.make(v, "尚未開放" ,
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            break;
                    }

                }
            });
        }





        public void WatchList(View v){

            if(mainActivity.file.length()==0){


                RollCall_Dialog rollCall_dialog = new RollCall_Dialog(v.getContext());
                rollCall_dialog.setTitle(R.string.RollCall_Dialog_Title_ListEmpty);
                rollCall_dialog.setMessage(v.getContext().getResources().getString(R.string.RollCall_Dialog__Message_GoToScan));
                rollCall_dialog.setIcon(R.mipmap.exclamation128);
                rollCall_dialog.setCancelable(false);
//                rollCall_dialog.setButton(DialogInterface.BUTTON_POSITIVE,v.getContext().getResources().getString(R.string.RollCall_Dialog__Button_GoToAddDevice),GoToSetPeople);
                rollCall_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, v.getContext().getResources().getString(R.string.RollCall_Dialog__Button_close), close);

                rollCall_dialog.show();



                TextView messageText = (TextView)rollCall_dialog.findViewById( android.R.id.message );
                messageText.setGravity( Gravity.CENTER_HORIZONTAL );



            }
            else {

                Intent it = new Intent(Intent.ACTION_VIEW);
                it.setClass(v.getContext(), Watch_List.class);

                String x = "";

                Bundle bundle = new Bundle();
                bundle.putStringArray("devicename",manualAdd_ble_mainActivity.readData(mainActivity.file, x));
                it.putExtras(bundle);

                v.getContext(). startActivity(it);

            }



        }

//        DialogInterface.OnClickListener GoToSetPeople = new DialogInterface.OnClickListener() {
//
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//
//                ((Activity)context).getFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.main_fragment, new mainview_fragmentlayout_SetPeople())
//                        .commit();
//
//            }
//
//        };
//



        DialogInterface.OnClickListener close = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {




            }

        };










    }


}