package processes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import support.Process;
import support.PsuedoPM;

import java.util.ArrayList;
import java.util.Date;

public class TaskManager extends Process {

    private static ArrayList<Process> list = new ArrayList<>(), terminatedList = new ArrayList<>() ;

    public TaskManager() {
        pcb.setPid("pid2");
        pcb.setAppName("Task Manager");
        pcb.setMemorySize(150);
        pcb.setRomSize(200);
        pcb.setProcessType("System");
        pcb.setStatus("Running");


    }

    public void launch() {

//        tc.setTable();
    //    tc.show();
    }

    public Process endProcess(String p){
        for (Process i :list
                ) {
            if(i.getPcb().getPid().compareTo(p)==0){
            //    i.getPcb().setStatus("Running");
                i.getPcb().setEndTime(new Date());
                removeFromProcessList(i);
                return i;
            }

        }
        return null;
    }

    public Process returnProcess(String p){
        for (int i1 = 0; i1 < list.size(); i1++) {
            Process i = list.get(i1);
            if (i.getPcb().getPid().compareTo(p) == 0) {
                removeFromProcessList(i);
                return i;
            }

        }
        for (int i1 = 0; i1 < terminatedList.size(); i1++) {
            Process i = terminatedList.get(i1);
            if (i.getPcb().getPid().compareTo(p) == 0) {
                removeFromTerminatedProcessList(i);
                return i;
            }

        }

        return null;
    }


    public long getCpuTime(){
        return getPcb().getCPUTime();
    }

    public void addToProcessList(Process p){
       // p.getPcb().setStatus("Running");
        list.add(p);
    }

    public void addToTerminatedProcessList(Process p){
        list.add(p);
    }

    public void removeFromProcessList(Process p){



        for (int i = 0; i < list.size(); i++) {
            if(p.getPcb().getPid() == list.get(i).getPcb().getPid()){
                list.get(i).getPcb().setStatus("Terminated");
                terminatedList.add(list.get(i));
                list.remove(i);
            }
        }
    }

    public void removeFromTerminatedProcessList(Process p){

        for (int i = 0; i < terminatedList.size(); i++) {
            if(p.getPcb().getPid() == terminatedList.get(i).getPcb().getPid()){
                terminatedList.remove(i);
                list.add(p);
            }
        }
    }

    public void removeFromTerminatedProcessList(String p){

        for (int i = 0; i < terminatedList.size(); i++) {
            if(p.compareTo(terminatedList.get(i).getPcb().getPid())==0){
                list.add(terminatedList.get(i));
                terminatedList.remove(i);

            }
        }
    }


    public ObservableList<PsuedoPM> getObservableList(){

        final ObservableList<PsuedoPM> data = FXCollections.observableArrayList();

        for (Process i: list
                ) {
            data.add(new PsuedoPM(i.getPcb().getPid(),i.getPcb().getAppName(),i.getPcb().getProcessType(),i.getPcb().getStatus()));
        }

        for (Process i: terminatedList
                ) {
            data.add(new PsuedoPM(i.getPcb().getPid(),i.getPcb().getAppName(),i.getPcb().getProcessType(),i.getPcb().getStatus()));
        }

        return data;


    }


    public void remove(Process p) {
        for (int i = 0; i < list.size(); i++) {
            if(p.getPcb().getPid() == list.get(i).getPcb().getPid()){
                list.remove(i);
                // terminatedList.add(p);
            }
        }
    }

    public void hide(Process p) {
        for (int i = 0; i < list.size(); i++) {
            if(p.getPcb().getPid().compareTo(list.get(i).getPcb().getPid())==0){
                list.get(i).getPcb().setStatus("Hidden");

              //  list.remove(i);
                // terminatedList.add(p);
            }
        }
    }


    public void removeFromProcessList(String pid) {

        for (int i = 0; i < list.size(); i++) {
            if(pid.compareTo(list.get(i).getPcb().getPid())==0){
                terminatedList.add(list.get(i));
                list.remove(i);
            }
        }
    }

    public void closeAll() {

        for (int i1 = 0; i1 < list.size(); i1++) {
            Process i = list.get(i1);
           i.appendToFile();
        }
        for (int i1 = 0; i1 < terminatedList.size(); i1++) {
            Process i = terminatedList.get(i1);
           i.appendToFile();
        }

    }
}
