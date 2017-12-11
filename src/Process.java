public class Process {
    private int processId;
    private int arrivalTime;
    private int burstLength;
    private int remainTime;
    private int arrivalTimeCopy;

    public void setArrivalTimeCopy(int arrivalTimeCopy){
        this.arrivalTimeCopy = arrivalTimeCopy;
    }
    public void setProcessId(int processId){
        this.processId = processId;
    }
    public void setArrivalTime(int arrivalTime){
        this.arrivalTime = arrivalTime;
    }
    public void setBurstLength(int burstLength){
        this.burstLength = burstLength;
        setRemainTime(burstLength);
    }
    public void setRemainTime(int remainTime){
        this.remainTime = remainTime;
    }
    public int getProcessId(){ return this.processId; }
    public int getArrivalTime(){
        return this.arrivalTime;
    }
    public int getBurstLength(){
        return this.burstLength;
    }
    public int getRemainTime(){
        return  this.remainTime;
    }
    public int getArrivalTimeCopy(){
        return this.arrivalTimeCopy;
    }
}
