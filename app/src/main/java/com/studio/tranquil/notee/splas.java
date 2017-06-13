package com.studio.tranquil.notee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class splas extends Activity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            setContentView(R.layout.spl);

            Thread timer = new Thread(){
                public void run(){
                    try{
                        sleep(1500);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }finally{
                        Intent openMainActivity= new Intent(splas.this,Main_Screen.class);
                        startActivity(openMainActivity);
                    }
                }
            };
            timer.start();
        }

        @Override
        protected void onPause() {
            // TODO Auto-generated method stub
            super.onPause();
            finish();
        }

    }
