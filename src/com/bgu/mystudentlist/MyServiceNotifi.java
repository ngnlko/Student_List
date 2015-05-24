package com.bgu.mystudentlist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyServiceNotifi extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//Toast.makeText(this, "Запуск сервиса", Toast.LENGTH_SHORT).show();
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		String message = intent.getStringExtra("msg");
		//Toast.makeText(this, "Уведомления запущено", Toast.LENGTH_SHORT).show();		
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
		Intent notificationIntent = new Intent(this, MainActivity.class);		
		//создание уведомления
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		String tickerText = "Сегодня день рождения"; //сообщение в статусбаре
		long when = System.currentTimeMillis(); //когда(время)			
		Notification notification = new Notification(R.drawable.birthday_, tickerText, when);
		String contentTitle = "Сегодня день рождения у "; //заголовок уведомления
		String contentText = message; //основной текст
		notification.setLatestEventInfo(this, contentTitle, contentText, pendingIntent);
		//добавляем в нотификейшн менеджр
		notificationManager.notify(123, notification);
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(this, "Уведомления удалены", Toast.LENGTH_SHORT).show();
	}

	
	
	
}
