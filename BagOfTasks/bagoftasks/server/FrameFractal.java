
package bagoftasks.server;

import java.math.BigDecimal;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class FrameFractal extends JFrame {
    public FrameFractal(int width, int height) {
        setTitle("Fractale avec java RMI");
        setSize(width,height);
        PanelFractal pf = new PanelFractal(new double[]{-2., -1.5, 3., 3.});
	    setContentPane(pf);
    }
}

class PanelFractal extends JPanel implements MouseListener {
    private BigDecimal zone[];

    public PanelFractal(double zone[]) {
        this.zone = new BigDecimal[]{
            new BigDecimal(zone[0], MyConst.MC),
            new BigDecimal(zone[1], MyConst.MC),
            new BigDecimal(zone[2], MyConst.MC),
            new BigDecimal(zone[3], MyConst.MC)};
        addMouseListener(this);
        CalculFractal.initBagOfTask();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        List<TaskFractal> listData = CalculFractal.run(zone,getWidth(),getHeight());
        for (TaskFractal data: listData) {
            if ( data.getConverge() != -1 )
                g.setColor(new Color(0,0,(int)((255./MyConst.PRECISION_FRACTAL)*data.getConverge())));
            else g.setColor(Color.BLACK);
            g.drawLine(data.getXPixel(),data.getYPixel(),data.getXPixel(),data.getYPixel());
        }
    }

    public void mouseClicked(MouseEvent e) {
        BigDecimal pct1 = new BigDecimal(0.1,MyConst.MC);
        BigDecimal pct5 = new BigDecimal(0.5,MyConst.MC);
        BigDecimal px = zone[0].add( new BigDecimal(e.getX()/((double)getWidth()), MyConst.MC).multiply(zone[2], MyConst.MC) );
        BigDecimal py = zone[1].add( new BigDecimal(e.getY()/((double)getHeight()), MyConst.MC).multiply(zone[3], MyConst.MC) );
        BigDecimal t[] = new BigDecimal[]{
            px.subtract(zone[2].multiply(pct1, MyConst.MC).multiply(pct5, MyConst.MC)),
            py.subtract(zone[3].multiply(pct1, MyConst.MC).multiply(pct5, MyConst.MC)),
            zone[2].multiply(pct1, MyConst.MC),
            zone[3].multiply(pct1, MyConst.MC)};
        zone = t;
        repaint();
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
