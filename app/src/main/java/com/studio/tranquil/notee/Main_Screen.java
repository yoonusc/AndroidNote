package com.studio.tranquil.notee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main_Screen extends AppCompatActivity {
    Button add_btn;
	EditText search;
    ListView Contact_listview;
    ArrayList<Contact> contact_data = new ArrayList<Contact>();
    Contact_Adapter cAdapter;
    DatabaseHandler db;
    String Toast_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	try {
		search=(EditText)findViewById(R.id.edit_search);
	    Contact_listview = (ListView) findViewById(R.id.list);
	    Contact_listview.setItemsCanFocus(false);
	   // add_btn = (Button) findViewById(R.id.button);
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent add_user = new Intent(Main_Screen.this,
						Add_Update_User.class);
				add_user.putExtra("called", "add");
				//add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						//| Intent.FLAG_ACTIVITY_NEW_TASK);
				Main_Screen.this.startActivity(add_user);
			}
		});
        search.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
search_data(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	    Set_Referash_Data();

	} catch (Exception e) {
	    // TODO: handle exception
	    Log.e("some error", "" + e);
	}


    }

    public void Call_My_Blog(View v) {
	//Intent intent = new Intent(Main_Screen.this, My_Blog.class);
	//startActivity(intent);

    }

    public void Set_Referash_Data() {
	contact_data.clear();
	db = new DatabaseHandler(this);
	ArrayList<Contact> contact_array_from_db = db.Get_Contacts();

	for (int i = 0; i < contact_array_from_db.size(); i++) {

	    int tidno = contact_array_from_db.get(i).getID();
	    String name = contact_array_from_db.get(i).get_sub();
	    String mobile = contact_array_from_db.get(i).get_note();
	    String email = contact_array_from_db.get(i).get_date();
	    Contact cnt = new Contact();
	    cnt.setID(tidno);
	    cnt.set_sub(name);
	    cnt.set_note(email);
	    cnt.set_date(mobile);

	    contact_data.add(cnt);
	}
	db.close();
	cAdapter = new Contact_Adapter(Main_Screen.this, R.layout.listview_row,
		contact_data);
	Contact_listview.setAdapter(cAdapter);
	cAdapter.notifyDataSetChanged();
    }

	public void search_data(String  key) {
		contact_data.clear();
		db = new DatabaseHandler(this);
		ArrayList<Contact> contact_array_from_db = db.Search_Contacts(key);

		for (int i = 0; i < contact_array_from_db.size(); i++) {

			int tidno = contact_array_from_db.get(i).getID();
			String name = contact_array_from_db.get(i).get_sub();
			String mobile = contact_array_from_db.get(i).get_note();
			String email = contact_array_from_db.get(i).get_date();
			Contact cnt = new Contact();
			cnt.setID(tidno);
			cnt.set_sub(name);
			cnt.set_note(email);
			cnt.set_date(mobile);

			contact_data.add(cnt);
		}
		db.close();
		cAdapter = new Contact_Adapter(Main_Screen.this, R.layout.listview_row,
				contact_data);
		Contact_listview.setAdapter(cAdapter);
		cAdapter.notifyDataSetChanged();
	}

    public void Show_Toast(String msg) {
	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	Set_Referash_Data();

    }

    public class Contact_Adapter extends ArrayAdapter<Contact> {
	Activity activity;
	int layoutResourceId;
	Contact user;
	ArrayList<Contact> data = new ArrayList<Contact>();

	public Contact_Adapter(Activity act, int layoutResourceId,
		ArrayList<Contact> data) {
	    super(act, layoutResourceId, data);
	    this.layoutResourceId = layoutResourceId;
	    this.activity = act;
	    this.data = data;
	    notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View row = convertView;
	    UserHolder holder = null;

	    if (row == null) {
		LayoutInflater inflater = LayoutInflater.from(activity);

		row = inflater.inflate(layoutResourceId, parent, false);
		holder = new UserHolder();
		holder.name = (TextView) row.findViewById(R.id.user_name_txt);
		holder.email = (TextView) row.findViewById(R.id.textView2);
		holder.number = (TextView) row.findViewById(R.id.user_mob_txt);
		holder.edit = (Button) row.findViewById(R.id.btn_update);
		holder.delete = (Button) row.findViewById(R.id.btn_delete);
		row.setTag(holder);
	    } else {
		holder = (UserHolder) row.getTag();
	    }
	    user = data.get(position);
	    holder.edit.setTag(user.getID());
	    holder.delete.setTag(user.getID());
	    holder.name.setText(user.get_sub());
	    holder.email.setText(user.get_note());
	    holder.number.setText(user.get_date());

	    holder.edit.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    Log.i("Edit Button Clicked", "**********");

		    Intent update_user = new Intent(activity,
			    Add_Update_User.class);
		    update_user.putExtra("called", "update");
		    update_user.putExtra("USER_ID", v.getTag().toString());
		    activity.startActivity(update_user);

		}
	    });
	    holder.delete.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(final View v) {
		    // TODO Auto-generated method stub

		    // show a message while loader is loading

		    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
		    adb.setTitle("Delete?");
		    adb.setMessage("Are you sure you want to delete ");
		    final int user_id = Integer.parseInt(v.getTag().toString());
		    adb.setNegativeButton("Cancel", null);
		    adb.setPositiveButton("Ok",
			    new AlertDialog.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
					int which) {
				    // MyDataObject.remove(positionToRemove);
				    DatabaseHandler dBHandler = new DatabaseHandler(
					    activity.getApplicationContext());
				    dBHandler.Delete_Contact(user_id);
				    Main_Screen.this.onResume();

				}
			    });
		    adb.show();
		}

	    });
	    return row;

	}

	class UserHolder {
	    TextView name;
	    TextView email;
	    TextView number;
	    Button edit;
	    Button delete;
	}

    }

}
