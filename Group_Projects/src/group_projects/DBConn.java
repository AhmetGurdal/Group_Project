/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group_projects;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author anormal
 */
public class DBConn {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static Connection conn = null;
    // jdbc:mysql://hostname:port/database?useUnicode=true&characterEncoding=utf8
    private static final String URL = "jdbc:mysql://127.0.0.1/deneme?useUnicode=true&characterEncoding=utf8";
    private static final String USER = "anormal";
    private static final String PASSWORD = "anormal";
    static Statement stmt = null;

    public static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } 
        
        catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
    }
 
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e){
           throw e;
        }
    }
 

    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            dbConnect();
            //System.out.println("Select statement: " + queryStmt + "\n");
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(queryStmt);
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
            return crs;
            
            
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            
           
            dbDisconnect();
        }
        
        
    }
 
    
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        System.out.println(sqlStmt);
        try {
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at execute Update operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }
}
