import javax.swing.text.Element;
import java.util.Date;

public class Scheduler {
    CircularLinkedList readyQueues = new CircularLinkedList();
    public void addProcess(Process process){
        int sizeOfReadyQueues = this.readyQueues.size();
        int flag = 1;
        int flag2 = 0;
        if (sizeOfReadyQueues ==0){
        }else if(sizeOfReadyQueues == 1){
            CircularLinkedList.Element element1 =  readyQueues.getElement(1);
            Process p1 = (Process)element1.value;
            if (p1.getArrivalTime() <= process.getArrivalTime()) {
                flag = 2;
            }
        }else {
            for(int i = 0;i < sizeOfReadyQueues-1;i++){
                CircularLinkedList.Element element1 =  readyQueues.getElement(i+1);
                CircularLinkedList.Element element2 =  readyQueues.getElement(i+2);
                Process p1 = (Process)element1.value;
                Process p2 = (Process)element2.value;
                if (p1.getArrivalTime()<=process.getArrivalTime() && process.getArrivalTime()<p2.getArrivalTime()){
                    flag = i+2;
                    break;
                }
                flag2 = i;
            }
            if(flag2 == sizeOfReadyQueues-2){
                flag = sizeOfReadyQueues+1;
            }

        }
        readyQueues.insertList(process,flag);
    }

    public void removeProcess(Process process){
        readyQueues.deleteList(process);
    }



}
