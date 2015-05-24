package com.bgu.mystudentlist;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	//необходимые переменные
	public static final String DATABASE_NAME = "student_list_db"; //имя файла в папке андроид 
	public static final String DATABASE_TABLE_GROUP = "student_list"; //имя таблицы

	public static final int VERSION_DB = 5; //версия базы
	
	
	//наши таблицы
	private static final String DATABASE_CREATE_GROUP = "create table " + DATABASE_TABLE_GROUP + "("
	          + "id integer primary key autoincrement," 
	          + "group_id int,"
	          + "gender text," 
	          + "name text," 
	          + "surname text," 
	          + "phone int," 
	          + "adress text," 
	          + "region text,"
	          + "form text," 
	          + "mother text," 
	          + "mother_phone int," 
	          + "father text," 
	          + "father_phone int," 
	          + "patronymic text,"
	          + "district text," 
	          + "birth long," 
	          + "photo text" 
	          + ");";
	
	

	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

    public DatabaseHelper(Context context) {
	      // конструктор суперкласса (контекст,название бд,объект для работы с курсором,и версия)
	      super(context, DATABASE_NAME, null, VERSION_DB);
	    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	      // создаем таблицу с полями
	      db.execSQL(DATABASE_CREATE_GROUP);

		
	}
	
	//для последующего обновления бд
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (newVersion > oldVersion) 
		{

		//запись в журнал
		Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
		
		// Удаляем старую таблицу и создаём новую
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_GROUP);
		
		// Создаём новую таблицу
		onCreate(db);
		
		}
	}

}
