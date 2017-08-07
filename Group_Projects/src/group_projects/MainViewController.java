package group_projects;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainViewController {

   @FXML
    public TreeView<Group> tree;

    @FXML
    private ListView<?> DocList;


    @FXML
    private Label info;

    
    
    
    @FXML
    void newGroup(ActionEvent event) {
        try {
            String resource = "AddView.fxml";

            URL location = getClass().getResource(resource);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent root = (Parent) fxmlLoader.load();

            AddViewController controller = (AddViewController) fxmlLoader.getController();
            controller.mc = this;
            Scene addScene = new Scene(root);
            Stage addStage = new Stage();
            addStage.setScene(addScene);
            addStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void click(MouseEvent mouseEvent)
    {
        TreeItem<Group> gr = tree.getSelectionModel().getSelectedItem();
        if (gr == null) return;
        Group clicked = gr.getValue();
        String clickedItem = clicked.getId();
        if(clickedItem.equals("roott") || new Integer(clickedItem.substring(clickedItem.indexOf("-")+1, clickedItem.length())) == 0) return;
        
        if(mouseEvent.getClickCount() == 2)
        {
            AddViewController.setInfo(clicked);
            try {
                String resource = "AddView.fxml";

                URL location = getClass().getResource(resource);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

                Parent root = (Parent) fxmlLoader.load();

                AddViewController controller = (AddViewController) fxmlLoader.getController();
                controller.mc = this;
                Scene addScene = new Scene(root);
                Stage addStage = new Stage();
                addStage.setScene(addScene);
                addStage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
           

        }

        else if (mouseEvent.getClickCount()==1){
            
            String text =   "Group ID   : " + clickedItem + "\n" +
                            "Group Name : " + clicked.getGName() + "\n" + 
                            "Description: \n" + clicked.getUsers();
            
            info.setText(text);
            System.out.println(clickedItem);
        }
    }
    
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DocList.setVisible(false);
        getGroupList();
        
        
        
        
        
        
        
        //tree.setOnMouseClicked(new EventHandler<MouseEvent>()
        //{
            //@Override
            
        //});
    }
    
    public void getGroupList() throws SQLException, ClassNotFoundException {
       
        ResultSet rs1 = DBConn.dbExecuteQuery("Select * from Groups;");
        Map<String,TreeItem<Group>> years = new HashMap<String,TreeItem<Group>>();
        TreeItem<Group> rootItem = new TreeItem<Group>(new Group("roott"));
        tree.setRoot(rootItem);
        rootItem.setExpanded(true);
        while (rs1.next()) {
            Group per = new Group(rs1.getString("id"));
            String thatYear = per.getId().substring(0,4);
            if(!years.containsKey(thatYear)) {
                TreeItem<Group> yearItem = new TreeItem<Group>(new Group(thatYear+"-0"));
                yearItem.setExpanded(true);
                years.put(thatYear,yearItem);
                rootItem.getChildren().add(yearItem);
            }
            per.setGName(rs1.getString("G_Name"));
            per.setUsers(rs1.getString("StuNote"));
            ResultSet rs2 = DBConn.dbExecuteQuery("Select * from Docs where id =" + per.getId() + ";");
            List<String> docc = new ArrayList<String>(); 
            while (rs2.next()){
                docc.add(rs2.getString("D_Loc"));
            }
            rs2.close();
            per.setDocs(docc);
            TreeItem<Group> item = new TreeItem<Group>(per);            
            years.get(thatYear).getChildren().add(item);
            
        }
        rs1.close();
        
        
    }

}
