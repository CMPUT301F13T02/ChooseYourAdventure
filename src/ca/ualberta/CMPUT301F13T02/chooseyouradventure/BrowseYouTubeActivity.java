/*
* Copyright (c) 2013, TeamCMPUT301F13T02
* All rights reserved.
* 
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
* 
* Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
* 
* Redistributions in binary form must reproduce the above copyright notice, this
* list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
* 
* Neither the name of the {organization} nor the names of its
* contributors may be used to endorse or promote products derived from
* this software without specific prior written permission.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * Activity that displays and allows a user to navigate through a web site.
 * It provides two buttons; one called cancel that finishes the activity
 * and returns nothing and one that finishes the activity but returns the
 * url the webView is currently displaying.
 * 
 * @author jbmoore
 */
public class BrowseYouTubeActivity extends Activity {
	
	private WebView webPage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_youtube);
	}
	
	/** Loads a web page into this activities webView and sets listeners
	 * for the two buttons, one that cancels and the other that returns
	 * the current url.
	 * 
	 * This activity expects an intent with a tag/value pair of the following
	 * format:
	 * 		tag: url
	 * 		value: the url to start the webView at.
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		
		webPage = (WebView) findViewById(R.id.webView1);
		
		Intent intent = getIntent();
		String url = (String) intent.getSerializableExtra("url");
		
		WebSettings settings = webPage.getSettings();
		settings.setJavaScriptEnabled(true);
		webPage.setWebViewClient(new WebViewClient() {
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            return (false);
	        }
		});
		
		webPage.loadUrl(url);
		
		Button cancel = (Button) findViewById(R.id.cancel_button);
		Button select = (Button) findViewById(R.id.select_button);
		
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				returnNothing();
			}
		});
		
		select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				returnURL();
			}
		});
	}
	
	/**
	 * Simply ends the activity, returning nothing.
	 */
	private void returnNothing() {
		Log.d("cancel", "Cancel pressed");
		finish();
	}
	
	/**
	 * Reads the current url from the webView and sends that back to the 
	 * calling activity in an intent under the tag "returnUrl".
	 */
	private void returnURL() {
		Log.d("select", "Select pressed");
		String url = webPage.getUrl();
		Intent returnIntent = new Intent();
		returnIntent.putExtra("returnUrl", url);
		Log.d("selected page", url);
		if(getParent() == null) {
			setResult(RESULT_OK, returnIntent);
		} else {
			getParent().setResult(RESULT_OK, returnIntent);
		}
		finish();
	}
}
