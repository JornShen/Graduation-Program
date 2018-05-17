package com.sch.util;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HiveJdbcClient {

    private String driverName;

    private String url;

    private String userName; // 此处没有使用

    private String psw; // 此处没有使用

    private Connection connection;

    public HiveJdbcClient(){
        //　先启动 mysql, 运行 hive --service metastore, hive --service hiveserver2
        // 设置账号密码参考 https://blog.csdn.net/hua_ed/article/details/51693659
        // 默认缺省的密码是 None, 不需要密码

        driverName = "org.apache.hive.jdbc.HiveDriver";
        url = "jdbc:hive2://localhost:10000/aliclick";
        userName = "hive";
        psw = "hive";
    }

    private void getConnection() throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        connection = DriverManager.getConnection(url);
    }


    private void closeConnection() throws SQLException {
        if (connection != null) connection.close();
    }

    public Map<String, List<Integer>> getGenderPeopleGroupByShoppingLevel() throws SQLException {
        Map<String, List<Integer>> result = new HashMap<>();
        getConnection();
        Statement stmt = connection.createStatement();

        String sql = "select gender, shopping_level, count(*) from userprofile group by gender, shopping_level";
        List<Integer> maleData = new ArrayList<>();
        List<Integer> femaleData = new ArrayList<>();

        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {
            int gender = res.getInt(1);
            if (gender == 1) maleData.add(res.getInt(3));
            else femaleData.add(res.getInt(3));
        }
        result.put("male", maleData);
        result.put( "female", femaleData);

        closeConnection();
        return result;
    }


    public static void main(String[] args) throws SQLException {

        Map<String, List<Integer>> results = new HiveJdbcClient().getGenderPeopleGroupByShoppingLevel();

        int b = 0;

    }
}
