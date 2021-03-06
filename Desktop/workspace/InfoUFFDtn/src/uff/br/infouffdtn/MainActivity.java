package uff.br.infouffdtn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.tubs.ibr.dtn.api.GroupEndpoint;
import uff.br.infouffdtn.R;
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
import uff.br.infouffdtn.db.*;

public class MainActivity extends Activity
{
	
    private InfoService mService = null;
    private boolean mBound = false;

    //Teste do git numero 3 agora é o pull!!!!!!!!
    //private EditText mTextEid = null;
    //private TextView mResult = null;
    int n = 0;
    private TextView editText;
    private TextView editText2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setTitle("Info UFF DTN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main3);
        //mTextEid = (EditText)findViewById(R.id.editEid);
        editText = (TextView) findViewById(R.id.textView1);
        editText2 = (TextView) findViewById(R.id.textView2);
        
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
        Button b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
					save();
				} 
                catch (IOException e)
                {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        Button b3 = (Button)findViewById(R.id.button3);
        b3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                try 
                {
					ler();
				} 
                catch (FileNotFoundException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        Button b4 = (Button)findViewById(R.id.button4);
        b4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                	Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                	startActivity(intent);         
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
        //IntentFilter filter = new IntentFilter(InfoService.DATA_UPDATED);
        //registerReceiver(mDataReceiver, filter);
        IntentFilter filter = new IntentFilter(InfoService.PAYLOAD_UPDATED);
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
        //i.putExtra("destination", "dtn://androidRolim/example-app");
        
        //i.putExtra("destination", "dtn://broadcast.dtn/ping/echo");
        
        startService(i);
         i = new Intent(this, InfoService.class);
       // i.setAction(InfoService.PING_INTENT);
        
        //i.putExtra("destination", mTextEid.getText().toString());
       // i.putExtra("destination", "dtn://androidRolim");
     //   startService(i);
    	}
    	catch(Exception e)
    	{
    		
    	}
   }
    private void save() throws IOException
    {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	Date date = new Date();
    	String d = dateFormat.format(date);  
    	
    	Content teste = new Content("Arquivo "+ String.valueOf(n++),d,"Mensagem 1 alow alow ! Testando a mensagem 1 é isso ae!");
    	Content teste2 = new Content("Arquivo" + String.valueOf(n++),d,"Mensagem 2 AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHEEEEEEE");
    	Content teste3 = new Content("Arquivo"+ String.valueOf(n++),d,"Mensagem 3 TREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEES");
    	ContentsDatabase.writeTest(teste,this);
    	ContentsDatabase.writeTest(teste2,this);
    	ContentsDatabase.writeTest(teste3,this);
    }
    private void ler() throws FileNotFoundException 
    {
    	ContentsDatabase.deleteAllArchives(this);
    }
    
    private void updateResult() {
        runOnUiThread(new Runnable() {
            public void run()
            {
                if (mService != null) {
                    //Resources res = getResources();
                    //String text = String.format(res.getString(R.string.resultText), mService.getPayload());
                    //editText.setText(mService.getPayload());
                }
            }
        });
    }
    
    private BroadcastReceiver mDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*if (intent.hasExtra("localeid")) {
                //mTextEid.setText( intent.getStringExtra("localeid") + "/echo" );
                
            } else {
                // update the displayed result
                updateResult();
            }*/
        	if (intent.hasExtra("payload")) {
        		editText.setText(intent.getStringExtra("payload"));
            
        	} else {
            // update the displayed result
            updateResult();
        	}
        }
    };
}
