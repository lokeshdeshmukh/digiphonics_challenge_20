package db;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import structures.ArtistInfo;
import structures.eventlistvo;

public class Backgroundservice extends IntentService {

   public static String[] table_name={"Artist_Info","Upcoming_Events","Previous_Events","Audio_Files"};
   public static final String REQUEST_STRING = "myRequest";
   public static final String RESPONSE_STRING = "myResponse";
   public static final String RESPONSE_MESSAGE = "myResponseMessage";

   private String URL = null;
   private static final int REGISTRATION_TIMEOUT = 3 * 1000;
   private static final int WAIT_TIMEOUT = 30 * 1000;

   public Backgroundservice() {
      super("MyWebRequestService");
   }

   @Override
   protected void onHandleIntent(Intent intent) {
      int i=0;
      for(;;)
      {

         try {
            Thread.sleep(10000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         SharedPreferences sharedpreferences;
         sharedpreferences = getSharedPreferences("simjorprivate", getApplicationContext().MODE_PRIVATE);

         if(sharedpreferences.getInt("Upcomingdata",-1)==-1) {
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putInt("Upcomingdata", 0);
            editor.putInt("Previousdata", 0);
            editor.putInt("Audiodata", 0);
            editor.commit();
         }
         else
         {

            DBHelper setdata=new DBHelper(getApplicationContext());

            int maxint_previous = setdata.maxint("select max(sno) as sno  from " + table_name[i]);

            String jsonObj =  new GetServerDataAsynctTask(getApplicationContext()).doInBackground("http://www.cchat.in/simjor/production/PHPFiles/getpreviouseventlist.php?msxint_previous=" + maxint_previous + "&table_name=" + table_name[i]);
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .registerTypeAdapter(Date.class,
                            new JsonDateDeserializer()).create();

            if(table_name[i].equalsIgnoreCase("Artist_Info"))
            {
               Type listOfTestObject = new TypeToken<ArrayList<ArtistInfo>>() {
               }.getType();
               ArrayList<ArtistInfo> contactVO = gson.fromJson(jsonObj, listOfTestObject);

               for (ArtistInfo eventlistvo1 : contactVO) {
                  setdata.insert_data_artist(table_name[i], eventlistvo1.getSno(),
                          eventlistvo1.getArtist_Name(), eventlistvo1.getAge(), eventlistvo1.getDob(),
                          eventlistvo1.getStory(), eventlistvo1.getDate(), eventlistvo1.getImage(),
                          eventlistvo1.getGender(), eventlistvo1.getLocalimagepath());
               }
            } else {
               Type listOfTestObject = new TypeToken<ArrayList<eventlistvo>>() {
               }.getType();
               ArrayList<eventlistvo> contactVO = gson.fromJson(jsonObj, listOfTestObject);


               for (eventlistvo eventlistvo1 : contactVO) {
                  setdata.insert_data(table_name[i], Integer.parseInt(eventlistvo1.getSno()), eventlistvo1.getUrl(), eventlistvo1.getFormat(), eventlistvo1.getName(), eventlistvo1.getDescription(), eventlistvo1.getDate(), eventlistvo1.getTitle_image_path(), eventlistvo1.getArtist_name(), eventlistvo1.getPlace(), "");
               }
            }
         }
         i++;

         if(i==4)
            i=0;


         Toast.makeText(getApplicationContext(),"hii",Toast.LENGTH_SHORT).show();
      }
   }
}
class temp
{
   Set<eventlistvo> temp=new HashSet<eventlistvo>();

   public Set<eventlistvo> getTemp() {
      return temp;
   }

   public void setTemp(Set<eventlistvo> temp) {
      this.temp = temp;
   }
}