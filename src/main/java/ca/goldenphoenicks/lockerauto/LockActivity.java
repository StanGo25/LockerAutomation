
package ca.goldenphoenicks.lockerauto;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Stanley on 10/04/17.
 */

public class LockActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private ImageButton img;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        img=(ImageButton) findViewById(R.id.lockNow);

        img.setOnClickListener(this);

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
        TextView locking=(TextView)findViewById(R.id.locking);
        ImageButton change = findViewById(R.id.lockNow);
        String send;
        String res = null;

        if(locking.getText().toString().equals(getString(R.string.lockOn)))
        {
            locking.setText(R.string.lockOff);
            change.setBackgroundResource(R.drawable.off);
            send = "4";
        } else {
            locking.setText(R.string.lockOn);
            change.setBackgroundResource(R.drawable.on);
            send = "3";
        }

        try {
            res=new setLock().execute(send,pref.getString("u_name","")).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

class setLock extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... strings) {

        String result = "";
        HttpURLConnection urlConnection=null;
        String validateUrl = "http://munro.humber.ca/~n01116269/lock.php?p_stat=" + strings[0]+"&u_name="+strings[1];

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
import android.graphics.drawable.Drawable;
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
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Stanley on 10/04/17.
 */

public class LockActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private ImageButton img;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        img=(ImageButton) findViewById(R.id.lockNow);

        img.setOnClickListener(this);

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
        TextView locking=(TextView)findViewById(R.id.locking);
        ImageButton change = findViewById(R.id.lockNow);
        if(locking.getText().toString().equals(getString(R.string.lockOn)))
        {
            locking.setText(R.string.lockOff);
            change.setBackgroundResource(R.drawable.off);

        } else {
            locking.setText(R.string.lockOn);
            change.setBackgroundResource(R.drawable.on);

        }
    }
}

