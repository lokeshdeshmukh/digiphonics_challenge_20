package db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.google.gson.Gson;


public class GetServerDataAsynctTask extends
        AsyncTask<String, String, String> {

    protected Context context;

    int localOpportunityID;

    public GetServerDataAsynctTask(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;


    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub

        String strResult = WebserviceConnect.getdatafromserver(
                context, params[0]);
        return strResult;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);


    }

}
