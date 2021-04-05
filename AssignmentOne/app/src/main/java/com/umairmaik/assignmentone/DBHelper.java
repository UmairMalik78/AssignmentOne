package com.umairmaik.assignmentone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String QUEST_TABLE= "QuestionPool";
    public static final String QUESTION_ID = "id";
    public static final String DESCRIPTION = "Description";
    public static final String CATEGORY = "category";
    public static final String OPTION_1 = "option1";
    public static final String OPTION_2 = "option2";
    public static final String OPTION_3 = "option3";
    public static final String OPTION_4 = "option4";
    public static final String ANSWER = "answer";

    public DBHelper(@Nullable Context context) {
        super(context, "MyQuestionDB.db", null, 1);
        Log.d("ALC","Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("ALC","Database created");
        //String createTableSTatementOne = "CREATE TABLE CustTable(CustomerID Integer PRIMARY KEY AUTOINCREMENT, " + CUSTOMER_NAME_FIRST + " Text, CustomerAge Int, ActiveCustomer BOOL) ";
        String createTableStatement = "CREATE TABLE " + QUEST_TABLE
                + "(" + QUESTION_ID + " Integer PRIMARY KEY AUTOINCREMENT,"
                + CATEGORY +" Text, "
                + DESCRIPTION + " Text, "
                + OPTION_1 + " Text, "
                + OPTION_2 + " Text, "
                + OPTION_3 + " Text, "
                + OPTION_4 + " Text, "
                + ANSWER + " Text )";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addQuestion(Question question){
        Log.d("ALC","Database created");
        Log.d("option1",question.getCategory());
        Log.d("option1",question.getAnswer());
        SQLiteDatabase db = this.getWritableDatabase();
        //Hash map, as we did in bundles
        ContentValues cv = new ContentValues();
        cv.put(OPTION_1,question.getOption1());
        cv.put(CATEGORY,question.getCategory());
        cv.put(DESCRIPTION,question.getQuestionDescription());
        cv.put(ANSWER,question.getAnswer());
        cv.put(OPTION_3,question.getOption3());
        cv.put(OPTION_4,question.getOption4());
        cv.put(OPTION_2,question.getOption2());
        //insert data to table
        long insert = db.insert(QUEST_TABLE, null, cv);
        Log.d("DBMS","Database created");

        if (insert == -1) {
            Log.d("ALCasas", "Failed");
            return false;
        }
        else {
                Log.d("ALCasas","Success");
                return true;
            }
    }
}

/*
    public ArrayList<CustomerModel> GetAllRecords(){
        ArrayList<CustomerModel> myList=new ArrayList<>();
        String query="Select * from " +CUST_TABLE;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                CustomerModel tempCustomerModel=new CustomerModel("",-1,false);
                int id=cursor.getInt(0);
                String name=cursor.getString(1);
                int age=cursor.getInt(2);
                boolean isActive=cursor.getInt(3)==1?true:false;
                tempCustomerModel.setId(id);
                tempCustomerModel.setName(name);
                tempCustomerModel.setAge(age);
                tempCustomerModel.setActive(isActive);
                myList.add(tempCustomerModel);
            }while(cursor.moveToNext()!=false);
        }
        cursor.close();
        sqLiteDatabase.close();
        return myList;
    }
*/
