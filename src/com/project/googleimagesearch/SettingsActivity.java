package com.project.googleimagesearch;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
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
		
		Spinner imageSizeSpinner = (Spinner) findViewById(R.id.sImageSize);
		String imageSize = imageSizeSpinner.getSelectedItem().toString();
		
		Spinner colorFilterSpinner = (Spinner) findViewById(R.id.sColorFilter);
		String colorFilter = colorFilterSpinner.getSelectedItem().toString();
		
		Spinner imageTypeSpinner = (Spinner) findViewById(R.id.sImageType);
		String imageType = imageTypeSpinner.getSelectedItem().toString();
		
		EditText etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		String siteFilter = null;
		if (etSiteFilter != null && etSiteFilter.getText() != null) {
			siteFilter = etSiteFilter.getText().toString();
		}
		
		StringBuffer filters = new StringBuffer();
		filters.append(imageSize == null ? null : imageSize + "%20");
		filters.append(colorFilter == null ? null : colorFilter + "%20");
		filters.append(imageType == null ? null : imageType + "%20");
		filters.append(siteFilter == null ? null : siteFilter + ":%20");
		
		Toast.makeText(this, "Saving filters:" + filters.toString(), Toast.LENGTH_SHORT).show();
		
		if (!StringUtils.isEmpty(filters)) {
			String FILENAME = "settings";

			FileOutputStream fos;
			try {
				fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
				fos.write(filters.toString().getBytes());
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		Intent i = new Intent(this, GoogleImageActivity.class);
//		i.putExtra("filters", filters.toString());
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
