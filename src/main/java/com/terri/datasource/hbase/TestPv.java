package com.terri.datasource.hbase;
/** 
 *  
 */  
  
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.hbase.HBaseConfiguration;  
import org.apache.hadoop.hbase.HColumnDescriptor;  
import org.apache.hadoop.hbase.HTableDescriptor;  
import org.apache.hadoop.hbase.client.Get;  
import org.apache.hadoop.hbase.client.HBaseAdmin;  
import org.apache.hadoop.hbase.client.HTable;  
import org.apache.hadoop.hbase.client.Put;  
import org.apache.hadoop.hbase.client.Result;  
import org.apache.hadoop.hbase.client.ResultScanner;  
import org.apache.hadoop.hbase.client.Scan;  
import org.apache.hadoop.hbase.util.Bytes;  
  
public class TestPv {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
    	/**
    	 * 注意，需要启动所有的zookeeper.
    	 */
        String tableName = "DEVICE_LOG_PV";  
        String columnFamily = "PV";  
        try {  
  
            if (true == TestPv.delete(tableName)) {  
                System.out.println("Delete Table " + tableName + " success!");  
                TestPv.create(tableName, columnFamily); 
  
                String fileName="/home/terri/Downloads/pv.txt";
                File file = new File(fileName);
                BufferedReader reader = null;
                try {
                    System.out.println("以行为单位读取文件内容，一次读一整行：");
                    reader = new BufferedReader(new FileReader(file));
                    String tmpStr = null;
                    int line = 1;
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    List <Put> list=new ArrayList<Put>();
                      
                    // 一次读入一行，直到读入null为文件结束
                    while ((tmpStr = reader.readLine()) != null) {
                        // 显示行号　
                    	   String[] arr=tmpStr.split("\t");
                    	   
                    	   String row=getHbaseRowKeyUnique(sdf.parse(arr[3]),arr[0]);                     
                    	    Put put = new Put(Bytes.toBytes(row));
                    	    put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("DMAC"),Bytes.toBytes(arr[0]));
                    	    put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("MAC"),Bytes.toBytes(arr[1]));
                    	    put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("IP"),Bytes.toBytes(arr[2]));
                    	    put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("DATETIME_POINT"),Bytes.toBytes(arr[3]));
                         put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("DAY_ID"),Bytes.toBytes(arr[12]));
                         list.add(put);
                         line++;
                       if(line%100000==0) {
                    	   puts(tableName,list);
                    	   list=new ArrayList();
                    	  //  break;
                                                            }
 
                                                  }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                        }
                    }
                }}  
            
  
       
         
  
     //       TestPv.get(tableName, "row1");  
  
   //        TestPv.scan(tableName);  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    static Configuration cfg = HBaseConfiguration.create();  
    static {  
        System.out.println(cfg.get("hbase.master"));  
    }  
  
    
    public static String getHbaseRowKeyUnique(Date businessTime,String dmac) {
    	    UUID uuid = UUID.randomUUID();
    	    int rand=(int)(Math.random()*10);
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfhm=new SimpleDateFormat("HHmm");
        SimpleDateFormat sdfb=new SimpleDateFormat("yyyyMMddHHmm");
        
        return sdf.format(now)+rand+sdfhm.format(now)+ dmac+sdfb.format(businessTime) + uuid.toString().substring(0, 6).toUpperCase();
    }
    public static void create(String tableName, String columnFamily)  
            throws Exception {  
        HBaseAdmin admin = new HBaseAdmin(cfg);  
        if (admin.tableExists(tableName)) {  
            System.out.println(tableName + " exists!");  
        } else {  
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);  
            tableDesc.addFamily(new HColumnDescriptor(columnFamily));  
            admin.createTable(tableDesc);  
            System.out.println(tableName + " create successfully!");  
        }  
    }  
    public static void puts(String tablename, List<Put> list) throws Exception {  
  
        HTable table = new HTable(cfg, tablename);  
   
        table.put(list);  
  
        System.out.println("put" + list.size());  
  
    }  
    public static void put(String tablename, String row, String columnFamily,  
            String column, String data) throws Exception {  
  
        HTable table = new HTable(cfg, tablename);  
        Put put = new Put(Bytes.toBytes(row));  
  
        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column),  
                Bytes.toBytes(data));  
  
        table.put(put);  
  
        System.out.println("put '" + row + "', '" + columnFamily + ":" + column  
                + "', '" + data + "'");  
  
    }  
  
    public static void get(String tablename, String row) throws Exception {  
        HTable table = new HTable(cfg, tablename);  
        Get get = new Get(Bytes.toBytes(row));  
        Result result = table.get(get);  
        System.out.println("Get: " + result);  
    }  
  
    public static void scan(String tableName) throws Exception {  
  
        HTable table = new HTable(cfg, tableName);  
        Scan s = new Scan();  
        ResultScanner rs = table.getScanner(s);  
  
        for (Result r : rs) {  
            System.out.println("Scan: " + r);  
  
        }  
    }  
  
    public static boolean delete(String tableName) throws IOException {  
  
        HBaseAdmin admin = new HBaseAdmin(cfg);  
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