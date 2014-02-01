package com.baker.david.irishwhalespotting.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

import com.baker.david.irishwhalespotting.dao.LatestNewsManager;
import com.baker.david.irishwhalespotting.domain.LatestNewsItem;

public class LatestNewsSaxFeedParser
	implements LatestNewsFeedParser {

    // names of the XML tags
    static final String DESCRIPTION = "description";
    static final String LINK = "link";
    static final String TITLE = "title";
    static final String ITEM = "item";
    static final String AUTHOR = "author";
    static final String CATEGORY= "category"; 
    static final String PUB_DATE = "pubDate"; 
    
    private static final String LATEST_NEWS_RSS_URL =
			"http://www.iwdg.ie/index.php?option=com_k2&view=itemlist&task=category&id=1&Itemid=93&format=feed";
    
    private URL feedUrl;
    
    public List<LatestNewsItem> parse() {
    	
        try {
			this.feedUrl = new URL(LATEST_NEWS_RSS_URL);
		} 
        catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
    	
        final LatestNewsItem currentNewsItem = new LatestNewsItem();
        RootElement root = new RootElement("rss");
        final List<LatestNewsItem> latestNewsItems = new ArrayList<LatestNewsItem>();
        Element channel = root.getChild("channel");
        Element item = channel.getChild(ITEM);
        
        item.setEndElementListener(new EndElementListener(){
            public void end() {
            	LatestNewsItem item = new LatestNewsItem(currentNewsItem);	
                latestNewsItems.add(item);
                LatestNewsManager.addLatestNewsItem(item);
            }
        });
        item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentNewsItem.setTitle(body);
            }
        });
        item.getChild(LINK).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentNewsItem.setLink(body);
            }
        });
        item.getChild(DESCRIPTION).setEndTextElementListener(new 
        		EndTextElementListener(){
            public void end(String body) {
                currentNewsItem.setDescription(body);
            }
        });
        item.getChild(AUTHOR).setEndTextElementListener(new 
        		EndTextElementListener(){
            public void end(String body) {
                currentNewsItem.setAuthor(body);
            }
        });
        item.getChild(CATEGORY).setEndTextElementListener(new 
        		EndTextElementListener(){
            public void end(String body) {
                currentNewsItem.setCategory(body);
            }
        });
        item.getChild(PUB_DATE).setEndTextElementListener(new 
        		EndTextElementListener(){
            public void end(String body) {
                currentNewsItem.setPublicationDate(body);
            }
        });
        
        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, 
            		root.getContentHandler());
        } 
        catch(NoWebAccessException e){
        	throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return latestNewsItems;
    }
    
    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } 
        catch (IOException e) {
            throw new NoWebAccessException(e);
        }
    }
}