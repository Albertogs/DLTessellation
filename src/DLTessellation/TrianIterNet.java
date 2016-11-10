package DLTessellation;
import java.awt.geom.Point2D;

public class TrianIterNet 
{
	private PointConf sp;
	private int n, nt, nl, ntt;
	private int[][] inet;
	private int[][] enet;
	private int[][] net;
	private Point2D.Double[] vp;
	private Double[][] auxPoints = new Double[][]
			{
				{0.5, 5.0},{-4.5,-1.5},{5.5,-1.5}
			}; 
	private int[][] index = new int[][]
			{
		 		{0,1,2},{1,2,0},{0,2,1}
			};		
    private Double[][] tPoints;
	
	public TrianIterNet(PointConf s)
    {
            sp = s ;
    }
	 public int[][] getnet(int fl)
	 {
		 Double[]   px = new Double[2];
		 int[]      pt = new int[3]; 	 
		 nl  = sp.getDim();
		 System.out.println("Step 1 - nl = "+nl);	
		 if(fl == 0)
		 {
			 inet = new int[3][5*nl]; 
			 enet = new int[3][5*nl]; 
			 net  = new int[3][5*nl]; 
			 vp = sp.getPoints();
			 n = nl;
			 tPoints = new Double[n+3][2];
			 
			 System.out.println("Step 2A - n = "+n);
			 System.out.println("Step 2A - d1 = "+ tPoints.length);
			 System.out.println("Step 2A - d2 = "+ tPoints[0].length);
			 for(int i=0; i< n; i++)
			 {
				 //System.out.println("Step 2B - i = "+i);
				 tPoints[i][0] = vp[i].getX();
				 tPoints[i][1] = vp[i].getY();
			 }
		 }
		 else
		 {
			 inet = new int[3][15*nl];
			 enet = new int[3][15*nl]; 
			 net  = new int[3][15*nl]; 
			 vp   = sp.getCompletePoints();
			 n    = vp.length;
			 System.out.println("Step 2B - n = "+n);
			 for(int i = 0; i< n; i++)
			 {
				 tPoints[i][0] = vp[i].getX();
				 tPoints[i][1] = vp[i].getY();
			 }
		 } 
		 // Adding auxiliary points
		 //System.out.println("Step 2c -");
		 //System.out.println("Step 2c - d1= "+auxPoints.length);
		 //System.out.println("Step 2c - d2= "+auxPoints[0].length);
		 for(int i=0; i<3; i++)
		 {
			 //System.out.println("Step 2c - i="+i);
			 tPoints[n+i][0] = auxPoints[i][0];
			 //System.out.println("Step 2c - check");
			 tPoints[n+i][1] = auxPoints[i][1];
		 }
		 //System.out.println("Step 2c -end");
		 inet[0][0] = n;
		 inet[1][0] = n+1;
		 inet[2][0] = n+2;
		 nt = 1;
		 // insertions of points
		 //System.out.println("Step 2d - start");
		 int flag;
		 int cont;
		 for(int i=0; i<3; i++) //+++++++++
		 {
			 System.out.println("Step 2d - i ="+i);
			
			 cont = 0;
			 flag = 0;
			 while(flag == 0)
			 {
				 boolean bt = false;
				 //System.out.println("Step 2d - cont = "+ cont);
				 for(int j=0;j<3;j++)
				 {
					pt[j]=inet[j][cont];
				 }
				 //System.out.println("Step 2d - c ");
				 bt = isInside(pt, i);
				
				 if(bt == true)
				 {
					 //System.out.println("Step 2d - cont = "+cont);
					 //System.out.println("Step 2d - cont -> "+pt[0]+" - "+pt[1]+" "+pt[2]);
					 //System.out.println("-------------------------------------------------");
					 inet[0][cont] = pt[0];
					 inet[1][cont] = pt[1];
					 inet[2][cont] = i;
					 //System.out.println("Step 2d - cont -> "+inet[0][cont]+" - "+inet[1][cont]+" "+inet[2][cont]);
					 //System.out.println("-------------------------------------------------");
					 inet[0][nt]= pt[1];
					 inet[1][nt]= pt[2];
					 inet[2][nt]= i;
					 //System.out.println("Step 2d - nt = "+nt);
					 //System.out.println("Step 2d - cont -> "+inet[0][nt]+" - "+inet[1][nt]+" "+inet[2][nt]);
					 inet[0][nt+1] = pt[2];
					 inet[1][nt+1] = pt[0];
					 inet[2][nt+1] = i;
					 //System.out.println("Step 2d - nt+1 = "+(nt+1));
					 //System.out.println("Step 2d - cont -> "+inet[0][nt+1]+" - "+inet[1][nt+1]+" "+inet[2][nt+1]);
					 //System.out.println("***************************************************");
					 nt   = nt+2;
					 flag = 1;
				 }
				 cont++;
			 }
			 //System.out.println("Step 2d - end cicle ");
		 }
		 
		 /*for(int i = 0; i< nt; i++)
		 {
			 int[] vectv = findNeighbours( i, nt);
			 System.out.println("test initial net i="+i+" -> "+ vectv[0]);
			 if(vectv[0]==2)
			 {
				 System.out.println("test initial neighbours -> "+ vectv[1]+" - "+ vectv[6]); 
			 }
			 if(vectv[0]==3)
			 {
				 System.out.println("test initial neighbours -> "+ vectv[1]+" - "+ vectv[6]+" - "+vectv[11]); 
			 }
			 System.out.println("test triangle = "+inet[0][i]+" - "+inet[1][i]+" - "+inet[2][i]);
			 System.out.println("-----------------------------------------");
		 }*/
		 
		
		 System.out.println("Step 2e - start  flip");
		 System.out.println("Step 2e - start  flip: nt ="+nt);
		 flipNet(nt);
		
		// System.out.println("Step: prune - nt = "+nt);
		//System.out.println("Step: prune - nl = "+nl);
	    //Removing the triangles which contain the auxiliar points 
		 cont = 0;
		 for(int i = 0; i < nt; i++)
		 {
			 flag=0;
			 for(int j =0; j < 3; j++)
			 {
				 if(inet[j][i] == n || inet[j][i] == n+1 || inet[j][i] == n+2)
				 {
					 flag = 1;
				 }
			 }
			 if(flag == 0)
			 {
				 enet[0][cont] = inet[0][i];
				 enet[1][cont] = inet[1][i];
				 enet[2][cont] = inet[2][i];
				 cont++;
			 }
		 }
		 ntt = cont;
		 System.out.println("Step: prune - ntt = "+ntt);
		 if(fl != 0)
		 {
			 for(int i=0; i < nt; i++)
			 {
				 flag = 0;
				 for(int j=0; j < 3; j++)
				 {
					 if(enet[j][i] < nl)
					 {
						 flag = 1;
					 }
				 }
				 if(flag == 1)
				 {
					 net[0][cont] = enet[0][i];
					 net[1][cont] = enet[1][i];
					 net[2][cont] = enet[2][i];
				 cont++;
				 }	
			 }
			 ntt = cont;
			 System.out.println("Step: prune 2 - ntt = "+ntt);
		 }
		 else
		 {
			 System.out.println("Step: prune - c ");
			 for(int i= 0; i < ntt; i++)
			 {
				 net[0][i] = enet[0][i];
				 net[1][i] = enet[1][i];
				 net[2][i] = enet[2][i];
			 }
			 System.out.println("Step: prune - c end ");
		 }
		 return net;
	 }
	 
	 public void flipNet(int nnt)
	 {
		 int nn, flag;
		 boolean ans;
		 int[] vr;  
		 double pi1x, pi1y, pi2x, pi2y, pi3x, pi3y;
		 int changes = 1;
		 System.out.println("flip a ");
		 System.out.println("flip a : nnt = "+nnt);
		 //while(changes > 0)
		 //{
			 changes = 0;
			 for(int i=0; i < nnt; i++)
			 {
				 //System.out.println("flip a - while - -----------------before i = "+i);
				 vr = findNeighbours(i, nnt);
				 nn = vr[0];
				 System.out.println("flip a - while - nn = "+nn);
				
				      ans  = false;
				      flag = 0;
				      int j = 1;
				    while(flag == 0)
					//for(int j=1; j < nn+1; j++)
					{
						//System.out.println("flip a - while - n tria = "+vr[5*(j-1)+1]);
						//System.out.println("flip a - while - n c1 = "+vr[5*(j-1)+2]);
						//System.out.println("flip a - while - n c2 = "+vr[5*(j-1)+3]);
						//System.out.println("flip a - while - n et = "+vr[5*(j-1)+4]);
						//System.out.println("flip a - while - point = "+vr[5*j]);
						ans = evalTrian(i, vr[5*j]);
						//System.out.println("flip a - while - ans = "+ans);
						
						if(ans==true)
						{
							flag = 1;
							inet[0][i] = vr[5*(j-1)+2];
							inet[1][i] = vr[5*(j-1)+4];
							inet[2][i] = vr[5*j];
							
							//System.out.println("flip changed   = "+inet[0][i]+ " - "+inet[1][i]+" - "+inet[2][i] );
							
							inet[0][5*(j-1)+1] = vr[5*(j-1)+3];
							inet[1][5*(j-1)+1] = vr[5*(j-1)+4];
							inet[2][5*(j-1)+1] = vr[5*j];
							
							//System.out.println("flip changed   = "+inet[0][5*(j-1)+1]+ " - "+inet[1][5*(j-1)+1]+" - "+inet[2][5*(j-1)+1]);
							
						}
						j++;
						if(j>nn)
						{
							flag=1;
						}
						
						
					}
					
					 
					
					 /*if(ans == true)
					 {
						 flag = 1; 
						 inet[0][i] = vr[5*(j-1)+2];
						 inet[1][i] = vr[5*(j-1)+4];
						 inet[2][i] = vr[5*j];
					
						 inet[0][5*(j-1)+1] =  vr[5*(j-1)+3];
						 inet[1][5*(j-1)+1] =  vr[5*(j-1)+4];
						 inet[2][5*(j-1)+1] =  vr[5*j];
						 changes++;
					 }*/
					 //if(j == nn)
					 //{
					//	 flag = 1;
					 //}
					// System.out.println("flip a - while - changes = "+changes);
				 //}
			 }	
			 //System.out.println("Step: flip - changes = "+changes);
		 } 
	 //}
	 public boolean evalTrian(int ntrian, int npoint)
	 {
		 boolean output = false;
		 double x1, y1, x2, y2, x3, y3, pvec;
		 double c1, c2, c3, c4, c5, c6, ppx, ppy, r;
		
		 System.out.println("eval trian -----------ntrian = "+ntrian);
		 System.out.println("eval trian -----------npoint = "+npoint);
		 
		 x1 = tPoints[inet[0][ntrian]][0];
		 y1 = tPoints[inet[0][ntrian]][1];
		 
		 x2 = tPoints[inet[1][ntrian]][0];
		 y2 = tPoints[inet[1][ntrian]][1];
		 
		 x3 = tPoints[inet[2][ntrian]][0];
		 y3 = tPoints[inet[2][ntrian]][1];
		 
		 pvec = ((x2-x1)*(y3-y2)) - ((y2-y1)*(x3-x2));
		 System.out.println("eval trian -----------pvec = "+pvec);
		 if(pvec < 0)
		 {
			 System.out.println("eval trian -----------change ");
			 x1 = tPoints[inet[0][ntrian]][0];
			 y1 = tPoints[inet[0][ntrian]][1];
			 
			 x2 = tPoints[inet[2][ntrian]][0];
			 y2 = tPoints[inet[2][ntrian]][1];
			 
			 x3 = tPoints[inet[1][ntrian]][0];
			 y3 = tPoints[inet[1][ntrian]][1];
		 }
		
		 ppx = tPoints[npoint][0];
		 ppy = tPoints[npoint][1];
		  
          
          /*c1 = (x1-ppx) * (y2-ppy) * ( ((x3-ppx)*(x3-ppx)) + ((y3-ppy)*(y3-ppy)) );
          c2 = (y1-ppy) * (x3-ppx) * ( ((x2-ppx)*(x2-ppx)) + ((y2-ppy)*(y2-ppy)) ); 
          c3 = (x2-ppx) * (y3-ppy) * ( ((x1-ppx)*(x1-ppx)) + ((y1-ppy)*(y1-ppy)) );
          c4 = (y2-ppy) * (x3-ppx) * ( ((x1-ppx)*(x1-ppx)) + ((y1-ppy)*(y1-ppy)) );
          c5 = (y1-ppy) * (x2-ppx) * ( ((x3-ppx)*(x3-ppx)) + ((y3-ppy)*(y3-ppy)) );
          c6 = (x1-ppx) * (y3-ppy) * ( ((x2-ppx)*(x2-ppx)) + ((y2-ppy)*(y2-ppy)) );
          */
          c1 = (x1-ppx) * (y2-ppy) * ( (x3-ppx)*(x3-ppx) + (y3-ppy)*(y3-ppy) );
          c2 = (y1-ppy) * (x3-ppx) * ( (x2-ppx)*(x2-ppx) + (y2-ppy)*(y2-ppy) ); 
          c3 = (x2-ppx) * (y3-ppy) * ( (x1-ppx)*(x1-ppx) + (y1-ppy)*(y1-ppy) );
          c4 = (y2-ppy) * (x3-ppx) * ( (x1-ppx)*(x1-ppx) + (y1-ppy)*(y1-ppy) );
          c5 = (y1-ppy) * (x2-ppx) * ( (x3-ppx)*(x3-ppx) + (y3-ppy)*(y3-ppy) );
          c6 = (x1-ppx) * (y3-ppy) * ( (x2-ppx)*(x2-ppx) + (y2-ppy)*(y2-ppy) );
          
          
         
          
          r  = c1 + c2 + c3 -c4 -c5 -c6;
          System.out.println("eval trian ------------- r = "+r);
          if(r > 0 || Math.abs(r)< 0.00000001)
          {
                  output = true;
          }       
          
		 return output;
	 }
	 public int[] findNeighbours(int input, int nnt)
	 {
		 //System.out.println("find neighbour ------------- input = "+input);
		 
		 int  idx;
		 int[] p1 = new int[3];
		 int[] q1 = new int[3];
		 
		 int nn   = 0;
		 int[] output = new int[16];
		 
		 p1[0] = inet[0][input];
		 p1[1] = inet[1][input];
		 p1[2] = inet[2][input];
		 //System.out.println("input triangle = "+p1[0]+" - "+p1[1]+" - "+p1[2]);
		 for(int i = 0; i< nnt; i++)
		 {
			 //System.out.println("find neighbour - i = "+i);
			 if(input != i)
			 {
				 
				 q1[0] = inet[0][i];
				 q1[1] = inet[1][i];
				 q1[2] = inet[2][i];
				 
				 if((p1[0]==q1[0] && p1[1]==q1[1]) || (p1[0]==q1[1] && p1[1]==q1[0]))
				 {
					//System.out.println("find neighbour - i = "+i);
					 idx = 5*nn; 
					 output[idx+1] = i;
					 output[idx+2] = p1[0];
					 output[idx+3] = p1[1];
					 output[idx+4] = p1[2];
					 output[idx+5] = q1[2];
					 nn++;
				 }
				 if((p1[0]==q1[0] && p1[1]==q1[2]) || (p1[0]==q1[2] && p1[1]==q1[0]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 idx = 5*nn; 
					 output[idx+1] = i;
					 output[idx+2] = p1[0];
					 output[idx+3] = p1[1];
					 output[idx+4] = p1[2];
					 output[idx+5] = q1[1];
					 nn++;
				 }
				 if((p1[0]==q1[1] && p1[1]==q1[2]) || (p1[0]==q1[2] && p1[1]==q1[1]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 idx = 5*nn; 
					 output[idx+1] = i;
					 output[idx+2] = p1[0];
					 output[idx+3] = p1[1];
					 output[idx+4] = p1[2];
					 output[idx+5] = q1[0];
					 nn++;
				 }
				 //-----------
				 if((p1[0]==q1[0] && p1[2]==q1[1]) || (p1[0]==q1[1] && p1[2]==q1[0]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 idx = 5*nn; 
					 output[idx+1] = i;
					 output[idx+2] = p1[0];
					 output[idx+3] = p1[2];
					 output[idx+4] = p1[1];
					 output[idx+5] = q1[2];
					 nn++;
				 }
				 if((p1[0]==q1[0] && p1[2]==q1[2]) || (p1[0]==q1[2] && p1[2]==q1[0]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 idx = 5*nn; 
					 output[idx+1] = i;
					 output[idx+2] = p1[0];
					 output[idx+3] = p1[2];
					 output[idx+4] = p1[1];
					 output[idx+5] = q1[1];
					 nn++;
				 }
				 if((p1[0]==q1[1] && p1[2]==q1[2]) || (p1[0]==q1[2] && p1[2]==q1[1]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 idx = 5*nn; 
					 output[idx+1] = i;
					 output[idx+2] = p1[0];
					 output[idx+3] = p1[2];
					 output[idx+4] = p1[1];
					 output[idx+5] = q1[0];
					 nn++;
				 }
				 //---------------
				 if((p1[1]==q1[0] && p1[2]==q1[1]) || (p1[1]==q1[1] && p1[2]==q1[0]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 idx = 5*nn; 
					 output[idx+1] = i;
					 output[idx+2] = p1[1];
					 output[idx+3] = p1[2];
					 output[idx+4] = p1[0];
					 output[idx+5] = q1[2];
					 nn++;
				 }
				 if((p1[1]==q1[0] && p1[2]==q1[2]) || (p1[1]==q1[2] && p1[2]==q1[0]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 idx = 5*nn; 
					 output[idx+1] = i;
					 output[idx+2] = p1[1];
					 output[idx+3] = p1[2];
					 output[idx+4] = p1[0];
					 output[idx+5] = q1[1];
					 nn++;
				 }
				 if((p1[1]==q1[1] && p1[2]==q1[2]) || (p1[1]==q1[2] && p1[2]==q1[1]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 idx = 5*nn; 
					 output[idx+1] = i;
					 output[idx+2] = p1[1];
					 output[idx+3] = p1[2];
					 output[idx+4] = p1[0];
					 output[idx+5] = q1[0];
					 nn++;
				 }
						
			 }
			 
		 }
		 output[0] = nn;
		 if(nn >3)
		 {
			 System.out.println("find neighbour --------------------------------- exit nn = "+nn);
		 }
		 return output;
	 }
	 public boolean isInside(int[] tr, int po)
	 {
		 boolean ans = false;
		 double x1, x2, y1, y2, pr1, pr2, pr3;
		 int j;
		 int cont1 = 0;
		 int cont2 = 0;
		 
		 pr1 = (tPoints[tr[1]][0]-tPoints[tr[0]][0])*(tPoints[po][1]-tPoints[tr[0]][1]) - (tPoints[tr[1]][1]-tPoints[tr[0]][1])*(tPoints[po][0]-tPoints[tr[0]][0]);
		 pr2 = (tPoints[tr[2]][0]-tPoints[tr[1]][0])*(tPoints[po][1]-tPoints[tr[1]][1]) - (tPoints[tr[2]][1]-tPoints[tr[1]][1])*(tPoints[po][0]-tPoints[tr[1]][0]);
		 pr3 = (tPoints[tr[0]][0]-tPoints[tr[2]][0])*(tPoints[po][1]-tPoints[tr[2]][1]) - (tPoints[tr[0]][1]-tPoints[tr[2]][1])*(tPoints[po][0]-tPoints[tr[2]][0]);
		 //for(int i=0; i<3; i++)
		// {
		//	 x2 = tPoints[po][0]-tPoints[tr[i]][0];
		//	 y2 = tPoints[po][1]-tPoints[tr[i]][1];
		//	 j = i+1;
		//	 if(j > 2)
		//	 {
		//		 j=0;
		//	 }
		//	 x1 = tPoints[tr[j]][0]-tPoints[tr[i]][0];
		//	 y1 = tPoints[tr[j]][1]-tPoints[tr[i]][1];
		//	 pr = x2*y1-x1*y2;
			 if(pr1 > 0.0 && pr2 > 0.0 && pr3 > 0.0)
			 {
				 ans=true;
			 }
			 else if(pr1 < 0.0 && pr2 < 0.0 && pr3 < 0.0)
			 {
				 ans=true;
			 }
			 //else if(Math.abs(pr) < 0.0000001)
			 //{
			 //	 cont1=10;
			 //}
		 
		
		 return ans;
	 }
	 
	 
	 
}
