
package ca.goldenphoenicks.lockerauto;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;


public class DoorActivity extends AppCompatActivity implements View.OnClickListener {
    private Button dB;
    private int doorSpeed;
    private SeekBar sB;
    private TextView dsT;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        switch (pref.getInt("color", -1))
        {
            case 0:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 1:
                myToolbar.setBackgroundResource(R.color.purp);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 2:
                myToolbar.setBackgroundResource(R.color.blu);
                getWindow().getDecorView().setBackgroundResource(R.color.wht);
                break;
            case 3:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.ylw);
                break;
            default:
                myToolbar.setBackgroundResource(R.color.purp);
                getWindow().getDecorView().setBackgroundResource(R.color.wht);
        }
        dB=(Button) findViewById(R.id.dButt);
        dB.setOnClickListener(this);


        dsT = (TextView) findViewById(R.id.dps);

        sB=(SeekBar) findViewById(R.id.seeker);
        sB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                if(sB.getProgress()>50)
                {
                    new DoorActivity.GetPin().execute();
                }
                else if(sB.getProgress()<50)
                {
                    new DoorActivity.GetPin().execute();
                }
                else if(sB.getProgress()==50)
                {
                    new DoorActivity.GetPin().execute();
                }
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                sB.setProgress(50);
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.blu));
//        }
    }

    @Override
    public void onBackPressed()
    {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

        @Override
        public void onClick(View view) {

            if(dB.getText().equals("Open"))
            {
                new GetPin().execute("2");
                dB.setText("Close");
                dB.setEnabled(false);

            } else {
                new GetPin().execute("0");
                dB.setText("Open");
                dB.setEnabled(false);
//                SystemClock.sleep(3000);
//                new GetPin().execute("1");
//                dB.setEnabled(true);

            }
            SystemClock.sleep(3000);
            new GetPin().execute("1");
            dB.setEnabled(true);

        }



    class GetPin extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            HttpURLConnection urlConnection=null;
            String validateUrl = "http://munro.humber.ca/~n01116269/door.php?p_stat=" + Integer.parseInt(strings[0])+"&u_name="+ pref.getString("u_name", "");

            try {
                URL url = new URL(validateUrl);
                urlConnection =(HttpURLConnection)url.openConnection();
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line ="";
                while((line=br.readLine())!=null)
                {
                    result+=line;
                }
                is.close();
                Log.i("Result",result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}

package ca.goldenphoenicks.lockerauto;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;



public class DoorActivity extends AppCompatActivity implements View.OnClickListener {
    private Button dB;
    private int doorSpeed;
    private SeekBar sB;
    private TextView dsT;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        switch (pref.getInt("color", -1))
        {
            case 0:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 1:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 2:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 3:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            default:
                myToolbar.setBackgroundResource(R.color.red);
                getWindow().getDecorView().setBackgroundResource(R.color.blk);
        }
        dB=(Button) findViewById(R.id.dButt);
        dB.setOnClickListener(this);

        dsT = (TextView) findViewById(R.id.dps);

        sB=(SeekBar) findViewById(R.id.seeker);
        sB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                if(sB.getProgress()>50&&sB.getProgress()<75)
                {
                    doorSpeed=1;
                    dsT.setText(Integer.toString(doorSpeed));
                }else if(sB.getProgress()>=75)
                {
                    doorSpeed=2;
                    dsT.setText(Integer.toString(doorSpeed));
                }else if(sB.getProgress()<50 && sB.getProgress()>25)
                {
                    doorSpeed=-1;
                    dsT.setText(Integer.toString(doorSpeed));
                }else if(sB.getProgress()<=25)
                {
                    doorSpeed=-2;
                    dsT.setText(Integer.toString(doorSpeed));
                }
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                sB.setProgress(50);
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.blu));
//        }
    }

    @Override
    public void onBackPressed()
    {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

        @Override
        public void onClick(View view) {

            if(dB.getText().equals("Open"))
            {
                dB.setText("Close");
            } else {
                dB.setText("Open");
            }
        }

}

