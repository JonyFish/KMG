package android.kmg;

import android.app.*;
import android.content.*;
import android.kmg.ShellUtils.*;
import android.net.*;
import android.os.*;
import android.widget.*;
import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.http.util.*;

import android.net.Proxy;

public class KernelCode
{


//本源码仅供内部使用，切勿传播！
//等级：绝密！
//开发者:余中钰
//联系QQ:1396709648


	public static void Start(final Context context)
	{	
		//启动一个线程
		new Thread(){
			@Override
			public void run()
			{
				//在这里执行耗时操作

				SharedPreferences sp = context.getSharedPreferences("KMG1.3", 0);
				boolean z = sp.getBoolean("NoRoot", false);
				if (z)
				{
					//无需ROOT启动
					String[] com = new String[2];
					com[0] = "/data/data/android.kmg/files/tiny -c /data/data/android.kmg/files/tiny.conf -p /data/data/android.kmg/files/tiny.pid";
					com[1] = "chmod 777 /data/data/android.kmg/files/tiny.pid";
					ShellUtils.execCommand(com, false, true);
					final CommandResult test = ShellUtils.execCommand("ps | grep -i '[T]iny'", false, true);
					new Handler(context.getMainLooper()).post(new Runnable() {
							@Override
							public void run()
							{
								//在这里执行你要想的操作,比如直接在这里更新UI
								if (test.errorMsg.length() < 5 && test.successMsg.length() > 10)
								{

									Toast.makeText(context, " √ 已运行", Toast.LENGTH_SHORT).show();
									tz(context, "KMG正在后台运行（免ROOT）");
								}
								else
								{
									Toast.makeText(context, " X 未运行！ 可能是模式配置错误或软件不兼容你的手机", Toast.LENGTH_LONG).show();
								}

							}
						});

				}
				else
				{
					//需要ROOT权限启动
					String[] com = new String[3];
					com[0] = "/data/data/android.kmg/files/tiny -c /data/data/android.kmg/files/tiny.conf -p /data/data/android.kmg/files/tiny.pid";
					com[1] = "chmod 777 /data/data/android.kmg/files/tiny.pid";
					com[2] = "/data/data/android.kmg/files/start.sh";
					final CommandResult result = ShellUtils.execCommand(com, true, true);
					final CommandResult test = ShellUtils.execCommand("ps | grep -i '[T]iny'", false, true);
					new Handler(context.getMainLooper()).post(new Runnable() {
							@Override
							public void run()
							{
								//在这里执行你要想的操作,比如直接在这里更新UI

								if (test.errorMsg.length() < 5 && test.successMsg.length() > 10)
								{

									Toast.makeText(context, " √ 已运行", Toast.LENGTH_SHORT).show();
									tz(context, "KMG正在后台运行");
								}
								else
								{

									if (result.errorMsg.indexOf("ermission") != -1)
									{
										Toast.makeText(context, " X 未运行！ 无ROOT权限", Toast.LENGTH_LONG).show();
									}
									else
									{
										Toast.makeText(context, " X 未运行！ 可能是模式配置错误或软件不兼容你的手机", Toast.LENGTH_LONG).show();
									}
								}


							}
						});
				}


			}

		}.start();



	}

	public static void Stop(final Context context)
	{	
		//启动一个线程
		new Thread(){
			@Override
			public void run()
			{
				//在这里执行耗时操作

				SharedPreferences sp = context.getSharedPreferences("KMG1.3", 0);
				boolean z = sp.getBoolean("NoRoot", false);
				if (z)
				{
					//无需ROOT关闭
					String[] com = new String[1];
					com[0] = "/data/data/android.kmg/files/tiny -k /data/data/android.kmg/files/tiny.pid";
					final CommandResult result = ShellUtils.execCommand(com, false, true);
					final CommandResult test = ShellUtils.execCommand("ps | grep -i '[T]iny'", false, true);

					new Handler(context.getMainLooper()).post(new Runnable() {

							public void run()
							{
								//在这里执行你要想的操作,比如直接在这里更新UI
								if (test.errorMsg.length() < 5 && test.successMsg.length() > 10)
								{
									if (result.errorMsg.indexOf("not permit") != -1)
									{
										Toast.makeText(context, " 无法关闭！ Tiny核心被另一个用户占用", Toast.LENGTH_LONG).show();
									}
									else
									{
										Toast.makeText(context, " 无法关闭！ 如需关闭请重启手机", Toast.LENGTH_LONG).show();}


								}
								else
								{
									Toast.makeText(context, " 已关闭！", Toast.LENGTH_SHORT).show();

									//从系统服务中获得通知管理器
									NotificationManager nm=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
									nm.cancel(1);
								}

							}
						});
				}
				else
				{
					//需要ROOT权限关闭
					String[] com = new String[2];
					com[0] = "/data/data/android.kmg/files/tiny -k /data/data/android.kmg/files/tiny.pid";
					com[1] = "/data/data/android.kmg/files/stop.sh";
					final CommandResult result = ShellUtils.execCommand(com, true, true);
					final CommandResult test = ShellUtils.execCommand("ps | grep -i '[T]iny'", false, true);
					new Handler(context.getMainLooper()).post(new Runnable() {
							@Override
							public void run()
							{

								//在这里执行你要想的操作,比如直接在这里更新UI
								if (test.errorMsg.length() < 5 && test.successMsg.length() > 10)
								{

									if (result.errorMsg.indexOf("ermission") != -1)
									{
										Toast.makeText(context, " 无法关闭！ 无ROOT权限", Toast.LENGTH_LONG).show();
									}
									else
									{
										if (result.errorMsg.indexOf("not permit") != -1)
										{
											Toast.makeText(context, " 无法关闭！ Tiny核心被另一个用户占用", Toast.LENGTH_LONG).show();
										}
										else
										{
											Toast.makeText(context, " 无法关闭！ 如需关闭请重启手机", Toast.LENGTH_LONG).show();}
									}

								}
								else
								{
									Toast.makeText(context, " 已关闭！", Toast.LENGTH_SHORT).show();

									//从系统服务中获得通知管理器
									NotificationManager nm=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
									nm.cancel(1);
								}


							}
						});
				}
			}
		}.start();



	}



	public static void tz(Context context,  String a)
	{
		//从系统服务中获得通知管理器
		NotificationManager nm=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		//新建状态栏通知 
		Notification NF=new Notification(); 
		//设置通知在状态栏显示的图标 
		NF.icon = R.drawable.ic_launcher; 

		NF.flags = Notification.FLAG_ONGOING_EVENT;

		//通知消息与Intent关联
		Intent it=new Intent(context, MainActivity.class);
		PendingIntent pi=PendingIntent.getActivity(context, 100, it, PendingIntent.FLAG_CANCEL_CURRENT);

		//参数：this,标题，内容，跳转目的面页
		NF.setLatestEventInfo(context, "通知", a, pi);

		//发出状态栏通知 
		nm.notify(1, NF); 
	}

	public static String readFile(Context context, String fileName)
	{ 
		String res=""; 
		try
		{ 
			FileInputStream fin = context.openFileInput(fileName); 
			int length = fin.available(); 
			byte [] buffer = new byte[length]; 
			fin.read(buffer);     
			res = EncodingUtils.getString(buffer, "UTF-8"); 
			fin.close();     
		} 
		catch (Exception e)
		{ 
			return "读取/data/data/android.kmg/files/" + fileName + "配置文件失败：\n" + e.toString();
		}
		return res; 
	}

	public static void writeFile(Context context, String fileName, String writestr)
	{ 
		try
		{ 

			FileOutputStream fout =context.openFileOutput(fileName, 0); 
			byte [] bytes = writestr.getBytes(); 
			fout.write(bytes); 
			fout.close(); 
			//修改权限
			String absolutePath = context.getApplicationContext().getFilesDir().getAbsolutePath();
			ShellUtils.execCommand("chmod 777 " + absolutePath + "/" + fileName, false, false);
			Toast.makeText(context, "已保存！", Toast.LENGTH_SHORT).show();

		}
        catch (Exception e)
		{ 
			Toast.makeText(context, "保存失败：\n" + e.toString(), Toast.LENGTH_SHORT).show();
		}

	}


	//获取本地IP
	public static String getPsdnIp()
	{
        try
		{
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements())
			{
                Enumeration inetAddresses = ((NetworkInterface) networkInterfaces.nextElement()).getInetAddresses();
                while (inetAddresses.hasMoreElements())
				{
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address))
					{
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
		catch (Exception e)
		{
        }
        return "";
    }

	public static String apnhost()
	{
        return new StringBuffer().append(Proxy.getDefaultHost()).append("").toString();
    }

    public static String apnport()
	{
        return new StringBuffer().append(Proxy.getDefaultPort()).append("").toString();
    }

    public static String getApnType(Context context, int i)
	{
        try
		{
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            switch (i)
			{
                case 0:
                    return activeNetworkInfo.getExtraInfo();
                case 1:
                    return activeNetworkInfo.getTypeName();
                case 2:
                    return activeNetworkInfo.getSubtypeName();
                default:
                    return "";
            }
        }
		catch (Exception e)
		{
            return "";
        }
    }


	public static String guip(Context context)
	{
        return new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append("当前网络类型：").append(getApnType(context, 1)).toString()).append("\n").toString()).append("网络接入点名：").toString()).append(getApnType(context, 0)).toString()).append("").toString();
    }

    public static String iptext()
	{
        return new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append("系统代理IP ：").append(apnhost()).toString()).append(":").toString()).append(apnport()).toString()).append("\n").toString()).append("网关分配IP ：").toString()).append(getPsdnIp()).toString();
    }

}
