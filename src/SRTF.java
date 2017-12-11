import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class SRTF extends Scheduler{
    public void addProcess(Process process){
        int sizeOfReadyQueues = this.readyQueues.size();
        int flag = 1;
        int flag2 = 0;
        if (sizeOfReadyQueues ==0){
        }else if(sizeOfReadyQueues == 1){
            CircularLinkedList.Element element1 =  this.readyQueues.getElement(1);
            Process p1 = (Process)element1.value;
            if (p1.getArrivalTime() < process.getArrivalTime() || (p1.getArrivalTime() == process.getArrivalTime()&&p1.getBurstLength()<process.getBurstLength())) {
                flag = 2;
            }
        }else {
            for(int i = 0;i < sizeOfReadyQueues-1;i++){
                CircularLinkedList.Element element1 =  this.readyQueues.getElement(i+1);
                CircularLinkedList.Element element2 =  this.readyQueues.getElement(i+2);
                Process p1 = (Process)element1.value;
                Process p2 = (Process)element2.value;
                if (p1.getArrivalTime()<process.getArrivalTime() && process.getArrivalTime()<p2.getArrivalTime()){
                    flag = i+2;
                    break;
                }else if (p1.getArrivalTime() == process.getArrivalTime()){
                    if (p1.getBurstLength()<process.getBurstLength()){
                        flag = i+2;
                    }else{
                        flag = i+1;
                    }
                    break;
                }
                flag2 = i;
            }
            if(flag2 == sizeOfReadyQueues-2){
                flag = sizeOfReadyQueues+1;
            }

        }
        this.readyQueues.insertList(process,flag);
    }
    private int responseTimeTotal(Map<Integer, Integer> responseMap) {
        int sumResposta = 0;
        for (int key : responseMap.keySet()) {
            sumResposta += responseMap.get(key);
        }
        return sumResposta;
    }

    public void schedulerSRTF(){
        int sizeOfProcess = this.readyQueues.size();
        int responseTime = 0;
        int waitTime     = 0;
        int turnAroundTime = 0;
        int arrivalProcess = 0;
        int systemTime = 0;
        int freeTime = 0;
        Map<Integer, Integer> responseMap = new HashMap<Integer, Integer>();

        while (this.readyQueues.size()>0){

            int index0 = searchShortestRemainJob(systemTime);
            Process pp0 = (Process) this.readyQueues.getElement(index0).value;
            this.readyQueues.deleteList(pp0);
            this.readyQueues.insertList(pp0,1);
            Process p = (Process) this.readyQueues.getElement(1).value;
            if (p.getArrivalTime()>systemTime){
                int deltaFreeTime = p.getArrivalTime()-systemTime;
                freeTime +=deltaFreeTime;
                for(int ft = 0;ft < deltaFreeTime;ft++){
                    System.out.println("<system time "+systemTime+" >no process is running");
                    systemTime++;
                }
            }
            //Get response time: time used by the user to submit a request to the first response of the system.
            if (!responseMap.containsKey(p.getProcessId())) {
                responseMap.put(p.getProcessId(), systemTime - p.getArrivalTimeCopy());
            }
            if (p.getRemainTime() == 0){
                System.out.println("<system time "+systemTime+" > process "+p.getProcessId()+" is finished....");
                waitTime += systemTime - p.getBurstLength() - p.getArrivalTimeCopy();
                turnAroundTime +=systemTime - p.getArrivalTimeCopy();
                this.readyQueues.deleteList(p);
                continue;
            }
            System.out.println("<system time "+(systemTime)+" > process "+p.getProcessId()+" is running");
            systemTime++;
            p.setRemainTime(p.getRemainTime()-1);
            p.setArrivalTime(systemTime);
            int index = searchShortestRemainJob(systemTime);
            Process pp = (Process) this.readyQueues.getElement(index).value;
            this.readyQueues.deleteList(pp);
            this.readyQueues.insertList(pp,1);

        }
        System.out.println("<system time "+(systemTime--)+" > All processes finished......");
        responseTime = responseTimeTotal(responseMap);
        float avgWaitTime = (float) waitTime / sizeOfProcess;
        float avgResponseTime = (float) responseTime / sizeOfProcess;
        float avgTurnAroundTime = (float) turnAroundTime / sizeOfProcess;
        float avgCpuUsage = (((float)(systemTime-freeTime+1)/(systemTime+1))*100);
        System.out.println("============================================================");
        System.out.println(format("%s %.2f %s", "Average CPU usage: ",avgCpuUsage,"%"));
        System.out.println(format("%s %.2f", "Average waiting time:    ",avgWaitTime));
        System.out.println(format("%s %.2f", "Average response time:    ",avgResponseTime));
        System.out.println(format("%s %.2f", "Average turnaround time:    ",avgTurnAroundTime));
        System.out.println("============================================================");

    }

    public int searchShortestRemainJob(int time)//Finding the subscript of the job with the shortest remaining time and returning the subscript
    {
        int index=1;
        Process process = (Process) this.readyQueues.getElement(1).value;
        int remain=process.getRemainTime();
        for(int i=1;i<= this.readyQueues.size();i++){
            Process p = (Process) this.readyQueues.getElement(i).value;
                //The assignment has arrived, and it can be judged.
                if(p.getArrivalTime() <= time) {
                    //The assignment is not finished
                    if(p.getRemainTime()<remain){
                        remain=p.getRemainTime();
                        index=i;
                    }
                }
        }
        return index;//The subscript of a job that returns the shortest remaining time
    }
}
