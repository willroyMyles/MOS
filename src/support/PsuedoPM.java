package support;

import javafx.beans.property.SimpleStringProperty;

public class PsuedoPM {

    private SimpleStringProperty pid, processName, processType, status;

    public PsuedoPM(String a, String b, String c, String d) {
        this.pid = new SimpleStringProperty(a);
        this.processName = new SimpleStringProperty(b);
        this.processType = new SimpleStringProperty(c);
        this.status = new SimpleStringProperty(d);
    }

    public String getPid() {
        return pid.get();
    }

    public SimpleStringProperty pidProperty() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid.set(pid);
    }

    public String getProcessName() {
        return processName.get();
    }

    public SimpleStringProperty processNameProperty() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName.set(processName);
    }

    public String getProcessType() {
        return processType.get();
    }

    public SimpleStringProperty processTypeProperty() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType.set(processType);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
