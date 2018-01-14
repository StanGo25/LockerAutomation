package ca.goldenphoenicks.lockerauto;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.app.ProgressDialog.show;

/**
 * Created by Stanley on 12/10/17.
 */

public class SettingsFragment extends Fragment {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Toolbar myToolbar;
    View rootView;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.preferences, container, false);
        pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        myToolbar = (Toolbar) rootView.findViewById(R.id.my_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        switch (pref.getInt("color", -1))
        {
            case 0:
                myToolbar.setBackgroundResource(R.color.red);
                getActivity().getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 1:
                myToolbar.setBackgroundResource(R.color.red);
                getActivity().getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 2:
                myToolbar.setBackgroundResource(R.color.red);
                getActivity().getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            case 3:
                myToolbar.setBackgroundResource(R.color.red);
                getActivity().getWindow().getDecorView().setBackgroundResource(R.color.blk);
                break;
            default:
                myToolbar.setBackgroundResource(R.color.red);
                getActivity().getWindow().getDecorView().setBackgroundResource(R.color.blk);
        }
        ListView lv1 = (ListView)rootView.findViewById(R.id.listView);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                final int[] lang = new int[1];
                switch(position) {
                    case 0:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(getString(R.string.pref_msgHead1));
                        builder.setSingleChoiceItems(R.array.pref_lang, pref.getInt("lang",-1),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        lang[0] = item;
                                    }
                                });

                        builder.setPositiveButton(getString(R.string.pref_msgApply), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                editor.putInt("lang",lang[0]);
                                editor.commit();

                                switch (pref.getInt("color", -1))
                                {
                                    case 0:
                                        myToolbar.setBackgroundResource(R.color.red);
                                        getActivity().getWindow().getDecorView().setBackgroundResource(R.color.blk);
                                        break;
                                    case 1:
                                        myToolbar.setBackgroundResource(R.color.red);
                                        getActivity().getWindow().getDecorView().setBackgroundResource(R.color.blk);
                                        break;
                                    case 2:
                                        myToolbar.setBackgroundResource(R.color.red);
                                        getActivity().getWindow().getDecorView().setBackgroundResource(R.color.blk);
                                        break;
                                    case 3:
                                        myToolbar.setBackgroundResource(R.color.red);
                                        getActivity().getWindow().getDecorView().setBackgroundResource(R.color.blk);
                                        break;
                                    default:
                                        myToolbar.setBackgroundResource(R.color.red);
                                        getActivity().getWindow().getDecorView().setBackgroundResource(R.color.blk);
                                }

                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });

                        builder.setNegativeButton(getString(R.string.pref_msgCancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                        break;
                    case 1:
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                        builder2.setTitle(getString(R.string.pref_msgHead2));
                        builder2.setSingleChoiceItems(R.array.pref_colors, pref.getInt("color",-1),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        lang[0] = item;
                                    }
                                });

                        builder2.setPositiveButton(getString(R.string.pref_msgApply), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                editor.putInt("color",lang[0]);
                                editor.commit();
                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });

                        builder2.setNegativeButton(getString(R.string.pref_msgCancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                        AlertDialog alert2 = builder2.create();
                        alert2.show();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "WIP", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "Should not reach this", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ListView lv2 = (ListView)rootView.findViewById(R.id.listView2);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/StanGo25/LockerAutomation"));
                startActivity(i);
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.blu));
//        }

        return rootView;
    }

}