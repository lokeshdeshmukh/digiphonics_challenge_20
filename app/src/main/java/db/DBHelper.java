package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

import structures.Master_Message1;
import structures.Master_Message1;
import structures.Name_Master;


public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "chatapplication.db";
   public static final String CONTACTS_TABLE_NAME = "contacts";
   public static final String CONTACTS_COLUMN_ID = "id";
   public static final String CONTACTS_COLUMN_NAME = "name";
   public static final String CONTACTS_COLUMN_EMAIL = "email";
   public static final String CONTACTS_COLUMN_STREET = "street";
   public static final String CONTACTS_COLUMN_CITY = "place";
   public static final String CONTACTS_COLUMN_PHONE = "phone";
   private HashMap hp;

   public DBHelper(Context context) {
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      // TODO Auto-generated method stub
      db.execSQL(
              "create table contacts " +
                      "(id integer primary key, name text,phone text,email text, street text,place text)"
      );
      db.execSQL("CREATE TABLE IF NOT EXISTS Master_Message (" +
              "  SNO number   ," +
              "  RECIEVER varchar(1000) NOT NULL," +
              "  SENDER varchar(100) NOT NULL," +
              "  MESSAGE varchar(1000) NOT NULL," +
              "  DATE varchar(2000) NOT NULL," +
              "  TIME varchar(2000) NOT NULL," +
              "  GENDER varchar(1000) NOT NULL)");
       db.execSQL("CREATE TABLE IF NOT EXISTS Name_Master (" +
               "  SNO number   ," +
               "  NAME varchar(1000) NOT NULL," +
               "  MOBILE_NUMBER varchar(100) NOT NULL," +
               "  DATE varchar(1000) NOT NULL," +
               "  GENDER varchar(2000) NOT NULL)");
      

   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
      db.execSQL("DROP TABLE IF EXISTS contacts");
      onCreate(db);
   }

   public boolean insertContact (String name, String phone, String email, String street,String place) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("name", name);
      contentValues.put("phone", phone);
      contentValues.put("email", email);	
      contentValues.put("street", street);
      contentValues.put("place", place);
      db.insert("contacts", null, contentValues);
      return true;
   }
   public boolean insert_data (String tablename, int sno, String url, String format,String name,String description,String date,String TITLE_IMAGE_PATH,String artist_name,String place,String def3) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("SNO", sno);
      contentValues.put("URL", url);
      contentValues.put("FORMAT", format);
      contentValues.put("NAME", name);
      contentValues.put("DESCRIPTION", description);
       contentValues.put("DATE", date);
       contentValues.put("TITLE_IMAGE_PATH", TITLE_IMAGE_PATH);
       contentValues.put("ARTIST_NAME", artist_name);
   //    contentValues.put("localimagepath", def2);
       contentValues.put("place", place);

      db.insert(tablename, null, contentValues);
       db.close();
      return true;
   }



    public boolean insert_data_Master (String tablename, int SNO, String RECIEVER, String SENDER,String MESSAGE,String DATE,
                                       String TIME,String GENDER) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SNO", SNO);
        contentValues.put("RECIEVER", RECIEVER);
        contentValues.put("SENDER", SENDER);
        contentValues.put("MESSAGE", MESSAGE);
        contentValues.put("DATE", DATE);
        contentValues.put("TIME", TIME);
        contentValues.put("GENDER", GENDER);



        db.insert(tablename, null, contentValues);
        db.close();
        return true;
    }
    public boolean insert_data_Name_Master (String tablename, int SNO, String NAME, String MOBILE_NUMBER,String DATE,String GENDER) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SNO", SNO);
        contentValues.put("NAME", NAME);
        contentValues.put("MOBILE_NUMBER", MOBILE_NUMBER);
        contentValues.put("DATE", DATE);
        contentValues.put("GENDER", GENDER);




        db.insert(tablename, null, contentValues);
        db.close();
        return true;
    }

    public ArrayList<Master_Message1> get_Data_Master(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery(query, null);

        res.moveToFirst();
        ArrayList<Master_Message1> mainobj= new ArrayList<>();
        while(res.isAfterLast() == false){

            Master_Message1 temp=new Master_Message1();
            temp.setSNO(res.getInt(0));
            temp.setRECIEVER(res.getString(1));
            temp.setSENDER(res.getString(2));
            temp.setMESSAGE(res.getString(3));
            temp.setDATE(res.getString(4));
            temp.setTIME(res.getString(5));
            temp.setGENDER(res.getString(6));


            res.moveToNext();

            mainobj.add(temp);
        }
        db.close();
        return mainobj;
    }
    public ArrayList<Name_Master> get_Data_Name_Master(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery(query, null);

        res.moveToFirst();
        ArrayList<Name_Master> mainobj= new ArrayList<>();
        while(res.isAfterLast() == false){

            Name_Master temp=new Name_Master();
            temp.setSNO(res.getInt(0));
            temp.setNAME(res.getString(1));
            temp.setMOBILE_NUMBER(res.getString(2));
            temp.setDATE(res.getString(3));
            temp.setGENDER(res.getString(4));


            res.moveToNext();

            mainobj.add(temp);
        }
        db.close();
        return mainobj;
    }

    public int maxint(String query)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);

        int i=-1;
        res.moveToFirst();
        try {
            while (res.isAfterLast() == false) {
                i = res.getInt(res.getColumnIndex("sno"));
                res.moveToNext();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    db.close();
        return i;
    }
   public Cursor getData(int id) {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
      return res;
   }
   
   public int numberOfRows(){
      SQLiteDatabase db = this.getReadableDatabase();
      int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
      return numRows;
   }
   
   public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("name", name);
      contentValues.put("phone", phone);
      contentValues.put("email", email);
      contentValues.put("street", street);
      contentValues.put("place", place);
      db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
      return true;
   }

   public Integer deleteContact (Integer id) {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete("contacts", 
      "id = ? ", 
      new String[] { Integer.toString(id) });
   }
   
   public ArrayList<String> getAllCotacts() {
      ArrayList<String> array_list = new ArrayList<String>();
      
      //hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from contacts", null );
      res.moveToFirst();
      
      while(res.isAfterLast() == false){
         array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
         res.moveToNext();
      }
      return array_list;
   }
}