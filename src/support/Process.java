package support;

import java.io.FileWriter;
import java.io.IOException;

public class Process {

    protected  PCB pcb;

    public Process() {
        pcb = new PCB();
    }

    public PCB getPcb() {
        return pcb;
    }

    public void setPcb(PCB pcb) {
        this.pcb = pcb;
    }

    public void appendToFile(){
        try {

           FileWriter writer = new FileWriter("ProcessHistory.txt", true);
            writer.write(getPcb().getPid() + "\n");
            writer.write(getPcb().getAppName() + "\n");
            writer.write(getPcb().getProcessType() + "\n");
            writer.write(getPcb().getStartTime() + "\n");
            writer.write(getPcb().getEndTime() + "\n");
            writer.write(getPcb().getCPUTime() + "\n\n");



            writer.close();
           // return "file Process complete";

        } catch (IOException e) {
            e.printStackTrace();
        }
       // return "file Process complete";
    }
}
