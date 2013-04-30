package edu.vt.wuvt.androidwuvt.utils.httputils;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import edu.vt.wuvt.androidwuvt.utils.httputils.GenericAsyncTask.GenericCallback;

public class AsyncHttpUtils {
	public interface AsyncHttpListener {
		public void onResponse(String response);
	}
	public interface AsyncHttpErrorListener {
		public void onError(Exception exception);
	}
	public static void doGet(final String url, final AsyncHttpListener listener, final AsyncHttpErrorListener errorListener) {
		GenericAsyncTask<String> asyncTask = new GenericAsyncTask<String>(new GenericCallback<String>() {

			@Override
			public String toCall() {
				HttpResponse response = HttpUtils.doGet(url);
				try {
					return EntityUtils.toString(response.getEntity());
				} catch (ParseException e) {
					errorListener.onError(e);
				} catch (IOException e) {
					errorListener.onError(e);
				}
				return null;
			}

			@Override
			public void onComplete(String result) {
				listener.onResponse(result);
				
			}

			@Override
			public void onError(Exception error) {
				errorListener.onError(error);
				
			}
		});
		asyncTask.execute();
		
	}
	public static void doGet(final String url, final Map<String, String> queryString, final AsyncHttpListener listener,final AsyncHttpErrorListener errorListener) {
		GenericAsyncTask<String> asyncTask = new GenericAsyncTask<String>(new GenericCallback<String>() {

			@Override
			public String toCall() {
				HttpResponse response = HttpUtils.doGet(url,queryString);
				try {
					return EntityUtils.toString(response.getEntity());
				} catch (ParseException e) {
					errorListener.onError(e);
				} catch (IOException e) {
					errorListener.onError(e);
				}
				return null;
			}

			@Override
			public void onComplete(String result) {
				listener.onResponse(result);
				
			}

			@Override
			public void onError(Exception error) {
				errorListener.onError(error);
				
			}
		});
		asyncTask.execute();
	}

}
