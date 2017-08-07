/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group_projects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author anormal
 */
public class Group {
    private String id;
    static int year;
    static int number = -1;
    
    private List<String> docs;
    private String Users;
    private String GName;
    
    public Group(String ID){
        if (ID.isEmpty()){
            if (year != getYear()){
                number = 0;
            }
            id = getYear() +"-" + (++number);
        } else {
            id = ID;
            if(!ID.equals("roott")){
                number = new Integer(ID.substring(5));
                year = Integer.parseInt(ID.substring(0,4));
            }
        }
        
        docs = new ArrayList<String>();
        Users = "";
        GName = "";
    }

    public String getId() {
        return id;
    }

    public static int getYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    
    public List<String> getDocs() {
        return docs;
    }

    public void setDocs(List<String> docs) {
        this.docs = docs;
    }

    public String getUsers() {
        return Users;
    }

    public void setUsers(String Users) {
        this.Users = Users;
    }

    public String getGName() {
        return GName;
    }

    public void setGName(String GName) {
        this.GName = GName;
    }
    
    public String toString() {
        return id.equals("roott") ? "Groups" : id.substring(5).equals("0") ? id.substring(0,4) : GName;
    }
    
}
