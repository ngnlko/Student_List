package com.bgu.mystudentlist;

import java.io.File;

import com.bgu.mystudentlist.R.drawable;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Edit_Student extends Activity implements OnClickListener {
	
	final String LOG_TAG = "myLogs";
	
	Button save_stud, del_stud;
	EditText student_name, Surname, Patronymic, student_phone, date_of_birth,
	region_stud, district_stud, home_adress, student_father, student_mother , father_phone, mother_phone;
	ImageView photo;
	
	DatabaseHelper dbHelper;

	String id,form;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		dbHelper = new DatabaseHelper(this);
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_student); 
		setTitle("Редактирование данных");
		//Toast.makeText(this, "Для загрузки данных, выберите группе и укажите номер студента в группе", Toast.LENGTH_LONG).show();	//оповещение
		//кнопки
		save_stud = (Button) findViewById(R.id.save_stud);
		del_stud = (Button) findViewById(R.id.del_stud);
		//load_stud = (Button) findViewById(R.id.load_stud);
		
		save_stud.setOnClickListener(this);
		del_stud.setOnClickListener(this);
	
		//текстовые поля
		student_name = (EditText) findViewById(R.id.student_name);
		Surname = (EditText) findViewById(R.id.Surname);
		Patronymic = (EditText) findViewById(R.id.Patronymic);
		student_phone = (EditText) findViewById(R.id.student_phone);
		date_of_birth = (EditText) findViewById(R.id.date_of_birth);
		region_stud = (EditText) findViewById(R.id.region_stud);
		district_stud = (EditText) findViewById(R.id.district_stud);
		home_adress = (EditText) findViewById(R.id.home_adress);
		student_father = (EditText) findViewById(R.id.student_father);
		student_mother = (EditText) findViewById(R.id.student_mother);
		father_phone = (EditText) findViewById(R.id.father_phone);
		mother_phone = (EditText) findViewById(R.id.mother_phone);
		
		photo = (ImageView) findViewById(R.id.photo_student_view);
		
		
		
		
		id = getIntent().getExtras().getString("id");
		
		if(id != null && !id.isEmpty())
		{
			Load();
		}
		else
		{
			Log.d(LOG_TAG, "Ошибка при получении данных");
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
	}
	
	
	   
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dbHelper = new DatabaseHelper(this);
		ContentValues ContentValues = new ContentValues();
		
		final Spinner form_ = (Spinner) findViewById(R.id.form_);
		form = form_.getSelectedItem().toString();
	    //получение данных из текстовых полей
	    String name = student_name.getText().toString();
	    String surname = Surname.getText().toString();
	    String patronymic = Patronymic.getText().toString();
	    String phone = student_phone.getText().toString();
	    String birth = date_of_birth.getText().toString();
	    String region = region_stud.getText().toString();
	    String district = district_stud.getText().toString();
	    String adress = home_adress.getText().toString();
	    String father = student_father.getText().toString();
	    String mother = student_mother.getText().toString();
	    String phone_mother = mother_phone.getText().toString();
	    String phone_father = father_phone.getText().toString();
	    
	    // подключаемся к БД, для работы с ней. методы инсерт,квери.делет
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    
		//подготовка данных
    	ContentValues.put("name", name);
    	ContentValues.put("phone", phone);
    	ContentValues.put("surname", surname);
    	ContentValues.put("patronymic",patronymic);
    	ContentValues.put("birth",birth);
    	ContentValues.put("region", region);
    	ContentValues.put("district", district);
    	ContentValues.put("adress", adress);
    	ContentValues.put("father", father);
    	ContentValues.put("mother", mother);
    	ContentValues.put("form", form);
		ContentValues.put("father_phone", phone_father);
		ContentValues.put("mother_phone", phone_mother);
		
	    switch (v.getId()) 
	    {
	   // case R.id.load_stud:
	    	
	    case R.id.save_stud:
	    	//обновление данных под ID
				ContentValues.put("name", name);
				ContentValues.put("surname", surname);
				ContentValues.put("patronymic",patronymic);
				ContentValues.put("phone",phone);
				ContentValues.put("birth", birth);
				ContentValues.put("region", region);
				ContentValues.put("district",district);
				ContentValues.put("adress",adress);
				ContentValues.put("father",father);
				ContentValues.put("mother", mother);
				ContentValues.put("form", form);
				ContentValues.put("father_phone", phone_father);
				ContentValues.put("mother_phone", phone_mother);
				
	    	db.update("student_list", ContentValues, "id = " + id, null);
			Toast.makeText(this, "Данные успешно обновлены!", Toast.LENGTH_LONG ).show();

	    	
			dbHelper.close();
			Intent intent = new Intent (this, MainActivity.class);
			startActivity(intent);
	    	break;
	    	
	    case R.id.del_stud:
	    	db.delete("student_list", "id = "+ id, null);
			Toast.makeText(this, "Данные студента удалены!", Toast.LENGTH_LONG ).show();
	    	//Log.d(LOG_TAG, "Удалили студента:  " + delCount );
			dbHelper.close();
			Intent intent_del = new Intent (this, MainActivity.class);
			startActivity(intent_del);
	    	break;
	    }
	    
	    
		
	}
	
	
	
	public void Load()
	 {	
		 photo.setImageResource(drawable.camera);
		 SQLiteDatabase db = dbHelper.getWritableDatabase();
	    		Cursor c = db.query("student_list", null, "id = "+id, null, null, null, null);
	    		if(c.moveToFirst())
	    		{	
	    			String gender,form_;
	    			Spinner spinner = (Spinner)findViewById(R.id.change_group);
	    			Spinner spinner_ = (Spinner) findViewById(R.id.change_gender);
	    			Spinner form = (Spinner) findViewById(R.id.form_);
	    			
	    			int group = c.getColumnIndex("group_id");
	    			int name_out = c.getColumnIndex("name");
	    			int surname_out = c.getColumnIndex("surname");
	    			int phone_out = c.getColumnIndex("phone");
	    			int patronymic_out = c.getColumnIndex("patronymic");
	    			int birth_out = c.getColumnIndex("birth");
	    			int region_out = c.getColumnIndex("region");
	    			int district_out = c.getColumnIndex("district");
	    			int adress_out = c.getColumnIndex("adress");
	    			int father_out = c.getColumnIndex("father");
	    			int mother_out = c.getColumnIndex("mother");
	    			//int form_out = c.getColumnIndex("form");
	    			int photo_out = c.getColumnIndex("photo");
	    			int phone_father_out = c.getColumnIndex("mother_phone");
	    			int phone_mother_out = c.getColumnIndex("father_phone");
	    			
	    			gender = c.getString(c.getColumnIndex("gender"));
	    			if(gender.equals("Муж."))
	    			{	
	    				Log.d(LOG_TAG,c.getString(c.getColumnIndex("gender")));
	    				spinner_.setSelection(0);
	    			}
	    			else if(gender.equals("Жен."))
	    			{
	    				spinner_.setSelection(1);
	    			}
	    			
	    			form_ = c.getString(c.getColumnIndex("form"));
	    			if(form_.equals("Бюджет"))
	    			{
	    				form.setSelection(0);
	    			}
	    			else if(form_.equals("Коммерция"))
	    			{
	    				form.setSelection(1);
	    			}
	    			
	    				student_name.setText(c.getString(name_out));
	    				Surname.setText(c.getString(surname_out));
	    				Patronymic.setText(c.getString(patronymic_out));
	    				student_phone.setText(c.getString(phone_out));
	    				date_of_birth.setText(c.getString(birth_out));
	    				region_stud.setText(c.getString(region_out));
	    				district_stud.setText(c.getString(district_out));
	    				home_adress.setText(c.getString(adress_out));
	    				student_father.setText(c.getString(father_out));
	    				student_mother.setText(c.getString(mother_out));
	    				father_phone.setText(c.getString(phone_father_out));
	    				mother_phone.setText(c.getString(phone_mother_out));
	    				
	    				
	    				
	    				spinner.setSelection(c.getInt(group)-1);
	    				Uri selectedImage;
	    				if (c.getString(photo_out).length() !=0)
	    				{
	    					selectedImage = Uri.parse(c.getString(photo_out));	
	    					photo.setImageURI(selectedImage);
	    				/*
	    				//обращаемся к фотографии, указываем директорию куда сохраняли файл
	    				String folderphoto = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();

	    				//в файл передаем директорию и имя файла(имя берется из БД)
			    				File file = new File(folderphoto,c.getString(photo_out));
			    				if(file.exists())
			    				{		
			    					//если файл найден, производится декодирование изображения и вывод его на экран
			    					Bitmap Stud_Photo = BitmapFactory.decodeFile(file.getAbsolutePath());
			    					photo.setImageBitmap(Stud_Photo);
			    				}
	    					else
	    					{
	    						
	    					}
			    			*/
	    				 }

	    				
	    			c.close();
	    			dbHelper.close();
	    			}
	    		else
	    		{
	    			Toast.makeText(this, "Ошибка"+"\n"+"Загрузки данных", Toast.LENGTH_LONG ).show();
	    			c.close();
	    			dbHelper.close();
	    			onStop();
	    		}

	 }

}
