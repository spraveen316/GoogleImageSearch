package com.project.googleimagesearch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GoogleImageActivity extends Activity {
	
	EditText etSearchQuery;
	GridView gvResultsGrid;
	Button bSearch;
	List<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdpater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_image);
		setupViews();
		imageAdpater = new ImageResultArrayAdapter(this, imageResults);
		gvResultsGrid.setAdapter(imageAdpater);
		gvResultsGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				intent.putExtra("url", imageResult.getFullURL());
				startActivity(intent);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.google_image, menu);
		return true;
	}
	
	public void onStatus(MenuItem menu) {
		Intent i = new Intent(this, SettingsActivity.class);
		/*i.putExtra("label", "Second Test Input");
		i.putExtra("integer", 5);*/
		startActivity(i);
		
	}
	
	public void onSearch(View v) {
		if (StringUtils.isEmpty(etSearchQuery.getText().toString())) {
			etSearchQuery.setError(getResources().getString(R.string.errorSearchString));
			return;
		}
		Toast.makeText(this, "Searching for " + etSearchQuery.getText().toString(), Toast.LENGTH_SHORT).show();
		
		AsyncHttpClient httpClient = new AsyncHttpClient();
		
		String url = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=0&v=1.0&q=" + Uri.encode(etSearchQuery.getText().toString());
		
		httpClient.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJSONResults = null;
				try {
					imageJSONResults = response.getJSONObject("responseData")
							.getJSONArray("results");
					imageResults.clear();
					imageAdpater.addAll(ImageResult
							.fromJSONArray(imageJSONResults));
					Log.d("imageResults = ", imageResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void setupViews () {
		etSearchQuery = (EditText)findViewById(R.id.etSearch);
		gvResultsGrid = (GridView)findViewById(R.id.gvSearchResult);
		bSearch = (Button)findViewById(R.id.bSearch);
		
	}

}
