package intexsoft.by.crittercismapi.ui.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import org.androidannotations.annotations.EFragment;

import intexsoft.by.crittercismapi.R;

/**
 * Created by Евгений on 30.07.2014.
 */
@EFragment
public class SettingsFragment extends PreferenceFragment
{
    private static final String TAG = SettingsFragment.class.getSimpleName();
    private static final String KEY_APP_VERSION = "app_version_key";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        Preference appVersionPreference = getPreferenceManager() == null ? null : getPreferenceManager().findPreference(KEY_APP_VERSION);
        if (appVersionPreference != null)
        {
            appVersionPreference.setSummary(getString(R.string.setting_hint_version) + getAppVersion());
        }
    }

    private String getAppVersion()
    {
        String versionName = "";
        if (isAdded())
        {
            PackageManager pm = getActivity().getPackageManager();
            if (pm != null)
            {
                try
                {
                    versionName = pm.getPackageInfo(getActivity().getPackageName(), 0).versionName;
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    Log.d(TAG, "Error while get app version.");
                }
            }
        }
        return versionName;
    }
}
