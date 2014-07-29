package intexsoft.by.crittercismapi.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Евгений on 29.07.2014.
 */
public class Receiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "30 секунд", Toast.LENGTH_LONG).show();
    }
}
