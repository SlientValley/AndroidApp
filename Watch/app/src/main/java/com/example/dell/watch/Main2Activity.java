package com.example.dell.watch;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Vibrator vibrator;
    private TextView chronometer;
    private boolean flag = true; //开始，暂停切换标志
    private boolean flag1 = true;  //换手标志
    private boolean start = false;  //判断是否开局，以启用换手功能
    private Button btn_start;    //开始按钮
    private Button btn_change;   //换局按钮
    private Button btn_changeHand;  //换手按钮
    private Drawable white;
    private Drawable dark;
    private long recordingTimeDark;
    private long recordingTimeWhite;
    public  long count;
    private TextView tv;     //记录回合的文本框
    public  long leftTime;
    public  long addTime ;
    private Timer timer;
    private TimerTask timerTask;
    private boolean isPause = true;
    public static  Time time;
    private int num = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);//获取震动服务
        chronometer = (TextView) findViewById(R.id.player);
        btn_start = (Button) findViewById(R.id.start_stop);
        btn_change = (Button) findViewById(R.id.change);
        btn_changeHand = (Button) findViewById(R.id.changeHand);
        tv = (TextView) findViewById(R.id.hand);
        white = getResources().getDrawable(R.drawable.side_white);
        dark = getResources().getDrawable(R.drawable.side);
        time = new Time();
        timer = new Timer();

        //字体设置
        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/造字工房悦圆演示版常规体.otf");
        chronometer.setTypeface(typeface1);
        tv.setTypeface(typeface1);


        btn_start.setOnClickListener(new Main2Activity.ButtonClickListener());
        btn_change.setOnClickListener(new Main2Activity.ButtonClickListener());
        btn_changeHand.setOnClickListener(new Main2Activity.ButtonClickListener());
        onReset();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            CDialog dialog = new CDialog(Main2Activity.this,num,R.style.dialog);
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_contract_basis) {
            // Handle the camera action
            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent);
            this.finish();
        } else if (id == R.id.nav_adding_second) {
            Toast.makeText(getApplicationContext(), "你所在的就是呢", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Start(View v){
        isPause = false;
        addTime = time.getAddTime();
        leftTime = time.getBaseTime();
        recordingTimeWhite = time.getBaseTime()-time.getAddTime();
        recordingTimeDark = time.getBaseTime();
        start(v);
    }
    public void onAddTimeStart(View v){
        isPause = false;
        if (count%2 == 1) leftTime = recordingTimeDark+addTime;
        else leftTime = recordingTimeWhite+addTime;
        start(v);
    }
    public void onRecordStart(View v){
        if (isPause)stop();
        isPause = false;
        if (count%2 == 1) leftTime = recordingTimeDark;
        else leftTime = recordingTimeWhite;
        start(v);
    }
    public void stop(){
        if(!timerTask.cancel()){
            timerTask.cancel();
            timer.cancel();
        }
    }
    public void pause(){
        isPause = true;
    }

    public void onReset(){
        count = 1;
        addTime = time.getAddTime();
        leftTime = time.getBaseTime();
        recordingTimeWhite = time.getBaseTime()-time.getAddTime();
        recordingTimeDark = time.getBaseTime();
        start = false;
        btn_start.setText("开始");
        flag = true;
        flag1 = true;
        tv.setText("新 局");
        chronometer.setText("黑棋\n" + getStringTime(leftTime));// 更改时间显示格式
        chronometer.setTextColor(0xfffffaf0);
        chronometer.setBackground(white);
    }
    public void setWhiteFormat(){
        chronometer.setText("白棋\n"+ getStringTime(leftTime));
        chronometer.setTextColor(0xff000000);
        chronometer.setBackground(dark);
    }
    public void  setDarkFormat(){
        chronometer.setText("黑棋\n" + getStringTime(leftTime));// 更改时间显示格式
        chronometer.setTextColor(0xfffffaf0);
        chronometer.setBackground(white);
    }

    public class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start_stop:
                    if(flag) {
                        if (!start)Start(v);
                        else onRecordStart(v);
                        flag = false;
                        start = true;
                        btn_start.setText("暂停");
                    }else {
                        pause();
                        btn_start.setText("继续");
                        flag = true;
                    }
                    tv.setText("第"+count+"手");
                    break;
                case R.id.change:
                    if(!start){
                        Snackbar.make(v, "已经是新局啦", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        break;
                    }
                    stop();
                    onReset();
                    break;
                case R.id.changeHand:
                    if(!start){
                        Snackbar.make(v, "还未开始哦", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        break;
                    }
                    stop();
                    count++;
                    tv.setText("第"+count+"手");
                    if (!flag1) {
                        setDarkFormat();
                        flag1 = true;
                    }else{
                        setWhiteFormat();
                        flag1 = false;
                    }
                    onAddTimeStart(v);
                    btn_start.setText("暂停");
                    flag = false;
                    break;
                default:
                    break;
            }
        }
    }
    public void setChronometerClick(View v){
        vibrator.vibrate(new long[]{10,40,100},-1);
        if(!start){
            Snackbar.make(v, "还未开始哦", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }else {
            stop();
            count++;
            tv.setText("第"+count+"手");
            if (!flag1) {
                setDarkFormat();
                flag1 = true;
            }else{
                setWhiteFormat();
                flag1 = false;
            }
            onAddTimeStart(v);
            btn_start.setText("暂停");
            flag = false;
        }
    }

    public void start(View v) {
        timerTask = new TimerTask() {
            long cnt = leftTime;
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!isPause)cnt--;
                        if (count%2 ==1){
                            chronometer.setText("黑棋\n"+getStringTime(cnt));
                            recordingTimeDark = cnt;
                        }
                        else {
                            chronometer.setText("白棋\n"+getStringTime(cnt));
                            recordingTimeWhite = cnt;
                        }
                        if( cnt == 0 && count%2 ==1 ){
                            chronometer.setText("白棋\n赢了");
                            vibrator.vibrate(new long[]{1000,1000,1000,1000},-1);
                            isPause = true;
                        }
                        if (cnt == 0 && count%2 ==0 ){
                            chronometer.setText("黑棋\n赢了");
                            vibrator.vibrate(new long[]{1000,1000,1000,1000},-1);
                            isPause = true;
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    private String getStringTime(long cnt){
        long min = cnt/60;
        long sec = cnt%60;
        return String.format(Locale.CANADA,"%02d:%02d",min,sec);
    }

}
