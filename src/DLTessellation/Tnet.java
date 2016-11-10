package DLTessellation;
import java.awt.geom.Point2D;

public class Tnet 
{
	private int nl, n, nt, ntt;
	private PointConf sp;
	private int[][] inet,enet,net;
	private Point2D.Double[] vp;
	private Double[][] tPoints;
	private Double[][] auxPoints = new Double[][]
			{
				{0.5, 5.0},{-4.0,-1.5},{5.0,-1.5}
			};
	public Tnet(PointConf s)
	{
		sp = s ;
	}
//--------------------------------------------------	
	public int[][] getnet(int fl)
//--------------------------------------------------	
	 {
		System.out.println("getnet: fl ="+fl);
		if(fl ==0)
		{
			 nl  = sp.getDim();
			 inet = new int[3][5*nl]; 
			 enet = new int[3][5*nl];
			 net  = new int[3][5*nl]; 
			 vp = sp.getPoints();
			 n = nl;
			 tPoints = new Double[n+3][2];
			 System.out.println("getnet:1 n ="+n);
			 for(int i=0; i< n; i++)
			 {
				 tPoints[i][0] = vp[i].getX();
				 tPoints[i][1] = vp[i].getY();
			 }
		}
		else if(fl == 1)
		{
			 vp   = sp.getCompletePoints();
			 nl   = vp.length;
			 n    = nl;	 
			 inet = new int[3][10*nl];
			 enet = new int[3][10*nl];
			 net  = new int[3][10*nl]; 
			 tPoints = new Double[n+3][2];
			 System.out.println("getnet:2 n ="+n);
			 for(int i=0; i< n; i++)
			 {
				 tPoints[i][0] = vp[i].getX();
				 tPoints[i][1] = vp[i].getY();
			 }
		}
		System.out.println("getnet:step 2 n ="+n);
		for(int i=0; i<3; i++)
		{
			System.out.println("getnet:step 2 i ="+i);
			 tPoints[n+i][0] = auxPoints[i][0];
			 tPoints[n+i][1] = auxPoints[i][1];
		}
		System.out.println("getnet:step 3 n ="+n);
		inet[0][0] = n;
		inet[1][0] = n+1;
		inet[2][0] = n+2;
		System.out.println("getnet:step 4 n ="+n);
		nt = 1;
		System.out.println("getnet: 5 -----");
		int flag;
		int cont;
		int[] pt = new int[3];
		System.out.println("getnet: 6 ----- n ="+n);
		
		for(int i= 0; i < n; i++)
		{
			//System.out.println("getnet: 4a ----- i= "+i);
			 cont = 0;
			 flag = 0;
			 while(flag == 0)
			 {
				 int boo = 0;
				 //System.out.println("getnet: 4a ----- cont= "+cont);
				 
				 pt[0]=inet[0][cont];
				 pt[1]=inet[1][cont];
				 pt[2]=inet[2][cont];
				 //System.out.println("getnet: 4b ----- ");
				 boo = isInside(pt, i);
				 //System.out.println("getnet: 4b ----- boo = "+boo);
				 if(boo==1)
				 {
					 
					 inet[0][cont] = pt[0];
					 inet[1][cont] = pt[1];
					 inet[2][cont] = i;
					
					 inet[0][nt]= pt[1];
					 inet[1][nt]= pt[2];
					 inet[2][nt]= i;
					 
					 inet[0][nt+1] = pt[2];
					 inet[1][nt+1] = pt[0];
					 inet[2][nt+1] = i;
					 
					 nt   = nt+2;
					 flag = 1;
				 }
				 cont++;
				 if(cont >nt)
				 {
					 flag = 1;
					 System.out.println("getnet: there is no triangle ");
				 }
			 }
		}
		System.out.println("getnet: 5 -- nt ="+nt);
		
		
		for(int i = 0; i< nt; i++)
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
		 }
		
		
		 flipNet(nt);
		
		cont = 0;
		//Removing the triangles which contain the auxiliary points
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
				 //System.out.println("getnet: 5 -- i ="+i+" -> "+enet[0][i]+" - "+enet[1][i]+" - "+enet[2][i]);
				 cont++;
			 }
		 }
		 /*for(int i = 0; i < 100; i++)
		 {
			 System.out.println("Tnet: test 1 -> i="+i+" -> "+enet[0][i]+" - "+enet[1][i]+" - "+enet[2][i]);
		 }*/
		 inet = null;
		 ntt = cont;
		 System.out.println("getnet: 0 cut -- ntt ="+ntt);
//		 if(fl == 0)
//		 {
			 for(int i = 0; i < ntt ; i++)
			 {
				 net[0][i] = enet[0][i];
				 net[1][i] = enet[1][i];
				 net[2][i] = enet[2][i];
			 }
			 enet = null;
			/* for(int i = 0; i < 100; i++)
			 {
				 System.out.println("Tnet: test 2 A -> i="+i+" -> "+net[0][i]+" - "+net[1][i]+" - "+net[2][i]);
			 }*/
//		 }
//		 else if(fl == 1)
//		 {
//			
//			 cont   = 0;
//			 int nr = n /9;
//			 System.out.println("getnet:1 cut -- nr ="+nr);
//			 for(int i = 0; i < ntt; i++)
//			 {
//				 if(enet[0][i] < nr || enet[1][i] < nr || enet[2][i] < nr)
//				 {
//					 net[0][cont] = enet[0][i];
//					 net[1][cont] = enet[1][i];
//					 net[2][cont] = enet[2][i];
//					 cont++;
//				 }
//			 }
//			 enet = null;
			 /*for(int i = 0; i < 100; i++)
			 {
				 System.out.println("Tnet: test 2 B -> i="+i+" -> "+net[0][i]+" - "+net[1][i]+" - "+net[2][i]);
			 }*/
//		 } 
		 
		 ntt = cont;
		 System.out.println("getnet: 2 cut -- ntt ="+ntt);
		return net;
	 }
//--------------------------------------------------	
	 public void flipNet(int nnt)
//-------------------------------------------------- 
	 {
		 int nn, flag;
		 boolean ans;
		 int[]  vr;  
		 int ntria,c1, c2, et, pc, cuentas;
		 int changes = 1;
		 cuentas =0;
		 System.out.println("flip a : nnt = "+nnt);
		 while(changes > 0)
		 {
			 changes = 0;
			 for(int i=0; i < nnt; i++)
			 {
				 //System.out.println("flip a - while - -----------------before i = "+i);
				 vr = findNeighbours(i, nnt);
				 nn = vr[0];
				 //System.out.println("flip a - while - > "+inet[0][i]+" - "+inet[1][i]+" - "+inet[2][i]);
				 //System.out.println("flip a - while - nn = "+nn);
				
				      ans  = false;
				      flag = 0;
				      int j = 1;
				    while(flag == 0)
					{
				    	
				    	ntria = vr[5*(j-1)+1];
				    	c1    = vr[5*(j-1)+2];
				    	c2    = vr[5*(j-1)+3];
				    	et    = vr[5*(j-1)+4];
				    	pc    = vr[5*j];
				    	
						//System.out.println("flip a - while - n tria = "+vr[5*(j-1)+1]);
						//System.out.println("flip a - while - n c1 = "+vr[5*(j-1)+2]);
						//System.out.println("flip a - while - n c2 = "+vr[5*(j-1)+3]);
						//System.out.println("flip a - while - n et = "+vr[5*(j-1)+4]);
						//System.out.println("flip a - while - point = "+vr[5*j]);
						
						ans = evalTrian(i, pc);
						//System.out.println("flip a - while - ans = "+ans);
						
						if(ans==true)
						{
							flag = 1;
							inet[0][i] = c1;
							inet[1][i] = et;
							inet[2][i] = pc;
							
							//System.out.println("flip changed   = "+inet[0][i]+ " - "+inet[1][i]+" - "+inet[2][i] );
							
							inet[0][ntria] = c2;
							inet[1][ntria] = et;
							inet[2][ntria] = pc;
							
							//System.out.println("flip changed   = "+inet[0][5*(j-1)+1]+ " - "+inet[1][5*(j-1)+1]+" - "+inet[2][5*(j-1)+1]);
							changes++;
						}
						j++;
						if(j>nn)
						{
							flag=1;
						}
					}
			    }
			 	cuentas++;
			 	if(cuentas > 20)
			 	{
			 		changes =0;
			 	}
			 
			 }	
		 
		 System.out.println("flip: cuentas = "+cuentas);
		 } 
//--------------------------------------------------------	 
	 public boolean evalTrian(int ntrian, int npoint)
//--------------------------------------------------------	 
	 {
		 boolean output = false;
		 double x1, y1, x2, y2, x3, y3, pvec;
		 double c1, c2, c3, c4, c5, c6, ppx, ppy, r;
		
		 //System.out.println("eval trian -----------ntrian = "+ntrian);
		 //System.out.println("eval trian -----------npoint = "+npoint);
		 
		 x1 = tPoints[inet[0][ntrian]][0];
		 y1 = tPoints[inet[0][ntrian]][1];
		 
		 x2 = tPoints[inet[1][ntrian]][0];
		 y2 = tPoints[inet[1][ntrian]][1];
		 
		 x3 = tPoints[inet[2][ntrian]][0];
		 y3 = tPoints[inet[2][ntrian]][1];
		 
		 pvec = ((x2-x1)*(y3-y2)) - ((y2-y1)*(x3-x2));
		 //System.out.println("eval trian -----------pvec = "+pvec);
		 if(pvec < 0)
		 {
			 //System.out.println("eval trian -----------change ");
			 x1 = tPoints[inet[0][ntrian]][0];
			 y1 = tPoints[inet[0][ntrian]][1];
			 
			 x2 = tPoints[inet[2][ntrian]][0];
			 y2 = tPoints[inet[2][ntrian]][1];
			 
			 x3 = tPoints[inet[1][ntrian]][0];
			 y3 = tPoints[inet[1][ntrian]][1];
		 }
		
		 ppx = tPoints[npoint][0];
		 ppy = tPoints[npoint][1];
		  
      
          c1 = (x1-ppx) * (y2-ppy) * ( (x3-ppx)*(x3-ppx) + (y3-ppy)*(y3-ppy) );
          c2 = (y1-ppy) * (x3-ppx) * ( (x2-ppx)*(x2-ppx) + (y2-ppy)*(y2-ppy) ); 
          c3 = (x2-ppx) * (y3-ppy) * ( (x1-ppx)*(x1-ppx) + (y1-ppy)*(y1-ppy) );
          c4 = (y2-ppy) * (x3-ppx) * ( (x1-ppx)*(x1-ppx) + (y1-ppy)*(y1-ppy) );
          c5 = (y1-ppy) * (x2-ppx) * ( (x3-ppx)*(x3-ppx) + (y3-ppy)*(y3-ppy) );
          c6 = (x1-ppx) * (y3-ppy) * ( (x2-ppx)*(x2-ppx) + (y2-ppy)*(y2-ppy) );
          
          
          r  = c1 + c2 + c3 -c4 -c5 -c6;
          //System.out.println("eval trian ------------- r = "+r);
          if(r > 0 || Math.abs(r)< 0.00000001)
          {
                  output = true;
          }       
          
		 return output;
	 }
//--------------------------------------------------
	 public int getRealDim()
//---------------------------------------------------	 
	 {
		 return ntt;
	 }
//--------------------------------------------------
	 public int isInside(int[] tr, int po)
//--------------------------------------------------	 
	 {
		 int ans = 0;
		 double x1, x2, y1, y2, x3, y3, px, py ,pr1, pr2, pr3;
		 //int j;
		 //int cont1 = 0;
		 //int cont2 = 0;
		 
		 x1 = tPoints[tr[0]][0];
		 y1 = tPoints[tr[0]][1];
		 x2 = tPoints[tr[1]][0];
		 y2 = tPoints[tr[1]][1];
		 x3 = tPoints[tr[2]][0];
		 y3 = tPoints[tr[2]][1];
		 px = tPoints[po][0];
		 py = tPoints[po][1];
		 
	     //pr1 = (x2-x1)*(py-y1) - (y2-y1) * (px-x1);
	     //pr2 = (x3-x2)*(py-y2) - (y3-y2) * (px-x2);
	     //pr3 = (x1-x3)*(py-y3) - (y1-y3) * (px-x3);
		 pr1 =  ((y2 - y3)*(px - x3) + (x3 - x2)*(py - y3)) / ((y2 - y3)*(x1 - x3) + (x3 - x2)*(y1 - y3));
		 pr2 =  ((y3 - y1)*(px - x3) + (x1 - x3)*(py - y3)) / ((y2 - y3)*(x1 - x3) + (x3 - x2)*(y1 - y3));
		 pr3 = 1 - pr1-pr2;
		 
		 if(0.0 <= pr1 && pr1 <= 1.0 && 0.0 <= pr2 && pr2 <= 1.0 && 0.0 <= pr3 && pr3 <= 1.0)
		 {
			 ans=1;
		 }
		 
		 
		//if(pr1 > 0.0 && pr2 > 0.0 && pr3 > 0.0)
	    //{
	    //ans=1;
		// }
	    //else if(pr1 < 0.0 && pr2 < 0.0 && pr3 < 0.0)
	     //{
		    //ans=1;
			 //}
			 
		 return ans;
	 }
	 public int[] findNeighbours(int input, int nnt)
	 {
		 //System.out.println("find neighbour ------------- input = "+input);
		 
		 int  idx;
		 int[] p1 = new int[3];
		 int[] q1 = new int[3];
		 
		 int nn   = 0;
		 int[] output = new int[100];
		 
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
					 if(nn < 4)
					 {
						 idx = 5*nn; 
						 output[idx+1] = i;
						 output[idx+2] = p1[0];
						 output[idx+3] = p1[1];
						 output[idx+4] = p1[2];
						 output[idx+5] = q1[2];
						 nn++;
					 }
				 }
				 if((p1[0]==q1[1] && p1[1]==q1[2]) || (p1[0]==q1[2] && p1[1]==q1[1]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 if(nn < 4)
					 {
						 idx = 5*nn; 
						 output[idx+1] = i;
						 output[idx+2] = p1[0];
						 output[idx+3] = p1[1];
						 output[idx+4] = p1[2];
						 output[idx+5] = q1[0];
						 nn++;
					 }
				 }
				 if((p1[0]==q1[2] && p1[1]==q1[0]) || (p1[0]==q1[0] && p1[1]==q1[2]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 if(nn < 4)
					 {
						 idx = 5*nn; 
						 output[idx+1] = i;
						 output[idx+2] = p1[0];
						 output[idx+3] = p1[1];
						 output[idx+4] = p1[2];
						 output[idx+5] = q1[1];
						 nn++;
					 }
				 }
				 //-----------
				 if((p1[1]==q1[0] && p1[2]==q1[1]) || (p1[1]==q1[1] && p1[2]==q1[0]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 if(nn < 4)
					 {
						 idx = 5*nn; 
						 output[idx+1] = i;
						 output[idx+2] = p1[1];
						 output[idx+3] = p1[2];
						 output[idx+4] = p1[0];
						 output[idx+5] = q1[2];
						 nn++;
					 }
				 }
				 if((p1[1]==q1[1] && p1[2]==q1[2]) || (p1[1]==q1[2] && p1[2]==q1[1]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 if(nn < 4)
					 {
						 idx = 5*nn; 
						 output[idx+1] = i;
						 output[idx+2] = p1[1];
						 output[idx+3] = p1[2];
						 output[idx+4] = p1[0];
						 output[idx+5] = q1[0];
						 nn++;
					 }
				 }
				 if((p1[1]==q1[2] && p1[2]==q1[0]) || (p1[1]==q1[0] && p1[2]==q1[2]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 if(nn < 4)
					 {
						 idx = 5*nn; 
						 output[idx+1] = i;
						 output[idx+2] = p1[1];
						 output[idx+3] = p1[2];
						 output[idx+4] = p1[0];
						 output[idx+5] = q1[1];
						 nn++;
					 }
				 }
				 //---------------
				 if((p1[2]==q1[0] && p1[0]==q1[1]) || (p1[2]==q1[1] && p1[0]==q1[0]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 if(nn < 4)
					 {
						 idx = 5*nn; 
						 output[idx+1] = i;
						 output[idx+2] = p1[2];
						 output[idx+3] = p1[0];
						 output[idx+4] = p1[1];
						 output[idx+5] = q1[2];
						 nn++;
					 }
				 }
				 if((p1[2]==q1[1] && p1[0]==q1[2]) || (p1[2]==q1[2] && p1[0]==q1[1]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 if(nn < 4)
					 {
						 idx = 5*nn; 
						 output[idx+1] = i;
						 output[idx+2] = p1[2];
						 output[idx+3] = p1[0];
						 output[idx+4] = p1[1];
						 output[idx+5] = q1[0];
						 nn++;
					 }
				 }
				 if((p1[2]==q1[2] && p1[0]==q1[0]) || (p1[2]==q1[0] && p1[0]==q1[2]))
				 {
					 //System.out.println("find neighbour - i = "+i);
					 if(nn < 4)
					 {
						 idx = 5*nn; 
						 output[idx+1] = i;
						 output[idx+2] = p1[2];
						 output[idx+3] = p1[0];
						 output[idx+4] = p1[1];
						 output[idx+5] = q1[1];
						 nn++;
					 }
				 }
						
			 }
			 
		 }
		 output[0] = nn;
		 //if(nn >3)
		 //{
		//	 System.out.println("find neighbour --------------------------------- exit nn = "+nn);
		// }
		 return output;
	 }	
	
	
}
