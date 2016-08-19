package com.terri.sys.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PagedDataVO
{

   public static int DEFAULT_PAGESIZE = 10;
   private List list;
   private int pageNo;
   private int rowSize;
   private int total;
   private int totalRows;
   private int start;
   private ResultSet rs;
   private CommonResultSet resultSet;

   public PagedDataVO()
   {
      list = new ArrayList();

      /*       IHsConfigurator config =HsContext.getInstance();
             if (config.getParamValue("PageSize")!=null)
                 DEFAULT_PAGESIZE = new Integer(config.getParamValue("PageSize")).intValue(); 
             if (DEFAULT_PAGESIZE==0) DEFAULT_PAGESIZE=10 ;*/

   }

   public static PagedDataVO getEmptyPage()
   {
      PagedDataVO page = new PagedDataVO();
      page.setRowSize(1);
      page.setList(new ArrayList());
      page.setTotal(1);
      page.setPageNo(1);
      return page;
   }

   public int getTotalRows()
   {
      return totalRows;
   }

   public void setTotalRows(int totalRows)
   {
      this.totalRows = totalRows;
   }

   public List getList()
   {
      return list;
   }

   public int getPageNo()
   {
      return pageNo;
   }

   public int getRowSize()
   {
      return rowSize;
   }

   public int getTotal()
   {
      return total;
   }

   public void setList(List list)
   {
      this.list = list;
   }

   public void setPageNo(String i)
   {
      if (i == null || i.trim().length() == 0)
      {
         pageNo = 0;
      }
      else
      {
         pageNo = new Integer(i).intValue();
      }
   }

   public void setPageNo(int i)
   {
      pageNo = i;
   }

   public void setRowSize(String i)
   {
      if (i == null || i.trim().length() == 0)
      {
         rowSize = 0;
      }
      else
      {
         rowSize = new Integer(i).intValue();
      }
   }

   public void setRowSize(int i)
   {
      rowSize = i;
   }

   public void setTotal(String i)
   {
      if (i == null || i.trim().length() == 0)
      {
         total = 0;
      }
      else
      {
         total = new Integer(i).intValue();
      }
   }

   public void setTotal(int i)
   {
      total = i;
   }

   public void excecute()
   {
      if (pageNo <= 0)
         pageNo = 1;
      if (rowSize <= 0)
         rowSize = DEFAULT_PAGESIZE;
      int start = 1;
      //���ҳ����˷�Χ
      if (totalRows <= (pageNo - 1) * rowSize)
         if (totalRows % rowSize == 0)
            pageNo = totalRows / rowSize;
         else
            pageNo = totalRows / rowSize + 1;
      if (pageNo <= 0)
         pageNo = 1;
      start = rowSize * (pageNo - 1);
      int totalpage = 1;
      //��ҳ��
      if (totalRows % rowSize == 0)
         totalpage = totalRows / rowSize;
      else
         totalpage = totalRows / rowSize + 1;
      total = totalpage;
      this.start = start;
   }

   public int getStart()
   {
      return start;
   }

   public void setStart(int start)
   {
      this.start = start;
   }

   public void setStart(String i)
   {
      if (i == null || i.trim().length() == 0)
      {
         start = 0;
      }
      else
      {
         start = new Integer(i).intValue();
      }
   }

   public CommonResultSet getResultSet()
   {
      return resultSet;
   }

   public void setResultSet(CommonResultSet resultSet)
   {
      this.resultSet = resultSet;
   }

   public ResultSet getRs()
   {
      return rs;
   }

   public void setRs(ResultSet rs)
   {
      this.rs = rs;
   }

}
