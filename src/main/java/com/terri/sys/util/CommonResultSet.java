package com.terri.sys.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CommonResultSet
{
   private ArrayList records;
   private ArrayList metadata;

   private int rsTotalRows;

   public CommonResultSet()
   {
      records = new ArrayList();
      metadata = new ArrayList();
      rsTotalRows = 0;
   }

   /**
    * 
    * @param result
    *            ResultSet
    * @param prsbegin
    *            int
    * @param prsend
    *            int
    * @throws DaoException
    */
   private void CreateResult(ResultSet result, int prsbegin, int prsend, int totalrows) throws DaoException
   {
      records = new ArrayList();
      metadata = new ArrayList();
      rsTotalRows = 0;
      if (result == null)
         return;
      try
      {
         ResultSetMetaData rsmData = result.getMetaData();
         int rsfieldCount = rsmData.getColumnCount();
         String rsfieldName = null;
         Object rsfieldValue = null;
         int rsfieldType = 0;
         int rscount = 0;
         int tmpprsbegin = prsbegin;
         int tmpprsend = prsend;
         if (prsbegin > prsend)
         {
            prsbegin = 1;
            prsend = Integer.MAX_VALUE;
         }

         for (int i = 0; i < rsfieldCount; i++)
         {
            metadata.add(i, rsmData.getColumnName(i + 1));
         }

         /*
          * oracle.jdbc.rowset.OracleCachedRowSet ocrs = new
          * oracle.jdbc.rowset.OracleCachedRowSet(); ocrs.populate(result);
          * ocrs.last(); int rowcount = ocrs.getRow();
          */

         do
         {
            if ((!result.next()) || ((rscount >= DataTypes.MAXROW) && (tmpprsbegin < tmpprsend)))
               break;
            if (++rscount >= prsbegin && rscount <= prsend)
            {
               CommonRecord rsrec = new CommonRecord();
               for (int i = 1; i <= rsfieldCount; i++)
               {
                  rsfieldName = rsmData.getColumnName(i);
                  rsfieldType = rsmData.getColumnType(i);
                  /* 支持 clob, blob */
                  if ((rsfieldType == DataTypes.CLOB) && (rscount == 1))
                  {
                     try
                     {
                        java.sql.Clob clob = result.getClob(rsfieldName);
                        if (clob != null)
                        {
                           Reader inStream = clob.getCharacterStream();
                           char[] c = new char[(int) clob.length()];
                           inStream.read(c);
                           rsfieldValue = new String(c);
                           inStream.close();
                        }
                     }
                     catch (IOException e)
                     {
                        e.printStackTrace();
                        rsfieldValue = result.getObject(i);
                     }
                  }
                  else if ((rsfieldType == DataTypes.BLOB) && rscount == 1)
                  {
                     try
                     {
                        java.sql.Blob blob = result.getBlob(rsfieldName);                        
                        if (blob != null)
                        {
                           InputStream inStream = blob.getBinaryStream();
                           byte[] data = new byte[inStream.available()];
                           inStream.read(data);
                           rsfieldValue = data;
                           inStream.close();
                        }
                     }
                     catch (IOException e)
                     {
                        e.printStackTrace();
                        rsfieldValue = result.getObject(i);
                     }
                  }else if((rsfieldType == DataTypes.DATE)){
                     rsfieldValue = result.getTimestamp(rsfieldName);
                  }
                  else
                     rsfieldValue = result.getObject(i);
                  rsrec.addField(rsfieldName.toUpperCase(), rsfieldType, rsfieldValue, i);
               }

               records.add(rsrec);
            }
         } while (true);
         if (totalrows < 0)
            rsTotalRows = rscount;
         else
            rsTotalRows = totalrows;
      }

      catch (SQLException ex)
      {
         throw new DaoException(ex);
      }
      // catch (IOException ex) {
      // throw new DaoException(ex);
      // }
   }

   /**
    * 封装数据库结果集为本地结果集
    * 
    * @param result
    *            ResultSet
    * @throws DaoException
    */
   public CommonResultSet(ResultSet result) throws DaoException
   {
      CreateResult(result, 1, Integer.MAX_VALUE, -1);
   }

   /**
    * 封装数据库结果集为本地结果集
    * 
    * @param result
    *            ResultSet
    * @throws DaoException
    */
   public CommonResultSet(ResultSet result, int totalrows) throws DaoException
   {
      CreateResult(result, 1, Integer.MAX_VALUE, totalrows);
   }

   /**
    * 封装数据库结果集为本地结果集(指定封装结果集合的范围)
    * 
    * @param result
    *            ResultSet
    * @param pageNo
    *            int
    * @param pageSize
    *            int
    * @throws DaoException
    */
   public CommonResultSet(ResultSet result, int pageNo, int pageSize) throws DaoException
   {
      int prsbegin = (pageNo - 1) * pageSize + 1;
      int prsend = pageNo * pageSize;
      CreateResult(result, prsbegin, prsend, -1);
   }

   /**
    * 封装数据库结果集为本地结果集(指定封装结果集合的范围及总数)
    * 
    * @param result
    * @param prsbegin
    * @param prsend
    * @param totalrows
    * @throws DaoException
    */
   public CommonResultSet(ResultSet result, int prsbegin, int prsend, int totalrows) throws DaoException
   {
      CreateResult(result, prsbegin, prsend, totalrows);
   }

   /**
    * 获取字段头
    */
   public List getMetaData()
   {
      return metadata;
   }

   public void setMetaData(ArrayList metadata)
   {
      this.metadata = metadata;
   }

   /**
    * 增加一条记录到本地结果集
    * 
    * @param record
    *            CommonRecord
    */
   public void addRecord(CommonRecord record)
   {
      records.add(record);
   }

   /**
    * 从本地结果集删除一条记录
    * 
    * @param index
    *            int
    */
   public void removeRecord(int index)
   {
      records.remove(index);
   }

   /**
    * 判断本地结果集是否为空
    * 
    * @return boolean
    */
   public boolean isEmpty()
   {
      return length() <= 0;
   }

   /**
    * 返回本地结果集的大小
    * 
    * @return int
    */
   public int length()
   {
      return records.size();
   }

   /**
    * 从本地结果集取一条记录
    * 
    * @param index
    *            int
    * @return CommonRecord
    */
   public CommonRecord getRecord(int index)
   {

      return (CommonRecord) records.get(index);
   }

   /**
    * 用List封装记录集合, 可以使用List的功能
    * 
    * @return List
    */
   public List getTotalRecord()
   {
      return records;
   }

   /**
    * 返回本地结果集数量
    * 
    * @return int
    */
   public int getrsTotalRows()
   {
      return rsTotalRows;
   }

   public void setRsTotalRows(int rsTotalrows)
   {
      this.rsTotalRows = rsTotalrows;
   }

}
