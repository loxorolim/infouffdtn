package uff.br.infouffdtn;

import de.tubs.ibr.dtn.ping.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
    private InfoService mService = null;
    private boolean mBound = false;
    //Teste do git numero 2!!!!!!!!
    //private EditText mTextEid = null;
    //private TextView mResult = null;
    private TextView editText;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setTitle("Info UFF DTN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main3);
        
        //mTextEid = (EditText)findViewById(R.id.editEid);
        editText = (TextView) findViewById(R.id.textView1);

        // assign an action to the ping button
        try
        {
        Button b = (Button)findViewById(R.id.button1);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ping();
            }
        });
        }
        catch(Exception e)
        {
        	
        }
    }
    
	@Override
	protected void onDestroy() {
        // unbind from service
        if (mBound) {
            // unbind from the PingService
            unbindService(mConnection);
            mBound = false;
        }
        
		super.onDestroy();
	}
	
    @Override
    protected void onPause() {
        super.onPause();
        
        // unregister the receiver for the DATA_UPDATED intent
        unregisterReceiver(mDataReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        if (!mBound) {
            // bind to the PingService
            bindService(new Intent(this, InfoService.class), mConnection, Context.BIND_AUTO_CREATE);
            mBound = true;
        }
        
        // register an receiver for DATA_UPDATED intent generated by the PingService
        IntentFilter filter = new IntentFilter(InfoService.DATA_UPDATED);
        registerReceiver(mDataReceiver, filter);
        
        // update the displayed result
        updateResult();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((InfoService.LocalBinder)service).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };
    
    private void ping() {
    	try
    	{
    		
    	
        Intent i = new Intent(this, InfoService.class);
        i.setAction(InfoService.PING_INTENT);
        //i.putExtra("destination", mTextEid.getText().toString());
        i.putExtra("destination", "dtn://androidRolim/echo");
        startService(i);
    	}
    	catch(Exception e)
    	{
    		
    	}
   }
    
    private void updateResult() {
        runOnUiThread(new Runnable() {
            public void run()
            {
                if (mService != null) {
                    //Resources res = getResources();
                    //String text = String.format(res.getString(R.string.resultText), mService.getPayload());
                    editText.setText(mService.getPayload());
                }
            }
        });
    }
    
    private BroadcastReceiver mDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("localeid")) {
                //mTextEid.setText( intent.getStringExtra("localeid") + "/echo" );
                
            } else {
                // update the displayed result
                updateResult();
            }
        }
    };
}
