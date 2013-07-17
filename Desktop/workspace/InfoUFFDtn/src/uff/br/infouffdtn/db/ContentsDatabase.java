package uff.br.infouffdtn.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import android.app.Activity;
import android.content.Context;

public class ContentsDatabase extends Activity
{
	
	public void writeTest(String archive,String message,Context ctx) throws IOException
	{
		 try 
		 {
	            FileOutputStream fOut = ctx.openFileOutput(archive,Context.MODE_PRIVATE);
	            OutputStreamWriter osw = new OutputStreamWriter(fOut);
	            osw.write (message);
	            osw.flush ();
	            osw.close ();
	     }
		 catch (Exception e) 
	     {
	            e.printStackTrace();
	     }
	}
	public String readTest(String archive,Context ctx) throws FileNotFoundException
	{
	    String datax = "" ;
        try 
        {
            FileInputStream fIn = ctx.openFileInput (archive);
            InputStreamReader isr = new InputStreamReader(fIn) ;
            BufferedReader buffreader = new BufferedReader(isr) ;

            String readString = buffreader.readLine() ;
            while (readString != null) 
            {
                datax = datax + readString ;
                readString = buffreader.readLine();
            }

            isr.close();
        } 
        catch (IOException ioe ) 
        {
            ioe.printStackTrace();
        }
        return datax ;
	}

	
	
}
