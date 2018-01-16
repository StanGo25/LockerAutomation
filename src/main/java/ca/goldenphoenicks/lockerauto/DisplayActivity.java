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

}