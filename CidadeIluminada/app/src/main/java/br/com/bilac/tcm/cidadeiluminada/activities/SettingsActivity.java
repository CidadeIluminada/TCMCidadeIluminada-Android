package br.com.bilac.tcm.cidadeiluminada.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;

import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;

public class SettingsActivity extends ActionBarActivity {

    SettingsFragment settingsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsFragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, settingsFragment)
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.pref_general);
            SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);
            findPreference(Constants.CEP_PREFERENCE_KEY)
                    .setSummary(sharedPreferences.getString(Constants.CEP_PREFERENCE_KEY, ""));
            findPreference(Constants.NOME_PREFERENCE_KEY)
                    .setSummary(sharedPreferences.getString(Constants.NOME_PREFERENCE_KEY, ""));
            findPreference(Constants.EMAIL_PREFERENCE_KEY)
                    .setSummary(sharedPreferences.getString(Constants.EMAIL_PREFERENCE_KEY, ""));
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(Constants.CEP_PREFERENCE_KEY) ||
                    key.equals(Constants.NOME_PREFERENCE_KEY) ||
                    key.equals(Constants.EMAIL_PREFERENCE_KEY)) {
                findPreference(key).setSummary(sharedPreferences.getString(key, ""));
            }
        }
    }
}
