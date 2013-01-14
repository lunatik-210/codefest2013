import java.util.ArrayList;


public class Fox {
	ArrayList<WayPoint> wps;
	ArrayList<Integer> throwablePoints;
	int current;
	
	public Fox(ArrayList<WayPoint> wayPointsArray, int startIndex) {
		wps = wayPointsArray;
		current = startIndex;
		for(int i=0; i<wps.size(); i++)
		{
			if (wps.get(i).isThrowable)
			{
				throwablePoints.add(i);
			}
		}
	}
	public Fox(ArrayList<WayPoint> wayPointsArray) {
		this(wayPointsArray, 0);
	}
	
	public int selectNext()
	{
		return 0;
	}
	
}
