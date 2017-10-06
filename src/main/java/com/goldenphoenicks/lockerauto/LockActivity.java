package com.goldenphoenicks.lockerauto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Stanley on 10/04/17.
 */

public class LockActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        Button lockNow=(Button) findViewById(R.id.lockNow);

        lockNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView locking=(TextView)findViewById(R.id.locking);
        if(locking.getText().toString().equals(getString(R.string.lockOn)))
        {
            locking.setText(R.string.lockOff);
        } else {
            locking.setText(R.string.lockOn);
        }
    }
}
