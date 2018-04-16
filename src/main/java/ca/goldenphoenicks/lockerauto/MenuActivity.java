package ca.goldenphoenicks.lockerauto;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static android.widget.Toast.LENGTH_SHORT;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

        super.
            onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu);

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().

            getItem(0).

            setChecked(true);

            Menu menuNav = navigationView.getMenu();

            MenuItem nav_item1 = menuNav.findItem(R.id.nav_lock);
            MenuItem nav_item2 = menuNav.findItem(R.id.nav_door);
            MenuItem nav_item3 = menuNav.findItem(R.id.nav_display);
        nav_item1.setEnabled(false);
        nav_item2.setEnabled(false);
        nav_item3.setEnabled(true);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View view){
                onBackPressed();
            }
            });

            pref =MenuActivity.this.

            getSharedPreferences("MyPref",0); // 0 - for private mode

            editor =pref.edit();

        switch(pref.getInt("color",-1))

            {
                case 0:
                    toolbar.setBackgroundResource(R.color.red);
                    getWindow().getDecorView().setBackgroundResource(R.color.blk);
                    break;
                case 1:
                    toolbar.setBackgroundResource(R.color.purp);
                    getWindow().getDecorView().setBackgroundResource(R.color.blk);
                    break;
                case 2:
                    toolbar.setBackgroundResource(R.color.blu);
                    getWindow().getDecorView().setBackgroundResource(R.color.wht);
                    break;
                case 3:
                    toolbar.setBackgroundResource(R.color.red);
                    getWindow().getDecorView().setBackgroundResource(R.color.ylw);
                    break;
                default:
                    toolbar.setBackgroundResource(R.color.purp);
                    getWindow().getDecorView().setBackgroundResource(R.color.wht);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

            Intent i = getIntent();
            username=i.getStringExtra("name");

        if(username!=null)

            {
                editor.putString("u_name", username);
                editor.commit();
            }

            View headerV = navigationView.getHeaderView(0);
            TextView navTitle = (TextView) headerV.findViewById(R.id.headerName);
        navTitle.setText("Welcome, "+pref.getString("u_name",""));
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().

            getItem(0).

            setChecked(true);


            String res = "";
        try

            {
                res = new MenuActivity.GetProducts().execute().get();
            }
        catch(
            Exception e)

            {
                e.printStackTrace();
            }


//        if(res.contains("1"))
//
//            {
//                nav_item1.setEnabled(true);
//            }
//        if(res.contains("2"))
//
//            {
//                nav_item2.setEnabled(true);
//            }
//
//        if(res.contains("3"))
//            {
//                nav_item3.setEnabled(true);
//            }
        }catch(Exception e)
        {
            Toast.makeText(MenuActivity.this, "Failed to Connect...", LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Button door=(Button) findViewById(R.id.dButt2);

        door.setOnClickListener(this);

        String display="";
        try {
            display = new GetStat().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        TextView displayStat = (TextView)findViewById(R.id.displayView);
        displayStat.setText(display);

    }


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.purp));
//        }


    @Override
    public void onClick(View view) {
        String res = null;

        Button door=(Button) findViewById(R.id.dButt2);

        if(door.getText().equals("Open"))
        {
            try {
                res=new setLock().execute("4",pref.getString("u_name","")).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            new setDoor().execute("2");
            door.setText("Close");
            door.setEnabled(false);

            String display="";
            try {
                display = new GetStat().execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            TextView displayStat = (TextView)findViewById(R.id.displayView);
            displayStat.setText(display);

        } else {
            new setDoor().execute("0");
            door.setText("Open");
            door.setEnabled(false);

            String display="";
            try {
                display = new GetStat().execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            TextView displayStat = (TextView)findViewById(R.id.displayView);
            displayStat.setText(display);

        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            res = new setLock().execute("3",pref.getString("u_name","")).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        new setDoor().execute("1");

        String display="";
        try {
            display = new GetStat().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        TextView displayStat = (TextView)findViewById(R.id.displayView);
        displayStat.setText(display);

        door.setEnabled(true);
    }

    class setLock extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            HttpURLConnection urlConnection = null;
            String validateUrl = "http://munro.humber.ca/~n01116269/lock.php?p_stat=" + strings[0]+ "&u_name=" + strings[1];

            try {
                URL url = new URL(validateUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = "";
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                is.close();
                Log.i("Result", result);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class setDoor extends AsyncTask<String,Void,String> {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?\nYou will be logged out").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        DrawerLayout drawer;
        int id = item.getItemId();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(id==R.id.nav_menu) {

        } else if (id == R.id.nav_qr_scanner) {
            intent = new Intent(MenuActivity.this, QRActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_display) {
            intent = new Intent(MenuActivity.this, DisplayActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_lock) {
            intent = new Intent(MenuActivity.this, LockActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_door) {
            intent = new Intent(MenuActivity.this, DoorActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            intent = new Intent(MenuActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class GetProducts extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            HttpURLConnection urlConnection = null;
            String validateUrl = "http://munro.humber.ca/~n01116269/returnproducts.php?u_name=" + pref.getString("u_name", "");
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
    class GetStat extends AsyncTask<String,Void,String> {

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
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(result);
            String realRes = sb.toString().replaceAll("\\<.*?>","");

            return realRes;
        }
    }

}
