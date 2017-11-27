// Part of glib: https://github.com/gnmmarechal/glib
package glib.math;
import java.lang.*;

public class Geometry {
	// Euclidian distances
	public static double euclidianDistance(double[] coords1, double[] coords2) throws Exception// This method calculates euclidian distances between points in coordinates.length dimensions
	{
		if (coords1.length != coords2.length) throw new IllegalArgumentException("Coordinates dimension mismatch!");
		double[] dists = new double[coords1.length];
		for (int i = 0; i < coords1.length; i++)
			dists[i] = coords2[i] - coords1[i];
		double finalDist = 0;
		for (double dist : dists)
			finalDist += dist*dist;
		return Math.sqrt(finalDist);
	}
	public static double euclidianDistance(double x1, double y1, double x2, double y2) throws Exception
	{
		double[][] coords = { {x1, y1}, {x2, y2}};
		return euclidianDistance(coords[0], coords[1]);
	}
	public static double euclidianDistance(double x1, double y1, double z1, double x2, double y2, double z2) throws Exception
	{
		double[][] coords = {{ x1, y1, z1}, { x2, y2, z2}};
		return euclidianDistance(coords[0], coords[1]);
	}
}
