package DLTessellation;
import java.awt.geom.Point2D;
public class TriangleNet 
{
	private PointConf sp;
    private int n;
    private Point2D.Double[] vp;
    private Point2D.Double p1;
    private Point2D.Double p2;
    private Point2D.Double p3;
    private Point2D.Double pi1;
    private Point2D.Double pi2;
    private Point2D.Double pi3;
    private Point2D.Double v1;
    private Point2D.Double v2;
    private Point2D.Double pp;
    private double c1;
    private double c2;
    private double c3;
    private double c4;
    private double c5;
    private double c6;
    private double r;
    private double pvec;
    private int[][] net;
    private int[][] inet;
    private int cont;
    
    public TriangleNet(PointConf s)
    {
            sp = s ;
    }

    public int[][] getnet(int fl)
    {
    	if(fl == 0)
    	{
            System.out.println("TriangleNet : comienza getNet nonperiodic");
            n  = sp.getDim();
            net= new int[3][5*n];
            System.out.println("TriangleNet : n = "+n);
            vp = sp.getPoints();
            cont = 0;
            for(int i = 0; i < n-2; i++)
            {
                    //System.out.println("TriangleNet : i = "+i);
                    for(int j = i+1; j < n-1; j++)
                    {
                            //System.out.println("TriangleNet : j = "+j);
                            for(int k = j+1; k < n; k++)
                            {
                                    //System.out.println("TriangleNet : k = "+k);
                                    pi1 = new Point2D.Double(vp[i].getX(), vp[i].getY());
                                    pi2 = new Point2D.Double(vp[j].getX(), vp[j].getY());
                                    pi3 = new Point2D.Double(vp[k].getX(), vp[k].getY());
                                    
                                    v1 = new Point2D.Double(vp[j].getX()-vp[i].getX(),vp[j].getY()-vp[i].getY());
                                    v2 = new Point2D.Double(vp[k].getX()-vp[j].getX(),vp[k].getY()-vp[j].getY());
                                    pvec = v1.getX()*v2.getY() - v1.getY()*v2.getX();
                                    if(pvec < 0)
                                    {
                                            p1 = new  Point2D.Double(pi1.getX(), pi1.getY());
                                            p2 = new  Point2D.Double(pi3.getX(), pi3.getY());
                                            p3 = new  Point2D.Double(pi2.getX(), pi2.getY());
                                    }
                                    else
                                    {
                                            p1 = new  Point2D.Double(pi1.getX(), pi1.getY());
                                            p2 = new  Point2D.Double(pi2.getX(), pi2.getY());
                                            p3 = new  Point2D.Double(pi3.getX(), pi3.getY());       
                                    }
                                    
                                    int flag = 0;
                                    for(int l = 0; l < n; l++ )
                                    {
                                            //System.out.println("TriangleNet : l = "+l);
                                            if(l != i && l!= j && l!=k)
                                            {
                                                    
                                                    //System.out.println("TriangleNet  : in l = "+l);
                                                pp = new Point2D.Double(vp[l].getX(), vp[l].getY());
                                                    
                                                    c1 = (p1.getX()-pp.getX()) * (p2.getY()-pp.getY()) * ( ((p3.getX()-pp.getX())*(p3.getX()-pp.getX())) + ((p3.getY()-pp.getY())*(p3.getY()-pp.getY())) );
                                                    c2 = (p1.getY()-pp.getY()) * (p3.getX()-pp.getX()) * ( ((p2.getX()-pp.getX())*(p2.getX()-pp.getX())) + ((p2.getY()-pp.getY())*(p2.getY()-pp.getY())) ); 
                                                    c3 = (p2.getX()-pp.getX()) * (p3.getY()-pp.getY()) * ( ((p1.getX()-pp.getX())*(p1.getX()-pp.getX())) + ((p1.getY()-pp.getY())*(p1.getY()-pp.getY())) );
                                                    c4 = (p2.getY()-pp.getY()) * (p3.getX()-pp.getX()) * ( ((p1.getX()-pp.getX())*(p1.getX()-pp.getX())) + ((p1.getY()-pp.getY())*(p1.getY()-pp.getY())) );
                                                    c5 = (p1.getY()-pp.getY()) * (p2.getX()-pp.getX()) * ( ((p3.getX()-pp.getX())*(p3.getX()-pp.getX())) + ((p3.getY()-pp.getY())*(p3.getY()-pp.getY())) );
                                                    c6 = (p1.getX()-pp.getX()) * (p3.getY()-pp.getY()) * ( ((p2.getX()-pp.getX())*(p2.getX()-pp.getX())) + ((p2.getY()-pp.getY())*(p2.getY()-pp.getY())) );
                                                    r  = c1 + c2 + c3 -c4 -c5 -c6;
                                                    //System.out.println("TriangleNet : out l = "+l);
                                                    if(r > 0)
                                                    {
                                                            flag++;
                                                    }       
                                            }
                                    }
                                    if(flag == 0)
                                    {
                                            //System.out.println("TriangleNet : end i = "+i);
                                            //System.out.println("TriangleNet : end j = "+j);
                                            //System.out.println("TriangleNet : end k = "+k);
                                            //System.out.println("TriangleNet : end cont = "+cont);
                                            
                                            net[0][cont] = i;
                                            net[1][cont] = j;
                                            net[2][cont] = k;
                                            cont++;
                                    }
                            }
                    }
            }
            
    	}
    	else
    	{
    		 System.out.println("TriangleNet : comienza getNet periodic");
    		 int lg  = sp.getDim();
             inet = new int[3][15*lg];
             System.out.println("TriangleNet : lg = "+lg);
             vp = sp.getCompletePoints();
             System.out.println("TriangleNet : paso 2 ");
             n  = vp.length;
             System.out.println("periodic conditions n = "+n);
             for(int i = 0; i < n-2; i++)
             {
                    // System.out.println("TriangleNet : i = "+i);
                for(int j = i+1; j < n-1; j++)
                {
                            // System.out.println("TriangleNet : j = "+j);
                     for(int k = j+1; k < n; k++)
                     {
                    	 //System.out.println("TriangleNet : k = "+k);
                         pi1 = new Point2D.Double(vp[i].getX(), vp[i].getY());
                         pi2 = new Point2D.Double(vp[j].getX(), vp[j].getY());
                         pi3 = new Point2D.Double(vp[k].getX(), vp[k].getY());
                         
                         v1 = new Point2D.Double(vp[j].getX()-vp[i].getX(),vp[j].getY()-vp[i].getY());
                         v2 = new Point2D.Double(vp[k].getX()-vp[j].getX(),vp[k].getY()-vp[j].getY());
                         pvec = v1.getX()*v2.getY() - v1.getY()*v2.getX();
                         if(pvec < 0)
                         {
                                 p1 = new  Point2D.Double(pi1.getX(), pi1.getY());
                                 p2 = new  Point2D.Double(pi3.getX(), pi3.getY());
                                 p3 = new  Point2D.Double(pi2.getX(), pi2.getY());
                         }
                         else
                         {
                                 p1 = new  Point2D.Double(pi1.getX(), pi1.getY());
                                 p2 = new  Point2D.Double(pi2.getX(), pi2.getY());
                                 p3 = new  Point2D.Double(pi3.getX(), pi3.getY());       
                         }
                         int flag = 0;
                         for(int l = 0; l < n; l++ )
                         {
                                 //System.out.println("TriangleNet : l = "+l);
                                 if(l != i && l!= j && l!=k)
                                 {
                                         
                                         //System.out.println("TriangleNet  : in l = "+l);
                                     pp = new Point2D.Double(vp[l].getX(), vp[l].getY());
                                         
                                         c1 = (p1.getX()-pp.getX()) * (p2.getY()-pp.getY()) * ( ((p3.getX()-pp.getX())*(p3.getX()-pp.getX())) + ((p3.getY()-pp.getY())*(p3.getY()-pp.getY())) );
                                         c2 = (p1.getY()-pp.getY()) * (p3.getX()-pp.getX()) * ( ((p2.getX()-pp.getX())*(p2.getX()-pp.getX())) + ((p2.getY()-pp.getY())*(p2.getY()-pp.getY())) ); 
                                         c3 = (p2.getX()-pp.getX()) * (p3.getY()-pp.getY()) * ( ((p1.getX()-pp.getX())*(p1.getX()-pp.getX())) + ((p1.getY()-pp.getY())*(p1.getY()-pp.getY())) );
                                         c4 = (p2.getY()-pp.getY()) * (p3.getX()-pp.getX()) * ( ((p1.getX()-pp.getX())*(p1.getX()-pp.getX())) + ((p1.getY()-pp.getY())*(p1.getY()-pp.getY())) );
                                         c5 = (p1.getY()-pp.getY()) * (p2.getX()-pp.getX()) * ( ((p3.getX()-pp.getX())*(p3.getX()-pp.getX())) + ((p3.getY()-pp.getY())*(p3.getY()-pp.getY())) );
                                         c6 = (p1.getX()-pp.getX()) * (p3.getY()-pp.getY()) * ( ((p2.getX()-pp.getX())*(p2.getX()-pp.getX())) + ((p2.getY()-pp.getY())*(p2.getY()-pp.getY())) );
                                         r  = c1 + c2 + c3 -c4 -c5 -c6;
                                         //System.out.println("TriangleNet : out l = "+l);
                                         if(r > 0)
                                         {
                                                 flag++;
                                         }       
                                 }
                         }
                         if(flag == 0)
                         {
                                 System.out.println("TriangleNet : end i = "+i);
                                 System.out.println("TriangleNet : end j = "+j);
                                 System.out.println("TriangleNet : end k = "+k);
                                 System.out.println("TriangleNet : end cont = "+cont);
                                 
                                 inet[0][cont] = i;
                                 System.out.println("TriangleNet : punto 1 = ");
                                 inet[1][cont] = j;
                                 System.out.println("TriangleNet : punto 2 = ");
                                 inet[2][cont] = k;
                                 cont++;
                         }
                   }	
                }
             }
             System.out.println("TriangleNet : prune");
             int sdim = net[1].length;
             int mx = n/9;
             int cont1=0;
             int cont2;
             for(int i=0; i < sdim;i++)
             {
            	 cont2 = 0;
            	 for(int j = 0; j<2; j++)
            	 {
            		 if(inet[j][i] < mx)
            		 {
            			 cont2++;
            		 }
            	 }
            	 if(cont2 > 0)
            	 {
            		 net[0][cont1] = inet[0][i];
            		 net[1][cont1] = inet[1][i];
            		 net[2][cont1] = inet[2][i];
            		 cont1++;
            	 }
             }
    	}
            System.out.println("TriangleNet : cont = "+cont);
            return net;
    }
    public int get_dim(int flg)
    {
    	return cont;    
    }
}


