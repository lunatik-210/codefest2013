package com.codefest2013.game.logic.tools;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.codefest2013.game.logic.WayPoint;

public class XmlLevelHandler extends DefaultHandler {
	private boolean x = false;
	private boolean y = false;
	private boolean isThrowable = false;
	private boolean id = false;
	private boolean list = false;
	private boolean object = false;
	private boolean neighbor = false;
	private boolean wayPoint = false;
	
	private int currentId = 0;
	private WayPoint newWayPoint = null;
	private List<WayPoint> wayPoints = null;
	
	public XmlLevelHandler()
	{
		super();
		setWayPoints(new ArrayList<WayPoint>());
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if( qName.equalsIgnoreCase("WayPoint") ) {
			wayPoint = true;
			newWayPoint = new WayPoint();
		}
		if( wayPoint && qName.equalsIgnoreCase("id") ) {
			id = true;
		}
		if( wayPoint && qName.equalsIgnoreCase("x") ) {
			x = true;
		}
		if( wayPoint && qName.equalsIgnoreCase("y") ) {
			y = true;
		}
		if( wayPoint && qName.equalsIgnoreCase("isThrowable") ) {
			isThrowable = true;
		}
		if( wayPoint && qName.equalsIgnoreCase("list") ) {
			list = true;
		}
		if( wayPoint && list && qName.equalsIgnoreCase("object") ) {
			object = true;
		}
		if( wayPoint && list && qName.equalsIgnoreCase("id") ) {
			neighbor = true;
		}
		super.startElement(uri, localName, qName, attributes);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if( wayPoint && qName.equalsIgnoreCase("WayPoint") ) {
			wayPoint = false;
			getWayPoints().add(currentId, newWayPoint);
			newWayPoint = null;
		}
		if( wayPoint && id && qName.equalsIgnoreCase("id") ) {
			id = false;
		}
		if( wayPoint && x && qName.equalsIgnoreCase("x") ) {
			x = false;
		}
		if( wayPoint && y && qName.equalsIgnoreCase("y") ) {
			y = false;
		}
		if( wayPoint && isThrowable && qName.equalsIgnoreCase("isThrowable") ) {
			isThrowable = false;
		}
		if( wayPoint && list && qName.equalsIgnoreCase("list") ) {
			list = false;
		}
		if( wayPoint && list && object && qName.equalsIgnoreCase("object") ) {
			object = false;
		}
		if( wayPoint && list && neighbor && qName.equalsIgnoreCase("id") ) {
			neighbor = false;
		}
		super.endElement(uri, localName, qName);
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if( wayPoint && !list && id ) {
			currentId = Integer.parseInt(new String(ch, start, length));
		}
		if( wayPoint && !list && x ) {
			newWayPoint.x = Integer.parseInt(new String(ch, start, length));
		}
		if( wayPoint && !list && y ) {
			newWayPoint.y = Integer.parseInt(new String(ch, start, length));
		}
		if( wayPoint && !list && isThrowable ) {
			String value = new String(ch, start, length);
			if( value.equalsIgnoreCase("True") ) {
				newWayPoint.isThrowable = true;
			} else {
				newWayPoint.isThrowable = false;
			}
		}
		if( wayPoint && list && object ) {
			newWayPoint.objects.add(new String(ch, start, length));
		}
		if( wayPoint && list && neighbor ) {
			newWayPoint.neighbors.add(Integer.parseInt(new String(ch, start, length)));
		}
		super.characters(ch, start, length);
	}

	public List<WayPoint> getWayPoints() {
		return wayPoints;
	}

	private void setWayPoints(List<WayPoint> wayPoints) {
		this.wayPoints = wayPoints;
	}
}