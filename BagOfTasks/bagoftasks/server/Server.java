
package bagoftasks.server;

import javax.swing.JFrame;
import java.math.MathContext;
import java.math.BigDecimal;

public class Server {
    public static void main(String[] args) {
        if (args.length==1 && args[0].equals("p"))
            procedure_performence();
        else if (args.length==1 && args[0].equals("h"))
            procedure_help();
        else if (args.length>=0 && args.length<=4)
            procedure_interface(args);
        else
            System.out.println("Error - Use option h for help");
    }

    public static void procedure_help() {
        System.out.println("Option :\n\th - help.\n\tp - performance calcul."+
            "\n\t0 number - use MathContext("+MyConst.MC.getPrecision()+"), "+
            "fractal precision("+MyConst.PRECISION_FRACTAL+"), mode("+MyConst.MODE+") on GUI."+
            "\n\t1 number - change MathContext on GUI."+
            "\n\t2 number - change MathContext and fractal precision on GUI."+
            "\n\t3 number - change MathContext, fractal precision and mode on GUI."+
            "\n\t4 number - change MathContext, fractal precision, mode and size for mode 4 on GUI.");
        System.out.println("Mode :"+
            "\n\t1 - a pixel per task."+
            "\n\t2 - a line per task."+
            "\n\t3 - the whole picture for a task = without RMI."+
            "\n\t4 - a group of pixel without any particular form per task.");
        System.out.println("Note : \n\tyou can click on GUI to zoom.");
    }

    public static void procedure_interface(String[] args) {
        try {
            if (args.length>0)
                MyConst.MC = new MathContext(Integer.parseInt(args[0]));
            if (args.length>1)
                MyConst.PRECISION_FRACTAL = Integer.parseInt(args[1]);
            if (args.length>2)
                MyConst.MODE = Integer.parseInt(args[2]);
            if (args.length>3)
                MyConst.MODE_4_SIZE = Integer.parseInt(args[3]);
        } catch(Exception e) {System.out.println("Error - Use option h for help");}

        System.out.println("Param :\n- MathContext : "+MyConst.MC.getPrecision()+
            "\n- Fractal precision : "+MyConst.PRECISION_FRACTAL+
            "\n- Mode : "+MyConst.MODE+
            "\n- Size  : "+MyConst.MODE_4_SIZE);

        JFrame ff = new FrameFractal(800,700);
	    ff.setVisible(true);
    }

    public static void procedure_performence() {
        CalculFractal.initBagOfTask();

        System.out.print("Run your clients and press enter for the performance calcul...");
        try { System.in.read(); }
        catch (Exception e) { e.printStackTrace(); }

        System.out.println("Calcul processing...");
        deleteErrFirstConnect();

        System.out.println("\nMode 1 - a pixel per task :");
        calcPerfMode(1,0);
        System.out.println("\nMode 2 - a line per task :");
        calcPerfMode(2,0);
        System.out.println("\nMode 3 - the whole picture for a task = without RMI :");
        calcPerfMode(3,0);
        int stepMode4Size[] = new int[]{10,20,100,1000};
        for (int msStep=0; msStep<stepMode4Size.length; msStep++) {
            System.out.format("\nMode 4 ( %4d ) - a group of pixel without any particular form per task :\n",
                stepMode4Size[msStep]);
            calcPerfMode(4,stepMode4Size[msStep]);
        }

        System.out.println("\nEnd processing !");
    }

    public static String pdt(long t) {
        return String.format("%d'%03d",t/1000000000,(t%1000000000)/1000000);
    }

    public static void deleteErrFirstConnect() {
        MyConst.MODE = 1;
        MyConst.MC = new MathContext(1);
        MyConst.PRECISION_FRACTAL = 1;
        BigDecimal[] zone = new BigDecimal[]{
            new BigDecimal(2., MyConst.MC),
            new BigDecimal(1.5, MyConst.MC),
            new BigDecimal(3., MyConst.MC),
            new BigDecimal(3., MyConst.MC)};
        CalculFractal.run(zone,100,100);
    }

    public static void calcPerfMode(int mode, int mode4Size) {
        MyConst.MODE = mode;
        MyConst.MODE_4_SIZE = mode4Size;
        int stepTest[] = new int[]{5,15,30};
        long timeTotal = 0l;
        BigDecimal[] zone;
        System.out.print(" MathContext : Precision Fractale(Time)");
        for (int mcStep=0; mcStep<stepTest.length; mcStep++) {
            System.out.format("\n %5s%2d%4s : ","",stepTest[mcStep],"");
            for (int pfStep=0; pfStep<stepTest.length; pfStep++) {
                MyConst.MC = new MathContext(stepTest[mcStep]);
                MyConst.PRECISION_FRACTAL = stepTest[pfStep];
                zone = new BigDecimal[]{
                   new BigDecimal(2., MyConst.MC),
                   new BigDecimal(1.5, MyConst.MC),
                   new BigDecimal(3., MyConst.MC),
                   new BigDecimal(3., MyConst.MC)};
                long debTime = System.nanoTime();
                CalculFractal.run(zone,100,100);
                long finTime = System.nanoTime();
                long delta = finTime-debTime;
                timeTotal += delta;
                System.out.format("%2d(%s)",stepTest[pfStep],pdt(delta));
                if (pfStep!=stepTest.length-1)
                    System.out.print(", ");
            }
        }
        System.out.println("\n Sum of all times : "+pdt(timeTotal));
    }
}
