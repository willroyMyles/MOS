package support;

import com.jfoenix.controls.JFXButton;
import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotImage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import processes.FileManger;
import processes.MemoryManager;
import processes.TaskManager;
import processes.TicTacToe;

import java.io.IOException;
import java.util.ArrayList;

public class Controller extends Application {

    private static TaskManager tm = new TaskManager();
    private static MemoryManager mm = new MemoryManager();
    private static FileManger fm = new FileManger();
    private static String pid;
    private static String g;
    private static ArrayList stageArray = new ArrayList();
    @FXML
    public Label label1;
    @FXML
    JFXButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, quit;
    @FXML
    JFXButton taskBtn, memBtn, fmExitBtn, exitpmBtn, rpBtn, epBtn, readFileBtn, shutdownBtn;
    private Stage primaryStage, taskStage, memStage, fileStage, win;
    private AnchorPane page;
    //tic tac toe items
    private Stage ticStage;
    private int ticks = 0, val = 3, setter = 0;
    @FXML
    private TableColumn pidCol, pnCol, ptCol, stCol;
    @FXML
    private TableView pTable;

    public static TaskManager getTm() {
        return tm;
    }

    public static MemoryManager getMm() {
        return mm;
    }

    public static FileManger getFm() {
        return fm;
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("../frames/main.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("new OS");
            primaryStage.setOnHidden(e -> Platform.exit());
            primaryStage.setOnCloseRequest(event -> {
                tm.closeAll();
            });


            //   primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();

            mm.allocate();
            tm.addToProcessList(mm);
            tm.addToProcessList(tm);
            tm.addToProcessList(fm);
            fm.allocate(mm);
            fm.allocate(fm);
            fm.allocate(tm);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void launchTask() {
        if (taskStage != null) {
            taskStage.show();
            setTable();
            return;
        }

        try {
            taskStage = new Stage();
            page = (AnchorPane) FXMLLoader.load(getClass().getResource("../frames/tm.fxml"));
            Scene scene = new Scene(page);
            taskStage.setScene(scene);
            //  taskStage.setTitle("Home and Away Institute");

            //  proStage.initStyle(StageStyle.UNDECORATED);
            taskStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void launchMemory() {
        if (memStage != null) {
            memStage.show();
            return;
        }

        try {
            memStage = new Stage();
            page = (AnchorPane) FXMLLoader.load(getClass().getResource("../frames/mm.fxml"));
            Scene scene = new Scene(page);
            memStage.setScene(scene);
            Canvas canvas = mm.getCanvas();
            canvas.setLayoutY(100);
            //  canvas.translateYProperty();
            page.getChildren().add(canvas);
            memStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///tictactoe part

    @FXML
    private void launchTicTacToe() {


        val++;
        TicTacToe game = new TicTacToe(val);
        mm.allocate(game);
        fm.allocate(game);
        tm.addToProcessList(game);
//        int r = fm.checkAllocation(game);
//
//        if (r == -2) {
//            //it was blocked
//            //write statemnt to cancel game
//            pm.remove(game);
//            //game = null;
//            return;
//        }


        try {
            ticStage = new Stage();
            page = (AnchorPane) FXMLLoader.load(getClass().getResource("../frames/tic.fxml"));
            Scene scene = new Scene(page);
            ticStage.setScene(scene);
            ticStage.setTitle("Tic Tac Toe");


            ticStage.setOnCloseRequest((we) -> {
                System.out.println("Stage is closing");
                game.getPcb().setStatus("Terminated");
////                playAgain();
                mm.clear(game);
                tm.removeFromProcessList(game);

            });


            ticStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

                public void handle(KeyEvent event) {
                    if (event.getText().compareToIgnoreCase("X") == 0) {

                        ticStage.close();
                    }
                    if (event.getText().compareToIgnoreCase("H") == 0) {

                        tm.hide(game);
                        ticStage.hide();
                    }


                }
            });

            ticStage.setOnHidden(event -> {

            });
            ticStage.show();
            stageArray.add(ticStage);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    public void playAgain() {

        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");
        setBtns(false);
        label1.setText("");
        ticks = 0;
    }

    private boolean compareBtns(JFXButton b1, JFXButton b2, JFXButton b3) {


        if (b1.getText().compareTo(b2.getText()) == 0 && b2.getText().compareTo(b3.getText()) == 0) {
            if (b1.getText().compareTo("X") == 0) {
                g = "player one wins!";
            } else {
                g = "player two wins!";
            }


            return true;

        }
        return false;

    }


    @FXML
    private void tick(ActionEvent e) {

        JFXButton btn = (JFXButton) e.getSource();
        if (ticks % 2 == 0) {
            btn.setText("X");
            btn.setDisable(true);

        } else
            btn.setText("O");
        btn.setDisable(true);

        // System.out.print(ticks);'


        if (compareBtns(btn1, btn2, btn3) && compDisabled(btn1, btn2, btn3) ||
                compareBtns(btn4, btn5, btn6) && compDisabled(btn4, btn5, btn6) ||
                compareBtns(btn7, btn8, btn9) && compDisabled(btn7, btn8, btn9) ||
                compareBtns(btn1, btn4, btn7) && compDisabled(btn1, btn4, btn7) ||
                compareBtns(btn2, btn5, btn8) && compDisabled(btn2, btn5, btn8) ||
                compareBtns(btn3, btn6, btn9) && compDisabled(btn3, btn6, btn9) ||
                compareBtns(btn1, btn5, btn9) && compDisabled(btn1, btn5, btn9) ||
                compareBtns(btn3, btn5, btn7) && compDisabled(btn3, btn5, btn7)) {
            System.out.println("winnings");
            //TODO stop game from running, pop up window and restart game;
            //won(g);
            setBtns(true);
            label1.setText(g + "\nwhat would you like to do?");
            ticks = 0;

        }


        if (ticks == 9) {
            //gameover();
            label1.setText("draw!" + "\nwhat would you like to do?");
            ticks = 0;
        }
        ticks++;
        ///  System.out.print("dfgh");

    }

    private void setBtns(boolean n) {

        btn1.setDisable(n);
        btn2.setDisable(n);
        btn3.setDisable(n);
        btn4.setDisable(n);
        btn5.setDisable(n);
        btn6.setDisable(n);
        btn7.setDisable(n);
        btn8.setDisable(n);
        btn9.setDisable(n);

    }

    private boolean compDisabled(JFXButton btn1, JFXButton btn2, JFXButton btn3) {

        if (btn1.isDisabled() && btn2.isDisabled() && btn3.isDisabled())
            return true;
        return false;
    }


    ///tictactoe finished


    //task managet part
    @FXML
    public void setTable() {
        ObservableList<PsuedoPM> data = tm.getObservableList();

        pidCol.setCellValueFactory(new PropertyValueFactory<>("Pid"));
        pnCol.setCellValueFactory(new PropertyValueFactory<>("ProcessName"));
        ptCol.setCellValueFactory(new PropertyValueFactory<>("ProcessType"));
        stCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        pTable.setItems(null);
        pTable.setItems(data);

    }

    @FXML
    public void endProcess() {
        PsuedoPM pspm = (PsuedoPM) pTable.getSelectionModel().getSelectedItem();
        String pid = pspm.getPid();
        Process temp = tm.returnProcess(pid);
        mm.clear(temp);
        tm.endProcess(pid);
        setTable();
        int stageNum = Character.getNumericValue(pid.charAt(3));
        primaryStage = (Stage) stageArray.get(stageNum - 4);
        // stageArray.remove(stageNum-4);
        primaryStage.close();

    }

    public void endProcess(Process p) {

        String pid = p.getPcb().getPid();
        Process temp = tm.returnProcess(pid);
        //   mm.clear(temp);
        tm.endProcess(pid);
        setTable();
        int stageNum = Character.getNumericValue(pid.charAt(3));
        primaryStage = (Stage) stageArray.get(stageNum - 4);
        primaryStage.close();

    }

    @FXML
    public void restartProcess() {
        PsuedoPM pspm = (PsuedoPM) pTable.getSelectionModel().getSelectedItem();
        String pid = pspm.getPid();
        Process temp = tm.returnProcess(pid);

        //    temp = new
        //    fm.checkAllocation(temp);
        if (mm.allocate(temp)) {
            temp.getPcb().setStatus("Running");
            tm.removeFromTerminatedProcessList(pid);
        } else
            return;
        setTable();
        int stageNum = Character.getNumericValue(pid.charAt(3));
        primaryStage = (Stage) stageArray.get(stageNum - 4);

        primaryStage.show();
    }

    @FXML
    private void disableBtn() {
        try {
            PsuedoPM pspm = (PsuedoPM) pTable.getSelectionModel().getSelectedItem();
            if (pspm.getPid() == "pid1" || pspm.getPid() == "pid2"
                    || pspm.getPid() == "pid3")
                epBtn.setDisable(true);
            else
                epBtn.setDisable(false);

            if (pspm.getStatus().compareToIgnoreCase("running") == 0)
                rpBtn.setDisable(true);
            else {
                rpBtn.setText("Restart Process");
                rpBtn.setDisable(false);
                epBtn.setDisable(true);
            }

            if (pspm.getStatus().compareToIgnoreCase("Hidden") == 0) {
                rpBtn.setText("Show Process");
                rpBtn.setDisable(false);
            }


        } catch (NullPointerException e) {

        }

    }

    //task manager end


    @FXML
    private void launchFile() {
        //   TaskManager.launch();

        if (fileStage != null) {
            fileStage.show();
            return;
        }


        try {
            fileStage = new Stage();
            page = (AnchorPane) FXMLLoader.load(getClass().getResource("../frames/fm.fxml"));
            Scene scene = new Scene(page);
            fileStage.setScene(scene);
            fileStage.setTitle("");
            fileStage.setOnCloseRequest(event -> {

            });
            Canvas canvas = fm.getCanvas1();
            canvas.setStyle("-fx-background-color: #1d1d1d; -fx-border-width: 1; -fx-border-color: #1d1d1d");


            page.getChildren().add(canvas);
//            canvas.setTranslateX(0);
            canvas.setTranslateY(100);
            canvas.translateYProperty();
            fileStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    public void shutdown(ActionEvent e) {
        JFXButton btn = (JFXButton) e.getSource();

        primaryStage = (Stage) btn.getScene().getWindow();
        if (primaryStage == ticStage) {
            ticStage.close();
            return;
        }
        primaryStage.close();


    }

    public void quit(ActionEvent e) {
        JFXButton btn = (JFXButton) e.getSource();
        ticStage = (Stage) btn.getScene().getWindow();

        ticStage.fireEvent(
                new WindowEvent(
                        ticStage,
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );


    }

}
