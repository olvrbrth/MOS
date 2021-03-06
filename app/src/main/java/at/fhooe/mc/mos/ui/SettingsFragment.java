package at.fhooe.mc.mos.ui;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import at.fhooe.mc.mos.R;

/**
 * Fragment which shows and can edit basic settings for this app.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
