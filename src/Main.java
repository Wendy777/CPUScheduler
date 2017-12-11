import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        String content;
        String[] content0 = new String[100];
        int quantum = 10;
        try {
            int size=0;
            //定义一个字节缓冲区,该缓冲区的大小根据需要来定义
            byte[] buffer=new byte[1024];
            FileInputStream fstream = new FileInputStream(".\\src\\SchedulerMessage.txt");
            //循环来读取该文件中的数据
            while((size=fstream.read(buffer))!=-1){
                content=new String(buffer, 0, size);
                System.out.println(size);
                content0 = content.split("\r");

            }
            //关闭此文件输入流并释放与此流有关的所有系统资源。
            fstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(args.length);
        if(args.length == 0){
            System.out.println(0);
            return;
        }else if(args.length >=1){
            String typeAlgorithm = args[0];
            System.out.println(typeAlgorithm.equals("RR"));
            if (typeAlgorithm.equals("FCFS")  ){
                FCFS schedulerFCFS = new FCFS();
                int[] processParameter = new int[3];
                for (int i = 0;i<content0.length;i++){
                    String[] str = content0[i].split(" ");
                    System.out.println(str.length);
                    for (int j=0;j<str.length;j++){
                        str[j] = str[j].replaceAll("\n"," ");
                        processParameter[j] = Integer.valueOf(str[j].trim());
                        System.out.println(processParameter[j]);
                    }
                    Process process = new Process();
                    process.setProcessId(processParameter[0]);
                    process.setArrivalTime(processParameter[1]);
                    process.setArrivalTimeCopy(processParameter[1]);
                    process.setBurstLength(processParameter[2]);
                    schedulerFCFS.addProcess(process);
                }
                schedulerFCFS.schedulerFCFS();
            }
            if(typeAlgorithm.equals("SRTF")){
                SRTF schedulerSRTF = new SRTF();
                int[] processParameter = new int[3];
                for (int i = 0;i<content0.length;i++){
                    String[] str = content0[i].split(" ");
                    for (int j=0;j<str.length;j++){
                        str[j] = str[j].replaceAll("\n"," ");
                        processParameter[j] = Integer.valueOf(str[j].trim());
                    }
                    Process process = new Process();
                    process.setProcessId(processParameter[0]);
                    process.setArrivalTime(processParameter[1]);
                    process.setArrivalTimeCopy(processParameter[1]);
                    process.setBurstLength(processParameter[2]);
                    schedulerSRTF.addProcess(process);
                }
                schedulerSRTF.schedulerSRTF();
            }
            if (typeAlgorithm.equals("RR") ){
                if (args.length <=1){
                    System.out.println(1);
                    return;
                }else {
                    quantum = Integer.valueOf(args[1]);
                    RR schedulerRR = new RR();
                    int[] processParameter = new int[3];
                    for (int i = 0;i<content0.length;i++){
                        String[] str = content0[i].split(" ");
                        for (int j=0;j<str.length;j++){
                            str[j] = str[j].replaceAll("\n"," ");
                            processParameter[j] = Integer.valueOf(str[j].trim());
                        }
                        Process process = new Process();
                        process.setProcessId(processParameter[0]);
                        process.setArrivalTime(processParameter[1]);
                        process.setArrivalTimeCopy(processParameter[1]);
                        process.setBurstLength(processParameter[2]);
                        schedulerRR.addProcess(process);
                    }
                    schedulerRR.schedulerRR(quantum);
                }

            }

        }


    }
}
