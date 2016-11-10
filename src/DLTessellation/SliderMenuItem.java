package DLTessellation;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.*;

import java.util.*;

public class SliderMenuItem extends JFrame 
{
	int value;
	JSlider slider;
	JOptionPane optionPane;
	
	public SliderMenuItem() 
	{
		
		//System.out.println("SliderMenuItem: p1 ");
		
		slider = new JSlider(JSlider.HORIZONTAL);
		slider.setMinimum(50);
		slider.setMaximum(100);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		 //System.out.println("SliderMenuItem: p2 ");
		slider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
		             value = slider.getValue();
		             //System.out.println("SliderMenuItem: built "+value);
			}
		});
		add(slider, BorderLayout.CENTER);
		optionPane = new JOptionPane();
		optionPane.setMessage(new Object[]{"Select the number of points", slider});
		optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = optionPane.createDialog(this, "Slider");
		dialog.setVisible(true);
		//System.out.println("SliderMenuItem: built ");	
	}
	
	
	public int getValue()
	{
		return value;
	}
	

	
	

	 
		  
}
