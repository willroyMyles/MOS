package processes;

import support.Process;

public class OS extends Process {

    public OS() {
        pcb.setStatus("running");
        pcb.setProcessType("system");
        pcb.setPid("pid0");
        pcb.setAppName("Operating System");
        pcb.setRomSize(350);

    }
}
