package com.bgu.mystudentlist;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.util.Linkify;

import android.widget.TextView;

public class Avtor extends Activity  {
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avtor);
		
		TextView tv = (TextView) findViewById(R.id.about_me);
		String data = "" +
				"Информация об авторе:\n" + 
				"Я Вконтакте: http://vk.com/id117176297 \n" +
				"Телефон: +7 (917)-432-16-56 \n" +
			    "Email: dilara4ka@mail.ru \n" +
				"\n" +
				"";

				if(tv != null) {
				tv.setText(data);
				Linkify.addLinks(tv, Linkify.ALL);
				tv.setLinkTextColor(Color.parseColor("#2f6699"));
				}
		

	}



}
