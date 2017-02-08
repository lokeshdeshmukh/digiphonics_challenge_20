package db;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import com.example.android.materialdesigncodelab.ListContentFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



import structures.Master_Message1;
import structures.Master_Message1;
import structures.Name_Master;


public class Backgroundservice extends IntentService {

   public static String[] table_name={"Master_Message","Name_Master"};
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
         sharedpreferences = getSharedPreferences("digiphonics", getApplicationContext().MODE_PRIVATE);



            DBHelper setdata=new DBHelper(getApplicationContext());

            int maxint_previous = setdata.maxint("select max(sno) as sno  from " + table_name[i]);

            String jsonObj =  new GetServerDataAsynctTask(getApplicationContext()).doInBackground("http://www.cchat.in/digiphonics/production/PHPFiles/getpreviouseventlist.php?msxint_previous=" + maxint_previous + "&table_name=" + table_name[i]);
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .registerTypeAdapter(Date.class,
                            new JsonDateDeserializer()).create();

            if(table_name[i].equalsIgnoreCase("Master_Message"))
            {
               Type listOfTestObject = new TypeToken<ArrayList<Master_Message1>>() {
               }.getType();
               ArrayList<Master_Message1> contactVO = gson.fromJson(jsonObj, listOfTestObject);

               for (Master_Message1 eventlistvo1 : contactVO) {
                  setdata.insert_data_Master(table_name[i], eventlistvo1.getSNO(),
                          eventlistvo1.getRECIEVER(), eventlistvo1.getSENDER(), eventlistvo1.getMESSAGE(),
                          eventlistvo1.getDATE(), eventlistvo1.getTIME(), eventlistvo1.getGENDER());
               }
            }
            else if(table_name[i].equalsIgnoreCase("Name_Master"))
            {
               Type listOfTestObject = new TypeToken<ArrayList<Name_Master>>() {
               }.getType();
               ArrayList<Name_Master> contactVO = gson.fromJson(jsonObj, listOfTestObject);

               for (Name_Master eventlistvo1 : contactVO) {
                  setdata.insert_data_Name_Master(table_name[i], eventlistvo1.getSNO(),
                          eventlistvo1.getNAME(), eventlistvo1.getMOBILE_NUMBER(), eventlistvo1.getDATE(),
                          eventlistvo1.getGENDER());
               }
               //smit update list
               if(contactVO.size()>0){
                  ListContentFragment listContentFragment = new ListContentFragment();
                  listContentFragment.setUpAdapter(contactVO);
               }
            }

         i++;

         if(i==2)
            i=0;


         Toast.makeText(getApplicationContext(),"hii",Toast.LENGTH_SHORT).show();
      }
   }
}
