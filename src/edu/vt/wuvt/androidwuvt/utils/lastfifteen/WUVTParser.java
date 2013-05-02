package edu.vt.wuvt.androidwuvt.utils.lastfifteen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import android.util.Log;
import android.widget.Toast;
import edu.vt.wuvt.androidwuvt.utils.httputils.AsyncHttpUtils;
import edu.vt.wuvt.androidwuvt.utils.httputils.AsyncHttpUtils.AsyncHttpErrorListener;
import edu.vt.wuvt.androidwuvt.utils.httputils.AsyncHttpUtils.AsyncHttpListener;
import edu.vt.wuvt.androidwuvt.view.mainactivity.MainActivity;


class WUVTParser {
	public static final String TAG = WUVTParser.class.getName();
	
//	public WUVTParser {
//		this.ParseAndUpdateGroup(LastFifteenGroup mFifteenGroup);
//	}
	
	public void ParseAndUpdateGroup(final LastFifteenGroup FifteenGroup) {
		AsyncHttpUtils.doGet("http://www.wuvt.vt.edu/last15", new AsyncHttpListener() {


			@Override
			public void onResponse(String response) {
				Document doc = Jsoup.parse(response);
				Element lastFifteen = doc.getElementById("last15");
				Element fifteenTable = lastFifteen.getElementById("table");
				for (int i = 2; i < 17; i = i+1) {
					String key = FifteenGroup.mFifteenKeyList.get(i);
					Element thisTRElement = fifteenTable.child(i);
					LastFifteenModel thisFifteenModel = FifteenGroup.mFifteenMap.get(key);
					
					thisFifteenModel.setTimeAired(thisTRElement.child(1).html());
					if (thisTRElement.child(2).getElementById("center").html() == "&nbsp;")
						thisFifteenModel.setIsNew(false);
					else
						thisFifteenModel.setIsNew(true);
					thisFifteenModel.setTrack(thisTRElement.child(3).html());
					thisFifteenModel.setAlbum(thisTRElement.child(4).html());
					thisFifteenModel.setDJ(thisTRElement.child(5).html());
					if (thisTRElement.child(6).getElementById("center").html() == "&nbsp;")
						thisFifteenModel.setIsRequest(false);
					else
						thisFifteenModel.setIsRequest(true);
					if (thisTRElement.child(7).getElementById("center").html() == "&nbsp;")
						thisFifteenModel.setIsVinyl(false);
					else
						thisFifteenModel.setIsVinyl(true);
					
					
						
						

					
				}
				
				
				
			}
		}, new AsyncHttpErrorListener() {
			
			@Override
			public void onError(Exception exception) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

	
}