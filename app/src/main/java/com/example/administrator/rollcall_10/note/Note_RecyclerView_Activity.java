package com.example.administrator.rollcall_10.note;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.rollcall_10.R;
import com.example.administrator.rollcall_10.rollcall_dialog.RollCall_Dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Note_RecyclerView_Activity extends AppCompatActivity {

    public static final String Edit_Note = "Edit_Note";
    public static final int requstcode = 10;
    public static RollCall_Note_database RND;


    // 獲取當前時間
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    public static Date curDate = new Date(System.currentTimeMillis());

    private Note_RecyclerView Note_RecyclerView;
    private FloatingActionButton AddNote_fab;

    Note_recyclerViewAdapter note_recyclerViewAdapter;


    //****Scan返回鍵(左上角鍵頭)監聽事件 Start***\\\
    @Override
    public Intent getSupportParentActivityIntent()
    {
        finish();
        return null;
    }
    //****Scan返回鍵(左上角鍵頭)監聽事件 End***\\\





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note__recycler_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e("A_Note_Object.Title.size()","數量是"+Note_Object.Title.size());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.Note_Title));

        AddNote_fab = (FloatingActionButton) findViewById(R.id.fab_addnote);
        AddNote_fab.setOnClickListener(new AddNotefab());
        Note_RecyclerView = (Note_RecyclerView) findViewById(R.id.note_recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Note_RecyclerView.setLayoutManager(layoutManager);


        note_recyclerViewAdapter = new Note_recyclerViewAdapter(this);

        Note_RecyclerView.setEmptyView(findViewById(R.id.empty_noItems));
        Note_RecyclerView.setAdapter(note_recyclerViewAdapter);


        note_recyclerViewAdapter.notifyDataSetChanged();



    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private class AddNotefab implements View.OnClickListener {
        @Override
        public void onClick(View view) {


            String Present = formatter.format(curDate);

            Intent note_Add = new Intent(Note_RecyclerView_Activity.this, Note_EditView.class);
            note_Add.putExtra("CreateTime", Present);
            note_Add.setAction("AddNote");

            startActivityForResult(note_Add, requstcode);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == requstcode) {


                note_recyclerViewAdapter.notifyDataSetChanged();

            }
        }
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消編輯", Toast.LENGTH_SHORT).show();
        }

    }

    public class Note_recyclerViewAdapter extends RecyclerView.Adapter<Note_recyclerViewAdapter.NoteViewHolder> {

        private Context context;

        public static final int VIEW_TYPE_ITEM = 1;
        public static final int VIEW_TYPE_EMPTY = 0;

        ArrayList<String> datas;

        public Note_recyclerViewAdapter(Context context) {

            this.context = context;
        }

        @Override
        public int getItemViewType(int position) {
            if (Note_Object.Title.size() == 0) {
                return VIEW_TYPE_EMPTY;
            }

            return VIEW_TYPE_ITEM;
        }

        class NoteViewHolder extends RecyclerView.ViewHolder {

            CardView note_CardView;
            TextView note_title, note_time;
            ImageView tickandcolck;


            public NoteViewHolder(View itemView) {
                super(itemView);
                note_CardView = (CardView) itemView.findViewById(R.id.card_view);
                note_title = (TextView) itemView.findViewById(R.id.pen_txt);
                note_time = (TextView) itemView.findViewById(R.id.createTime);
                tickandcolck = (ImageView) itemView.findViewById(R.id.tickandclock);

            }


        }

        @Override
        public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v;
            NoteViewHolder newsViewHolder;

            v = LayoutInflater.from(context).inflate(R.layout.note_recyclerview_item, viewGroup, false);
            newsViewHolder = new NoteViewHolder(v);

            return newsViewHolder;
        }

        @Override
        public void onBindViewHolder(final NoteViewHolder Note_itemViewHolder, final int i) {


            Note_itemViewHolder.note_title.setText(Note_Object.Title.get(i));
            Note_itemViewHolder.note_time.setText(Note_Object.Time.get(i));
            Note_itemViewHolder.tickandcolck.setImageResource(Note_Object.FinshOrNotIcon.get(i));


            Note_itemViewHolder.note_CardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = LayoutInflater.from(Note_RecyclerView_Activity.this);
                    final View layout = inflater.inflate(R.layout.watch_note_layout, null);

                    TextView note_conten = (TextView) layout.findViewById(R.id.note_conten_dailogview);
                    note_conten.setText(Note_Object.Content.get(i));
                    TextView note_time = (TextView) layout.findViewById(R.id.note_createtime_dailogview);
                    note_time.setText(Note_Object.Time.get(i));

                    final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(layout.getContext());
                    rollCall_dialog.setView(layout);
                    rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
                    rollCall_dialog.setCancelable(false);
                    rollCall_dialog.setCancelable(true);

                    rollCall_dialog.show();
                }

            });


            Note_itemViewHolder.note_CardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    List_onclick(Note_itemViewHolder.getAdapterPosition());
                    return true;
                }
            });


        }


        private void List_onclick(int position) {

            LayoutInflater inflater = LayoutInflater.from(Note_RecyclerView_Activity.this);
            final View layout = inflater.inflate(R.layout.dialog_list_longclick_layout, null);

            TextView title_txt = (TextView) layout.findViewById(R.id.Dialog_Title);

            title_txt.setText("選擇動作");
            RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView_list_long_click);
            recyclerView.setLayoutManager(new LinearLayoutManager(Note_RecyclerView_Activity.this));

            String[] item_txt;
            int[] item_image;


            if (Note_Object.IsFinsh.get(position) == 0) {
                item_txt = new String[]{"標記為已完成", "編輯內容", "刪除"};
                item_image = new int[]{R.mipmap.tick64, R.mipmap.pen_rename, R.mipmap.recyclerbin64};
            } else {
                item_txt = new String[]{"重設提醒", "編輯內容", "刪除"};
                item_image = new int[]{R.mipmap.alarm64, R.mipmap.pen_rename, R.mipmap.recyclerbin64};
            }

            final RollCall_Dialog rollCall_dialog = new RollCall_Dialog(layout.getContext());
            rollCall_dialog.setView(layout);
            rollCall_dialog.setIcon(R.mipmap.dialogscanicon128);
            rollCall_dialog.setCancelable(false);
            rollCall_dialog.setCancelable(true);

            ///**關閉dialog
            Button btn_close = (Button) layout.findViewById(R.id.btn_close);
            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rollCall_dialog.dismiss();
                }
            });

            Note_card_click_RecyclerviewAdapter recyclerviewAdapter_long_click = new Note_card_click_RecyclerviewAdapter(item_txt, item_image, Note_RecyclerView_Activity.this, rollCall_dialog, position, note_recyclerViewAdapter);

            recyclerView.setAdapter(recyclerviewAdapter_long_click);

            recyclerView.setItemAnimator(new DefaultItemAnimator());

            rollCall_dialog.show();


        }

        @Override
        public int getItemCount() {

//            if (Note_Object.Title.size() == 0) {
//                return 0;
//            }

            return Note_Object.Title.size();

        }
    }


}
