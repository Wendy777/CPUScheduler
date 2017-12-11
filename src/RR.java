
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class RR extends Scheduler {
    private void addProcess1(Process process,int ith){
        this.readyQueues.insertList(process,ith);
    }
    private int responseTimeTotal(Map<Integer, Integer> responseMap) {
        int sumResposta = 0;
        for (int key : responseMap.keySet()) {
            sumResposta += responseMap.get(key);
        }
        return sumResposta;
    }
    public void schedulerRR(int quantum){
        int sizeOfProcess = this.readyQueues.size();
        int responseTime = 0;
        int waitTime     = 0;
        int turnAroundTime = 0;
        int arrivalProcess = 0;
        int systemTime = 0;
        int freeTime = 0;
        Map<Integer, Integer> responseMap = new HashMap<Integer, Integer>();
        while(this.readyQueues.size()>0){

            Process p = (Process) this.readyQueues.getElement(1).value;
            if (p.getArrivalTime()>arrivalProcess){
                int deltaFreeTime = p.getArrivalTime()-arrivalProcess;
                freeTime +=deltaFreeTime;
                for(int ft = 0;ft < deltaFreeTime;ft++){
                    System.out.println("<system time "+systemTime+" >no process is running");
                    systemTime++;
                }
                arrivalProcess +=p.getArrivalTime()-arrivalProcess;
            }
            //Get response time: time used by the user to submit a request to the first response of the system.
            if (!responseMap.containsKey(p.getProcessId())) {
                responseMap.put(p.getProcessId(), arrivalProcess - p.getArrivalTime());
            }
            int burstLength = p.getBurstLength();
            if (burstLength <= quantum){
                for(int j = 0;j < burstLength; j++){
                    System.out.println("<system time "+systemTime+" > process "+p.getProcessId()+" is running");
                    systemTime++;
                }
                System.out.println("<system time "+(systemTime--)+" > process "+p.getProcessId()+" is finished....");
                systemTime++;
                this.readyQueues.deleteList(p);
                arrivalProcess += burstLength;
                turnAroundTime += (arrivalProcess - p.getArrivalTime());
                waitTime += (arrivalProcess - p.getArrivalTime() - burstLength);
//                System.out.println("waitTime: "+((arrivalProcess - p.getArrivalTime() - burstLength)));
                continue;
            }else if(burstLength > quantum){
                for(int j = 0;j<quantum;j++){
                    System.out.println("<system time "+systemTime+" > process "+p.getProcessId()+" is running");
                    systemTime++;
                }

                this.readyQueues.deleteList(p);
                arrivalProcess += quantum;
                turnAroundTime += (arrivalProcess - p.getArrivalTime());
                waitTime += (arrivalProcess - p.getArrivalTime() - quantum);
                System.out.println("waitTime: "+((arrivalProcess - p.getArrivalTime() - quantum)));
                int tmp = 0;
                for (;tmp < this.readyQueues.size();tmp++){
                    Process tempP = (Process) this.readyQueues.getElement(tmp+1).value;
                    if (tempP.getArrivalTime() > arrivalProcess){
                        break;
                    }
                }

                Process p1 = new Process();
                p1.setProcessId(p.getProcessId());
                p1.setArrivalTime(arrivalProcess);
                p1.setBurstLength(p.getBurstLength()-quantum);
                this.addProcess1(p1,tmp+1);
            }



        }
        System.out.println("<system time "+(systemTime--)+" > All processes finished......");
        responseTime = responseTimeTotal(responseMap); //Total response time
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

