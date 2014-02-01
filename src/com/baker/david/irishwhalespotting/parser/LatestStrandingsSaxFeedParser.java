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

import com.baker.david.irishwhalespotting.dao.LatestStrandingManager;
import com.baker.david.irishwhalespotting.domain.LatestStrandingItem;

public class LatestStrandingsSaxFeedParser extends AsyncTask<String,Integer,List<LatestStrandingItem>> 
	implements LatestStrandingsFeedParser {

    // names of the XML tags
    static final String LINK = "link";
    static final String ITEM = "item";
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String PUB_DATE = "pubDate";
    
    private URL feedUrl;
	
    public List<LatestStrandingItem> parse() {
    	
        final LatestStrandingItem currentStrandingItem = new LatestStrandingItem();
        RootElement root = new RootElement("rss");
        final List<LatestStrandingItem> latestStrandingItems = new ArrayList<LatestStrandingItem>();
        Element channel = root.getChild("channel");
        Element item = channel.getChild(ITEM);
        
        item.setEndElementListener(new EndElementListener(){
            public void end() {
            	LatestStrandingItem item = new LatestStrandingItem(currentStrandingItem);	
                latestStrandingItems.add(item);
                LatestStrandingManager.addLatestStrandingItem(item);
            }
        });
        item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentStrandingItem.setTitle(body);
            }
        });
        item.getChild(LINK).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentStrandingItem.setLink(body);
            }
        });
        item.getChild(DESCRIPTION).setEndTextElementListener(new 
        		EndTextElementListener(){
            public void end(String body) {
                currentStrandingItem.setDescription(body);
            }
        });
        item.getChild(PUB_DATE).setEndTextElementListener(new 
        		EndTextElementListener(){
            public void end(String body) {
                currentStrandingItem.setPublicationDate(body);
            }
        });
        
        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, 
            		root.getContentHandler());
        } 
        catch(NoWebAccessException e){
        	Log.d("LatestStrandingsSaxFeedParser", "Catch 1 of NoWebAccessException*******");
        	//throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return latestStrandingItems;
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
	protected List<LatestStrandingItem> doInBackground(String... params) {
		List<LatestStrandingItem> latestStrandingItems = null;
		try {
            this.feedUrl = new URL(params[0]);
            latestStrandingItems = this.parse();
        } 
        catch (MalformedURLException e) {
            // throw new RuntimeException(e);
         }
         catch (NoWebAccessException e) {
         	Log.d("LatestStrandingsSaxFeedParser", "Catch 3 of NoWebAccessException*******");
             //throw (e);
         }
		return latestStrandingItems;
	}
}
