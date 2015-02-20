package com.example.surveymanagerweb;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class Info extends Activity 
{
	private ProgressDialog pDialog;
	 
    JSONParser jsonParser = new JSONParser();
	Button next;
	EditText userName;
	EditText userPhone;
	int q_count;
	int q_num=1;
	int q_type;
	
	private static String url_create_user = "http://10.0.2.2/survey_manager/insert_user.php";
	private static final String TAG_SUCCESS = "success";
	private static final String url_get_count = "http://10.0.2.2/survey_manager/count.php";
	private static final String url_get_count_type = "http://10.0.2.2/survey_manager/count_type.php";


	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
		
		next = (Button) findViewById(R.id.next);
		userName = (EditText) findViewById(R.id.userName);
		userPhone = (EditText) findViewById(R.id.userPhone);
		
		new GetCount().execute();
		new Count_type().execute();
		
		
		
		next.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				
				
				new CreateNewUser().execute();

				
			}
		});
		
		
	}
	/**
     * Background Async Task to Create new user
     * */
    class CreateNewUser extends AsyncTask<String, String, String> 
    {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Info.this);
            pDialog.setMessage("Logging in..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating user
         * */
        protected String doInBackground(String... args) 
        {
            
            String uphone_ = userPhone.getText().toString();
            String uname_ = userName.getText().toString();
            
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ph_num", uphone_));
            params.add(new BasicNameValuePair("name", uname_));


 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_user,"POST", params);
 
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try 
            {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) 
                {
                	Intent intent = new Intent(getApplicationContext(),Survey_img.class);
    				intent.putExtra("ph_num",userPhone.getText().toString());
    				intent.putExtra("q_num",1);
    				intent.putExtra("q_count",q_count);
    				intent.putExtra("q_type",q_type);
    				startActivity(intent);    				
 
                    // closing this screen
                    finish();
                } 
                else 
                {
                	Intent intent = new Intent(getApplicationContext(),MainActivity.class);
    				startActivity(intent);    				

                    // closing this screen
                    finish();
                    // failed to create user
                }
            } 
            catch (JSONException e) 
            {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) 
        {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
 
    }
    
	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetCount extends AsyncTask<String, String, String> {


		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) 
		{

			// updating UI from Background Thread
			runOnUiThread(new Runnable() 
			{
				public void run() 
				{
					// Check for success tag
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						//params.add(new BasicNameValuePair("q_num", Integer.toString(q_num)));

						// getting product details by making HTTP request
						// Note that product details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_get_count, "GET", params);

						// check your log for json response
						Log.d("Count", json.toString());
						
						// json success tag
						q_count= json.getInt("total");
						Log.e("THIS IS THE Count in side info", String.valueOf(q_count).toString());
						//q_type= json.getInt("q_type");
						
					} 
					catch (JSONException e) 
					{
						e.printStackTrace();
					}
				}
			});

			return null;
		}

	}
	
	/**
	 * Background Async Task to Get complete product details
	 * */
	class Count_type extends AsyncTask<String, String, String> {


		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) 
		{

			// updating UI from Background Thread
			runOnUiThread(new Runnable() 
			{
				public void run() 
				{
					// Check for success tag
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("q_num", Integer.toString(q_num)));

						// getting product details by making HTTP request
						// Note that product details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_get_count_type, "GET", params);

						// check your log for json response
						Log.d("Type", json.toString());
						
						// json success tag
						//q_count= json.getInt("total");
						q_type= json.getInt("q_type");
						Log.e("THIS IS THE Q Type inside info", String.valueOf(q_type).toString());
						
					} 
					catch (JSONException e) 
					{
						e.printStackTrace();
					}
				}
			});

			return null;
		}

	}
	
	
	
	

}
