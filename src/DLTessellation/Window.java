package DLTessellation;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

//import java.util.Random;
import java.awt.geom.*;
import java.awt.geom.Point2D;
import java.io.*;
//import javax.imageio.*;

//import java.nio.charset.Charset;
import java.nio.file.*;
//import java.util.*;
import javax.swing.JOptionPane;
public class Window extends JFrame 

{

	private String title = "De Launay Tesellation";
	private BufferedImage graphicsContext;
	private RenderingHints antialiasing;
	private JLabel  contextRender;
	private JFrame frame;
	private JPanel contentPanel;// = new JPanel();
	private JMenuBar menuBar;
	private JMenu menuConf, menuOptions, menuImage;
	private JMenuItem newConf, loadConf, saveConf, menuExit;
	private JMenuItem numberPart, periodic, nonperiodic;
	private JMenuItem imageSave;
	private JFileChooser fileChooser = null;
	private BufferedReader br;
	private Graphics2D g;
	private int width   = 500;
	private int height  = 500;
	private int padding = 50;
	private int dim = 200;
	private Point2D.Double[]  vp;
	private Point2D.Double[]  vpt;
	private PointConf pf;
    private Tnet tn;
	private int n_net;
	int[][] net;
	
	
	
	public Window()
	{
		System.out.println("Pasa 1");
		antialiasing    = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		graphicsContext = new BufferedImage(width+(2*padding), width+(2*padding), BufferedImage.TYPE_INT_RGB);
		contextRender   = new JLabel(new ImageIcon(graphicsContext));
		contentPanel    = new JPanel();
		contentPanel.add(contextRender);
		contentPanel.setSize(width+padding*2,height+padding*2);
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		System.out.println("Pasa 2");
		menuConf     = new JMenu("Configuration");
		menuOptions  = new JMenu("Options");
		menuImage    = new JMenu("Image");
		menuBar.add(menuConf);
		menuBar.add(menuOptions);
		menuBar.add(menuImage);
		System.out.println("Pasa 3");
		newConf   = new JMenuItem("New Configuration");
		loadConf  = new JMenuItem("Load Configuration");
		saveConf  = new JMenuItem("Save Configuration");
		menuExit  = new JMenuItem("Exit");
		menuConf.add(newConf);
		menuConf.add(loadConf);
		menuConf.add(saveConf);
		menuConf.add(menuExit);
		
		numberPart  = new JMenuItem("Number of points");
		periodic    = new JMenuItem("Periodic conditions");
		nonperiodic = new JMenuItem("Non periodic conditions");
		menuOptions.add(numberPart);
		menuOptions.add(periodic);
		menuOptions.add(nonperiodic);
		imageSave = new JMenuItem("Save image");
		menuImage.add(imageSave);
		
			
		this.setTitle("Delaunay triangulation");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setContentPane(contentPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.initial();
		this.setVisible(true);
		
		menuExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		newConf.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pf = new PointConf(dim);
			    vp = pf.getPoints();
			    drawCircles(vp);
			  
			}
	     });
		loadConf.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadFile();
			}
		});
		saveConf.addActionListener(new ActionListener()
		{		
			public void actionPerformed(ActionEvent e)
			{
				saveFile();
			}	
		});
		numberPart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				popNumber();
			}
		});
		
		nonperiodic.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				if(pf != null)
				{
					if(net != null)
					{
						net = null;
						drawCircles(vp);		
					}
					
					drawTriangles(0);
				}
				else
				{
					System.out.println("First create a point confguration");
					JOptionPane.showMessageDialog(null,"First create a point configuration","Alert",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		periodic.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(pf != null)
				{
					if(net != null)
					{
						net = null;
						drawCircles(vp);		
					}
					drawTriangles(1);
				}
				else
				{
					System.out.println("First create a point confguration");
					JOptionPane.showMessageDialog(null,"First create a point configuration","Alert",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		imageSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				saveImage();
			}
		});
		
	}
	public void popNumber()
	{
		
	    SliderMenuItem sld = new SliderMenuItem();
		dim = sld.getValue();
		System.out.println("popNumber: dim = "+dim);
		
	}
	public void saveImage()
	{
		JFileChooser fileChooser = new JFileChooser(); 
		int retv = fileChooser.showSaveDialog(this);
		if(retv == fileChooser.APPROVE_OPTION)
		{
			Path pth = fileChooser.getSelectedFile().toPath();
			JOptionPane.showMessageDialog(null, pth.toString());
			try
			{
				ImageIO.write(graphicsContext, "png", new File(pth.toString()));
			}
			catch(Exception ioe)
			{
				System.out.println("Error trying to create the image file");
			}
		}
	}
	public void drawTriangles(int flg)
	{
		double x1,x2,y1,y2;
		Line2D li1, li2,li3;
		tn  = new Tnet(pf);
		//int[][] net = tn.getnet(flg);
		net = tn.getnet(flg);
		int dimt = tn.getRealDim();
		System.out.println("drawTriangles: dimt = "+dimt);
		contentPanel.repaint();
		if(flg == 0)
		{
			g.setColor(Color.GREEN);
			
			for(int i =0; i< dimt; i++)
	        {
				    //System.out.println("drawTriangles: for i= "+i);
	                x1 = vp[net[0][i]].getX()*(width + width/100) + padding;
	                y1 = vp[net[0][i]].getY()*(height + height/100)+ padding;
	                x2 = vp[net[1][i]].getX()*(width + width/100) + padding;
	                y2 = vp[net[1][i]].getY()*(height + height/100)+ padding;
	                li1 = new Line2D.Double(x1, y1, x2, y2);
	                g.draw(li1);
	                
	                x1 = vp[net[1][i]].getX()*(width + width/100) + padding;
	                y1 = vp[net[1][i]].getY()*(height + height/100)+ padding;
	                x2 = vp[net[2][i]].getX()*(width + width/100) + padding;
	                y2 = vp[net[2][i]].getY()*(height + height/100)+ padding;
	                li2 = new Line2D.Double(x1, y1, x2, y2);
	                g.draw(li2);
	                
	                x1 = vp[net[2][i]].getX()*(width + width/100) + padding;
	                y1 = vp[net[2][i]].getY()*(height + height/100)+ padding;
	                x2 = vp[net[0][i]].getX()*(width + width/100) + padding;
	                y2 = vp[net[0][i]].getY()*(height + height/100)+ padding;
	                li3 = new Line2D.Double(x1, y1, x2, y2);
	                g.draw(li3);   
	        }
			
            vpt=null;
		}
		else
		{
			vpt = pf.getCompletePoints();
			g.setColor(Color.RED);
			System.out.println("window: ****  dimt= "+dimt);
			Double x,y;
			
			
			for(int i =0; i< dimt; i++)
	        {
	                x1 = vpt[net[0][i]].getX()*(width + width/100) + padding;
	                y1 = vpt[net[0][i]].getY()*(height + height/100)+ padding;
	                x2 = vpt[net[1][i]].getX()*(width + width/100) + padding;
	                y2 = vpt[net[1][i]].getY()*(height + height/100)+ padding;
	                
	                li1 = new Line2D.Double(x1, y1, x2, y2);
	                g.draw(li1);
	                
	                x1 = vpt[net[1][i]].getX()*(width + width/100) + padding;
	                y1 = vpt[net[1][i]].getY()*(height + height/100)+ padding;
	                x2 = vpt[net[2][i]].getX()*(width + width/100) + padding;
	                y2 = vpt[net[2][i]].getY()*(height + height/100)+ padding;
	                li2 = new Line2D.Double(x1, y1, x2, y2);
	                g.draw(li2);
	                
	                x1 = vpt[net[2][i]].getX()*(width + width/100) + padding;
	                y1 = vpt[net[2][i]].getY()*(height + height/100)+ padding;
	                x2 = vpt[net[0][i]].getX()*(width + width/100) + padding;
	                y2 = vpt[net[0][i]].getY()*(height + height/100)+ padding;
	                li3 = new Line2D.Double(x1, y1, x2, y2);
	                g.draw(li3);    
	                
	                
	                if(net[0][i] > dim)
	                {
	                	x = (vpt[net[0][i]].getX() * width  + padding);
	                	y = (vpt[net[0][i]].getY() * height + padding);
	        			g.fillOval(x.intValue(), y.intValue(), width/50,height/50);
	                }
	                if(net[1][i] > dim)
	                {
	                	x = (vpt[net[1][i]].getX() * width  + padding);
	                	y = (vpt[net[1][i]].getY() * height + padding);
	        			g.fillOval(x.intValue(), y.intValue(), width/50,height/50);
	                }
	                if(net[2][i] > dim)
	                {
	                	x = (vpt[net[2][i]].getX() * width  + padding);
	                	y = (vpt[net[2][i]].getY() * height + padding);
	        			g.fillOval(x.intValue(), y.intValue(), width/50,height/50);
	                }
	                
	               
	                //x = (vpt[net[0][i]].getX() * width  + padding);
                	//y = (vpt[net[0][i]].getY() * height + padding);
        			//g.fillOval(x.intValue(), y.intValue(), width/50,height/50);
        			//x = (vpt[net[1][i]].getX() * width  + padding);
                	//y = (vpt[net[1][i]].getY() * height + padding);
        			//g.fillOval(x.intValue(), y.intValue(), width/50,height/50);
        			//x = (vpt[net[2][i]].getX() * width  + padding);
                	//y = (vpt[net[2][i]].getY() * height + padding);
        			//g.fillOval(x.intValue(), y.intValue(), width/50,height/50); 
	        }
			 vpt=null;
             
		}
	}
	public void saveFile()
	{
		if(fileChooser ==null)
		{
			fileChooser = new JFileChooser();
		}
		int retv = fileChooser.showOpenDialog(this);
		if(retv == fileChooser.APPROVE_OPTION)
		{
			Path pth = fileChooser.getSelectedFile().toPath();
			JOptionPane.showMessageDialog(null, pth.toString());
			try
			{
				FileWriter fw = new FileWriter(pth.toString());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(""+dim);
				bw.newLine();
				for(int i=0; i<dim; i++)
				{
					bw.write(vp[i].getX()+" "+vp[i].getY());
					bw.newLine();
				}
				bw.close();
			}
			catch(Exception iow)
			{
				
			}
		}
	}
	public void loadFile()
	{
		String cline;
		Double xi,yi;
		if(fileChooser ==null)
		{
			fileChooser = new JFileChooser();
		}
		int retv = fileChooser.showOpenDialog(this);
		if(retv == fileChooser.APPROVE_OPTION)
		{
			Path pth = fileChooser.getSelectedFile().toPath();
			JOptionPane.showMessageDialog(null, pth.toString());
			try
			{
				br = new BufferedReader(new FileReader(pth.toString()));
				dim = (int) Integer.parseInt((br.readLine()).toString());
				System.out.println("load method dim = "+dim);
				String[] vline = new String[dim];
				vp = new Point2D.Double[dim];
				
				for(int j= 0; j< dim; j++)
				{
					//System.out.println("load method j = "+j);
					cline=br.readLine();
					vline[j] = cline;
				}
				int j=0;
				for(String st: vline)
				{
					String[] ws = new String[2];
					CutString cs = new CutString(st);
					ws = cs.split();
					//System.out.println("load method st = "+st);
					xi   = Double.parseDouble(ws[0]);
					yi   = Double.parseDouble(ws[1]);
					vp[j]= new Point2D.Double(xi, yi);
					j++;
				}
				pf = new PointConf(vp);
				drawCircles(vp);
				for(int i=0; i< dim-1; i++)
				{
					for(int k=i+1; k< dim; k++)
					{
						double d1 = Math.abs(vp[i].getX() - vp[k].getX());
						double d2 = Math.abs(vp[i].getY() - vp[k].getY());
						if(d1 <0.0000001 && d2<0.0000001)
						{
							System.out.println("******Alerta********");
						}
					}
				}
				
				
			}
			catch(Exception iow)
			{
				 System.out.println("ERROR");
			}
		}
	}
	public void drawCircles(Point2D[] vec)
	{
		contentPanel.repaint();
		System.out.println("drawCircles 1");
		Double x, y;
		g = graphicsContext.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, graphicsContext.getWidth(), graphicsContext.getHeight());
		g.setColor(Color.BLUE);
		for(int i=0; i < dim; i++)
		{
			x = vec[i].getX() * width  + padding;
			y = vec[i].getY() * height + padding;
			g.fillOval(x.intValue(), y.intValue(), width/50,height/50);
			//g.drawString(""+i,x.intValue() , y.intValue());
		}
		System.out.println("drawCircles 2");
	}
	public void initial()
	{
		g = graphicsContext.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, graphicsContext.getWidth(), graphicsContext.getHeight());
		g.setColor(Color.BLUE);
		Font fnt = new Font("HANGING_BASELINE", Font.PLAIN, 30);
		g.setFont(fnt);
		g.drawString("DELAUNAY TRIANGULATIONS", 100, 200);
	}
	public void clean()
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, graphicsContext.getWidth(), graphicsContext.getHeight());
	}
	public static void main(String[] args)
	{
		 new Window();
		
	}
}

