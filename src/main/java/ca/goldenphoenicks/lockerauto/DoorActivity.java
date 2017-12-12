package ca.goldenphoenicks.lockerauto;

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
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
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
