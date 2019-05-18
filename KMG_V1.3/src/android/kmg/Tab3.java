package android.kmg;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.CompoundButton.*;

import android.view.View.OnClickListener;

public class Tab3 extends Activity
{
	private TextView help,yzy,qq,about;
	private CheckBox NoRoot,PowerBoot,NetBoot;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab3);

		help = (TextView) findViewById(R.id.tab3TextView1);
		yzy = (TextView) findViewById(R.id.tab3TextView2);
		qq = (TextView) findViewById(R.id.tab3TextView3);
		about = (TextView) findViewById(R.id.tab3TextView4);
		NoRoot = (CheckBox) findViewById(R.id.NoRoot);
		PowerBoot = (CheckBox) findViewById(R.id.PowerBoot);
		NetBoot = (CheckBox) findViewById(R.id.NetBoot);


		final SharedPreferences sp = getSharedPreferences("KMG1.3", MODE_WORLD_WRITEABLE);
        boolean z = sp.getBoolean("NoRoot", false);
        boolean z2 = sp.getBoolean("PowerBoot", false);
        boolean z3 = sp.getBoolean("NetBoot", false);

		if (z)
		{
			NoRoot.setChecked(true);
		}
		if (z2)
		{
			PowerBoot.setChecked(true);
		}
		if (z3)
		{
			NetBoot.setChecked(true) ;
		}

		//监听免ROOT启动按钮事件
		NoRoot.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if (NoRoot.isChecked())
					{

						sp.edit().putBoolean("NoRoot", true).commit();

					}
					else
					{

						sp.edit().putBoolean("NoRoot", false).commit();

					}

				}
			});


		//监听开机自启按钮事件
		PowerBoot.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if (PowerBoot.isChecked())
					{

						sp.edit().putBoolean("PowerBoot", true).commit();

					}
					else
					{

						sp.edit().putBoolean("PowerBoot", false).commit();

					}

				}
			});

		//监听网络自启按钮事件
		NetBoot.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if (NetBoot.isChecked())
					{

						sp.edit().putBoolean("NetBoot", true).commit();

					}
					else
					{

						sp.edit().putBoolean("NetBoot", false).commit();
						stopService(new Intent(Tab3.this, KmgService.class));

					}

				}
			});


		String str = "S01H5Lqk5rWB576k77yaNTQzNzUzNzk5";
		String str2 = "5byA5Y+R6ICF77ya5L2Z5Lit6ZKwKOW5v+S4nCnvvIzmoL7puL/oj7Io5bm/6KW/KQ==";


		help.setText("（1）如果你的手机无法获取ROOT权限，请勾选下面的［免ROOT启动］" +
		             "\n\n（2）新建一个网络接入点参数如下：" +
					 "\n\n名称：任意" +
					 "\nAPN：联通为3gwap或者3gnet，移动为cmwap，电信为ctwap" +
					 "\n代理：127.0.0.1" +
					 "\n端口：65080" +
					 "\n\n【注意保存并选中新建的这个接入点】" +
					 "\n\n（3）先将“模式配置”中的输入框清空，再将Tiny模式粘贴在输入框中并保存！" +
					 "\n\n（4）最后在“控制中心”点击启动服务，如果打开浏览器可以浏览网页则证明KMG的核心和模式配置成功运行！");

		yzy.setText(new String(Base64.decode(str.getBytes(), Base64.DEFAULT)) + "\n加入KMG交流群可获取详细教程和全国各地模式哦！");
		about.setText("【关于软件】\n版本：1.3" + "\n" +
					  new String(Base64.decode(str2.getBytes(), Base64.DEFAULT)) +
					  "\n本软件仅供网络测试之用，请勿用于非法用途！使用本软件产生的任何后果均与开发者无关！");

		qq.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					joinQQGroup("5-jYrWEhPITFgMJJQbd9jwsAeBeFrREF");

				}
			});
	}

	public boolean joinQQGroup(String key)
	{
		Intent intent = new Intent();
		intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		try
		{
			startActivity(intent);
			return true;
		}
		catch (Exception e)
		{
			Toast.makeText(this, "未安装手Q或安装的版本不支持", Toast.LENGTH_LONG).show();
			return false;
		}
	}

}
