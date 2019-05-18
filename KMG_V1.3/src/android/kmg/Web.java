package android.kmg;

import android.app.*;
import android.os.*;
import android.view.*;
import android.webkit.*;

public class Web extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);

		WebView webView = (WebView) this.findViewById(R.id.webview);
		webView.loadUrl("http://tiny.cc");
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Web.this.finish();
		}

		return super.onKeyDown(keyCode, event);
	}
}
