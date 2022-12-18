
package bagoftasks.server;

import bagoftasks.proto.Task;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.List;

public class TaskListFractal implements Serializable, Task {
    private List<TaskFractal> listData;

    public TaskListFractal(List<TaskFractal> listData) { this.listData = listData; }

    public List<TaskFractal> getData() { return listData; }

    public void run() {
        if (listData.isEmpty())
            try {Thread.sleep(200);}
            catch (Exception e) {e.printStackTrace();}
        else
            for (TaskFractal data: listData)
                data.calculConverge();
    }
}
