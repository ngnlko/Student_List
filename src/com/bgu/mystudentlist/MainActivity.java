package com.bgu.mystudentlist;


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button stud_list,add_stud,find,avtor,options,sms_send;
	
	DatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
		
		stud_list = (Button) findViewById(R.id.all_student);
		add_stud = (Button) findViewById(R.id.btn_new_student);
		find = (Button) findViewById(R.id.find);
		avtor = (Button) findViewById(R.id.about);
		options = (Button) findViewById(R.id.options);
		sms_send = (Button) findViewById(R.id.sms_send);
		
		stud_list.setOnClickListener(this);
		add_stud.setOnClickListener(this);
		find.setOnClickListener(this);
		avtor.setOnClickListener(this);
		options.setOnClickListener(this);
		sms_send.setOnClickListener(this);
		
		dbHelper = new DatabaseHelper(this);
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		
		}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.all_student:
			Intent intent_all = new Intent(this, student_show.class);
			startActivity(intent_all);
			break;
			
		case R.id.btn_new_student:
		    Intent intent_new = new Intent(this, New_Student.class);
		      startActivity(intent_new);
			break;

		case R.id.find:
			Intent intent_find = new Intent(this, Search_Filter.class );
			startActivity(intent_find);
			break;
		case R.id.about:
            Intent intent_about = new Intent(this, Avtor.class);
            startActivity(intent_about);
            break;
            
		case R.id.options:
			Intent intent_options = new Intent(this, options.class);
			startActivity(intent_options);
			break;
			
		case R.id.sms_send:
			Intent intent_sms = new Intent(this, sms_filter.class);
			startActivity(intent_sms);
			break;
		}
	}
	}

	
