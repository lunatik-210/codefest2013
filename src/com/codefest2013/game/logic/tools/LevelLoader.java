package com.codefest2013.game.logic.tools;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.codefest2013.game.logic.WayPoint;
import com.codefest2013.game.managers.ResourceManager;

import android.content.res.AssetManager;

public class LevelLoader {
	private ResourceManager mResourceManager = ResourceManager.getInstance();
	
	static private LevelLoader Instance = new LevelLoader();
	
	private LevelLoader() {
	}

	public List<WayPoint> load(String fileName) {
		AssetManager assetsManager = mResourceManager.context.getAssets();
		InputStream inputStream;
		
		SAXParserFactory factory = null;
		SAXParser saxParser = null;
		XmlLevelHandler handler = null;
		try {
			factory = SAXParserFactory.newInstance();
			saxParser = factory.newSAXParser();
			handler = new XmlLevelHandler();
			inputStream = assetsManager.open(fileName);
			saxParser.parse(inputStream, handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return handler.getWayPoints();
	}

	public static LevelLoader getInstance() {
		return Instance;
	}

}
