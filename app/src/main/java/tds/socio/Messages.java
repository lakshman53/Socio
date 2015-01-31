package tds.socio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Messages extends Activity {
	ListView msgList;
	ArrayList<MessageDetails> details;
	AdapterView.AdapterContextMenuInfo info;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			
			msgList = (ListView) findViewById(R.id.MessageList);
			
			details = new ArrayList<MessageDetails>();
			    
			MessageDetails Detail;
			Detail = new MessageDetails();
			Detail.setIcon(R.drawable.ic_launcher);
			Detail.setName("Bob");
			Detail.setSub("Dinner");
			Detail.setDesc("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla auctor.");
			Detail.setTime("12/12/2012 12:12");
			details.add(Detail);
			
			Detail = new MessageDetails();
			Detail.setIcon(R.drawable.ic_launcher);
			Detail.setName("Rob");
			Detail.setSub("Party");
			Detail.setDesc("Dolor sit amet, consectetur adipiscing elit. Nulla auctor.");
			Detail.setTime("13/12/2012 10:12");
			details.add(Detail);
			
			Detail = new MessageDetails();
			Detail.setIcon(R.drawable.ic_launcher);
			Detail.setName("Mike");
			Detail.setSub("Mail");
			Detail.setDesc("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
			Detail.setTime("13/12/2012 02:12");
			details.add(Detail);		
			
			msgList.setAdapter(new CustomAdapter(details , this));

			registerForContextMenu(msgList);
			
			msgList.setOnItemClickListener(new OnItemClickListener() {
				   public void onItemClick(AdapterView<?> a, View v, int position, long id) {
					   // System.out.println("Name: "+details.get(position).getName());
					   String s =(String) ((TextView) v.findViewById(R.id.From)).getText();
					   Toast.makeText(Messages.this, s, Toast.LENGTH_LONG).show();
				   }
		   });	
}
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			super.onCreateContextMenu(menu, v, menuInfo);		
					
				info = (AdapterContextMenuInfo) menuInfo;
	//			// System.out.println("Reached");
				 
				int id = (int) msgList.getAdapter().getItemId(info.position);			
	//			// System.out.println("id "+msgList.getAdapter().getItem(id));
										
	//			// System.out.println("Msg"+details.get(info.position).getName());
				
				menu.setHeaderTitle(details.get(info.position).getName());		
				menu.add(Menu.NONE, v.getId(), 0, "Reply");
				menu.add(Menu.NONE, v.getId(), 0, "Reply All");
				menu.add(Menu.NONE, v.getId(), 0, "Forward");
				
				// System.out.println("ID "+info.id);
	        	// System.out.println("Pos "+info.position);
	        	// System.out.println("Info "+info.toString());
		}
		
		@Override
	    public boolean onContextItemSelected(MenuItem item) {
	        if (item.getTitle() == "Reply") {
	  //      	// System.out.println("Id "+info.id);
	  //      	// System.out.println("Pos "+info.position);
	  //      	// System.out.println("info "+info.toString());
	        	}  
	        else if (item.getTitle() == "Reply All") {
	        	// System.out.println("Id "+info.id);
	        	// System.out.println("Pos "+info.position);
	        	// System.out.println("info "+info.toString());
	        	}  
	        else if (item.getTitle() == "Reply All") {
	        	// System.out.println("Id "+info.id);
	        	// System.out.println("Pos "+info.position);
	        	// System.out.println("info "+info.toString());
	        }
	        else 	{
	        	return false;
	        	}  
	    return true;  
	    }
		
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();			
	}
		
		public class CustomAdapter extends BaseAdapter {

		    private ArrayList<MessageDetails> _data;
		    Context _c;
		    
		    CustomAdapter (ArrayList<MessageDetails> data, Context c){
		        _data = data;
		        _c = c;
		    }
		   
		    public int getCount() {
		        // TODO Auto-generated method stub
		        return _data.size();
		    }
		    
		    public Object getItem(int position) {
		        // TODO Auto-generated method stub
		        return _data.get(position);
		    }
		
		    public long getItemId(int position) {
		        // TODO Auto-generated method stub
		        return position;
		    }
		   
		    public View getView(final int position, View convertView, ViewGroup parent) {
		        // TODO Auto-generated method stub
		         View v = convertView;
		         if (v == null) 
		         {
		            LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		            v = vi.inflate(R.layout.list_item_message, null);
		         }

		           ImageView image = (ImageView) v.findViewById(R.id.icon);
		           TextView fromView = (TextView)v.findViewById(R.id.From);
		           TextView subView = (TextView)v.findViewById(R.id.subject);
		           TextView descView = (TextView)v.findViewById(R.id.description);
		           TextView timeView = (TextView)v.findViewById(R.id.time);

		           MessageDetails msg = _data.get(position);
		           image.setImageResource(msg.icon);
		           fromView.setText(msg.from);
		           subView.setText("Subject: "+msg.sub);
		           descView.setText(msg.desc);
		           timeView.setText(msg.time);		              		
		        
		           image.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder adb=new AlertDialog.Builder(Messages.this);
			        adb.setMessage("Add To Contacts?");
			        adb.setNegativeButton("Cancel", null);
			        final int selectedid = position;
			        final String itemname= (String) _data.get(position).getName();

			        adb.setPositiveButton("OK", new AlertDialog.OnClickListener() {
			        	public void onClick(DialogInterface dialog, int which) {
			        				
			        		// System.out.println("Select " + selectedid);
			        		// System.out.println("Project " + itemname);
			        		
			        		Bundle b = new Bundle();
			    			b.putString("project", itemname);
			    			Intent createTask = new Intent("com.loginworks.tasktrek.CREATETASK");
			    			createTask.putExtras(b);
			    			startActivity(createTask);    	
			        		}});
			        
			        adb.show();      
				}    						
			});
		        
		        return v; 
		}
		}

		public class MessageDetails {
		    int icon ;
		    String from;
		    String sub;
		    String desc;
		    String time;

		    public String getName() {
		        return from;
		    }

		    public void setName(String from) {
		        this.from = from;
		    }

		    public String getSub() {
		        return sub;
		    }

		    public void setSub(String sub) {
		        this.sub = sub;
		    }
		    
		    public int getIcon() {
		        return icon;
		    }

		    public void setIcon(int icon) {
		        this.icon = icon;
		    }
		    
		    public String getTime() {
		        return time;
		    }

		    public void setTime(String time) {
		        this.time = time;
		    }
		    
		    public String getDesc() {
		        return desc;
		    }

		    public void setDesc(String desc) {
		        this.desc = desc;
		    }
		}
}
