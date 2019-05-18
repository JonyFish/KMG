package android.kmg;

import android.content.*;
import android.net.*;
import android.net.NetworkInfo.*;

public class AutoBoot extends BroadcastReceiver
{
    private void StartKmg(Context context)
	{
        context.startService(new Intent(context, KmgService.class));
    }



    @Override
    public void onReceive(Context context, Intent intent)
	{
		SharedPreferences sp = context.getSharedPreferences("KMG1.3", 1);
		
		boolean z2 = sp.getBoolean("PowerBoot", false);
		boolean z3 = sp.getBoolean("NetBoot", false);


        if (!intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
		{
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            State state = connectivityManager.getNetworkInfo(1).getState();
            State state2 = connectivityManager.getNetworkInfo(0).getState();
            if (state == null || state2 == null || State.CONNECTED == state || State.CONNECTED != state2)
			{
                if ((state == null || state2 == null || State.CONNECTED == state || State.CONNECTED == state2) && state != null && State.CONNECTED == state && z2)
				{
					//关闭
					context.stopService(new Intent(context, KmgService.class));
                }
            }
			else if (z3)
			{
                StartKmg(context);
            }
        }
		else if (z2)
		{
            StartKmg(context);
        }
    }
}

