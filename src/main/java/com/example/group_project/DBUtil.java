package com.example.group_project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import static java.sql.DriverManager.getConnection;

public class DBUtil {
    private static Connection connection = null;
    private static Statement statement = null;

    public static void dbConnect() throws SQLException {
        String dbURL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
        String username = "COMP214_F23_shah_19";
        String password = "password";
        connection =DriverManager.getConnection(dbURL, username, password);
        System.out.println("DB is connected");
        statement = connection.createStatement();

    }

    public static void dbDisConnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("DB is disconnected");
        }

    }

    public static void insert(int ownerID, String name, String address, String phone, String email, int carID, String make,
                              String model, int carVIN, int builtYear, String type, String date, String description,
                              int cost) throws SQLException {
        dbConnect();
        String sql = "INSERT INTO OWNER VALUES ("+ownerID+", '"+name+"', '"+address+"', '"+phone+"', '"+email+"')";
        statement.executeUpdate(sql);
        System.out.println("1 row inserted");
        sql = "INSERT INTO CAR VALUES ("+carID+", '"+make+"', '"+model+"', "+carVIN+", "+builtYear+", '"+type+"')";
        statement.executeUpdate(sql);
        System.out.println("1 row inserted");
        sql = "INSERT INTO REPAIR (ownerid, carid, s_date, s_description, s_cost) VALUES ("+ownerID+", "+carID+", '"+date+"', '"+description+"', "+cost+")";
        statement.executeUpdate(sql);
        System.out.println("1 row inserted");
        if (statement != null) statement.close();
        dbDisConnect();
    }
    public static void update(int ownerID, String name, String address, String phone, String email, int carID, String make,
                              String model, int carVIN, int builtYear, String type, String date, String description,
                              int cost) throws SQLException {
        dbConnect();
        String sql = "UPDATE OWNER SET NAME='"+name+"', ADDRESS='"+address+"', PHONE='"+phone+"', EMAIL='"+email+"' WHERE OWNERID="+ownerID;
        statement.executeUpdate(sql);
        System.out.println("1 row UPDATED");
        sql = "UPDATE CAR SET MAKE='"+make+"', MODEL='"+model+"', VIN="+carVIN+", BUILDYEAR="+builtYear+", TYPE='"+type+"'  WHERE CARID="+carID;
        statement.executeUpdate(sql);
        System.out.println("1 row UPDATED");
        sql = "UPDATE REPAIR SET S_DATE='"+date+"', S_DESCRIPTION='"+description+"', S_COST="+cost+"  WHERE CARID="+carID;
        statement.executeUpdate(sql);
        System.out.println("1 row inserted");
        if (statement != null) statement.close();
        dbDisConnect();
    }


    public static ResultSet query(String sql) throws SQLException {
        CachedRowSet crs= RowSetProvider.newFactory().createCachedRowSet();
        dbConnect();
        ResultSet resultSet = statement.executeQuery(sql);
        crs.populate(resultSet);
        dbDisConnect();
        return crs;
    }

    public static void deleteData(String tableName, String idName, int id) throws SQLException {
        dbConnect();
        String sql = "DELETE FROM " + tableName + " WHERE "+idName+"=" + id;
        statement.executeUpdate(sql);
        System.out.println(id + "has been deleted");
        dbDisConnect();
    }


//    public static void main(String[] args) throws SQLException {
//        DBUtil.dbConnect();
//        insert(34, "Tom", "45 Blvd", "4755893849", "tom@email.com", 453, "Audi",
//                "Q7", 897545,2023, "SUV", "14-02-25", "replace tyre",
//        899);
//        DBUtil.dbDisConnect();
//   }
}
