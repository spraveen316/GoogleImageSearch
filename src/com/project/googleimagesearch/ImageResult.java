package com.project.googleimagesearch;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {

	private String fullURL;
	private String thumbnailURL;
	
	public ImageResult (JSONObject json) {
		try {
			this.fullURL = json.getString("url");
			this.thumbnailURL = json.getString("tbUrl");
		} catch (JSONException e) {
			System.out.println("Exception thrown getting from json" + e);
			this.fullURL = null;
			this.fullURL = null;
		}
	}

	public String getFullURL() {
		return fullURL;
	}

	public String getThumbnailURL() {
		return thumbnailURL;
	}

	@Override
	public String toString() {
		return "ImageResult [fullURL=" + fullURL + ", thumbnailURL="
				+ thumbnailURL + "]";
	}

	public static List<ImageResult> fromJSONArray(JSONArray imageJSONResults) {
	
		List<ImageResult> results = new ArrayList<ImageResult>();

		for (int i = 0; i < imageJSONResults.length(); i++) {
			try {
				results.add(new ImageResult(imageJSONResults.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return results;
	}

}
