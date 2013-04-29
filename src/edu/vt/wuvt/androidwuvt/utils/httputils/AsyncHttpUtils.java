package edu.vt.wuvt.androidwuvt.utils.httputils;

import java.util.Map;

import org.apache.http.HttpResponse;

import edu.vt.wuvt.androidwuvt.utils.httputils.GenericAsyncTask.GenericCallback;

public class AsyncHttpUtils {
	public interface AsyncHttpListener {
		public void onResponse(HttpResponse response);
	}
	public static void doGet(final String url, final AsyncHttpListener listener) {
		GenericAsyncTask<HttpResponse> asyncTask = new GenericAsyncTask<HttpResponse>(new GenericCallback<HttpResponse>() {

			@Override
			public HttpResponse toCall() {
				return HttpUtils.doGet(url);
			}

			@Override
			public void onComplete(HttpResponse result) {
				listener.onResponse(result);
				
			}

			@Override
			public void onError(Exception error) {
				// TODO Auto-generated method stub
				
			}
		});
		asyncTask.execute();
		
	}
	public static void doGet(final String url, final Map<String, String> queryString, final AsyncHttpListener listener) {
		GenericAsyncTask<HttpResponse> asyncTask = new GenericAsyncTask<HttpResponse>(new GenericCallback<HttpResponse>() {

			@Override
			public HttpResponse toCall() {
				return HttpUtils.doGet(url,queryString);
			}

			@Override
			public void onComplete(HttpResponse result) {
				listener.onResponse(result);
				
			}

			@Override
			public void onError(Exception error) {
				// TODO Auto-generated method stub
				
			}
		});
		asyncTask.execute();
	}

}
