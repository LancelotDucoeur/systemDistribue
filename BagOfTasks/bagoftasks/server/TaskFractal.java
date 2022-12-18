
package bagoftasks.server;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.MathContext;

public class TaskFractal implements Serializable {
    private BigDecimal xFract, yFract;
    private int xPixel, yPixel;
    private int converge;
    private final int precisionFractale;
    private final MathContext mathCont;

    public TaskFractal(BigDecimal xFract, BigDecimal yFract,
                     int xPixel, int yPixel,
                     int precisionFractale, MathContext mathCont) {
        this.xFract = xFract;
        this.yFract = yFract;
        this.xPixel = xPixel;
        this.yPixel = yPixel;
        converge = -1;
        this.precisionFractale = precisionFractale;
        this.mathCont = mathCont;
    }
    public BigDecimal getXFract() { return xFract; }
    public BigDecimal getYFract() { return yFract; }
    public int getXPixel() { return xPixel; }
    public int getYPixel() { return yPixel; }
    public int getConverge() { return converge; }
    public void calculConverge() { converge = deep(xFract,yFract); }

    private int deep(BigDecimal x, BigDecimal y) {
        BigDecimal xTmp = new BigDecimal(0.0);
        BigDecimal yTmp = new BigDecimal(0.0);
        BigDecimal deux = new BigDecimal(2);
        for (int i = 0; i < precisionFractale; i++) {
            BigDecimal xNew = xTmp.multiply(xTmp, mathCont).subtract( yTmp.multiply(yTmp, mathCont), mathCont ).add(x, mathCont);
            BigDecimal yNew = xTmp.multiply(yTmp, mathCont).multiply(deux, mathCont).add(y, mathCont);
            xTmp = xNew;
            yTmp = yNew;
            if ( xTmp.abs().compareTo(deux) == 1 || yTmp.abs().compareTo(deux)==1 ) {
                return i;
            }
        }
        return -1;
    }
}
