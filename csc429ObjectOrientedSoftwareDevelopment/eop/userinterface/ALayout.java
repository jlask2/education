package userinterface;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class ALayout {
	public static Rectangle[] generate(JPanel jPanel){
		Rectangle[] r = new Rectangle[2];//0 outer 1 inner 
		r[0] = new Rectangle();
		r[0].x = 0;
		r[0].y = 0;
		r[0].width = jPanel.getPreferredSize().width;
		r[0].height = jPanel.getPreferredSize().height;
		r[1] = new Rectangle();
		r[1].x = r[0].x + jPanel.getInsets().left;
		r[1].y = r[0].y + jPanel.getInsets().top;
		r[1].width = r[0].width - (jPanel.getInsets().left + jPanel.getInsets().right);
		r[1].height = r[0].height - (jPanel.getInsets().top + jPanel.getInsets().bottom);
		return r;
	}
	public static Rectangle[] generateHdrBtnMenu(Rectangle[] rs, int numButtons, int bWidth, int bHeight){
		Rectangle[] r = new Rectangle[4];
		r[0] = new Rectangle();//header
		r[0].x = rs[1].x;
		r[0].y = rs[1].y;
		r[0].width = rs[1].width;
		r[0].height = 0;	
		r[1] = new Rectangle();//menu buttons rect
		r[1].x = rs[1].x;
		r[1].y = r[0].y + r[0].height;
		r[1].height = bHeight * numButtons;
		r[1].width = bWidth;
		r[2] = new Rectangle();//menu panel to the right
		r[2].x = r[1].x + r[1].width;
		r[2].y = r[1].y;
		r[2].width = rs[1].width - r[1].width;
		r[2].height = rs[1].height - r[0].height;
		r[3] = new Rectangle();//left over
		r[3].x = rs[1].x;
		r[3].y = r[1].y + r[1].height;
		r[3].width = bWidth;
		r[3].height = rs[1].height - r[3].y;
		return r;
	}
	public static Rectangle[] generateHdrMain(Rectangle[] rs){
		Rectangle[] r = new Rectangle[2];
		r[0] = new Rectangle();
		r[0].x = rs[1].x;
		r[0].y = rs[1].y;
		r[0].width = rs[1].width;
		r[0].height = 0;	
		r[1] = new Rectangle();
		r[1].x = rs[1].x;
		r[1].y = r[0].y + r[0].height;
		r[1].width = rs[1].width;
		r[1].height = rs[1].height - r[0].height;
		return r;
	}
}
