package com.example.duy26.app1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHander extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHander.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "List_details";
    private static final String TABLE_NAME = "favorite";
    private static final String NUMBERDT ="numberdt";
    private static final String FOOD = "food";
    private static final String ID_PRINCE = "prince";
    private static final String ID_KEY = "id";
    private static final String TOATAL = "toatal";
    private static final String ID_BILL = "id_bill";
    private static final String ID_FOOD = "id_food";


    public SQLiteHander(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_NAME + "("
                + ID_KEY + " INTERGER PRIMARY KEY ,"
                + FOOD + " TEXT,"
                + ID_FOOD + " TEXT,"
                + NUMBERDT + " TEXT,"
                + ID_PRINCE + " TEXT,"
                + TOATAL + " TEXT,"
                + ID_BILL + " TEXT" + ")";
        db.execSQL(script);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long addTEAM(Data_oderdetails data){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FOOD,data.getFood());
        values.put(ID_FOOD,data.getId_food());
        values.put(NUMBERDT,data.getNumber_details());
        values.put(ID_PRINCE,data.getPrince());
        values.put(TOATAL,data.getToatal());
        values.put(ID_BILL,data.getId_bill());

        long result = db.insert(TABLE_NAME,null,values);
        db.close();
        return result;
    }
//    public Data_oderdetails getData(String id){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_NAME,new String[]{ID_FOODDT,NUMBERDT,ID_BILLDT,FOOD},ID_BILLDT +"=?", new String[]{String.valueOf(id)},null,null,null,null);
//        if(cursor!=null) cursor.moveToFirst();
//        else return new Data_oderdetails(cursor.getInt(1),cursor.getString(2),cursor.getString(3)
//                ,cursor.getString(4),cursor.getString(5),cursor.getString(6));
//        return null;
//    }
    public List<Data_oderdetails> getallteam(){
        List<Data_oderdetails> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuerry = "SELECT "+ ID_KEY +","+ FOOD +","+ ID_FOOD +","+ NUMBERDT +","+ ID_PRINCE +","+ NUMBERDT +"*"+
                ID_PRINCE +" AS "+ TOATAL +","+ ID_BILL +" FROM " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(selectQuerry, null);
        if (cursor.moveToFirst()) {
            do {
                data.add(new Data_oderdetails(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6)));
            }
            while (cursor.moveToNext());
        }
        return data;
    }
    public void update(Data_oderdetails data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FOOD,data.getFood());
        values.put(NUMBERDT,data.getNumber_details());
        values.put(ID_PRINCE,data.getPrince());
        values.put(TOATAL,data.getToatal());

        db.update(TABLE_NAME,values,ID_KEY +"=?",new String[]{String.valueOf(data.getId())});
        db.close();
    }
    public void delete(ArrayList<Data_oderdetails> id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,ID_KEY +"=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME); //delete all rows in a table
        db.close();
    }
    public int getCount(){//đếm số phần tử của Data đã ghi
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public int addAllValues(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(" + (ID_PRINCE) + "*" + (NUMBERDT)+ ") FROM " + TABLE_NAME, null);
        c.moveToFirst();
        int i=c.getInt(0);

        return i;
    }

}
