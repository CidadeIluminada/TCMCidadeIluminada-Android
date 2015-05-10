package br.com.bilac.tcm.cidadeiluminada.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;

public class SettingsActivity extends Activity {

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

            Preference cepPref = findPreference(Constants.CEP_PREFERENCE_KEY);
            cepPref.setSummary(sharedPreferences.getString(Constants.CEP_PREFERENCE_KEY, ""));
            cepPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    return ((String) newValue).matches("^\\d{5}-?\\d{3}$");
                }
            });

            Preference nomePref = findPreference(Constants.NOME_PREFERENCE_KEY);
            nomePref.setSummary(sharedPreferences.getString(Constants.NOME_PREFERENCE_KEY, ""));
            nomePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    return !((String) newValue).isEmpty();
                }
            });

            Preference emailPref = findPreference(Constants.EMAIL_PREFERENCE_KEY);
            emailPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    return ((String) newValue).matches("^.+@([^.@][^@]+)$");
                }
            });
            emailPref.setSummary(sharedPreferences.getString(Constants.EMAIL_PREFERENCE_KEY, ""));
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
