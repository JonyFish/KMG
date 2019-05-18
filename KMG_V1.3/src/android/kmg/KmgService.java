package android.kmg;

import android.app.*;
import android.content.*;
import android.os.*;

public class KmgService extends Service
{

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		KernelCode.Start(this);
	}
	

	@Override
	public void onStart(Intent intent, int startId)
	{
		// TODO: Implement this method
		super.onStart(intent, startId);
		
		KernelCode.Start(this);
	}


	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	
}
