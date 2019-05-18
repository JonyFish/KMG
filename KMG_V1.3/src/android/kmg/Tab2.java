package android.kmg;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;


public class Tab2 extends Activity
{
	private EditText conf;
	private TextView ok;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2);
		conf = (EditText) findViewById(R.id.tab2EditText1);
		ok = (TextView) findViewById(R.id.tab2TextView2);

		conf.setText(KernelCode.readFile(this, "tiny.conf"));

		ok.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method		
					KernelCode.writeFile(Tab2.this, "tiny.conf", conf.getText().toString());

				}
			});
	}

	//“清空”按钮事件
	public void clear(View view)
	{
		conf.setText("");
	}

}
