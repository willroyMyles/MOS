package support;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class PCB {
    private String Pid;
    private String AppName;

    public String getBlockAssigned() {
        return blockAssigned;
    }

    public void setBlockAssigned(String blockAssigned) {
        this.blockAssigned = blockAssigned;
    }

    private String ProcessType;
    private String blockAssigned;
    private String status;
    private Date startTime, endTime, CPUTime;
    private int memorySize;
    private int romSize;
    private int memLocation;
    public PCB(){
        startTime = new Date();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMemLocation() {
        return memLocation;
    }

    public void setMemLocation(int memLocation) {
        this.memLocation = memLocation;
    }

    public int getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    public int getRomSize() {
        return romSize;
    }

    public void setRomSize(int romSize) {
        this.romSize = romSize;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getProcessType() {
        return ProcessType;
    }

    public void setProcessType(String processType) {
        ProcessType = processType;
    }

    public Date getStartTime() {
        return startTime;
    }

  /*  public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }*/

    public Date getEndTime() {
        if(endTime==null)
       return endTime = new Date();
        else
            return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getCPUTime() {
        return Integer.valueOf((int) (endTime.getTime() - (startTime.getTime())))/1000;
    }

  /*  public void setCPUTime(Long CPUTime) {
        this.CPUTime = CPUTime;
    }*/

    //write to the process control block
    protected String writePCB(Process pm) {

        try {

            FileWriter writer = new FileWriter("ProcessHistory.txt", true);
            writer.write("##########\n"+getPid() + "\n");
            writer.write(getAppName() + "\n");
            writer.write(getProcessType() + "\n");
            writer.write(getStartTime() + "\n");
            writer.write(getEndTime() + "\n");
            writer.write(getCPUTime() + " secs"+ "\n ######### \n\n");


            writer.close();
            return "file Process complete";

        } catch (IOException e) {
            e.printStackTrace();

        }
        return "file Process complete";

    }
}
