package processes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import support.Controller;
import support.Process;

import java.util.ArrayList;
import java.util.Random;

public class MemoryManager extends Process
{



    private static Canvas canvas = new Canvas(600,300);
    private static int blockA = 350, blockB = 400, blockC = 250;
    private static int usedA=0,usedB=0,usedC=0;
    private ArrayList list = new ArrayList();


    public MemoryManager(){
        pcb.setPid("pid1");
        pcb.setAppName("Memory Manager");
        pcb.setMemorySize(150);
        pcb.setRomSize(200);
        pcb.setProcessType("System");
        pcb.setStatus("Running");

  }

    public static Canvas getCanvas() {
        return canvas;
    }




    public void allocate(){
        allocate(Controller.getTm());
        allocate(Controller.getMm());
        allocate(Controller.getFm());



    }

    public boolean allocate(Process p) {

        if(tryA(p)){
            System.out.println(p.getPcb().getAppName() +"allocated to block a");
            p.getPcb().setBlockAssigned("a");
            list.add(p);
            return true;
        }
        if(tryB(p)){
            System.out.println(p.getPcb().getAppName() +"allocated to block b");
            p.getPcb().setBlockAssigned("b");

            list.add(p);

            return true;
        }
        if(tryC(p)){
            System.out.println(p.getPcb().getAppName() +"allocated to block c");
            p.getPcb().setBlockAssigned("c");

            list.add(p);

            return true;
        }

        System.out.println(p.getPcb().getAppName() +" could not be allocated to block c");
        return false;
    }

    private boolean tryC(Process p) {
        if(blockC-p.getPcb().getMemorySize()>=0){
           draw((p.getPcb().getMemorySize()*8/10),250-blockC, "c",p);
            blockC = blockC-p.getPcb().getMemorySize();
            usedC = usedC+p.getPcb().getMemorySize();

            //8/10

            return true;
        }

        return false;
    }

    private boolean tryB(Process p) {
        if(blockB-p.getPcb().getMemorySize()>=0){
        draw((p.getPcb().getMemorySize()/2),400-blockB, "b",p);
            blockB = blockB-p.getPcb().getMemorySize();
            usedB = usedB+p.getPcb().getMemorySize();

            //8/10

            return true;
        }
        return false;
    }

    private boolean tryA(Process p) {
        if(blockA-p.getPcb().getMemorySize()>=0){
         draw(p.getPcb().getMemorySize()*4/7,350-blockA, "a",p);
            blockA = blockA-p.getPcb().getMemorySize();
            usedA = usedA+p.getPcb().getMemorySize();
            //8/10

            return true;
        }

        return false;
    }

    public void draw(int size, int block, String c, Process p) {


        GraphicsContext gc = canvas.getGraphicsContext2D();

        int a=0;
        Random rand = new Random();

        gc.setFill(Color.rgb(rand.nextInt(205) + 50, rand.nextInt(205) + 50, rand.nextInt(205) + 50, .5));
        gc.setStroke(Color.rgb(rand.nextInt(205) + 50, rand.nextInt(205) + 50, rand.nextInt(205) + 50, .5));

        if(c.compareToIgnoreCase("c")==0){
            a = 400;
            gc.fillRect(a+block*8/10,0,size,200);
            drawLabel(a+block*8/10,p,size);
         //   gc.strokeRect(a+block*8/10,0,size,200);
            p.getPcb().setMemLocation(a+block*8/10);

        }if(c.compareToIgnoreCase("b")==0){
            a = 200;
            gc.fillRect(a+block/2,0,size,200);
            drawLabel(a+block/2,p,size);

            //    gc.strokeRect(a+block/2,0,size,200);
            p.getPcb().setMemLocation(a+block/2);

        }if(c.compareToIgnoreCase("a")==0){
            a = 0;
            gc.fillRect((a+block*4/7),0,size,200);
            drawLabel(a+block*4/7,p,size);

            //    gc.strokeRect((a+block*4/7),0,size,200);
            p.getPcb().setMemLocation(a+block*4/7);

        }

//       JFXButton btn = new JFXButton();
//        btn.setPrefSize(size,200);
//        btn.setLayoutX(a+block);
//        btn.setLayoutY(0);
    }

    private void drawLabel(int i, Process p, int size) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(58, 59, 59));
        gc.translate(i+size/2,160);
        gc.rotate(-90);
        gc.fillText(p.getPcb().getAppName(),0,0);
        gc.rotate(90);
        gc.translate(-(i+size/2),-160);

    }

    public void clear(Process p){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        switch (p.getPcb().getBlockAssigned()){
            case "a":
                usedA = usedA-p.getPcb().getMemorySize();
                blockA = blockA+p.getPcb().getMemorySize();
                gc.clearRect(p.getPcb().getMemLocation(),0,p.getPcb().getMemorySize()*4/7,200);

                break;
            case "b":
                usedB = usedB-p.getPcb().getMemorySize();
                blockB = blockB+p.getPcb().getMemorySize();
                gc.clearRect(p.getPcb().getMemLocation(),0,p.getPcb().getMemorySize()/2,200);

                break;
            case "c":
                usedC = usedC-p.getPcb().getMemorySize();
                blockC = blockC+p.getPcb().getMemorySize();
                gc.clearRect(p.getPcb().getMemLocation(),0,p.getPcb().getMemorySize()*8/10,200);

                break;
            default:
                break;
        }


    }

}
