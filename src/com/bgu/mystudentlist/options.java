package com.bgu.mystudentlist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class options extends Activity implements OnClickListener {
	
	final String LOG_TAG = "myLogs";
	
	Button btnOn,btnOff;
	final int ID = 1;
	TimePicker timePicker;
	private NotificationManager nm;
	
	Calendar calendar = Calendar.getInstance();
	
	DatabaseHelper dbHelper;
	Cursor c;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
		btnOn = (Button) findViewById(R.id.notification_on);
		btnOff = (Button) findViewById(R.id.notification_off);
		timePicker = (TimePicker) findViewById(R.id.time_notification);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		
		btnOn.setOnClickListener(this);
		btnOff.setOnClickListener(this);
		
		nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		switch (v.getId()) {
		case R.id.notification_on:
			NotifiOn();
			break;
		case R.id.notification_off:
			//intent.setClass(this, MyServiceNotifi.class);
			//stopService(intent);
			NotifiOff();
			break;


		}


	}
	
	
	private void NotifiOff() 
	{
		// TODO Auto-generated method stub
		dbHelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		c = db.query("student_list", null, null, null, null, null, null);	
		ArrayList<Integer> idlist = new ArrayList<Integer>();
		
		
 		if (c.moveToFirst())
 			
 		{
 			int id = c.getColumnIndex("id");
 			int birthd = c.getColumnIndex("birth");
 			int NotifID = c.getInt(id);
 			idlist.add(NotifID);
 			do
 			{
				if (c.getString(birthd).length() !=0)
				{
					for(int i = 0 ; i<idlist.size(); i++)
					{
						AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
						Intent intent = new Intent();
						intent.setClass(this, MyServiceNotifi.class);
						PendingIntent pendingIntent = PendingIntent.getService(this, idlist.get(i) , intent, PendingIntent.FLAG_UPDATE_CURRENT);
						alarmManager.cancel(pendingIntent);
						Toast.makeText(this, "Уведомления отключены", Toast.LENGTH_SHORT).show();
					}
				}
 			}
 			while(c.moveToNext());
 			
 		}
			else
			{
	
					c.close();
					dbHelper.close();
			}
			c.close();
		dbHelper.close();
		
	}


	private void NotifiOn()
	{	
		dbHelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		c = db.query("student_list", null, null, null, null, null, null);
		
		Intent intent = new Intent();
		
 		if (c.moveToFirst())
 			
 		{
		
 			int id = c.getColumnIndex("id");
			int name = c.getColumnIndex("name");
			int surname = c.getColumnIndex("surname");
			int birthd = c.getColumnIndex("birth");


			do
			{
				if (c.getString(birthd).length() !=0)
				{

					//получаем дату рождения из бд
					String myTime = c.getString(birthd);
					//Log.i(LOG_TAG, myTime);

					//указываем формат для дальнешей манипуляции
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
					Calendar cal = Calendar.getInstance();

				try 
					{
						
						//парсим полученые из строкового типа данные в  нормальный формат даты
						Date myDate = dateFormat.parse(myTime);	
						//устанавливаем для календяря наши данные
						cal.setTime(myDate);	
						int month = cal.get(Calendar.MONTH);
						Log.i(LOG_TAG, "Дата: "+myDate);
						Log.i(LOG_TAG, "Месяц: " + month);
					} 
					catch (java.text.ParseException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

				calendar.set(calendar.MONTH, cal.get(Calendar.MONTH));
				calendar.set(calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
				calendar.set(calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
				calendar.set(calendar.MINUTE, timePicker.getCurrentMinute());
				calendar.set(calendar.SECOND, 0);
				
				AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				
				intent.setClass(this, MyServiceNotifi.class);
				intent.putExtra("msg", c.getString(surname)+" "+c.getString(name));
				Log.i(LOG_TAG, "ФИ: " + c.getString(surname)+" "+c.getString(name));
				int NotifID = c.getInt(id);
				PendingIntent pendingIntent = PendingIntent.getService(this, NotifID , intent, PendingIntent.FLAG_UPDATE_CURRENT);
				
				
				
				alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
				//startService(intent);
				
				int mont = calendar.get(calendar.MONTH)+1;
				//Log.i(LOG_TAG, "Дата срабатывания уведомления: "  + calendar.get(calendar.DAY_OF_MONTH) + "." + mont + "." + calendar.get(calendar.YEAR));
				//Log.i(LOG_TAG, "Время уведомления: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
				//Log.i(LOG_TAG, timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString());
			}
			}
			while(c.moveToNext());
		

 		}
 			else
 			{
		
 					c.close();
 					dbHelper.close();
 			}
 			c.close();
			dbHelper.close();
	}

}
