
package bagoftasks.server;

import bagoftasks.proto.Task;
import bagoftasks.proto.BagOfTasks;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class BagOfTasksFractal extends UnicastRemoteObject implements BagOfTasks {
    public List<TaskListFractal> listTask;
    private List<TaskListFractal> listComplete;
    public int ind;
    private boolean end;

    public BagOfTasksFractal() throws RemoteException {
        end = true;
    }

    public void runBagOfTask(BigDecimal zone[], int width, int height) {
        listTask = new ArrayList<TaskListFractal>();
        listComplete = new ArrayList<TaskListFractal>();
        switch(MyConst.MODE) {
            case 1: engineMode_1(zone,width,height); break;
            case 2: engineMode_2(zone,width,height); break;
            case 3: engineMode_3(zone,width,height); break;
            case 4: engineMode_4(zone,width,height);
        }
        ind = 0;
        end = false;
    }

    public boolean isEnd() { return end; }

    public List<TaskListFractal> getResult() { return listComplete; }

    public void endTask(Task t) throws RemoteException {
        if (listComplete==null || listTask==null)
            return;
        TaskListFractal tlf = (TaskListFractal)t;
        if (!tlf.getData().isEmpty())
            listComplete.add(tlf);
        if (listComplete.size() == listTask.size())
            end = true;
    }

    public Task getTask() throws RemoteException {
        //System.out.println("-> "+ind+" "+listTask.size());
        if (listTask!=null && ind<listTask.size())
            return listTask.get(ind++);
        return new TaskListFractal(new ArrayList<TaskFractal>());//TaskListFractal(null,null,-1,-1);
    }

    //un pixel par client
    public void engineMode_1(BigDecimal zone[], int width, int height) {
        BigDecimal offsetWidth = zone[2].divide(new BigDecimal(width), MyConst.MC);
        BigDecimal offsetHeight = zone[3].divide(new BigDecimal(height), MyConst.MC);
	    for (int i = 0; i < width; i++) {
            BigDecimal x = zone[0].add(offsetWidth.multiply(new BigDecimal(i), MyConst.MC), MyConst.MC);
            for (int j = 0; j < height; j++) {
                BigDecimal y = zone[1].add(offsetHeight.multiply(new BigDecimal(j), MyConst.MC), MyConst.MC);
                listTask.add(new TaskListFractal(
                    new ArrayList<TaskFractal>(Arrays.asList(new TaskFractal(x,y,i,j,MyConst.PRECISION_FRACTAL,MyConst.MC)))
                ));
            }
        }
    }

    //une ligne par client
    public void engineMode_2(BigDecimal zone[], int width, int height) {
        BigDecimal offsetWidth = zone[2].divide(new BigDecimal(width), MyConst.MC);
        BigDecimal offsetHeight = zone[3].divide(new BigDecimal(height), MyConst.MC);
	    for (int i = 0; i < width; i++) {
            BigDecimal x = zone[0].add(offsetWidth.multiply(new BigDecimal(i), MyConst.MC), MyConst.MC);
            List<TaskFractal> listData = new ArrayList<TaskFractal>();
            for (int j = 0; j < height; j++) {
                BigDecimal y = zone[1].add(offsetHeight.multiply(new BigDecimal(j), MyConst.MC), MyConst.MC);
                listData.add(new TaskFractal(x,y,i,j,MyConst.PRECISION_FRACTAL,MyConst.MC));
            }
            listTask.add(new TaskListFractal(listData));
        }
    }

    //toute l'image pour un client (=sans RMI)
    public void engineMode_3(BigDecimal zone[], int width, int height) {
        BigDecimal offsetWidth = zone[2].divide(new BigDecimal(width), MyConst.MC);
        BigDecimal offsetHeight = zone[3].divide(new BigDecimal(height), MyConst.MC);
        List<TaskFractal> listData = new ArrayList<TaskFractal>();
	    for (int i = 0; i < width; i++) {
            BigDecimal x = zone[0].add(offsetWidth.multiply(new BigDecimal(i), MyConst.MC), MyConst.MC);
            for (int j = 0; j < height; j++) {
                BigDecimal y = zone[1].add(offsetHeight.multiply(new BigDecimal(j), MyConst.MC), MyConst.MC);
                listData.add(new TaskFractal(x,y,i,j,MyConst.PRECISION_FRACTAL,MyConst.MC));
            }
        }
        listTask.add(new TaskListFractal(listData));
    }

    //paquet de pixel sans forme particuliere (taille = MODE_SIZE)
    public void engineMode_4(BigDecimal zone[], int width, int height) {
        BigDecimal offsetWidth = zone[2].divide(new BigDecimal(width), MyConst.MC);
        BigDecimal offsetHeight = zone[3].divide(new BigDecimal(height), MyConst.MC);
        List<TaskFractal> listData = new ArrayList<TaskFractal>();
	    for (int i = 0; i < width; i++) {
            BigDecimal x = zone[0].add(offsetWidth.multiply(new BigDecimal(i), MyConst.MC), MyConst.MC);
            for (int j = 0; j < height; j++) {
                BigDecimal y = zone[1].add(offsetHeight.multiply(new BigDecimal(j), MyConst.MC), MyConst.MC);
                listData.add(new TaskFractal(x,y,i,j,MyConst.PRECISION_FRACTAL,MyConst.MC));
                if (listData.size()>=MyConst.MODE_4_SIZE) {
                    listTask.add(new TaskListFractal(listData));
                    listData = new ArrayList<TaskFractal>();
                }
            }
        }
        if (!listData.isEmpty())
            listTask.add(new TaskListFractal(listData));
    }
}
