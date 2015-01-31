package tds.socio;

import android.app.Activity;
import android.content.Context;
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
import java.util.List;

public class Messages extends Activity {
	ListView msgList;
	ArrayList<MessageDetails> details;
	AdapterView.AdapterContextMenuInfo info;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			
			msgList = (ListView) findViewById(R.id.MessageList);
			
			details = new ArrayList<MessageDetails>();

            List<Offers> offers;
            offers = Offers.listAll(Offers.class);

            MessageDetails Detail;

            Detail = new MessageDetails();

            int offerSize = offers.size();



//            for (int i=0; i<offerSize; i=i+1) {
//
//                Detail.setIcon(offers.get(i).getIcon());
//                Detail.setName(offers.get(i).getSender());
//                Detail.setSub(offers.get(i).getSubject());
//                Detail.setDesc(offers.get(i).getDescription());
//                Detail.setTime(offers.get(i).getReceivedTime().toString());
//                details.add(Detail);
//
//            }


            Detail.setIcon(offers.get(0).getIcon());
            Detail.setName(offers.get(0).getSender());
            Detail.setSub(offers.get(0).getSubject());
            Detail.setDesc(offers.get(0).getDescription());
            Detail.setTime(offers.get(0).getReceivedTime().toString());
            details.add(Detail);

            Detail.setIcon(offers.get(1).getIcon());
            Detail.setName(offers.get(1).getSender());
            Detail.setSub(offers.get(1).getSubject());
            Detail.setDesc(offers.get(1).getDescription());
            Detail.setTime(offers.get(1).getReceivedTime().toString());
            details.add(Detail);

            Detail.setIcon(offers.get(2).getIcon());
            Detail.setName(offers.get(2).getSender());
            Detail.setSub(offers.get(2).getSubject());
            Detail.setDesc(offers.get(2).getDescription());
            Detail.setTime(offers.get(2).getReceivedTime().toString());
            details.add(Detail);

            msgList.setAdapter(new CustomAdapter(details , this));
			registerForContextMenu(msgList);
			msgList.setOnItemClickListener(new OnItemClickListener() {
				   public void onItemClick(AdapterView<?> a, View v, int position, long id) {
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

				int id = (int) msgList.getAdapter().getItemId(info.position);			


				menu.setHeaderTitle(details.get(info.position).getName());		
				menu.add(Menu.NONE, v.getId(), 0, "Add to Favourites");
				menu.add(Menu.NONE, v.getId(), 0, "Delete");

		}
		
		@Override
	    public boolean onContextItemSelected(MenuItem item) {
	        if (item.getTitle() == "Add to Favourites") {

            }
	        else if (item.getTitle() == "Delete") {

       		}
	        else 	{
	        	return false;
        	}
	    return true;  
	    }
		
		@Override
		protected void onResume() {
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
		        return _data.size();
		    }
		    
		    public Object getItem(int position) {
		        return _data.get(position);
		    }
		
		    public long getItemId(int position) {
		        return position;
		    }
		   
		    public View getView(final int position, View convertView, ViewGroup parent) {
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
		           subView.setText(msg.sub);
		           descView.setText(msg.desc);
		           timeView.setText(msg.time);		              		
		        
		           image.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
//					AlertDialog.Builder adb=new AlertDialog.Builder(Messages.this);
//			        adb.setMessage("Add To Contacts?");
//			        adb.setNegativeButton("Cancel", null);
//			        final int selectedid = position;
//			        final String itemname= (String) _data.get(position).getName();
//
//			        adb.setPositiveButton("OK", new AlertDialog.OnClickListener() {
//			        	public void onClick(DialogInterface dialog, int which) {
//
//
//			        		Bundle b = new Bundle();
//			    			b.putString("project", itemname);
//			    			Intent createTask = new Intent("com.loginworks.tasktrek.CREATETASK");
//			    			createTask.putExtras(b);
//			    			startActivity(createTask);
//			        		}});
//
//			        adb.show();
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
