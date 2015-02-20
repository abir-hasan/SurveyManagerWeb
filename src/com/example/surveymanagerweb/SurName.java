package com.example.surveymanagerweb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurName extends Activity {

	Button a;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.surname);
		
		a = (Button) findViewById(R.id.surButt);
		
		a.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(SurName.this, Survey.class));
				
			}
		});
	}

}
