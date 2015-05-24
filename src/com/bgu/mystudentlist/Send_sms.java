package com.bgu.mystudentlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Send_sms extends Activity implements OnClickListener {
	
	final String LOG_TAG = "myLogs";
	
	Button send_sms;
	DatabaseHelper dbHelper;

	TextView selection;
	ListView choiceList;
	EditText sms_Text;
	Cursor c;
	String select_phone = "";
	String all_,fio_,phone_,city_,form_,parent_,gender_,group_,zapros,search,stud_gender,stud_group;
	ArrayList<HashMap<String, Object>> Student; //массив объектов

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_sms);      
		
		send_sms = (Button) findViewById(R.id.sms_send);
		send_sms.setOnClickListener(this);
		
		dbHelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		all_ = getIntent().getExtras().getString("all");
		fio_ = getIntent().getExtras().getString("fio");
		phone_ = getIntent().getExtras().getString("phone");
		city_ = getIntent().getExtras().getString("city");
		form_ = getIntent().getExtras().getString("form");
		parent_ = getIntent().getExtras().getString("parent");
		gender_ = getIntent().getExtras().getString("gender");
		group_ = getIntent().getExtras().getString("group");
		search = getIntent().getExtras().getString("search");
		stud_gender = getIntent().getExtras().getString("stud_gender");
		stud_group = getIntent().getExtras().getString("stud_group");
		search = getIntent().getExtras().getString("search");
		
		choiceList = (ListView) findViewById(R.id.list_stud_sms);

		List<String> array = new ArrayList<String>();
		ArrayList<HashMap<String,String>> Student = new ArrayList<HashMap<String,String>>(); //массив списков
		int code = Integer.parseInt(all_);
		
		if(code==1)
		{
		c = db.query("student_list", null, null, null, null, null, null);

	       //если получили первый объект в бд курсором, продолжаем
	 		if (c.moveToFirst())	 			
	 		{		
				int name = c.getColumnIndex("name");
				int surname = c.getColumnIndex("surname");
				int patron = c.getColumnIndex("patronymic");
				int phone = c.getColumnIndex("phone");	
	 				do
					{		
		
						//String folderphoto = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
				        array.add(c.getString(name)+ " " + c.getString(surname) + " " + c.getString(patron) + "\n" + c.getString(phone));
					
					}
					
	 				while(c.moveToNext());
			}
			else
			{
			c.close();
			dbHelper.close();
			}
		}
		else if(code==0)
		{
    		int ph = Integer.parseInt(phone_); //телефон
    		int ct = Integer.parseInt(city_); //город
    		int fm = Integer.parseInt(form_); //форма обучения
    		int frm = Integer.parseInt(fio_); //фио
    		int par = Integer.parseInt(parent_); //родители
    		int gen = Integer.parseInt(gender_); //пол
    		int grp = Integer.parseInt(group_); //группа
    		
    		zapros = "";
	    if(ct==1)
    		{
    			if(zapros != null && !zapros.isEmpty())
    			{	
    			zapros = zapros + "OR adress LIKE" + "'%" + search + "%'"	+ "OR region LIKE" + "'%" + search + "%'"  ;
    			}
    			else
    			{
    				zapros = "adress LIKE" + "'%" + search + "%'"	+ "OR region LIKE" + "'%" + search + "%'" ;
    			}
    		}
    	
    	if(fm==1)
    		{
    			
    			if(zapros != null && !zapros.isEmpty())
    			{
    				
    			zapros = zapros + "OR name LIKE" + "'%" + search + "%'" + "OR surname LIKE" + "'%" + search + "%'"  ;
    			}
    			else
    			{
    				zapros = "name LIKE" + "'%" + search + "%'" + "OR surname LIKE" + "'%" + search + "%'" ;
    			}
    		}
		if(frm==1)
		{
			if(zapros != null && !zapros.isEmpty())
			{
			zapros = zapros + "OR form LIKE" + "'%" + search + "%'" ;
			}
			else
			{
				zapros = zapros + "form LIKE" + "'%" + search + "%'" ;
			}
		}
		if(ph==1)
		{	
			if(zapros != null && !zapros.isEmpty())
			{
				
			zapros = zapros + "OR phone LIKE" + "'%" + search + "%'"  ;
			}
			else
			{
				zapros = "phone LIKE" + "'%" + search + "%'" ;
			}
		}
		if(par==1)
		{
			if(zapros != null && !zapros.isEmpty())
			{
				
			zapros = zapros + "OR mother LIKE" + "'%" + search + "%'" + "OR father LIKE" + "'%" + search + "%'" ;
			}
			else
			{
				zapros = "mother LIKE" + "'%" + search + "%'" + "OR father LIKE" + "'%" + search + "%'" ;
			}
		}
		if(gen==1)
		{
			if(zapros != null && !zapros.isEmpty())
			{
				
			zapros = zapros + "OR gender LIKE" + "'%" + stud_gender + "%'";
			}
			else
			{
				zapros = "gender LIKE" + "'%" + stud_gender + "%'";
			}
		}
		if(grp==1)
		{
			if(zapros != null && !zapros.isEmpty())
			{
				
			zapros = zapros + "OR group_id LIKE" + "'%" + stud_group + "%'";
			}
			else
			{
				zapros = "group_id LIKE" + "'%" + stud_group + "%'";
			}
		}
		 c = db.query("student_list", null, zapros ,null ,null,null,null); 
		
		       //если получили первый объект в бд курсором, продолжаем
		 		if (c.moveToFirst())	 			
		 		{		
					int name = c.getColumnIndex("name");
					int surname = c.getColumnIndex("surname");
					int patron = c.getColumnIndex("patronymic");
					int phone = c.getColumnIndex("phone");	
		 				do
						{		
			
							//String folderphoto = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
					        array.add(c.getString(name)+ " " + c.getString(surname) + " " + c.getString(patron) + "\n" + c.getString(phone));
						
						}
						
		 				while(c.moveToNext());
				}
				else
				{
				c.close();
				dbHelper.close();
				}

		}
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, array);
		

	choiceList.setAdapter(adapt);
	choiceList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
	}
	


	
	private void getPhoneStud()
	{	
		//бд
		dbHelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		c = db.query("student_list", null, null, null, null, null, null);
		
		//переменные
        int id = c.getColumnIndex("id");
        int phone = c.getColumnIndex("phone");
        
    
		//вызов обработчика
		SparseBooleanArray checkedItems = choiceList.getCheckedItemPositions();
		if (checkedItems != null) 
		{	
			 select_phone = "";
		    for (int i=0; i<checkedItems.size(); i++) 
		    {	
		        if (checkedItems.valueAt(i)) 
		        	{
		        		String item = choiceList.getAdapter().getItem(checkedItems.keyAt(i)).toString();
		        		//Log.i(LOG_TAG,item + " was selected");
		            
		        		int idx = checkedItems.keyAt(i);
		        		// Log.i(LOG_TAG, "Позиция" + idx);
		            
		            
					if (c.moveToPosition(idx))
						{		
							Log.i(LOG_TAG, "ID студента: " + c.getString(id));
							select_phone += c.getString(phone)+";";
							
						}
		            
		        	}
		        
		    }

		   // Log.i(LOG_TAG, "Номера: " + select_phone);
		    		    	    
		}


	}

	public void sendSms()
	{
		sms_Text = (EditText) findViewById(R.id.sms_text);
		String Text = sms_Text.getText().toString();
		

		try
		{
			Intent sendInten = new Intent(Intent.ACTION_VIEW);
			sendInten.putExtra("address", select_phone);
			sendInten.putExtra("sms_body", Text);
			sendInten.setType("vnd.android-dir/mms-sms");
			startActivity(sendInten);
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(), "Ошибка отправления смс", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}


	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

				
		switch(v.getId())
		{
		case R.id.sms_send:
			getPhoneStud();
			Log.i(LOG_TAG, "Номера: " + select_phone);
			if(select_phone.length()!=0)
			{
				sendSms();
			}
			    else
			    {
			    	Toast.makeText(getApplicationContext(), "Никто не выбран", Toast.LENGTH_LONG).show();
			    }
			//sendSms();
			break;

			
		}	
	}
}
