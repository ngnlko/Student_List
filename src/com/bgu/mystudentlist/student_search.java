package com.bgu.mystudentlist;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class student_search extends Activity implements OnClickListener {
	
	final String LOG_TAG = "myLogs";
	DatabaseHelper dbHelper; 
	Button btn_search;
	EditText terms_search;
	TextView  search_result;
	Cursor c;
	
	String all,fio,telephone,city,forma,gender,group,form,gender_text,group_text, zapros="";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		//setTitle("Поиск студента");
		
		btn_search = (Button) findViewById(R.id.btn_search);
		
		btn_search.setOnClickListener(this);
		
		terms_search = (EditText) findViewById(R.id.terms_search);
		
		search_result = (TextView) findViewById(R.id.search_result);
		
		dbHelper = new DatabaseHelper(this);
		
		all = getIntent().getExtras().getString("all");
		city = getIntent().getExtras().getString("city");
		telephone = getIntent().getExtras().getString("telephone");
		fio = getIntent().getExtras().getString("fio");
		forma = getIntent().getExtras().getString("forma");
		gender = getIntent().getExtras().getString("gender");
		group = getIntent().getExtras().getString("group");
		
		form = getIntent().getExtras().getString("select_form");
		gender_text = getIntent().getExtras().getString("select_gender");
		group_text = getIntent().getExtras().getString("select_group");

	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub

		    switch (v.getId()) 
		    {
		    
		   case R.id.btn_search:
			String search = "";
			search = terms_search.getText().toString();

		    search_db(search);

		    	
		   }
		
	}
	
	
	public void search_db(String search)
	{	
		int code = Integer.parseInt(all);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		search_result.setText("");
		Log.d(LOG_TAG, "При поиске значение общее: "+all);
    	if(code==1)
    	{
    		
	    c = db.query("student_list", null, 
	    		"phone LIKE" + "'%" + search + "%'" 
	    		+ "OR name LIKE" + "'%" + search + "%'"
	    		+ "OR surname LIKE" + "'%" + search + "%'"
	    		+ "OR adress LIKE" + "'%" + search + "%'"
	    		+ "OR region LIKE" + "'%" + search + "%'"
	    		+ "OR form LIKE" + "'%" + search + "%'"
	    		+ "OR mother LIKE" + "'%" + search + "%'"
	    		+ "OR father LIKE" + "'%" + search + "%'"
	    		+ "OR patronymic LIKE" + "'%" + search + "%'"
	    		+ "OR district LIKE" + "'%" + search + "%'"
	    		+ "OR birth LIKE" + "'%" + search + "%'"
	    		+ "OR id LIKE" + "'%" + search + "%'",
	    		
	    		null ,null,null,null); 
	    
	    if(c.moveToFirst())	
		{
		int id = c.getColumnIndex("id");
		int gropu_id = c.getColumnIndex("group_id");
		int name = c.getColumnIndex("name");
		int surname = c.getColumnIndex("surname");
		int phone = c.getColumnIndex("phone");
		int form = c.getColumnIndex("form");
		int adress = c.getColumnIndex("adress");
		int region = c.getColumnIndex("region");
		int district = c.getColumnIndex("district");
		
			do
				{
					search_result.setText(search_result.getText()+"Найден студент:"+ "\n" + "№" + c.getString(id)+"\n"
				+ "Группа: " + c.getString(gropu_id)+"\n"
				+ "Фамилия: " + c.getString(surname)+"\n"
				+ "Имя: " + c.getString(name)+"\n"
				+ "Отчество: " + c.getString(district) + "\n"
				+"Телефон: "+c.getString(phone)+"\n"
				+ "Форма обучения: " + c.getString(form)+ "\n"
				+ "Регион: " + c.getString(region) + "\n"
				+ "Улица и дом: " + c.getString(adress) + "\n" + "\n"
				
							);
				}
			while(c.moveToNext());
		}
	    else
	    	{
			search_result.setText("Ничего не найдено");
	    		c.close();
	    		dbHelper.close();
	    	}
    	}
    	else if(code==0)
	    	{	
    		zapros = "";
    		
		    if(Integer.parseInt(city)==1)
		    	{
		    	if(zapros != null && !zapros.isEmpty())
		    			{	
		    		zapros = zapros + "AND adress LIKE" + "'%" + search + "%'"	+ "AND region LIKE" + "'%" + search + "%'"  ;
		    			}
		    		else
		    		{
		    			zapros = "adress LIKE" + "'%" + search + "%'"	+ "AND region LIKE" + "'%" + search + "%'" ;
		    		}
		    	}
		    	
		    	if(Integer.parseInt(fio)==1)
		    	{
		    		if(zapros != null && !zapros.isEmpty())
		    			{
		    				
		    			zapros = zapros + "AND name LIKE" + "'%" + search + "%'" + "AND surname LIKE" + "'%" + search + "%'"  ;
		    			}
		    			else
		    			{
		    				zapros = "name LIKE" + "'%" + search + "%'" + "AND surname LIKE" + "'%" + search + "%'" ;
		    			}
		    		}
	    		if(Integer.parseInt(forma)==1)
	    		{	
	    			
	    			if(zapros != null && !zapros.isEmpty())
	    			{
	    			zapros = zapros + "AND form LIKE" + "'%" + form + "%'" ;
	    			}
	    			else
	    			{	
	    				zapros = "";
	    				zapros = "form LIKE" + "'%" + form + "%'" ;
	    			}
	    		}
	    		if(Integer.parseInt(telephone)==1)
	    		{	
	    			if(zapros != null && !zapros.isEmpty())
	    			{
	    				
	    			zapros = zapros + "AND phone LIKE" + "'%" + search + "%'"  ;
	    			}
	    			else
	    			{
	    				zapros = "phone LIKE" + "'%" + search + "%'" ;
	    				
	    			}
	    		}
	    		if(Integer.parseInt(gender)==1)
	    		{
	    			if(zapros != null && !zapros.isEmpty())
	    			{
	    				
	    			zapros = zapros + "AND gender LIKE" + "'%" + gender_text + "%'"  ;
	    			}
	    			else
	    			{
	    				zapros = "gender LIKE" + "'%" + gender_text + "%'" ;
	    				
	    			}
	    		}
	    		if(Integer.parseInt(group)==1)
	    		{
	    			if(zapros != null && !zapros.isEmpty())
	    			{
	    				
	    			zapros = zapros + "AND group_id LIKE" + "'%" + group_text + "%'"  ;
	    			}
	    			else
	    			{
	    				zapros = "group_id LIKE" + "'%" + group_text + "%'" ;
	    				
	    			}
	    		}
	    		
	    		 c = db.query("student_list", null, zapros ,null ,null,null,null); 
	    		 search_result.setText("");
	    		    if(c.moveToFirst())	
	    			{	
	    			int id = c.getColumnIndex("id");
	    			int gropu_id = c.getColumnIndex("group_id");
	    			int name = c.getColumnIndex("name");
	    			int surname = c.getColumnIndex("surname");
	    			int phone = c.getColumnIndex("phone");
	    			int form = c.getColumnIndex("form");
	    			int adress = c.getColumnIndex("adress");
	    			int region = c.getColumnIndex("region");
	    			int district = c.getColumnIndex("district");
	    			
	    				do
	    					{
	    						search_result.setText(search_result.getText()+"Найден студент:"+ "\n" + "№" + c.getString(id)+"\n"
	    					+ "Группа: " + c.getString(gropu_id)+"\n"
	    					+ "Фамилия: " + c.getString(surname)+"\n"
	    					+ "Имя: " + c.getString(name)+"\n"
	    					+ "Отчество: " + c.getString(district) + "\n"
	    					+"Телефон: "+c.getString(phone)+"\n"
	    					+ "Форма обучения: " + c.getString(form)+ "\n"
	    					+ "Регион: " + c.getString(region) + "\n"
	    					+ "Улица и дом: " + c.getString(adress) + "\n" + "\n"
	    					
	    								);
	    					}
	    				while(c.moveToNext());
	    			}
	    		    else
	    		    	{
	    				search_result.setText("Ничего не найдено");
	    		    		c.close();
	    		    		db.close();
	    		    		dbHelper.close();
	    		    	}
	    		
	    	}
    		else
    		{	
	    		c.close();
	    		db.close();
	    		dbHelper.close();
    			Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
    		}
	    
	}

}
