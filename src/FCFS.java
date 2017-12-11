import static java.lang.String.format;

public class FCFS extends Scheduler {
    public void schedulerFCFS() {
        int sizeOfProcess = this.readyQueues.size();
        int responseTime = 0;
        int waitTime     = 0;
        int turnAroundTime = 0;
        int arrivalProcess = 0;
        int systemTime = 0;
        int freeTime = 0;
        while(this.readyQueues.size()>0){
            Process p = (Process) this.readyQueues.getElement(1).value;
            if (p.getArrivalTime()>arrivalProcess){
                int deltaFreeTime = p.getArrivalTime()-arrivalProcess;
                freeTime +=deltaFreeTime;
                for(int ft = 0;ft < deltaFreeTime;ft++){
                    System.out.println("<system time "+systemTime+" >no process is running");
                    systemTime++;
                }
            }
            for(int j = 0;j<p.getBurstLength();j++){
                System.out.println("<system time "+systemTime+" > process "+p.getProcessId()+" is running");
                systemTime++;
            }
            System.out.println("<system time "+(systemTime--)+" > process "+p.getProcessId()+" is finished....");
            systemTime++;
            arrivalProcess += p.getBurstLength();


            turnAroundTime += (arrivalProcess - p.getArrivalTime());
            waitTime += (arrivalProcess - p.getArrivalTime() - p.getBurstLength());
            this.readyQueues.deleteList(p);
        }
        System.out.println("<system time "+(systemTime--)+" > All processes finished......");
        responseTime = waitTime; //
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
}

