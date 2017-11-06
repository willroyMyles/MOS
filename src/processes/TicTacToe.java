package processes;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import support.Controller;
import support.Process;

import java.io.IOException;

public class TicTacToe extends Process{


 //   private static TicControl tic = new TicControl();


    public TicTacToe(int a) {
        pcb.setPid("pid"+a);
        pcb.setAppName("Tic Tac Toe");
        pcb.setMemorySize(50);
        pcb.setRomSize(50);
        pcb.setProcessType("game");
        pcb.setStatus("Running");
    }


}
