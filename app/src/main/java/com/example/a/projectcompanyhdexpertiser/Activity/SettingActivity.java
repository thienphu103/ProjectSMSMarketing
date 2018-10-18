package com.example.a.projectcompanyhdexpertiser.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.example.a.projectcompanyhdexpertiser.R;

public class SettingActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
    @Override
    protected void onResume(){
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
        updateTimePreference("pref_edittext");
        updateHostPreference("pref_list");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        updateTimePreference(key);
        updateHostPreference(key);
    }

    private void updateTimePreference(String key){
        if (key.equals("pref_edittext")){
            Preference preference = findPreference(key);
            if (preference instanceof EditTextPreference){
                EditTextPreference editTextPreference =  (EditTextPreference)preference;
                if (editTextPreference.getText().trim().length() > 0){
                    editTextPreference.setSummary("Thời gian:  " + editTextPreference.getText());
                }else{
                    editTextPreference.setSummary("Thời gian gủi tin nhắn");
                }
            }
        }
    }
    private void updateHostPreference(String key){
        if (key.equals("pref_list")){
            Preference preference = findPreference(key);
            if (preference instanceof ListPreference){
                ListPreference listPreference =  (ListPreference) preference;
                if (listPreference.getEntry().length() > 0){
                    listPreference.setSummary("Server:  " + listPreference.getEntry());
                }else{
                    listPreference.setSummary("Server gủi tin nhắn");
                }
            }
        }
    }
}
