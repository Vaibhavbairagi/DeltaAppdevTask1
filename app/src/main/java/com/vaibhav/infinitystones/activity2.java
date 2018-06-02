package com.vaibhav.infinitystones;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class activity2 extends AppCompatActivity {
    Button button2, button3;
    TextView textView1,textView2;
    int x,counter1=0,counter2=0;
    private int[] myindex=new int[6];
    ListView listView;
    ArrayList<String> arrayList= new ArrayList<String>();
    ArrayAdapter<String> adapter;
    String[] Stones = {"You Have Got The POWER Stone", " You Have Got The SPACE Stone", "You Have Got The TIME Stone",
            "You Have Got The REALITY Stone", "You Have Got The SOUL Stone", "You Have Got The MIND Stone"};
    String[] liststone={"POWER Stone","SPACE Stone","TIME Stone","REALITY Stone","SOUL Stone","MIND Stone"};
    String nstring;
    View view;
    ArrayList<String> copylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        defineview();
        definebuttons();
        int l = myindex.length;
        myindex = randomindex(l);
        SharedPreferences sharedPreferences=getSharedPreferences("prefs",Context.MODE_PRIVATE);
        if (savedInstanceState != null){
            counter1=savedInstanceState.getInt("counter");
            myindex=savedInstanceState.getIntArray("indexlist");
            restorelist();
        }
        else if (sharedPreferences.getInt("counter",0)!=0) {
            counter1 = sharedPreferences.getInt("counter1", 0);
            myindex[0] = sharedPreferences.getInt("myindex0", 0);
            myindex[1] = sharedPreferences.getInt("myindex1", 0);
            myindex[2] = sharedPreferences.getInt("myindex2",0);
            myindex[3] = sharedPreferences.getInt("myindex3", 0);
            myindex[4] = sharedPreferences.getInt("myindex4", 0);
            myindex[5] = sharedPreferences.getInt("myindex5", 0);
            restorelist();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("counter",counter1);
        savedInstanceState.putIntArray("indexlist",myindex);
        savedInstanceState.putInt("counter2",counter2);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        counter1=savedInstanceState.getInt("counter");
        myindex=savedInstanceState.getIntArray("indexlist");
        counter2=savedInstanceState.getInt("counter2");
        if (counter1!=0)
        restorelist();
        if (counter2==1&&counter1!=0) {
            settext(Stones[myindex[counter1 - 1]]);
            setbackground(Stones[myindex[counter1 - 1]]);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        SharedPreferences sharedPreferences=getSharedPreferences("prefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor saver= sharedPreferences.edit();
        saver.putInt("counter1",counter1);
        saver.putInt("myindex0",myindex[0]);
        saver.putInt("myindex1",myindex[1]);
        saver.putInt("myindex2",myindex[2]);
        saver.putInt("myindex3",myindex[3]);
        saver.putInt("myindex4",myindex[4]);
        saver.putInt("myindex5",myindex[5]);
        saver.apply();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences sharedPreferences=getSharedPreferences("prefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor saver= sharedPreferences.edit();
        saver.putInt("counter",counter1);
        saver.putInt("myindex0",myindex[0]);
        saver.putInt("myindex1",myindex[1]);
        saver.putInt("myindex2",myindex[2]);
        saver.putInt("myindex3",myindex[3]);
        saver.putInt("myindex4",myindex[4]);
        saver.putInt("myindex5",myindex[5]);
        saver.apply();
        super.onDestroy();
    }
    public void restorelist(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        adapter.clear();
        adapter.notifyDataSetChanged();
        for(int i=0;i<counter1;i++) {
            listView.setAdapter(adapter);
            arrayList.add(liststone[myindex[i]]);
            adapter.notifyDataSetChanged();
        }
    }

    public void defineview(){
        textView1= findViewById(R.id.textView1);
        textView2= findViewById(R.id.textView3);
        listView= findViewById(R.id.listView);
    }
    public void definebuttons() {
        findViewById(R.id.button2).setOnClickListener(buttonClickListener);
        findViewById(R.id.button3).setOnClickListener(buttonClickListener);
    }

    public View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            counter2=1;
            switch (v.getId()) {
                case R.id.button2:
                    if(counter1<=5){
                        stoneselect();
                        counter1++;
                    }
                    if(counter1>5){
                        Toast.makeText(getBaseContext(),"You've already achieved all the stones,\n Now you've become a GOD",Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.button3:
                    resetApp();
                    break;
            }
        }
    };

    public void stoneselect() {
        String[] copystones = {"You Have Got The POWER Stone", " You Have Got The SPACE Stone", "You Have Got The TIME Stone",
                "You Have Got The REALITY Stone", "You Have Got The SOUL Stone", "You Have Got The MIND Stone"};
        x= myindex[counter1];
        nstring= copystones[x];
        settext(nstring);
        setlist(x);
        setbackground(nstring);
        Toast.makeText(getBaseContext(),liststone[x]+" Added",Toast.LENGTH_SHORT).show();
    }
    public void setbackground(String nstring){
        view = getWindow().getDecorView();
        if(nstring == Stones[0])
            view.setBackgroundColor(getResources().getColor(R.color.Purple));
        if(nstring==Stones[1])
            view.setBackgroundColor(getResources().getColor(R.color.Blue));
        if(nstring==Stones[2])
            view.setBackgroundColor(getResources().getColor(R.color.Green));
        if(nstring==Stones[3])
            view.setBackgroundColor(getResources().getColor(R.color.Red));
        if(nstring==Stones[4])
            view.setBackgroundColor(getResources().getColor(R.color.Orange));
        if(nstring==Stones[5])
            view.setBackgroundColor(getResources().getColor(R.color.Yellow));
    }
    public void setlist(int x){
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
        arrayList.add(liststone[x]);
        adapter.notifyDataSetChanged();
    }

    public void settext(String nstring){
        textView1.setText(nstring);
        if(counter1==5)
            textView2.setText("         Hurray!!!\nYou got all stones");
    }

    public void resetApp(){
        counter1=0;
        counter2=0;
        textView1.setText("");
        textView2.setText("");
        resetbackground();
        resetlist();
    }

    public void resetbackground(){
        view = getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.White));
    }

    public void resetlist(){
       adapter.clear();
       adapter.notifyDataSetChanged();
    }

    public int[] randomindex(int MAX){
        int[] drawNum = new int[MAX];
        for (int i = 0;i<MAX ;i++)
        {
            drawNum[0] = (int)(Math.random()*MAX);
            while (drawNum[1] == drawNum[0])
                drawNum[1] = (int)(Math.random()*MAX);
            while ((drawNum[2] == drawNum[0]) || (drawNum[2] == drawNum[1]) )
                drawNum[2] = (int)(Math.random()*MAX);
            while ((drawNum[3] == drawNum[0]) || (drawNum[3] == drawNum[1]) || (drawNum[3] == drawNum[2]) )
                drawNum[3] = (int)(Math.random()*MAX);
            while ((drawNum[4] == drawNum[0]) ||(drawNum[4] == drawNum[1]) ||(drawNum[4] == drawNum[2]) ||(drawNum[4] == drawNum[3]) )
                drawNum[4] = (int)(Math.random()*MAX);
            while ((drawNum[5] == drawNum[0]) ||(drawNum[5] == drawNum[1]) ||(drawNum[5] == drawNum[2]) ||(drawNum[5] == drawNum[3]) ||
                    (drawNum[5] == drawNum[4]))
                drawNum[5] = (int)(Math.random()*MAX);
        }
        return drawNum;
    }
}