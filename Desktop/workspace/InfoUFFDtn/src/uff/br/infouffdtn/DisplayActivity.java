package uff.br.infouffdtn;

import java.util.LinkedList;

import uff.br.infouffdtn.db.ContentsDatabase;


import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

public class DisplayActivity extends ListActivity 
{

	 public void onCreate(Bundle icicle)
	 {	 
	    super.onCreate(icicle);
	    
	    try
	    {
	    String[] values =  ContentsDatabase.readAllArchivesNames(this);
	    // Use your own layout
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        R.layout.displayactivitymenu, R.id.label, values);
	    setListAdapter(adapter);
	    }
	    catch(Exception e)
	    {
	    	
	    }
	  }

	  @Override
	  protected void onListItemClick(ListView l, View v, int position, long id) 
	  {
	    String item = (String) getListAdapter().getItem(position);
	    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	  }

	  
	  
	 


}
