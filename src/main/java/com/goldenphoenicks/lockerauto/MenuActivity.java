package com.goldenphoenicks.lockerauto;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button lockb=(Button)findViewById(R.id.lockButton);
        Button doorb=(Button)findViewById(R.id.doorButton);
        Button qrb=(Button)findViewById(R.id.QRbutton);
        Button displayb=(Button)findViewById(R.id.displayButton);

        Intent logInt=getIntent();
        int loginCheck=logInt.getIntExtra("validate",0);
        if(loginCheck==2) {
            lockb.setEnabled(false);
            doorb.setEnabled(false);
            displayb.setEnabled(false);

        } else if(loginCheck==1) {
            lockb.setEnabled(true);
            doorb.setEnabled(true);
            displayb.setEnabled(true);
            qrb.setEnabled(true);

        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.showOverflowMenu();
        setSupportActionBar(myToolbar);

        lockb.setOnClickListener(this);
        doorb.setOnClickListener(this);
        displayb.setOnClickListener(this);
        qrb.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent nextInt2;
        switch(view.getId())
        {
            case R.id.lockButton:
                nextInt2=new Intent(MenuActivity.this,LockActivity.class);
                startActivity(nextInt2);
                break;

            case R.id.doorButton:
                nextInt2=new Intent(MenuActivity.this,DoorActivity.class);
                startActivity(nextInt2);
                break;

            case R.id.QRbutton:
                nextInt2=new Intent(MenuActivity.this,QRActivity.class);
                startActivity(nextInt2);
                break;

            case R.id.displayButton:
                nextInt2=new Intent(MenuActivity.this,DisplayActivity.class);
                startActivity(nextInt2);
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent toSettings = new Intent(MenuActivity.this,SettingsActivity.class);
                startActivity(toSettings);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
