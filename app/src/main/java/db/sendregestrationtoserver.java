package db;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.android.materialdesigncodelab.ChatBox;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.jar.Attributes;

import structures.Master_Message1;
import structures.Name_Master;
import utils.ChatBoxAdapter;


public class sendregestrationtoserver extends
        AsyncTask<String, String, String> {

    protected Context context;

    int localOpportunityID;
    Name_Master temp;
    ProgressDialog progressDialog;
    public sendregestrationtoserver(Context context, Name_Master data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        temp=data;
        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle("Sending message");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        String url="http://www.cchat.in/digiphonics/production" +
                "/PHPFiles/setsender.php?NAME="+temp.getNAME()
                +"&MOBILE_NUMBER="+temp.getMOBILE_NUMBER()+"&DATE="+temp.getDATE()+"&GENDER="+temp.getGENDER();

        String strResult = WebserviceConnect.getdatafromserver(
                context, url);
        return strResult;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

        if(progressDialog!=null)
            if(progressDialog.isShowing())
                progressDialog.cancel();

        DBHelper setdata = new DBHelper(context);
        try {

            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .registerTypeAdapter(Date.class,
                            new JsonDateDeserializer()).create();

            Type listOfTestObject = new TypeToken<ArrayList<Name_Master>>() {
            }.getType();
            ArrayList<Name_Master> contactVO = gson.fromJson(result, listOfTestObject);

            for (Name_Master eventlistvo1 : contactVO) {
                setdata.insert_data_Name_Master("Name_Master", eventlistvo1.getSNO(),
                        eventlistvo1.getNAME(), eventlistvo1.getMOBILE_NUMBER(), eventlistvo1.getDATE(),
                        eventlistvo1.getGENDER());
            }
        }

        catch (Exception e)
        {

        }

    }

}
