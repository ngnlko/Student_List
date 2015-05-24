package com.bgu.mystudentlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class sms_filter extends Activity implements OnClickListener {
	final String LOG_TAG = "myLogs";
	CheckBox all,fio,phone,city,form,parent,gender,group;
	String all_,fio_,phone_,city_,form_,parent_,gender_,group_,sms_filter,std_grp,std_gnd;
	int stud_gender,stud_group;
	EditText filter_text;
	Button next;
	Spinner gen,grp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_filter);
		
		gen = (Spinner) findViewById(R.id.gender_sms);
		grp = (Spinner) findViewById(R.id.group_id_select);
		
		all = (CheckBox)findViewById(R.id.search_all);
		fio = (CheckBox)findViewById (R.id.search_fio);
		phone = (CheckBox)findViewById (R.id.search_phone);
		city = (CheckBox)findViewById (R.id.search_city);
		form = (CheckBox)findViewById (R.id.study_form);
		parent = (CheckBox)findViewById (R.id.parent_check);
		gender = (CheckBox)findViewById (R.id.gender_sms_check);
		group = (CheckBox)findViewById (R.id.group_id_check);
		
		filter_text = (EditText) findViewById(R.id.sms_filter_text);
		
		ArrayAdapter<?> adapter = 
				ArrayAdapter.createFromResource(this, R.array.group_id, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			gen.setAdapter(adapter);
			
			gen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent,
						View itemSelected, int selectedItemPosition, long selectedId) {
					
					stud_gender = selectedItemPosition + 1;	
 
				}
				
				public void onNothingSelected(AdapterView<?> parent) {
				}
				
			});
			
			ArrayAdapter<?> adapter2 = 
					ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
				adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				gen.setAdapter(adapter2);
				
				gen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View itemSelected, int selectedItemPosition, long selectedId) {
						
						stud_group = selectedItemPosition + 1;	
	 
					}
					
					public void onNothingSelected(AdapterView<?> parent) {
					}
					
				});
		
		next = (Button) findViewById(R.id.go_next);
		next.setOnClickListener(this);
		
		if (all.isChecked())
		{
			fio.setEnabled(false);
			fio.setChecked(false);
			phone.setEnabled(false);
			phone.setChecked(false);
			city.setEnabled(false);
			city.setChecked(false);
			form.setEnabled(false);
			form.setChecked(false);
			parent.setEnabled(false);
			parent.setChecked(false);
			gender.setEnabled(false);
			gender.setChecked(false);
			group.setEnabled(false);
			group.setChecked(false);
			gen.setEnabled(false);
			grp.setEnabled(false);
			filter_text.setEnabled(false);
			all_ = "1";
		}
		else
		{
			all_ = "0";
		}
		
		all.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked)
				{
					fio.setEnabled(false);
					fio.setChecked(false);
					phone.setEnabled(false);
					phone.setChecked(false);
					city.setEnabled(false);
					city.setChecked(false);
					form.setEnabled(false);
					form.setChecked(false);
					parent.setEnabled(false);
					parent.setChecked(false);
					gender.setEnabled(false);
					gender.setChecked(false);
					group.setEnabled(false);
					group.setChecked(false);
					gen.setEnabled(false);
					grp.setEnabled(false);
					filter_text.setEnabled(false);
					all_ = "1";
				}
				else
				{
					fio.setEnabled(true);
					fio.setChecked(false);
					phone.setEnabled(true);
					phone.setChecked(false);
					city.setEnabled(true);
					city.setChecked(false);
					form.setEnabled(true);
					form.setChecked(false);
					parent.setEnabled(true);
					parent.setChecked(false);
					gender.setEnabled(true);
					gender.setChecked(false);
					group.setEnabled(true);
					group.setChecked(false);
					gen.setEnabled(true);
					grp.setEnabled(true);
					filter_text.setEnabled(true);
					all_ = "0";
				}
			}
		});
	}
	
	public void search()
	{	
		sms_filter = filter_text.getText().toString();
		if(all.isChecked())
		{
			all_ = "1";
		}
		else
		{
			all_ = "0";
			if(fio.isChecked())
			{
				fio_ = "1";
			}
			else
			{
				fio_ = "0";
			}
			
			if(phone.isChecked())
			{	
				
				phone_ = "1";
			}
			else
			{
				
				phone_ = "0";
			}
			
			if(city.isChecked())
			{
				city_ = "1";
			}
			else
			{
				city_ = "0";
			}
			
			if(form.isChecked())
			{
				form_ = "1";
			}
			else
			{
				form_ = "0";
			}
			
			if(parent.isChecked())
			{
				parent_ = "1";
			}
			else
			{
				parent_ = "0";
			}
			
			if(gender.isChecked())
			{
				gender_ = "1";
				final Spinner spin_sex = (Spinner) findViewById(R.id.gender_sms);
				std_gnd = spin_sex.getSelectedItem().toString();
			}
			else
			{
				gender_ = "0";
			}
			
			if(group.isChecked())
			{
				group_ = "1";
				final Spinner spin_group = (Spinner) findViewById(R.id.group_id_select);
				std_grp = spin_group.getSelectedItem().toString();
			}
			else
			{
				group_ = "0";
			}
			

		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.go_next:
				search();
				
				Intent intent = new Intent(this, Send_sms.class);
				intent.putExtra("all", all_);
				intent.putExtra("fio", fio_);
				intent.putExtra("phone", phone_);
				intent.putExtra("city", city_);
				intent.putExtra("form", form_);
				intent.putExtra("parent", parent_);
				intent.putExtra("gender", gender_);
				intent.putExtra("group", group_);
				Log.d(LOG_TAG,"Проверка "+std_gnd  +" "+ std_grp);
				intent.putExtra("stud_gender",std_gnd);
				intent.putExtra("stud_group", std_grp);
				intent.putExtra("search", sms_filter);
				startActivity(intent);
		break;
		}
	}

}
