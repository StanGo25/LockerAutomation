package ca.goldenphoenicks.lockerauto;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Stanley on 12/10/17.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}