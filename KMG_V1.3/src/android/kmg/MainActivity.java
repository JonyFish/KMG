package android.kmg;

import android.app.*;
import android.content.*;
import android.content.SharedPreferences.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.TabHost.*;
import hxm.tool.*;
import java.io.*;

public class MainActivity extends TabActivity
{
	TabHost tabhost;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);	

		tabhost = getTabHost();
		TabSpec tab1=tabhost.newTabSpec("tab1")
			.setIndicator("控制中心")
			.setContent(new Intent(MainActivity.this, Tab1.class));
		tabhost.addTab(tab1);
		tabhost.setCurrentTab(0);

		TabSpec tab2=tabhost.newTabSpec("tab2")
			.setIndicator("模式配置")
			.setContent(new Intent(MainActivity.this, Tab2.class));
		tabhost.addTab(tab2);

		TabSpec tab3=tabhost.newTabSpec("tab3")
			.setIndicator("帮助&设置")
			.setContent(new Intent(MainActivity.this, Tab3.class));
		tabhost.addTab(tab3);


		//***********************签名验证法************************
		Hxx hh=new Hxx();
		String sf=hh.getappSign(this, "android.kmg");
		//把签名信息加密为md5值
		Hjm h=new Hjm();
		String j=h.getSHA(sf);

		if (j.equals("a722dce40fda0f0e39ef33425e3b703b8dbe68cc") == false)
		{	
			finish();
			System.exit(0);
			android.os.Process.killProcess(android.os.Process.myPid());
		}
		//***********************签名验证法************************

		SharedPreferences sp = this.getSharedPreferences("KMG1.3", 1);
		int count = sp.getInt("count", 0);

		//判断程序与第几次运行，如果是第一次运行则初始化
		if (count == 0)
		{
			try
			{
				copyAssetFileToFiles(this, "start.sh");
				copyAssetFileToFiles(this, "stop.sh");
				copyAssetFileToFiles(this, "tiny.conf");
				copyAssetFileToFiles(this, "tiny");
			}
			catch (IOException e)
			{
				Toast.makeText(this, "初始化失败：" + e.toString(), Toast.LENGTH_SHORT).show();
			}

			sp.edit().putBoolean("PowerBoot", true).commit();
		}  
		Editor editor = sp.edit();  
		//存入数据    
		editor.putInt("count", ++count);  
		//提交修改    
		editor.commit();
	}

	public static void copyAssetFileToFiles(Context context, String filename)
	throws IOException
	{
		InputStream is = context.getAssets().open(filename);
		byte[] buffer = new byte[is.available()];
		is.read(buffer);
		is.close();

		File of = new File(context.getFilesDir() + "/" + filename);
		of.createNewFile();
		FileOutputStream os = new FileOutputStream(of);
		os.write(buffer);
		os.close();
		//修改权限
		String absolutePath = context.getApplicationContext().getFilesDir().getAbsolutePath();
		ShellUtils.execCommand("chmod 777 " + absolutePath + "/" + filename, false, false);
	}


	public void button1(View view)
	{
		((Button) findViewById(R.id.button1)).setBackgroundColor(Color.parseColor("#f0303030"));
		((Button) findViewById(R.id.button2)).setBackgroundColor(Color.parseColor("#b0303030"));
		((Button) findViewById(R.id.button3)).setBackgroundColor(Color.parseColor("#b0303030"));
		((Button) findViewById(R.id.button1)).setTextColor(Color.parseColor("#18B4ED"));
		((Button) findViewById(R.id.button2)).setTextColor(Color.parseColor("#fffafafa"));
		((Button) findViewById(R.id.button3)).setTextColor(Color.parseColor("#fffafafa"));
		tabhost.setCurrentTab(0);
	}

	public void button2(View view)
	{
		((Button) findViewById(R.id.button2)).setBackgroundColor(Color.parseColor("#f0303030"));
		((Button) findViewById(R.id.button1)).setBackgroundColor(Color.parseColor("#b0303030"));
		((Button) findViewById(R.id.button3)).setBackgroundColor(Color.parseColor("#b0303030"));
		((Button) findViewById(R.id.button2)).setTextColor(Color.parseColor("#18B4ED"));
		((Button) findViewById(R.id.button1)).setTextColor(Color.parseColor("#fffafafa"));
		((Button) findViewById(R.id.button3)).setTextColor(Color.parseColor("#fffafafa"));
		tabhost.setCurrentTab(1);
	}

	public void button3(View view)
	{
		((Button) findViewById(R.id.button3)).setBackgroundColor(Color.parseColor("#f0303030"));
		((Button) findViewById(R.id.button2)).setBackgroundColor(Color.parseColor("#b0303030"));
		((Button) findViewById(R.id.button1)).setBackgroundColor(Color.parseColor("#b0303030"));
		((Button) findViewById(R.id.button3)).setTextColor(Color.parseColor("#18B4ED"));
		((Button) findViewById(R.id.button2)).setTextColor(Color.parseColor("#fffafafa"));
		((Button) findViewById(R.id.button1)).setTextColor(Color.parseColor("#fffafafa"));
		tabhost.setCurrentTab(2);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			MainActivity.this.finish();
		}

		return super.onKeyDown(keyCode, event);
	}

}
