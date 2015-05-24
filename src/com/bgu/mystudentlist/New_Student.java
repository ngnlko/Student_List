package com.bgu.mystudentlist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class New_Student extends Activity implements OnClickListener {
	final String LOG_TAG = "myLogs";
	DatabaseHelper dbHelper;
	
	  int DIALOG_DATE = 1;
	  int myYear = 1995;
	  int myMonth = 00;
	  int myDay = 01;
	  int group_id = 0;
	  String stud_gender,form;
	  
	Button save,call,add_photo,select_photo;
	ImageView photo;
	TextView date_of_birth;
	EditText  student_name, Surname, Patronymic, student_phone,
	region_stud, district_stud, home_adress, student_father, student_mother, father_phone, mother_phone;
	
	private static final int CAM_REQUEST = 1313;
	private static final int GAL_REQUEST = 1417;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_student);
		
		
		
		setTitle("����� �������");
		
		Toast.makeText(this, "���������� ������� ������������ ����", Toast.LENGTH_SHORT).show();	//����������
		save = (Button) findViewById(R.id.save_stud);
		call = (Button) findViewById(R.id.call_stud);
		add_photo = (Button) findViewById(R.id.add_photo);
		select_photo = (Button) findViewById(R.id.select_photo);
		
		photo = (ImageView) findViewById(R.id.student_image);
		
		//������� ��� ��������

		save.setOnClickListener(this);
		call.setOnClickListener(this);
		add_photo.setOnClickListener(this);
		select_photo.setOnClickListener(this);
		
		date_of_birth = (TextView) findViewById(R.id.date_of_birth);
		
		date_of_birth.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DIALOG_DATE);
			}
		});
		final Spinner spinner = (Spinner)findViewById(R.id.change_group);
		
		ArrayAdapter<?> adapter = 
				ArrayAdapter.createFromResource(this, R.array.group_id, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			
			spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent,
						View itemSelected, int selectedItemPosition, long selectedId) {
					
					group_id = selectedItemPosition + 1;	
 
				}
				
				public void onNothingSelected(AdapterView<?> parent) {
				}
				
			});
			


	}
	
    @SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
          DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
          return tpd;
        }
        return super.onCreateDialog(id);
      }
      
    OnDateSetListener myCallBack = new OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
            int dayOfMonth) {
          myYear = year;
          myMonth = monthOfYear;
          myDay = dayOfMonth;
          date_of_birth.setText(myDay + "." + myMonth + "." + myYear);
        }
        };
	
	
String photo_name = "";
	
	//��� ������ � �������
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {	
		// TODO Auto-generated method stub
		//��������� ���������� �������, ��� �������, ��� ����������, � ������
		super.onActivityResult(requestCode, resultCode, data);
		
		OutputStream fOut = null;
		
	
		//���� ������ ������� � ������ ������
		if (requestCode == CAM_REQUEST)
		{
			Bitmap thumbnail = (Bitmap) data.getExtras().get("data"); //���� ���������� �����������
			
			photo.setImageBitmap(thumbnail); //������������� ��� ���������
			
			String folderToSave = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();//����� ���� ���������
			

			    Time time = new Time();
			    time.setToNow(); //��� ��������� �������/����
			    File file;
				
					try 
					{
		
					    file = new File(folderToSave, Integer.toString(time.year) 
					    	    + Integer.toString(time.month) + Integer.toString(time.monthDay) 
					    	    + Integer.toString(time.hour) + Integer.toString(time.minute) 
					    	    + Integer.toString(time.second) +".jpg"); // ��������� ���������� ��� ��� ����� ����������� �� ���� ����������
						
					    fOut = new FileOutputStream(file);
					    
					    thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, fOut); //��������� � JPG � �������� � 90
					    
				        fOut.flush();
				        fOut.close();
			        
				        //����������� � �����������
				        MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(),  file.getName());
     
				        Toast.makeText(this, "���������� ���������: " + file.getName(), Toast.LENGTH_LONG ).show();
				        
				        Uri uri = Uri.fromFile(file);
				        //photo_name = file.getName();
				        photo_name = file.getAbsolutePath();
				        Log.d(LOG_TAG,file + "name: " +photo_name + "         !!! " + uri);
					}

					catch (Exception e) // ������������ ������
				    {
			    	e.printStackTrace();
				    } 
					finally
					{
						if (fOut != null)
						{
							try
							{
						        fOut.flush();
						        fOut.close();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}

				
			}
		else if(requestCode == GAL_REQUEST)
		    {  	
			 Uri selectedImage = data.getData();
			 Log.d(LOG_TAG,selectedImage + "");
			Cursor cursor = null;
			String fileName;
			try
			{	
				
				String[] proj = { MediaStore.Images.Media.DATA };
				cursor = this.getContentResolver().query(selectedImage, proj, null, null, null);
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				photo_name = cursor.getString(column_index);
				selectedImage = Uri.parse(photo_name);
				photo.setImageURI(selectedImage);
				fileName = selectedImage.getLastPathSegment().toString();
				//photo_name = fileName;
				
			}
			finally
			{
				if(cursor != null)
				{
					cursor.close();
				}
		
			}

				
				Log.d(LOG_TAG,selectedImage + "  ���: " + photo_name + "         " + fileName);
		    }
		else photo_name = "";
	}
	

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// ������� ������ ��� ������, ��� ���������� ������ � ���������� ����������� � ����
	    ContentValues ContentValues = new ContentValues();
	    
	    dbHelper = new DatabaseHelper(this);
	    // ������������ � ��, ��� ������ � ���. ������ ������,�����.�����
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		final Spinner spin_sex = (Spinner) findViewById(R.id.change_gender);
		stud_gender = spin_sex.getSelectedItem().toString();
		
		final Spinner form_ = (Spinner) findViewById(R.id.form_);
		form = form_.getSelectedItem().toString();
		
		student_name = (EditText) findViewById(R.id.student_name);
		Surname = (EditText) findViewById(R.id.Surname);
		Patronymic = (EditText) findViewById(R.id.Patronymic);
		student_phone = (EditText) findViewById(R.id.student_phone);
		date_of_birth = (TextView) findViewById(R.id.date_of_birth);
		region_stud = (EditText) findViewById(R.id.region_stud);
		district_stud = (EditText) findViewById(R.id.district_stud);
		home_adress = (EditText) findViewById(R.id.home_adress);
		student_father = (EditText) findViewById(R.id.student_father);
		student_mother = (EditText) findViewById(R.id.student_mother);
		father_phone = (EditText) findViewById(R.id.father_phone);
		mother_phone = (EditText) findViewById(R.id.mother_phone);
		
		
		final CheckBox add_to_phonebook = (CheckBox) findViewById(R.id.add_to_phonebook);
		final CheckBox add_to_phonebook_father = (CheckBox) findViewById(R.id.add_to_phonebook_father);
		final CheckBox add_to_phonebook_mother = (CheckBox) findViewById(R.id.add_to_phonebook_mother);
		
		//�������� ������ �� �����
	    String name = student_name.getText().toString();
	    
	    String surname = Surname.getText().toString();
	    String patronymic = Patronymic.getText().toString();
	    String phone = student_phone.getText().toString();
	   // String birth = date_of_birth.getText().toString();
	    String region = region_stud.getText().toString();
	    String district = district_stud.getText().toString();
	    String adress = home_adress.getText().toString();
	    String father = student_father.getText().toString();
	    String mother = student_mother.getText().toString();
	    //String form = education_form.getText().toString();
	    String phone_mother = mother_phone.getText().toString();
	    String phone_father = father_phone.getText().toString();
	    String dateBirth = date_of_birth.getText().toString();

	    
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.select_photo:
			Intent pickPhoto = new Intent(Intent.ACTION_PICK, 
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(pickPhoto, GAL_REQUEST);
			break;
		case R.id.add_photo:
			 
		    //��� ������ � �������, ���� �������� ������ � ������ ��������� ������������������ ������.

		    Intent cameraintent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		    startActivityForResult(cameraintent, CAM_REQUEST);
			break;
			
		case R.id.save_stud:
			
			//���������� �������� � ���.�����
			if (add_to_phonebook.isChecked()) //�������� ���������� ������� ���������� � ���������� ��� ���
			{
				if (name.length()!=0  & phone.length()!=0) //�������� �� ���  � ����� ��������(��������� �� ���)
					{	

						//��� ��������� � ���� ������ �� �����, ���� ������ ��������
						ContentValues.put("group_id",group_id);
						ContentValues.put("gender", stud_gender);
						ContentValues.put("name", name);
						ContentValues.put("surname", surname);
						ContentValues.put("patronymic",patronymic);
						ContentValues.put("phone",phone);
						ContentValues.put("birth", dateBirth);
						ContentValues.put("region", region);
						ContentValues.put("district",district);
						ContentValues.put("adress",adress);
						ContentValues.put("father",father);
						ContentValues.put("mother", mother);
						ContentValues.put("form", form);
						ContentValues.put("father_phone", phone_father);
						ContentValues.put("mother_phone", phone_mother);
						ContentValues.put("photo",photo_name);
						
						
						//��������� ������ � �������� � ID (�������� ��� ������� � ��������)

						db.insert("student_list",null,ContentValues);

						Toast.makeText(this, "����� ������� ��������", Toast.LENGTH_SHORT).show(); //����������� � ����������
			
						Intent intent = new Intent (this, MainActivity.class);
						startActivity(intent);
						dbHelper.close();
						
						Toast.makeText(this, "��������� ����� �������: " + name + "" + phone, Toast.LENGTH_LONG).show();
					    Intent intent_new = new Intent(ContactsContract.Intents.Insert.ACTION, ContactsContract.Contacts.CONTENT_URI);
					    intent_new.putExtra(ContactsContract.Intents.Insert.NAME, name );
					    intent_new.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
					    intent_new.putExtra(ContactsContract.Intents.Insert.POSTAL, adress);
					    
					    startActivity(intent_new);
					    
					    if(add_to_phonebook_father.isChecked())
					    {	
					    	if(father_phone.length()!=0)
					    	{
						    Intent intent_father = new Intent(ContactsContract.Intents.Insert.ACTION, ContactsContract.Contacts.CONTENT_URI);
						    intent_father.putExtra(ContactsContract.Intents.Insert.NAME, father );
						    intent_father.putExtra(ContactsContract.Intents.Insert.PHONE, phone_father);
						    
						    startActivity(intent_father);
					    	}
					    }

					    if(add_to_phonebook_mother.isChecked())
					    {	
					    	if(mother_phone.length()!=0)
					    	{
						    Intent intent_mother = new Intent(ContactsContract.Intents.Insert.ACTION, ContactsContract.Contacts.CONTENT_URI);
						    intent_mother.putExtra(ContactsContract.Intents.Insert.NAME, mother );
						    intent_mother.putExtra(ContactsContract.Intents.Insert.PHONE, phone_mother);
 
						    startActivity(intent_mother);
					    	}
					    }

						db.close();
					    break;
				    //break;
					}
			    else
					Toast.makeText(this, "������ ���������� ������ ��������! " + "" +
							"��������� ��� � �������", Toast.LENGTH_LONG).show();	
				db.close();
				break;
			}
			else
				
			if (name.length()!=0  & phone.length()!=0 & surname.length()!=0) //���� � ���������� �� ���������, ��������� �� ������������� ������������� �����
				{	
				
					//��� ��������� � ���� ������ �� �����, ���� ������ ��������
					ContentValues.put("group_id",group_id);
					ContentValues.put("gender", stud_gender);
					ContentValues.put("name", name);
					ContentValues.put("surname", surname);
					ContentValues.put("patronymic",patronymic);
					ContentValues.put("phone",phone);
					ContentValues.put("birth", dateBirth);
					ContentValues.put("region", region);
					ContentValues.put("district",district);
					ContentValues.put("adress",adress);
					ContentValues.put("father",father);
					ContentValues.put("mother", mother);
					ContentValues.put("form", form);
					ContentValues.put("father_phone", phone_father);
					ContentValues.put("mother_phone", phone_mother);
					ContentValues.put("photo",photo_name);
					
					
					//��������� ������ � �������� � ID (�������� ��� ������� � ��������)

					db.insert("student_list",null,ContentValues);			
					Toast.makeText(this, "����� ������� ��������", Toast.LENGTH_SHORT).show(); //����������� � ����������
					
					//Log.d(LOG_TAG, "������ ���������: " + rowID);
					dbHelper.close();
					
					Intent intent = new Intent (this, MainActivity.class);
					startActivity(intent);
					
				    if(add_to_phonebook_father.isChecked())
				    {	
				    	if(father_phone.length()!=0)
				    	{
					    Intent intent_father = new Intent(ContactsContract.Intents.Insert.ACTION, ContactsContract.Contacts.CONTENT_URI);
					    intent_father.putExtra(ContactsContract.Intents.Insert.NAME, father );
					    intent_father.putExtra(ContactsContract.Intents.Insert.PHONE, phone_father);
					    
					    startActivity(intent_father);
				    	}
				    }

				    if(add_to_phonebook_mother.isChecked())
				    {	
				    	if(mother_phone.length()!=0)
				    	{
					    Intent intent_mother = new Intent(ContactsContract.Intents.Insert.ACTION, ContactsContract.Contacts.CONTENT_URI);
					    intent_mother.putExtra(ContactsContract.Intents.Insert.NAME, mother );
					    intent_mother.putExtra(ContactsContract.Intents.Insert.PHONE, phone_mother);

					    startActivity(intent_mother);
				    	}
				    }					
					
					db.close();
					break;
				}
			  else
			Toast.makeText(this, "������ ���������� ������ ��������! " + "\n" +
							"��������� ������������ ����", Toast.LENGTH_LONG).show();		//���-�� ����� �� ���
			db.close();
			dbHelper.close();
			break;
			
			
		case R.id.call_stud:
			if (phone.length()!=0) //���� ����� ������, ����� �������
			{
				Toast.makeText(this, "��������: " + phone, Toast.LENGTH_SHORT).show();
				
			Intent intent_call = new Intent(Intent.ACTION_DIAL);
			intent_call.setData(Uri.parse("tel:" + phone));
			intent_call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent_call);
			}
			else
				Toast.makeText(this, "����� �� ������!", Toast.LENGTH_SHORT).show();
			break;
		}
		
		
	}

}
