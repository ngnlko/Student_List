package com.bgu.mystudentlist;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import com.bgu.mystudentlist.R.drawable;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;



public class student_show extends Activity implements OnClickListener {
	
	 final String LOG_TAG = "myLogs";
	
	ArrayList<HashMap<String, Object>> Student; //массив объектов
	private static final String PHONEKEY = "phone_student";
	private static final String HEADERKEY = "header";
	private static final String PHOTOKEY = "photo";
	private static final String ADRESSKEY = "adress";
	private static final String FORMKEY = "form";
	private static final String FIOKEY = "fio";
	private static final String GROUP_ID = "group_id";

	
	private static final int CALL = 1;
	private static final int EDIT = 2;
	
	Button add_stud,find,sms_send;
	
	int group_id;
	DatabaseHelper dbHelper;
	
	Cursor c;
	HashMap<String, Object> hm; //список объектов
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_list);
		

		
		add_stud = (Button) findViewById(R.id.btn_new_student);
		find = (Button) findViewById(R.id.find);
		sms_send = (Button) findViewById(R.id.sms_send);
		
		add_stud.setOnClickListener(this);
		
		find.setOnClickListener(this);
		sms_send.setOnClickListener(this);
		final Spinner change_group = (Spinner) findViewById(R.id.change_group_id);
		
		ArrayAdapter<?> adapter_group = 
				ArrayAdapter.createFromResource(this, R.array.change_group, android.R.layout.simple_spinner_item);
		adapter_group.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			change_group.setAdapter(adapter_group);
			
			change_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent,
						View itemSelected, int selectedItemPosition, long selectedId) {
					
					group_id = selectedItemPosition;
					Log.d(LOG_TAG, "ВЫБРАНА Группа " + group_id);
					if(group_id==0)
					{	
						Log.d(LOG_TAG, "Выбрали все группы");
						
						ShowStudent(null);
					}
					else
					{	
						Log.d(LOG_TAG, "Выбрали группу: " + group_id);			
						
						String selection = "group_id LIKE" + "'%" + group_id  + "%'";
						
						ShowStudent(selection);
					}
				}
				public void onNothingSelected(AdapterView<?> parent) {
				}
				
			});


	}
	
	public void ShowStudent(String selection)
	{

		dbHelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		c = db.query("student_list", null, selection , null, null, null, null);

		
		
        //работа с листвью
        ListView list = (ListView) findViewById(R.id.list_student);
        list.setAdapter(null);
        list.setOnCreateContextMenuListener(this);
       
        
        Student = new ArrayList<HashMap<String,Object>>(); //массив списков
        
        //если получили первый объект в бд курсором, продолжаем
 		if (c.moveToFirst())
 			
 		{	
 			int group = c.getColumnIndex("group_id");
			int id = c.getColumnIndex("id");
			int name = c.getColumnIndex("name");
			int surname = c.getColumnIndex("surname");
			int patron = c.getColumnIndex("patronymic");
			int phone = c.getColumnIndex("phone");
			int adress = c.getColumnIndex("adress");
			int form = c.getColumnIndex("form");
			int photo_out = c.getColumnIndex("photo");

			
			//photo.setImageResource(drawable.camera);
			
			//обращаемся к фотографии, указываем директорию куда сохраняли файл
			String folderphoto = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();

			SimpleAdapter adapter;
			Uri selectedImage;
				do
				{			

				/*	использовалось когда обращались как к файлу
				 * 
				 * File file = null;
					if (c.getString(photo_out).length() !=0)
					{	
						
						file = new File(folderphoto,c.getString(photo_out));
						Log.d(LOG_TAG, "Photo: "+file);
						
					}
				*/	
					 hm = new HashMap<String, Object>();
					hm.clear();
					hm.put(GROUP_ID, "Группа: " + c.getString(group));
			        hm.put(HEADERKEY, "Номер студента: " + c.getString(id));
			        hm.put(FIOKEY, "ФИО: " + c.getString(name)+ " " + c.getString(surname) + " " + c.getString(patron));
			        
			        hm.put(PHONEKEY, "Телефон: " + c.getString(phone));
			        
			        if (c.getString(adress).length()!=0)
			        		{
			        			hm.put(ADRESSKEY, "Адресс проживания: " + c.getString(adress));
			        		}
			        else
			        {
			        	hm.put(ADRESSKEY, "Адресс проживания: не указан");
			        }
			        
			        if(c.getString(form).length()!=0)
			        {
			        hm.put(FORMKEY, "Форма обучения: " + c.getString(form));
			        }
			        else
			        {
			        	hm.put(FORMKEY, "Форма обучения: не указана");
			        }
			        
			        if (c.getString(photo_out).length() !=0)
					{	
			        	selectedImage = Uri.parse(c.getString(photo_out));
						hm.put(PHOTOKEY, selectedImage );
						Log.d(LOG_TAG, "ФОТКА " + selectedImage);
					}
				else
					{
						hm.put(PHOTOKEY, drawable.camera);
					}
		        
			        Student.add(hm);
			        Log.d(LOG_TAG, "WARNING!!!! "+Student);

				}
				
			while(c.moveToNext());
				
			String[] from = {GROUP_ID,HEADERKEY,FIOKEY,PHONEKEY,ADRESSKEY,FORMKEY,PHOTOKEY};
			int[] to = {R.id.groupid_show,R.id.header_view,R.id.fio_view,R.id.footer_view,R.id.adress_view,R.id.form_view, R.id.image_view};
				
			adapter = new SimpleAdapter(this, Student, R.layout.list_new, 
							from, 
							to);
			//listItemAdapter = new CheckboxAdapter(this, Student);	 		 
			list.setAdapter(adapter);
			//list.setAdapter(listItemAdapter);

			list.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE); //для  выбора элемента
			c.close();
			db.close();
			dbHelper.close();
			
		}
		else
		{
		list.setAdapter(null);
		c.close();
		db.close();
		dbHelper.close();
		}


 		list.setOnItemClickListener(new OnItemClickListener() {
 			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
 			{				
 				SQLiteDatabase db = dbHelper.getWritableDatabase();
 				Cursor c2;
 				Log.d(LOG_TAG, "Группа: " + group_id);
 				String selection;
				if(group_id==0)
				{	
					selection = null;
				}
				else
				{	
					Log.d(LOG_TAG, "Выбрали группу: " + group_id);			
					
					selection = "group_id LIKE" + "'%" + group_id  + "%'";

				}
 				
 				c2 = db.query("student_list", null, selection, null, null, null, null);
 				int nom_id = position;
 				
				if (c2.moveToPosition(nom_id))
				{	
 				
 				int studId_val = c2.getColumnIndex("id");

 				
 				//CheckBox chk_stud = (CheckBox) view.findViewById(R.id.sms_check);
 				//Boolean chk_val = chk_stud.isChecked();

 				Log.d(LOG_TAG, " ВЫБРАНА ПОЗИЦИЯ№ " + nom_id + " ID: " + studId_val + "  " + group_id);
 				
 				Intent edit_intent = new Intent(getApplicationContext(), Edit_Student.class);
 				edit_intent.putExtra("id", c2.getString(studId_val));
 				startActivity(edit_intent);
 				c2.close();
 				db.close();
 				dbHelper.close();
				}
				else
				{
					
					Log.d(LOG_TAG,"Ошибка загрузки" );
				
				}

 			}
 		});
 		
 		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.btn_new_student:
		    Intent intent_new = new Intent(this, New_Student.class);
		      startActivity(intent_new);
			break;

		case R.id.find:
			Intent intent_find = new Intent(this, Search_Filter.class );
			startActivity(intent_find);
			break;
		
		case R.id.sms_send:
			Intent intent_sms = new Intent(this, sms_filter.class);
			startActivity(intent_sms);
			break;
		}
	}


	//контекстное меню
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{	

		menu.setHeaderTitle("Меню");
		menu.add(0,CALL,0,"Позвонить");//.setOnMenuItemClickListener(new OnMenuItemClickListener()
		menu.add(0, EDIT, 0, "Редактировать");
			
		
	}
	
	//выбранный пункт
		public boolean onContextItemSelected(MenuItem item) 
		{	
			dbHelper = new DatabaseHelper(this);
	        
        	SQLiteDatabase db = dbHelper.getWritableDatabase();
        	c = db.query("student_list", null, null, null, null, null, null);
        	
			if(item.getItemId() == CALL)
			{

				AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo(); 

				int nom_id = (int) acmi.id;
				int phone = c.getColumnIndex("phone");
				
				//переходим на выделенную позицию и получаем телефонный номер, звоним
				if (c.moveToPosition(nom_id))
					{
					Log.d(LOG_TAG, "Телефон: "  + c.getString(phone) );
					String phone_n = "tel:" +c.getString(phone);
						Intent intent_call = new Intent(Intent.ACTION_CALL);
						intent_call.setData(Uri.parse(phone_n));	
						intent_call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent_call);
						return true;
					}
				else
					{
						return false;
					}
			}
			
			if(item.getItemId() == EDIT)	
			{	
			
				AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo(); 
				
				int nom_id = (int) acmi.id;
				int id = c.getColumnIndex("id");
				
				if (c.moveToPosition(nom_id))
				{	
					Log.d(LOG_TAG, "ВЫБРАНА ПОЗИЦИЯ№ " + nom_id + " ID у него: " + id );
					
	 				Intent edit_intent_ = new Intent(student_show.this, Edit_Student.class);
	 				edit_intent_.putExtra("id", c.getString(id));
	 				startActivity(edit_intent_);
	 				
					return true;
					
				}

			else
				{
					return false;
				}
			}
			c.close();
			db.close();
			dbHelper.close();
			return super.onContextItemSelected(item);
		}

}
