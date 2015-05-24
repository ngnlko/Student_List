package com.bgu.mystudentlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Search_Filter extends Activity implements OnClickListener {
	CheckBox All,FIO,phone,City,mod,gender_check,group_check;
	Button gonext;
	String all,fio,telephone,city,forma,gender,group,select_form,select_gender,select_group;
	
	final String LOG_TAG = "myLogs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filter);
		
		All = (CheckBox)findViewById(R.id.search_all);
		FIO = (CheckBox) findViewById(R.id.search_fio);
		phone = (CheckBox) findViewById(R.id.search_phone);
		City = (CheckBox) findViewById(R.id.search_city);
		mod = (CheckBox) findViewById(R.id.study_form);
		gender_check = (CheckBox) findViewById(R.id.gender_sms_check);
		group_check = (CheckBox) findViewById(R.id.group_id_check);
		gonext = (Button) findViewById(R.id.go_next);
		
		gonext.setOnClickListener(this);
		final Spinner form_select = (Spinner) findViewById(R.id.form_selected);
		final Spinner gender_select = (Spinner) findViewById(R.id.gender_sms);
		final Spinner group_id_select = (Spinner) findViewById(R.id.group_id_select);

		// TODO Auto-generated method stub

		
		if(All.isChecked())
		{
			FIO.setEnabled(false);
			FIO.setChecked(false);
			phone.setEnabled(false);
			phone.setChecked(false);
			City.setEnabled(false);
			City.setChecked(false);
			mod.setEnabled(false);
			mod.setChecked(false);
			gender_check.setEnabled(false);
			gender_check.setChecked(false);
			group_check.setEnabled(false);
			group_check.setChecked(false);
			form_select.setEnabled(false);
			gender_select.setEnabled(false);
			group_id_select.setEnabled(false);
			all = "1";
		}
		else
		{
			all = "0";
		}
		All.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				// TODO Auto-generated method stub
				if(isChecked)
				{
					FIO.setEnabled(false);
					FIO.setChecked(false);
					phone.setEnabled(false);
					phone.setChecked(false);
					City.setEnabled(false);
					City.setChecked(false);
					mod.setEnabled(false);
					mod.setChecked(false);
					gender_check.setEnabled(false);
					gender_check.setChecked(false);
					group_check.setEnabled(false);
					group_check.setChecked(false);
					
					form_select.setEnabled(false);
					gender_select.setEnabled(false);
					group_id_select.setEnabled(false);
					all = "1";
				}
				else
				{
					FIO.setEnabled(true);
					FIO.setChecked(false);
					phone.setEnabled(true);
					phone.setChecked(false);
					City.setEnabled(true);
					City.setChecked(false);
					mod.setEnabled(true);
					mod.setChecked(false);
					gender_check.setEnabled(true);
					gender_check.setChecked(false);
					group_check.setEnabled(true);
					group_check.setChecked(false);
					
					form_select.setEnabled(true);
					gender_select.setEnabled(true);
					group_id_select.setEnabled(true);
					all = "0";
				}
			}
		});
		
		

	
	}
	
	public void search()
	{	
		final Spinner form_select = (Spinner) findViewById(R.id.form_selected);
		final Spinner gender_select = (Spinner) findViewById(R.id.gender_sms);
		final Spinner group_id_select = (Spinner) findViewById(R.id.group_id_select);
		if(All.isChecked())
		{
			all = "1";
		}
		else
		{
			all = "0";
			if(FIO.isChecked())
			{
				fio = "1";
			}
			else
			{
				fio = "0";
			}
			
			if(phone.isChecked())
			{	
				
				telephone = "1";
			}
			else
			{
				
				telephone = "0";
			}
			
			if(City.isChecked())
			{
				city = "1";
			}
			else
			{
				city = "0";
			}
			
			if(mod.isChecked())
			{
				forma = "1";
				select_form = form_select.getSelectedItem().toString();

			}
			else
			{
				forma = "0";
				select_form = "";
			}
			
			if(gender_check.isChecked())
			{	
				gender = "1";
				select_gender = gender_select.getSelectedItem().toString();
			}
			else
			{	
				gender = "0";
				select_gender = "";
			}
			if(group_check.isChecked())
			{	
				group = "1";
				select_group = group_id_select.getSelectedItem().toString();
			}
			else
			{
				group = "0";
				select_group = "";
			}

		}
	}
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.go_next:
				
				if(!All.isChecked() && !FIO.isChecked() && !phone.isChecked() && !City.isChecked() && !mod.isChecked() && !gender_check.isChecked() && !group_check.isChecked())
				{
					Toast.makeText(this, "Ничего не выбрано! Сделайте выбор", Toast.LENGTH_SHORT).show();	
				}
				else if(All.isChecked() || FIO.isChecked() || phone.isChecked() || City.isChecked() || mod.isChecked() || gender_check.isChecked() || group_check.isChecked())
				{
				search();
				Intent intent = new Intent(this, student_search.class);
				intent.putExtra("all", all);
				intent.putExtra("city", city);
				intent.putExtra("telephone", telephone);
				intent.putExtra("fio", fio);
				intent.putExtra("forma", forma);
				intent.putExtra("gender",gender);
				intent.putExtra("group", group);
				intent.putExtra("select_form", select_form);
				intent.putExtra("select_gender", select_gender);
				intent.putExtra("select_group", select_group);
				startActivity(intent);
				}
		break;
		}
	}
	
}
