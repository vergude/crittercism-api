package intexsoft.by.crittercismapi.ui.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import intexsoft.by.crittercismapi.ui.fragment.SettingsFragment;

/**
 * Created by Евгений on 30.07.2014.
 */

public class SettingsActivity extends PreferenceActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	}
}
