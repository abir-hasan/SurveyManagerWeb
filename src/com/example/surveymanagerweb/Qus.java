package com.example.surveymanagerweb;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List; 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class Qus extends Activity 
{
	private ProgressDialog pDialog;
	 
    JSONParser jsonParser = new JSONParser();

	EditText question;
	EditText a;
	EditText b;
	EditText c;
	EditText d;
	
	TextView display1;
	TextView display2;

	Button addQuestion;
	Button finish;
	int flag=0;
	
	private static String url_create_question = "http://10.0.2.2/survey_manager/insert_question.php";
	private static final String TAG_SUCCESS = "success";

	int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qus);
		
		Bundle q_num = getIntent().getExtras();
		
		if(q_num !=null) 
		{
		    i = q_num.getInt("q_num");
		}

		
		question = (EditText)findViewById(R.id.questionIn);
		a = (EditText)findViewById(R.id.aIn);
		b = (EditText)findViewById(R.id.bIn);
		c = (EditText)findViewById(R.id.cIn);
		d = (EditText)findViewById(R.id.dIn);
		
		display1 = (TextView) findViewById(R.id.tv1);
		display2 = (TextView) findViewById(R.id.tv2);
		display2.setText("QUESTION #"+i);
		
		addQuestion = (Button)findViewById(R.id.addQuestion);
		finish = (Button)findViewById(R.id.finish);
		
		finish.setOnClickListener(
				
		        new View.OnClickListener()
		        {
		            public void onClick(View view)
		            {
		            	flag=1;
		            	new CreateNewQuestion().execute();
		            }
		        });
		
		addQuestion.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				flag=0;
				new CreateNewQuestion().execute();	
				
			}
		});
	}
	
	/**
     * Background Async Task to Create new product
     * */
    class CreateNewQuestion extends AsyncTask<String, String, String> 
    {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Qus.this);
            pDialog.setMessage("Creating Question..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) 
        {
            String question_ = question.getText().toString();
            String a_ = a.getText().toString();
            String b_ = b.getText().toString();
            String c_ = c.getText().toString();
            String d_ = d.getText().toString();
            
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("question", question_));
            params.add(new BasicNameValuePair("a", a_));
            params.add(new BasicNameValuePair("b", b_));
            params.add(new BasicNameValuePair("c", c_));
            params.add(new BasicNameValuePair("d", d_));
 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_question,"POST", params);
 
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try 
            {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) 
                {
                	if (flag==1)
                	{
                		Intent i = new Intent(getApplicationContext(),Splash1.class);
                		startActivity(i);
                	}
                	else
                	{
                    // successfully created product
                		Intent intent = new Intent(getApplicationContext(),Qus.class);
                		i++;
                		intent.putExtra("q_num",i);
                		startActivity(intent);
                	}
 
                    // closing this screen
                    finish();
                } 
                else 
                {
                    // failed to create product
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
}





