package com.google.mcommerce.sample.android.chapter07.preferences;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.google.mcommerce.sample.android.R;

public class PrefsActivity extends PreferenceActivity {

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.toplevel, target);
	}
	
    public static class Frag1 extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.sound_preferences);
        }
    }

}
