package mainpanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public final class imgpanel extends JPanel{
	private static final long serialVersionUID = 5805543053359120595L;
	Image img;
	protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setPaintMode();
        if (img != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            int x = (getWidth() - img.getWidth(null)) / 2;
            int y = (getHeight() - img.getHeight(null)) / 2;
            g2d.drawImage(img, x, y, this);
            g2d.dispose();
        }
    }
	void setimg(Image imgx){
		img=imgx;
	}
	public void pack() {
		// TODO Auto-generated method stub
		
	}
}