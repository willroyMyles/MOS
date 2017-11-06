package processes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import support.Process;

import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileManger extends Process {

    private static int[] array = new int[100];
    private static FCB[] array1 = new FCB[100];
    private static int usedRom = 0;
    private static Canvas canvas1 = new Canvas(360, 290);
    private Random rand = new Random();

    public FileManger() {
        pcb.setPid("pid3");
        pcb.setAppName("File Manager");
        pcb.setMemorySize(200);
        pcb.setRomSize(150);
        pcb.setProcessType("System");
        pcb.setStatus("Running");

        try {
            FileWriter writer = new FileWriter("ProcessHistory.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }


        darwGrid();

        canvas1.setOnMouseClicked((javafx.scene.input.MouseEvent e) -> {

//            System.out.println(+e.getX() +"  "+ +e.getY());
//            System.out.println(getX(+e.getX()) +"  "+ getY(+e.getY()));
//            System.out.println(  "index of object = "+      (getX(+e.getX())+ (18* getY(+e.getY()))) );
//            System.out.println(  "\n"+      array1[(getX(+e.getX())+ (18* getY(+e.getY()))) -1].toString() );

            GraphicsContext gc = canvas1.getGraphicsContext2D();

            gc.translate(0, 140);
            gc.clearRect(0, 0, 360, 80);

            try {
                FCB temp = array1[(getX(+e.getX()) + (18 * getY(+e.getY()))) - 1];
                gc.fillText(temp.toString(), 10, 10);
            } catch (NullPointerException n) {
                gc.fillText("empty", 10, 10);
            }
            gc.translate(0, -140);


        });
    }

    public static int[] getArray() {
        return array;
    }

    public static Canvas getCanvas1() {
        return canvas1;
    }

    public FCB getFCBInfo(MouseEvent e) {

        try {
            FCB temp = array1[(getX(+e.getX()) + (18 * getY(+e.getY()))) - 1];
            return temp;

        } catch (NullPointerException n) {
            return null;
        }
    }

    private void darwGrid() {
        GraphicsContext gc = canvas1.getGraphicsContext2D();
        gc.setLineWidth(.1);
        gc.setFill(Color.rgb(127, 221, 57, 0.1176));

        for (int i = 0; i < array.length; i++) {
            gc.strokeRect(x(i), y(i), 20, 20);
            gc.fillRect(x(i), y(i), 20, 20);

        }

    }

    public boolean allocate(Process p) {

        if (usedRom + p.getPcb().getRomSize() <= 1000) {

            for (int i = 0; i < p.getPcb().getRomSize(); i += 10) {

                if (!enterInArrayLinked(p)) {
                    enterInArraySequential(p);
                }
            }

            usedRom = usedRom + p.getPcb().getRomSize();
            draw();
            updateUsed();
            System.out.println("allocated");


        } else {
            System.out.println("system full enuh bossy");
        }


        return false;
    }

    private void updateUsed() {
        GraphicsContext gc = canvas1.getGraphicsContext2D();
gc.translate(0,220);

        gc.clearRect(0,0,360,60);
        gc.setFill(Color.rgb(30,30,40,0.9));
        gc.fillRect(0,0,360,30);
        gc.setFill(Color.rgb(240, 42, 94, 0.902));
        gc.fillRect(0,0,(usedRom*9/25),30);
        //    gc.applyEffect(new DropShadow(6,2,2,Color.grayRgb(200)));
        gc.setFont(Font.font("System Bold"));
        gc.setFill(Color.rgb(30,30,40,0.9));

        gc.fillText("Used Rom : "+usedRom + "                  Remaining : "+(1000-usedRom), 20,20);
        gc.translate(0,-220);


    }

    private void enterInArraySequential(Process p) {
        for (int i = 0; i < array.length; i += 10) {
            if (array[i] == 0) {
                array[i] = Character.getNumericValue(p.getPcb().getPid().charAt(3));
                array1[i] = new FCB();
                array1[i].fileID = "FID" + i;
                array1[i].processId = p.getPcb().getPid();
                array1[i].gameState = p.getPcb().getStatus();
                array1[i].startIndex = i * 10;
                array1[i].endIndex = array1[i].getStartIndex() + 10;

                return;
            }
        }
    }

    private boolean enterInArrayLinked(Process p) {

        for (int i = 0; i <= 4; i++) {
            int n = rand.nextInt(99);

            if (array[n] == 0) {
                array[n] = Character.getNumericValue(p.getPcb().getPid().charAt(3));
                // array[n] = Character.getNumericValue(p.getPcb().getPid().charAt(3));
                array1[n] = new FCB();
                array1[n].fileID = "FID" + i;
                array1[n].processId = p.getPcb().getPid();
                array1[n].gameState = p.getPcb().getStatus();
                array1[n].startIndex = n * 10;
                array1[n].endIndex = array1[n].getStartIndex() + 10;
                return true;
            }

        }
        return false;
    }

    public void draw() {
        GraphicsContext gc = canvas1.getGraphicsContext2D();
        gc.setStroke(Color.gray(0.098));
        //       gc.setGlobalBlendMode(BlendMode.ADD);
        //    gc.setFill(Color.rgb(0, 100, 200, 1));

        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                switch (array[i]) {
                    case 1:
                        gc.setFill(Color.rgb(0, 183, 200, 0.9882));
                        break;
                    case 2:
                        gc.setFill(Color.rgb(10, 79, 110, 0.9882));
                        break;
                    case 3:
                        gc.setFill(Color.rgb(0, 100, 200, .99));
                        break;
                    default:
                        gc.setFill(Color.rgb(255, 248, 80));
                        break;
                }


                gc.fillRect(x(i), y(i), 20, 20);
                //    Tooltip.install(nnn,new Tooltip("testing"));
                gc.strokeRect(x(i), y(i), 20, 20);


            }


        }
    }

    private double x(int i) {
        int g = i % 18;
        return g * 20;
    }

    private double y(int i) {
        int g = i / 18;
        return g * 20;
    }

    private int getX(double num) {
        for (int i = 0; i <= 360; i += 20) {
            if (num > i && num < i + 20) {
                if ((i / 20) < 1)
                    return 1;
                return (i / 20) + 1;
            }
        }
        return 0;
    }

    private int getY(double num) {
        for (int i = 0; i <= 120; i += 20) {
            if (num > i && num < i + 20) {

                if ((i / 20) < 1)
                    return 0;
                return (i / 20);

            }
        }
        return 0;
    }

    public class FCB {

        private String fileID, processId, gameState;
        private int startIndex, endIndex;

        public FCB() {
            fileID = "";
            processId = "";
            gameState = "";
            startIndex = 0;
            endIndex = 0;
        }

        public String getFileID() {
            return fileID;
        }

        public void setFileID(String fileID) {
            this.fileID = fileID;
        }

        public String getProcessId() {
            return processId;
        }

        public void setProcessId(String processId) {
            this.processId = processId;
        }

        public String getGameState() {
            return gameState;
        }

        public void setGameState(String gameState) {
            this.gameState = gameState;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(int endIndex) {
            this.endIndex = endIndex;
        }

        @Override
        public String toString() {
            return "FCB{" +
                    "fileID='" + fileID + "\n" +
                    ", processId='" + processId + "\n" +
                    ", gameState='" + gameState + "\n" +
                    ", startIndex=" + startIndex +
                    ", endIndex=" + endIndex +
                    '}';
        }
    }

}
