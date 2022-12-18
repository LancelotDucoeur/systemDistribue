
package bagoftasks.client;

import bagoftasks.proto.Task;
import bagoftasks.proto.BagOfTasks;

import java.rmi.Naming;

public class Client{
    public static void main(String[] args) {
        try {
            BagOfTasks bot = (BagOfTasks)Naming.lookup("fractalCalcul");
            while (true) {
                Task t = bot.getTask();
                t.run();
                bot.endTask(t);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
