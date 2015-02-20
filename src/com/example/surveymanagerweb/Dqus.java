package com.example.surveymanagerweb;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dqus extends Activity {

	Button a, b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dqus);

		a = (Button) findViewById(R.id.no);
		b = (Button) findViewById(R.id.yes);

		a.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(Dqus.this, MainActivity.class));

			}
		});

		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) 
			{
				new DropCreate().execute();

			}
		});
	}

	class DropCreate extends AsyncTask<String, String, String> {

		protected String doInBackground(String... args) 
		{

			try 
			{
				 String url = "http://10.0.2.2/survey_manager/drop_create.php";
				 HttpClient client = new DefaultHttpClient();
				 client.execute(new HttpGet(url));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();

			}
			finally 
			{
				Intent intent = new Intent(getApplicationContext(),Qus.class);
				intent.putExtra("q_num",1);
				startActivity(intent);
			}

			return null;
		}

	}

}
