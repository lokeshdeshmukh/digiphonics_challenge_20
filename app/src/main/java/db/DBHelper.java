package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

import structures.ArtistInfo;
import structures.eventlistvo;

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
      db.execSQL("CREATE TABLE IF NOT EXISTS Upcoming_Events (" +
              "  SNO number   ," +
              "  URL varchar(1000) NOT NULL," +
              "  FORMAT varchar(100) NOT NULL," +
              "  NAME varchar(1000) NOT NULL," +
              "  DESCRIPTION varchar(2000) NOT NULL," +
              "  DATE date NOT NULL," +
              "  TITLE_IMAGE_PATH varchar(1000) NOT NULL," +
              "  ARTIST_NAME varchar(1000) NOT NULL," +
              "  localimagepath varchar(1000) ," +
              "  place varchar(1000) NOT NULL)");
      
      
      db.execSQL("CREATE TABLE IF NOT EXISTS Previous_Events (" +
              "  SNO number NOT NULL ," +
              "  URL varchar(1000) NOT NULL," +
              "  FORMAT varchar(100) NOT NULL," +
              "  NAME varchar(1000) NOT NULL," +
              "  DESCRIPTION varchar(2000) NOT NULL," +
              "  DATE date NOT NULL," +
              "  TITLE_IMAGE_PATH varchar(1000) NOT NULL," +
              "  ARTIST_NAME varchar(1000) NOT NULL," +
              "  localimagepath varchar(1000) ," +
              "  place varchar(1000) NOT NULL)");

      db.execSQL(
              "CREATE TABLE IF NOT EXISTS Audio_Files (" +
                      "  SNO number NOT NULL," +
                      "  URL varchar(1000) NOT NULL," +
                      "  FORMAT varchar(100) NOT NULL," +
                      "  NAME varchar(1000) NOT NULL," +
                      "  DESCRIPTION varchar(2000) NOT NULL," +
                      "  DATE date NOT NULL," +
                      "  TITLE_IMAGE_PATH varchar(1000) NOT NULL," +
                      "  ARTIST_NAME varchar(1000) NOT NULL," +
                      "  localimagepath varchar(1000) ," +
                      "  place varchar(1000) NOT NULL)"
      );
      db.execSQL(
              "CREATE TABLE IF NOT EXISTS `Artist_Info` (" +
                      "  SNO number NOT NULL ," +
                      "  Artist_Name varchar(1000) NOT NULL," +
                      "  Age number NOT NULL," +
                      "  DOB varchar(1000) NOT NULL," +
                      "  Story varchar(1000) NOT NULL," +
                      "  Date date NOT NULL," +
                      "  Image varchar(1000) NOT NULL," +
                      "  Gender varchar(1000) NOT NULL," +
                      "  localimagepath varchar(1000) NOT NULL)"
      );
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



    public boolean insert_data_artist (String tablename, int sno, String Artist_Name, String Age,String DOB,String Story,
                                       String Date,String Image,String Gender,String localimagepath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SNO", sno);
        contentValues.put("Artist_Name", Artist_Name);
        contentValues.put("Age", Age);
        contentValues.put("DOB", DOB);
        contentValues.put("Story", Story);
        contentValues.put("Date", Date);
        contentValues.put("Image", Image);
        contentValues.put("Gender", Gender);
        contentValues.put("localimagepath", localimagepath);


        db.insert(tablename, null, contentValues);
        db.close();
        return true;
    }
    public ArrayList<eventlistvo> get_Data(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery(query, null);

        res.moveToFirst();
        ArrayList<eventlistvo> mainobj=new ArrayList<eventlistvo>();
        while(res.isAfterLast() == false){

            eventlistvo temp=new eventlistvo();
            temp.setSno(res.getString(0));
            temp.setUrl(res.getString(1));
            temp.setFormat(res.getString(2));
            temp.setName(res.getString(3));
            temp.setDescription(res.getString(4));
            temp.setDate(res.getString(5));
            temp.setTitle_image_path(res.getString(6));
            temp.setArtist_name(res.getString(7));
            temp.setLocalimagepath(res.getString(8));
            temp.setPlace(res.getString(9));
            res.moveToNext();

            mainobj.add(temp);
        }
    db.close();
        return mainobj;
    }
    public ArrayList<ArtistInfo> get_Data_artist(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery(query, null);

        res.moveToFirst();
        ArrayList<ArtistInfo> mainobj= new ArrayList<>();
        while(res.isAfterLast() == false){

            ArtistInfo temp=new ArtistInfo();
            temp.setSno(res.getInt(0));
            temp.setArtist_Name(res.getString(1));
            temp.setAge(res.getString(2));
            temp.setDob(res.getString(3));
            temp.setStory(res.getString(4));
            temp.setDate(res.getString(5));
            temp.setImage(res.getString(6));
            temp.setGender(res.getString(7));
            temp.setLocalimagepath(res.getString(8));

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

        while(res.isAfterLast() == false){
            i=res.getInt(res.getColumnIndex("sno"));
            res.moveToNext();
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