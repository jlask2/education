// tabs=4
//************************************************************
//	COPYRIGHT 2009 Sandeep Mitra and Michael Steves, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
//specify the package
package userinterface;

//system imports
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

//==========================================================
public class LineLabel extends JPanel
{

	private int width;

	//-----------------------------------------------------
	public LineLabel()
	{
		setPreferredSize(new Dimension(250, 10));
		width = 250;
	}

	//---------------------------------------------------------------
	public LineLabel(int w)
	{
		setPreferredSize(new Dimension(w, 10));
		width = w;
	}

	//Overide the paint method to ensure we can set the focus when made visible
	//---------------------------------------------------------------
	public void paintComponent(Graphics g)
	{
		g.drawLine(0, 5, width, 5);

	}


}