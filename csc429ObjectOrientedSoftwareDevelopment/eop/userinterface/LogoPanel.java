// tabs=4
//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and Students, The
//    College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
//
// Specify the package
package userinterface;

// system imports
import javax.swing.*;

import java.awt.*;
import java.net.URL;

//============================================================
public class LogoPanel extends JPanel
{
	private LogoJLabel leftLabel;
	private Font font;
	private int centerX;
	private String logoTitleBar;
	//----------------------------------------------------------
	// Construct the Logo Panel once in the beginning of the
	// program run. It is instantiated by the MainFrame
	//----------------------------------------------------------
	public LogoPanel(String panelTitle)
	{
		logoTitleBar = "default";
		setLayout(null);
		setPreferredSize(new Dimension(800, 155));
		setBackground(Color.white);
		centerX = 460;
		leftLabel = new LogoJLabel(panelTitle);
		font = new Font("Arial", Font.BOLD, 32);
		leftLabel.setFont(font);
		leftLabel.setBounds(centerX, 45, (800 - 530) * 2, leftLabel.getPreferredSize().height);
		add (leftLabel);
		repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		if (logoTitleBar.equals("default")){
			if (ImageHolder.LG_BK_IMG_0 != null){
				g2d.drawImage(ImageHolder.LG_BK_IMG_0, 0, 0, null);
			}
		} else if (logoTitleBar.equals("tcv")){
			if (ImageHolder.LG_BK_IMG_1 != null){
				g2d.drawImage(ImageHolder.LG_BK_IMG_1, 0, 0, null);
			}
		}
	}
	public JLabel getLeftLabel() {
		return leftLabel;
	}
	public String getLogoTitleBar() {
		return logoTitleBar;
	}
	public void setLogoTitleBar(String logoTitleBar) {
		this.logoTitleBar = logoTitleBar;
	}
	public class LogoJLabel extends JLabel{
		public LogoJLabel(String text){
			super(text);
		}
		@Override
		public void setText(String text){
			text = text.replaceFirst("(^\\s+)", "");
			if (font == null){
				super.setText(text);
				return;
			}
			FontMetrics fm = leftLabel.getFontMetrics(font);
			int sw = fm.charsWidth(text.toCharArray(), 0, text.length()) + leftLabel.getInsets().left + leftLabel.getInsets().right;
			Rectangle r = new Rectangle(leftLabel.getBounds());
			r.x = centerX - (sw / 2);
			r.width = sw;
			leftLabel.setBounds(r);
			super.setText(text);
		}
	}

}

