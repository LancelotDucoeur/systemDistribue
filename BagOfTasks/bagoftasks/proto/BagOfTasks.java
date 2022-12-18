package bagoftasks.proto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BagOfTasks extends Remote {
    public void endTask(Task t) throws RemoteException;
    public Task getTask() throws RemoteException;
}
