/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package edu.vt.wuvt.androidwuvt.utils.httputils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;






/**
 * Class used for synchronous HTTP calls. Also contains the url and paths for all endpoints.
 * @author Adam Hickey
 *
 */
public class HttpUtils {		
	public static final String TAG = HttpUtils.class.getName();
	
	
	/** Empty constructor used to make calls without keys */
	public HttpUtils() {
		
	}
	
	/**
	 * Synchronous HTTP get to the specified url. Sets the apikey, salt, and signature as headers.
	 * @param apiKey the user's api key.
	 * @param salt 
	 * @param signature
	 * @param url the absolute url or the endpoint
	 * @return the http response
	 * @throws Exception 
	 */
	public static HttpResponse doGet(String url) {
		return doGet(url, new HashMap<String,String>());
	}
	/**
	 * Synchronous HTTP get to the specified url. Sets the apikey, salt, and signature as headers.
	 * @param apiKey
	 * @param salt
	 * @param signature
	 * @param url absolute url to endpoing
	 * @param queryString 
	 * @return the http response
	 * @throws Exception 
	 */
	public static HttpResponse doGet(String url, Map<String, String> queryString) {
		

		url = appendQueryStringToUrl(url, queryString);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);

		HttpResponse response = null;

		try {
			response = httpClient.execute(getRequest);
		} catch (ClientProtocolException e) {
			Log.e(TAG,e.getMessage());
		} catch (IOException e) {
			Log.e(TAG,e.getMessage());
		}

		if(response == null) {
			Log.e(TAG,"The http response was null");
		}

		return response;

	}
	
//	/**
//	 * Synchronous HTTP post to the specified url. Set's apikey, salt, and signature as headers.
//	 * @param apiKey
//	 * @param salt
//	 * @param signature
//	 * @param url absolute url to endpoing
//	 * @param queryString
//	 * @return the HTTP response
//	 */
	
//	public static HttpResponse doPost(String url, Map<String,String> queryString) {
//		return doPost(url,new MultipartEntity(), queryString);
//	}
//	/**
//	 * Synchronous HTTP post to the specified url. Set's apikey, salt, and signature as headers.
//	 * @param apiKey
//	 * @param salt
//	 * @param signature
//	 * @param url
//	 * @param entity a multipart entity that can be used for sending images to api endpoints
//	 * @param queryString
//	 * @return the http response
//	 */
//	public static HttpResponse doPost(String url, MultipartEntity entity, Map<String,String> queryString) {
//			
//			url = appendQueryStringToUrl(url, queryString);		
//			
//			
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//			HttpPost postRequest = new HttpPost(url);
//			
//			
//			
//			postRequest.setEntity(entity);
//			HttpResponse response = null;
//			
//			try {
//				response = httpClient.execute(postRequest);
//			} catch (ClientProtocolException e) {
//				throw new ARException("Couldn't create site: The HTTP response from the server was invalid.",e);
//			} catch (IOException e) {
//				throw new ARException("Couldn't create site: The HTTP connection was aborted or a problem occurred.",e);
//			}
//			
//			return response;
//
//	}

	public static String appendQueryStringToUrl(String url,
			Map<String, String> queryString) {
		url += "?";
		Iterator<Entry<String, String>> it = queryString.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) it
					.next();
			try {
				String valueToEncode = pairs.getValue();
				if(valueToEncode == null) {
					valueToEncode = "";
				}
				url += "&" + pairs.getKey() + "=" + URLEncoder.encode(valueToEncode,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG,e.getMessage());
			}
			it.remove();
		}

		return url;
	}
	

}
