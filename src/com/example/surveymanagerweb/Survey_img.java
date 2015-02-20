package com.example.surveymanagerweb;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.example.surveymanagerweb.Info.Count_type;
import com.example.surveymanagerweb.Survey.CreateNewResponse;
import com.example.surveymanagerweb.Survey.GetProductDetails;

//import com.example.surveymanagerweb.Survey.DownloadImageTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class Survey_img extends Activity {
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	private static final String url_get_question = "http://10.0.2.2/survey_manager/get_question.php";
	private static final String url_create_response = "http://10.0.2.2/survey_manager/insert_response.php";
	private static final String url_get_count_type = "http://10.0.2.2/survey_manager/count_type.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_QUESTIONNAIRE = "questions";
	private static final String TAG_QNUM = "q_num";
	private static final String TAG_QUESTION = "question";
	private static final String TAG_Q_TYPE = "q_type";
	private static final String TAG_A = "a";
	private static final String TAG_B = "b";
	private static final String TAG_C = "c";
	private static final String TAG_D = "d";

	TextView questionNum;
	TextView question;
	TextView opt1;
	TextView opt2;
	TextView opt3;
	TextView opt4;

	// ImageView i1;
	// ImageView i2;
	// ImageView i3;
	// ImageView i4;

	ImageButton i1, i2, i3, i4;

	int q_num;
	String ph_num;
	String response;
	int q_count;
	int q_type;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Bundle data = getIntent().getExtras();

		if (data != null) {
			ph_num = data.getString("ph_num");
			q_num = data.getInt("q_num");
			q_count = data.getInt("q_count");
			q_type = data.getInt("q_type");
		}

		Log.e("THIS IS THE COUNT bla", String.valueOf(q_count).toString());

		if (q_count == 0) {
			Log.e("THIS IS THE COUNT bla bla", String.valueOf(q_count)
					.toString());
			Intent intent = new Intent(getApplicationContext(), Splash2.class);
			startActivity(intent);

		}

		// Log.e("THIS IS THE COUNT bla", String.valueOf(q_count).toString());
		// Log.e("THIS IS THE Q Type", String.valueOf(q_type).toString());

		if (q_type == 0) 
		{
			new Count_type().execute();
			Log.e("THIS IS THE Q Type inside text-type question", String
					.valueOf(q_type).toString());

			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.imgb);
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			questionNum = (TextView) findViewById(R.id.questionNum);
			question = (TextView) findViewById(R.id.question);

			opt1 = (TextView) findViewById(R.id.opt1);
			opt2 = (TextView) findViewById(R.id.opt2);
			opt3 = (TextView) findViewById(R.id.opt3);
			opt4 = (TextView) findViewById(R.id.opt4);

			i1 = (ImageButton) findViewById(R.id.i1);
			i2 = (ImageButton) findViewById(R.id.i2);
			i3 = (ImageButton) findViewById(R.id.i3);
			i4 = (ImageButton) findViewById(R.id.i4);

			// Getting the next question
			new GetQuestionDetails().execute();

			i1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					response = "a";
					new CreateNewResponse().execute();
					new Count_type().execute();
					Log.e("THIS IS THE q_num inside A", String.valueOf(q_num)
							.toString());
					q_num++;
					Log.e("THIS IS THE q_num inside A", String.valueOf(q_num)
							.toString());
					q_count--;

					Intent intent = new Intent(getApplicationContext(),
							Survey_img.class);
					intent.putExtra("ph_num", ph_num);
					intent.putExtra("q_num", q_num);
					intent.putExtra("q_count", q_count);
					intent.putExtra("q_type", q_type);
					startActivity(intent);

				}
			});

			i2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					response = "b";
					new CreateNewResponse().execute();

					q_num++;
					q_count--;

					Intent intent = new Intent(getApplicationContext(),
							Survey_img.class);
					intent.putExtra("ph_num", ph_num);
					intent.putExtra("q_num", q_num);
					intent.putExtra("q_count", q_count);
					intent.putExtra("q_type", q_type);
					startActivity(intent);

				}
			});

			i3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					response = "c";
					new CreateNewResponse().execute();

					q_num++;
					q_count--;

					Intent intent = new Intent(getApplicationContext(),
							Survey_img.class);
					intent.putExtra("ph_num", ph_num);
					intent.putExtra("q_num", q_num);
					intent.putExtra("q_count", q_count);
					intent.putExtra("q_type", q_type);
					startActivity(intent);

				}
			});

			i4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					response = "d";
					new CreateNewResponse().execute();

					q_num++;
					q_count--;

					Intent intent = new Intent(getApplicationContext(),
							Survey_img.class);
					intent.putExtra("ph_num", ph_num);
					intent.putExtra("q_num", q_num);
					intent.putExtra("q_count", q_count);
					intent.putExtra("q_type", q_type);
					;
					startActivity(intent);

				}
			});

			/*
			 * Intent intent = new Intent(getApplicationContext(),
			 * Survey_img.class); intent.putExtra("ph_num", ph_num);
			 * intent.putExtra("q_num", q_num); intent.putExtra("q_count",
			 * q_count); intent.putExtra("q_type", q_type);
			 * startActivity(intent);
			 */

		} else {
			new Count_type().execute();
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.imgb);
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			Log.e("THIS IS THE Q Type inside image-type question", String
					.valueOf(q_type).toString());

			questionNum = (TextView) findViewById(R.id.questionNum);
			question = (TextView) findViewById(R.id.question);

			/*
			 * i1 = (ImageView) findViewById(R.id.i1); i2 = (ImageView)
			 * findViewById(R.id.i2); i3 = (ImageView) findViewById(R.id.i3); i4
			 * = (ImageView) findViewById(R.id.i4);
			 */

			opt1 = (TextView) findViewById(R.id.opt1);
			opt2 = (TextView) findViewById(R.id.opt2);
			opt3 = (TextView) findViewById(R.id.opt3);
			opt4 = (TextView) findViewById(R.id.opt4);

			i1 = (ImageButton) findViewById(R.id.i1);
			i2 = (ImageButton) findViewById(R.id.i2);
			i3 = (ImageButton) findViewById(R.id.i3);
			i4 = (ImageButton) findViewById(R.id.i4);

			// Getting the next question
			new GetProductDetails().execute();

			i1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					response = "a";
					new CreateNewResponse().execute();
					// new Count_type().execute();

					q_num++;
					q_count--;

					Intent intent = new Intent(getApplicationContext(),
							Survey_img.class);
					intent.putExtra("ph_num", ph_num);
					intent.putExtra("q_num", q_num);
					intent.putExtra("q_count", q_count);
					intent.putExtra("q_type", q_type);
					startActivity(intent);
				}
			});

			i2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					response = "b";
					new CreateNewResponse().execute();
					// new Count_type().execute();

					q_num++;
					q_count--;

					Intent intent = new Intent(getApplicationContext(),
							Survey_img.class);
					intent.putExtra("ph_num", ph_num);
					intent.putExtra("q_num", q_num);
					intent.putExtra("q_count", q_count);
					intent.putExtra("q_type", q_type);
					startActivity(intent);

				}
			});

			i3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					response = "c";
					new CreateNewResponse().execute();
					// new Count_type().execute();

					q_num++;
					q_count--;

					Intent intent = new Intent(getApplicationContext(),
							Survey_img.class);
					intent.putExtra("ph_num", ph_num);
					intent.putExtra("q_num", q_num);
					intent.putExtra("q_count", q_count);
					intent.putExtra("q_type", q_type);
					startActivity(intent);

				}
			});

			i4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					response = "d";
					new CreateNewResponse().execute();
					// new Count_type().execute();

					q_num++;
					q_count--;

					Intent intent = new Intent(getApplicationContext(),
							Survey_img.class);
					intent.putExtra("ph_num", ph_num);
					intent.putExtra("q_num", q_num);
					intent.putExtra("q_count", q_count);
					intent.putExtra("q_type", q_type);
					startActivity(intent);

				}
			});
		}

	}

	/**
	 * Background Async Task to Get Question for only Text type details
	 * */
	class GetQuestionDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Survey_img.this);
			pDialog.setMessage("Loading Question. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) {
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				@SuppressWarnings("deprecation")
				public void run() {
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("q_num", Integer
								.toString(q_num)));

						// getting product details by making HTTP request
						// Note that product details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_get_question, "GET", params);

						// check your log for json response
						Log.d("Single Question Details", json.toString());

						// json success tag
						success = json.getInt(TAG_SUCCESS);
						
						Log.e("MISS STARK BEFORE", "blablABLA");
						
						if (success == 1) 
						{
							// successfully received product details
							JSONArray productObj = json
									.getJSONArray(TAG_QUESTIONNAIRE); // JSON
																		// Array

							// get first product object from JSON Array
							JSONObject product = productObj.getJSONObject(0);
							
							Log.e("MISS STARK WORKING", product.getString(TAG_A));
							
							// product with this pid found
							// Edit Text

							// display product data in EditText
							questionNum.setText("Question #"
									+ product.getString(TAG_QNUM));
							question.setText(product.getString(TAG_QUESTION));

							//Context context = getApplicationContext();

							opt1.setText(product.getString(TAG_A));
							Log.e("MISS STARK WORKING", product.getString(TAG_A));
							opt2.setText(product.getString(TAG_B));
							opt3.setText(product.getString(TAG_C));
							opt4.setText(product.getString(TAG_D));

						} else 
						{
							Log.e("MISS STARK NOT WORKING", "not working");
							// product with pid not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {

			// dismiss the dialog once got all details
			pDialog.dismiss();
		}
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Survey_img.this);
			pDialog.setMessage("Loading Question. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) {
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				@SuppressWarnings("deprecation")
				public void run() {
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("q_num", Integer
								.toString(q_num)));

						// getting product details by making HTTP request
						// Note that product details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_get_question, "GET", params);

						// check your log for json response
						Log.d("Single Question Details", json.toString());

						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received product details
							JSONArray productObj = json
									.getJSONArray(TAG_QUESTIONNAIRE); // JSON
																		// Array

							// get first product object from JSON Array
							JSONObject product = productObj.getJSONObject(0);

							// product with this pid found
							// Edit Text

							// display product data in EditText
							questionNum.setText("Question #"
									+ product.getString(TAG_QNUM));
							question.setText(product.getString(TAG_QUESTION));

							new DownloadImageTask(i1).execute(product
									.getString(TAG_A));

							new DownloadImageTask(i2).execute(product
									.getString(TAG_B));

							new DownloadImageTask(i3).execute(product
									.getString(TAG_C));

							new DownloadImageTask(i4).execute(product
									.getString(TAG_D));

						} else {
							// product with pid not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {

			// dismiss the dialog once got all details
			pDialog.dismiss();

		}
	}

	/**
	 * Background Async Task to Create new Response
	 * */
	class CreateNewResponse extends AsyncTask<String, String, String> {

		protected String doInBackground(String... args) {

			String uphone_ = ph_num;
			String uresponse_ = response;
			String uqnum_ = Integer.toString(q_num - 1);

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("ph_num", uphone_));
			params.add(new BasicNameValuePair("q_num", uqnum_));
			params.add(new BasicNameValuePair("answer", uresponse_));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_response,
					"POST", params);

			// check log cat from response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					// closing this screen
					finish();
				} else {

					// closing this screen
					finish();
					// failed to create user
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}

	class Count_type extends AsyncTask<String, String, String> {

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) {

			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Check for success tag
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("q_num", Integer
								.toString(q_num + 1)));

						// getting product details by making HTTP request
						// Note that product details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_get_count_type, "GET", params);

						// check your log for json response
						Log.d("Type", json.toString());

						// json success tag
						// q_count= json.getInt("total");
						q_type = json.getInt("q_type");
						Log.e("THIS IS THE Q Type inside count type", String
								.valueOf(q_type).toString());

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}

	}

}