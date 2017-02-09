package com.example.android.materialdesigncodelab;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import db.DBHelper;
import db.sendmessagetoserver;
import de.hdodenhof.circleimageview.CircleImageView;
import structures.Master_Message1;
import structures.Name_Master;
import utils.ChatBoxAdapter;

import static com.example.android.materialdesigncodelab.R.drawable.e;

/**
 * Created by Timmy on 2/8/17.
 */

public class ChatBox extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    //need to get from shredpref
    public static String mynum = "7415451094";
    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private Name_Master name_master;
    private TextView title,bigTitle,description;
    private CircleImageView profileImage;
    public static RecyclerView recyclerView;
    public static ArrayList<Master_Message1> master_messages = new ArrayList<>();
    public static ChatBoxAdapter chatBoxAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbox);
        bindActivity();
        title = (TextView)findViewById(R.id.title);
        bigTitle = (TextView)findViewById(R.id.bigTitle);
        description = (TextView)findViewById(R.id.description);
        profileImage = (CircleImageView)findViewById(R.id.profileImage);
        recyclerView = (RecyclerView)findViewById(R.id.chatList);
        ImageButton send=(ImageButton)findViewById(R.id.send);
        final EditText message=(EditText)findViewById(R.id.message);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Master_Message1 temp=new Master_Message1();
                temp.setSENDER(mynum + "");
                temp.setRECIEVER(description.getText().toString());
                temp.setMESSAGE(message.getText().toString());
                Date nowdate=new Date();
                temp.setDATE(String.valueOf(nowdate.getDate() + "/" + (nowdate.getMonth() + 1) + "/" + nowdate.getYear()));
                Calendar c = Calendar.getInstance();
                int seconds = c.get(Calendar.SECOND);
                int minutes = c.get(Calendar.MINUTE);
                int hours = c.get(Calendar.HOUR);

                temp.setTIME(hours+":"+minutes+":"+seconds);
                new sendmessagetoserver(ChatBox.this,temp).execute();
                message.setText("");

            }
        });

        recyclerView.setNestedScrollingEnabled(true);
        /*final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        try {
            Field f = swipeRefreshLayout.getClass().getDeclaredField("mCircleView");
            f.setAccessible(true);
            ImageView img = (ImageView)f.get(swipeRefreshLayout);
            img.setImageResource(0);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAppBarLayout.setExpanded(true);
                swipeRefreshLayout.setRefreshing(false);
            }
        });*/


        context = this;


        name_master = (Name_Master)getIntent().getBundleExtra("bundle").getSerializable("Name_Master");
        if(name_master!=null){
            if(name_master.getNAME()!=null){
                title.setText(name_master.getNAME());
                bigTitle.setText(name_master.getNAME());
            }else{
                title.setText("");
                bigTitle.setText("");
            }

            if(name_master.getMOBILE_NUMBER()!=null){
                description.setText(name_master.getMOBILE_NUMBER());

                DBHelper dbHelper = new DBHelper(context);
                String sql = "Select * from Master_Message where (RECIEVER in('"+name_master.getMOBILE_NUMBER()+"') and SENDER in('"
                        +mynum+"')) or (RECIEVER in('"+mynum+"') and SENDER in('"+name_master.getMOBILE_NUMBER()+"')) order by SNO asc";

                try{
                    master_messages = dbHelper.get_Data_Master(sql);
                    chatBoxAdapter = new ChatBoxAdapter(context,master_messages);
                    staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(staggeredGridLayoutManager);
                    recyclerView.setAdapter(chatBoxAdapter);

                    DisplayMetrics displaymetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                    int a =  (displaymetrics.heightPixels*80)/100;

                    recyclerView.getLayoutParams().height =a;

                }catch (Exception e){

                }

            }else{
                description.setText("");
            }
            if(name_master.getGENDER()!=null){
                if(name_master.getGENDER().equalsIgnoreCase("Male")){
                    profileImage.setImageResource(R.drawable.man);
                } else {
                    profileImage.setImageResource(R.drawable.girl);
                }
            }
        }


        mAppBarLayout.addOnOffsetChangedListener(this);

        mToolbar.inflateMenu(R.menu.menu_main);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }

    private void bindActivity() {
        mToolbar        = (Toolbar) findViewById(R.id.toolbarMain);
        mTitle          = (TextView) findViewById(R.id.title);
        mTitleContainer = (LinearLayout) findViewById(R.id.linearMain);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.appbarMain);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}

