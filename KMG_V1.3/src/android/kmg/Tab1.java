package android.kmg;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;

//开发者：余中钰。QQ:1396709648
public class Tab1 extends Activity
{
	private Button state,start,stop,setapn,setjb,ip,apn;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1);

		setjb = (Button) findViewById(R.id.tab1Button1);
		state = (Button) findViewById(R.id.tab1Button2);
		setapn = (Button) findViewById(R.id.tab1Button3);
		start = (Button) findViewById(R.id.tab1Button4);
		stop = (Button) findViewById(R.id.tab1Button5);
		apn = (Button) findViewById(R.id.button_page1_top1);
		ip = (Button) findViewById(R.id.button_page1_top2);

		//获取接入点和IP信息
		apn.setText(KernelCode.guip(this));
		ip.setText(KernelCode.iptext());


		state.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					Intent i=new Intent();
					i.setClass(Tab1.this, Web.class);
					startActivity(i);

				}
			});

		setjb.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					dialog();
				}
			});

		setapn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					Intent intent = new Intent(android.provider.Settings.ACTION_APN_SETTINGS);
					Tab1.this.startActivity(intent);
				}
			});

		start.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{

					KernelCode.Start(Tab1.this);
				}
			});

		stop.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{		
					KernelCode.Stop(Tab1.this);
					stopService(new Intent(Tab1.this, KmgService.class));

				}
			});

	}

	public void dialog()
	{
		View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.setft, null);
		final EditText editText = (EditText) inflate.findViewById(R.id.jbno);
		final EditText editText2 = (EditText) inflate.findViewById(R.id.jboff);
		editText.setText(KernelCode.readFile(this, "start.sh"));
		editText2.setText(KernelCode.readFile(this, "stop.sh"));

		//得到构建对象
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("脚本规则");
		builder.setView(inflate);
		builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
				{
					KernelCode.writeFile(Tab1.this, "start.sh", editText.getText().toString());
					KernelCode.writeFile(Tab1.this, "stop.sh", editText2.getText().toString());
                }
            });
		builder.setNeutralButton("AppUid", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton)
				{
					aleruid();
				}
			});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
				{

                }
            });
		builder.create().show();
	}

	private static String Kmguid(Context context, String str)
	{
        try
		{
            return new StringBuffer().append("").append(context.getPackageManager().getApplicationInfo(str, 1).uid).toString();
        }
		catch (Exception e)
		{
            e.printStackTrace();
            return null;
        }
    }

	public void aleruid()
	{
        View inflate = ((LayoutInflater) this.getSystemService("layout_inflater")).inflate(R.layout.appuid, null);
        ListView listView = (ListView) inflate.findViewById(R.id.uidlist);
        List arrayList = new ArrayList();

        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", null);
        intent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        List arrayList2 = new ArrayList();
        for (ResolveInfo resolveInfo : queryIntentActivities)
		{
            String stringBuffer = new StringBuffer().append(new StringBuffer().append(resolveInfo.activityInfo.applicationInfo.loadLabel(packageManager).toString()).append(" UID=").toString()).append(Kmguid(this, resolveInfo.activityInfo.packageName)).toString();
            arrayList.add(stringBuffer);
            Map hashMap = new HashMap();
            hashMap.put("uidtext", stringBuffer);
            arrayList2.add(hashMap);
        }
        listView.setAdapter(new SimpleAdapter(this, arrayList2, R.layout.uidview, new String[]{"uidtext"}, new int[]{R.id.uidtext}));
        //得到构建对象
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("应用UID浏览");
		builder.setView(inflate);
		builder.create().show();
    }


}
