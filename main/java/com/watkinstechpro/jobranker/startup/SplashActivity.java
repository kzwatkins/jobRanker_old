package com.watkinstechpro.jobranker.startup;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.watkinstechpro.jobranker.MainRankerActivity;
import com.watkinstechpro.jobranker.R;
import com.watkinstechpro.jobranker.globals.Globals;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRandomBlsFact();

        animate();
    }

    private void setRandomBlsFact(){
        EditText editText = (EditText)findViewById(R.id.edtTxtBlsFacts);
        String result = "Did You Know?";
        result += "\n" + Globals.BLS_FACTS[(int)(Math.random()*Globals.BLS_FACTS.length)];
        editText.setText(result);
    }

    private void animate(){
        final RunBar runTask = new RunBar();
        runTask.execute();
    }

    private class RunBar extends AsyncTask<Void, Void, Void> {
        final ProgressBar mProg = (ProgressBar) findViewById(R.id.progressBar);
        final ImageView imageView = (ImageView) findViewById(R.id.ladderImg);

        final int MAX_PROGRESS = 100;
        final int MAX_TIME = 5000;
        final int NUM_IMGS = 3;

         int imgID = 0;

        @Override
        protected void onPreExecute() {
            Log.d("MyAsyncTask","onPreExecute");
            mProg.setProgress(0);

            mProg.setMax(MAX_PROGRESS);

            //change the color of the progress bar from blue to green

            int colorDark = getResources().getColor(R.color.burntSienna);
            mProg.getIndeterminateDrawable().setColorFilter(colorDark, PorterDuff.Mode.SRC_IN);
            mProg.getProgressDrawable().setColorFilter(colorDark, PorterDuff.Mode.SRC_IN);

            ProgressBarAnimation animation = new ProgressBarAnimation(mProg, 0, MAX_PROGRESS);
            animation.setDuration(MAX_TIME);

            mProg.startAnimation(animation);
        }


        @Override
        protected Void doInBackground(Void... params) {

            Log.d("MyAsyncTask","doInBackground");
            try {
                Thread.sleep(MAX_TIME);

              } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void...params) {
            Log.d("MyAsyncTask", "onProgressUpdate");
            super.onProgressUpdate(params);

            imgID = (imgID + 1) % NUM_IMGS;
            if(imgID == 0) {
                imageView.setImageResource(R.drawable.jobladderlowest_color);
            }
            else if(imgID == 1){
                imageView.setImageResource(R.drawable.jobladderlower_color);
            }
            else{
                imageView.setImageResource(R.drawable.jobladderhigh_color);
            }

        }

        @Override
        protected void onPostExecute(Void result){
            Log.d("MyAsyncTask", "onPostExecute");
            super.onPostExecute(result);

            //Class nextClass = (Globals.isOnline()? CareersActivity.class:MainRankerActivity.class);
            Class nextClass = MainRankerActivity.class;
            Intent nextScreen = new Intent(getApplicationContext(), nextClass);

            SplashActivity.this.startActivity(nextScreen);
            SplashActivity.this.finish();
        }
    }
}
