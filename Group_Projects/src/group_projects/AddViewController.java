/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group_projects;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author anormal
 */


public class AddViewController implements Initializable {

    /**
     * Initializes the controller class.
     */

    public MainViewController mc;
    static Group og;
    @FXML
    private TextArea SName;
    
    @FXML
    private ListView<?> DocList;

    @FXML
    private TextField GName;

    @FXML
    private Button edit;
    
    public static void setInfo(Group g){
        og = g;
    }

    @FXML
    void Create(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        if (og != null){
            DBConn.dbExecuteUpdate("Update Groups set G_Name = '" + GName.getText() + "' , StuNote = '" + SName.getText() +"' where id = '"+og.getId() +"';");
            og.setGName(GName.getText());
            og.setUsers(SName.getText());
            mc.tree.refresh();
        }
        else{
            DBConn.dbExecuteUpdate("Insert into Groups Values ('"+Group.getYear() +"-"+(++Group.number)+"','" + GName.getText() + "','" + SName.getText()+"');");
            mc.getGroupList();
            
        }
        
    }

    @FXML
    void AddStu(ActionEvent event) {

    }

    @FXML
    void DelStu(ActionEvent event) {

    }
    
    @FXML
    void DelFile(ActionEvent event) {

    }
    
    @FXML
    void AddFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog((Stage) GName.getScene().getWindow());
        if(file == null) return;
        List a = new ArrayList<String>();
        a.add(file.toString());
        DocList.getItems().addAll(a);
        
                
    }

    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(og != null){
            SName.setText(og.getUsers());
            GName.setText(og.getGName());
            edit.setText("UPDATE");
        }
    }    
    
}
