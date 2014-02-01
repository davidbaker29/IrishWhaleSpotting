package com.baker.david.irishwhalespotting.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;
import android.util.Xml;

import com.baker.david.irishwhalespotting.dao.LatestSightingManager;
import com.baker.david.irishwhalespotting.domain.LatestSightingItem;

public class LatestSightingsSaxFeedParser extends AsyncTask<String,Integer,List<LatestSightingItem>> 
	implements LatestSightingsFeedParser {

    // names of the XML tags
    static final String LINK = "link";
    static final String TITLE = "title";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate"; 
    
    private URL feedUrl;
	private boolean webAccessFailure;
	
    public List<LatestSightingItem> parse() {
    	
        final LatestSightingItem currentSightingItem = new LatestSightingItem();
        RootElement root = new RootElement("rss");
        final List<LatestSightingItem> latestSightingsItems = new ArrayList<LatestSightingItem>();
        Element channel = root.getChild("channel");
        Element item = channel.getChild(ITEM);
        
        item.setEndElementListener(new EndElementListener(){
            public void end() {
            	LatestSightingItem item = new LatestSightingItem(currentSightingItem);	
                latestSightingsItems.add(item);
                LatestSightingManager.addLatestSightingItem(item);
            }
        });
        item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentSightingItem.setTitle(body);
            }
        });
        item.getChild(LINK).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentSightingItem.setLink(body);
            }
        });
        item.getChild(PUB_DATE).setEndTextElementListener(new 
        		EndTextElementListener(){
            public void end(String body) {
                currentSightingItem.setPublicationDate(body);
            }
        });
        
        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, 
            		root.getContentHandler());
        } 
        catch(NoWebAccessException e){
        	Log.d("LatestSightingsSaxFeedParser", "Catch 1 of NoWebAccessException*******");
        	this.webAccessFailure = true;
        	//throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return latestSightingsItems;
    }
    
    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } 
        catch (IOException e) {
            throw new NoWebAccessException(e);
        }
    }

	@Override
	protected List<LatestSightingItem> doInBackground(String... params) {
		List<LatestSightingItem> latestSightingsItems = null;
		try {
            this.feedUrl = new URL(params[0]);
            latestSightingsItems = this.parse();
        } 
        catch (MalformedURLException e) {
           // throw new RuntimeException(e);
        }
        catch (NoWebAccessException e) {
        	Log.d("LatestSightingsSaxFeedParser", "Catch 3 of NoWebAccessException*******");
            //throw (e);
        }
		return latestSightingsItems;
	}
	
	@Override
	protected void onPostExecute(List<LatestSightingItem> result) {
		if(this.webAccessFailure){
			//throw new NoWebAccessException();
		}
    }
}