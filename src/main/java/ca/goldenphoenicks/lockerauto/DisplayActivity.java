<<<<<<< HEAD
package ca.goldenphoenicks.lockerauto;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Stanley on 12/10/17.
 */

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();


        String res="";
        try
        {
            res = new DisplayActivity.GetPin().execute().get();


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(res);
        String realRes = sb.toString().replaceAll("\\<.*?>","");


        realRes = realRes.replace("Owned Devices Status:","Owned Devices Status: \n");
        realRes = realRes.replace(": 0",": Door closing\n");
        realRes = realRes.replace(": 1",": Door is stationary\n");
        realRes = realRes.replace(": 2",": Door is opening\n");
        realRes = realRes.replace(": 3",": Lock is locked\n");
        realRes = realRes.replace(": 4",": Lock is unlocked\n");

        TextView displayView = (TextView) findViewById(R.id.statView);
        displayView.setText(realRes);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


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

    }

    class GetPin extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            HttpURLConnection urlConnection = null;
            String validateUrl = "http://munro.humber.ca/~n01116269/display.php?u_name=" + pref.getString("u_name", "");
            try {
                URL url = new URL(validateUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = "";

                while((line = br.readLine())!=null)
                {
                    result+=line;
                }
                is.close();
                Log.i("Result",result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

=======
package ca.goldenphoenicks.lockerauto;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Stanley on 12/10/17.
 */

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        String res="";
        try
        {
            res = new DisplayActivity.GetPin().execute().get();


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(res);
        String realRes = sb.toString().replaceAll("\\<.*?>","");



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

    }



    class GetPin extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            HttpURLConnection urlConnection = null;
            String validateUrl = "http://munro.humber.ca/~n01116269/display.php?u_name=" + pref.getString("u_name", "");
            try {
                URL url = new URL(validateUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = "";

                while((line = br.readLine())!=null)
                {
                    result+=line;
                }
                is.close();
                Log.i("Result",result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

>>>>>>> e85493692c1994a6b92a212fedb5823ed428d526
}