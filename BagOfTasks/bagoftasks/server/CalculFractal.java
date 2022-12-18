
package bagoftasks.server;

import bagoftasks.proto.Task;

import java.rmi.Naming;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class CalculFractal {
    private static BagOfTasksFractal botF = null;

    public static void initBagOfTask() {
        if (botF == null)
            try {
                botF = new BagOfTasksFractal();
                Naming.bind("fractalCalcul", botF);
            } catch(Exception e) {e.printStackTrace();}
    }

    public static List<TaskFractal> run(BigDecimal zone[], int width, int height) {
        List<TaskFractal> listDataResult = new ArrayList<TaskFractal>();
        try {
            botF.runBagOfTask(zone,width,height);
            while (!botF.isEnd()) {
                try {Thread.sleep(10);}
                catch(Exception e) {e.printStackTrace();}
                //System.out.println(botF.ind + " " + botF.listTask.size() + " "+ botF.getResult().size());
            }
            List<TaskListFractal> listTask = botF.getResult();
            for (int t=0; t<listTask.size(); t++)
                listDataResult.addAll((listTask.get(t)).getData());
        }
        catch(Exception e) {e.printStackTrace();}
        return listDataResult;
    }
}
