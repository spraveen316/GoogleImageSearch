package com.project.googleimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends Activity implements OnItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public void onSave(View v) {
		
//		Spinner spinner = (Spinner) findViewById(R.id.sImageSize);
//		// Create an ArrayAdapter using the string array and a default spinner layout
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//		        R.string.imageSize, android.R.layout.simple_spinner_item);
//		// Specify the layout to use when the list of choices appears
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		// Apply the adapter to the spinner
//		spinner.setAdapter(adapter);
//		
		
		Spinner imageSizeSpinner = (Spinner) findViewById(R.id.sImageSize);
		String imageSize = imageSizeSpinner.getSelectedItem().toString();
		
		Spinner colorFilterSpinner = (Spinner) findViewById(R.id.sColorFilter);
		String colorFilter = colorFilterSpinner.getSelectedItem().toString();
		
		Spinner imageTypeSpinner = (Spinner) findViewById(R.id.sImageType);
		String imageType = imageTypeSpinner.getSelectedItem().toString();
		
		StringBuffer filters = new StringBuffer();
		filters.append(imageSize == null ? null : imageSize);
		filters.append(colorFilter == null ? null : colorFilter);
		filters.append(imageType == null ? null : imageType);
		
		Toast.makeText(this, "Selected filters:" + filters.toString(), Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(this, GoogleImageActivity.class);
		i.putExtra("label", "Second Test Input");
		i.putExtra("integer", 5);
		startActivity(i);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Spinner spinner = (Spinner) findViewById(R.id.sImageSize);
		spinner.setOnItemSelectedListener(this);
		String value = spinner.getSelectedItem().toString();
		Toast.makeText(this, "Selected:" + value, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
