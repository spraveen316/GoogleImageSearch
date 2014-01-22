package com.project.googleimagesearch;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GoogleImageActivity extends Activity {
	
	EditText etSearchQuery;
	GridView gvResultsGrid;
	Button bSearch;
	Button bLoadMore;
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
	
	public void onClickSettings(MenuItem menu) {
		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);
		
	}
	
	public void makeApiCall (int start) {
		if (StringUtils.isEmpty(etSearchQuery.getText().toString())) {
			etSearchQuery.setError(getResources().getString(R.string.errorSearchString));
			return;
		}
		
		String filters = null;
		FileInputStream fis;
		InputStream in = null;
		try {
			fis = openFileInput("settings");
			in = new BufferedInputStream(fis);
			filters = IOUtils.toString(in);
			in.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AsyncHttpClient httpClient = new AsyncHttpClient();
		
		String url = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start="+start+"&v=1.0&q="
				+ Uri.encode(etSearchQuery.getText().toString());
		
		if (!StringUtils.isEmpty(filters)) {
			url = url + "%20" + filters;
		}
		
		Log.d("url = ", url);
		
		httpClient.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJSONResults = null;
				try {
					imageJSONResults = response.getJSONObject("responseData")
							.getJSONArray("results");
					imageResults.clear();
					imageAdpater.addAll(ImageResult.fromJSONArray(imageJSONResults));
					imageResults.addAll(ImageResult.fromJSONArray(imageJSONResults));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void onSearch(View v) {
		bLoadMore.setVisibility(View.VISIBLE);
		makeApiCall(0);
	}
	
	public void onLoadMore(View v) {
		makeApiCall(imageResults.size());
	}
	
	private void setupViews () {
		etSearchQuery = (EditText)findViewById(R.id.etSearch);
		gvResultsGrid = (GridView)findViewById(R.id.gvSearchResult);
		bSearch = (Button)findViewById(R.id.bSearch);
		bLoadMore = (Button)findViewById(R.id.bLoadMore);
		bLoadMore.setVisibility(View.GONE);
	}

}
