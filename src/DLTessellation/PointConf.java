package DLTessellation;
import java.awt.geom.Point2D;
import java.util.Random;
//import java.awt.*;
//import java.awt.geom.Point2D;
public class PointConf 
{
	int dm, dcm;
	Point2D.Double[] vp, vcp;
	double x;
	double y;
	Double[][] operations= new Double[][] {
		{0.0, 0.0},
		{1.0, 0.0},
		{1.0, 1.0},
		{0.0, 1.0},
		{-1.0, 1.0},
		{-1.0, 0.0},
		{-1.0,-1.0},
		{0.0, -1.0},
		{1.0, -1.0}
	};
	public PointConf(Point2D.Double[] vp2)
	{
		vp = vp2;
		dm =vp2.length;
	}
	public PointConf(int dim)
	{
	 	dm = dim;
	 	vp = new Point2D.Double[dm];
	 	System.out.println("Constructor dim = "+dim);
	 	Random rn = new Random();
	 	for(int i = 0; i < dm; i++)
	 	{
	 		x = rn.nextDouble();
	 		y = rn.nextDouble();
	 		vp[i] = new Point2D.Double(x, y); 
	 	}
	}
	public Point2D.Double[] getPoints()
 	{
 		return vp;
 	}
	public int getDim()
	{
		return dm;
	}
	public Point2D.Double[] getCompletePoints()
	{
		
		dcm = 9*dm;
		vcp = new Point2D.Double[dcm];
		
		int indx;
		for(int i=0 ; i< 9; i++)
		{
			
			for(int j=0; j< dm; j++)
			{
				
				x    = vp[j].getX() + operations[i][0];
				y    = vp[j].getY() + operations[i][1];
				indx = j + i*dm;
				vcp[indx]= new Point2D.Double(x, y);
			}
		}
		
		return vcp;
	}
}

