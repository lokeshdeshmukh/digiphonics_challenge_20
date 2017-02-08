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

import structures.Master_Message1;
import utils.ChatBoxAdapter;


public class sendmessagetoserver extends
        AsyncTask<String, String, String> {

    protected Context context;

    int localOpportunityID;
    Master_Message1 temp;
    ProgressDialog progressDialog;
    public sendmessagetoserver(Context context,Master_Message1 data) {
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
                "/PHPFiles/setmessagedata.php?RECIEVER="+temp.getRECIEVER()
                +"&SENDER="+temp.getSENDER()+"&MESSAGE="+temp.getMESSAGE()+"&DATE="+temp.getDATE()
                +"&TIME="+temp.getTIME();

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

            Type listOfTestObject = new TypeToken<ArrayList<Master_Message1>>() {
            }.getType();
            ArrayList<Master_Message1> contactVO = gson.fromJson(result, listOfTestObject);

            for (Master_Message1 eventlistvo1 : contactVO) {
                setdata.insert_data_Master("Master_Message", eventlistvo1.getSNO(),
                        eventlistvo1.getRECIEVER(), eventlistvo1.getSENDER(), eventlistvo1.getMESSAGE(),
                        eventlistvo1.getDATE(), eventlistvo1.getTIME(), eventlistvo1.getGENDER());
            }
        }

        catch (Exception e)
        {

        }
        String sql = "Select * from Master_Message where (RECIEVER in('"+temp.getRECIEVER()+"') and SENDER in('"
                + ChatBox.mynum+"')) or (RECIEVER in('"+ChatBox.mynum+"') and SENDER in('"+temp.getRECIEVER()+"')) order by SNO asc";

         ArrayList<Master_Message1> master_messages = setdata.get_Data_Master(sql);
        ChatBoxAdapter chatBoxAdapter = new ChatBoxAdapter(context,master_messages);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        ChatBox.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        ChatBox.recyclerView.setAdapter(chatBoxAdapter);
        ChatBox.recyclerView.requestFocus();
    }

}
