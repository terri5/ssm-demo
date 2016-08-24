package com.terri.datasource.hbase;
/** 
 *  
 */  
  
import java.io.IOException;  
  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.hbase.HBaseConfiguration;  
import org.apache.hadoop.hbase.HColumnDescriptor;  
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;  
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;  
import org.apache.hadoop.hbase.client.Put;  
import org.apache.hadoop.hbase.client.Result;  
import org.apache.hadoop.hbase.client.ResultScanner;  
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;  
  
public class HBaseTestCase2 {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
    	/**
    	 * 注意，需要启动所有的zookeeper.
    	 */
        String tableName = "test";  
        String columnFamily = "cf";  
        try {  
  
            if (true == HBaseTestCase.delete(tableName)) {  
                System.out.println("Delete Table " + tableName + " success!");  
  
            }  
            TableName tabName=TableName.valueOf(tableName, columnFamily);
  
            HBaseTestCase2.create(tabName, columnFamily);  
         

            HBaseTestCase2.put(tabName, "row1", columnFamily, "column1",  
                    "data1");  
            HBaseTestCase2.put(tabName, "row2", columnFamily, "column2",  
                    "data2");  
            HBaseTestCase2.put(tabName, "row3", columnFamily, "column3",  
                    "data3");  
            HBaseTestCase2.put(tabName, "row4", columnFamily, "column4",  
                    "data4");  
            HBaseTestCase2.put(tabName, "row5", columnFamily, "column5",  
                    "data5");  
  
            HBaseTestCase2.get(tabName, "row1");  
  
            HBaseTestCase2.scan(tabName);  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    static Configuration cfg = HBaseConfiguration.create();  
    static Connection connection=null;
    static {  
    	
    	  try {
			     connection = ConnectionFactory.createConnection(cfg);
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println(cfg.get("hbase.master"));  
                
    }  
  
    public static void create(TableName tableName, String columnFamily)  
            throws Exception {  
        
        Admin admin = connection.getAdmin();
        if (admin.tableExists(tableName))  {
            System.out.println(tableName + " exists!");  
        } else {  
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);  
            tableDesc.addFamily(new HColumnDescriptor(columnFamily));  
            admin.createTable(tableDesc);  
            System.out.println(tableName + " create successfully!");  
        }  
    }  
  
    public static void put(){
    	
    	
        }
  
    public static void put(TableName tablename, String row, String columnFamily,  
            String column, String data) throws Exception {  
  
        Table table = connection.getTable(tablename);
        Put put = new Put(Bytes.toBytes(row));  
                    
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column),  
                Bytes.toBytes(data));  
  
        table.put(put); 
  
        System.out.println("put '" + row + "', '" + columnFamily + ":" + column  
                + "', '" + data + "'");  
  
    }  
  
    public static void get(TableName tablename, String row) throws Exception {  
        Table table = connection.getTable(tablename);  
        Get get = new Get(Bytes.toBytes(row));  
        Result result = table.get(get);  
        System.out.println("Get: " + result);  
    }  
  
    public static void scan(TableName tableName) throws Exception {  
  
    	   Table table = connection.getTable(tableName);  
        Scan s = new Scan();  
        ResultScanner rs = table.getScanner(s);  
  
        for (Result r : rs) {  
            System.out.println("Scan: " + r);  
  
        }  
    }  
  
    public static boolean delete(TableName tableName) throws IOException {  
  
        Admin admin = connection.getAdmin(); 
        if (admin.tableExists(tableName)) {  
            try {  
                admin.disableTable(tableName);  
                admin.deleteTable(tableName);  
            } catch (Exception e) {  
                e.printStackTrace();  
                return false;  
            }  
        }  
        return true;  
    }  
}  