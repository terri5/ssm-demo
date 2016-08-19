package com.terri.sys.util;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;


public class CommonRecord
{
   private Hashtable objCollection;
   private Collection valueCollecton;

   /**
    * 
    */
   public CommonRecord()
   {
      objCollection = new Hashtable();
   }

   private Field getField(int pos)
   {
      Field retField;
      if (valueCollecton == null)
      {
         valueCollecton = objCollection.values();
      }

      Iterator it = valueCollecton.iterator();
      while (it.hasNext())
      {
         retField = (Field) (it.next());
         if (retField.getPos() == pos)
         {
            return retField;
         }
      }
      return null;
   }

   /*
    * private Field getField(String fieldName){ fieldName =
    * fieldName.toUpperCase(); return (Field)objCollection.get(fieldName); }
    */

   public String getColName(int pos)
   {
      return getField(pos).getName();
   }

   /**
    * 
    * @param field
    *            Field
    */
   public void addField(Field field)
   {
      objCollection.put(field.getName(), field);
   }

   /**
    * 
    * @param key
    *            String
    * @param field
    *            Field
    */
   public void addField(String key, Field field)
   {
      objCollection.put(key, field);
   }

   /**
    * 
    * @param fieldName
    *            String
    * @param fieldType
    *            int
    * @param value
    *            Object
    */
   public void addField(String fieldName, int fieldType, Object value, int pos)
   {
      objCollection.put(fieldName, new Field(fieldName, fieldType, value, pos));
   }

   /**
    * 
    * @param key
    *            String
    */
   public void removeField(String key)
   {
      objCollection.remove(key);
   }

   /**
    * 
    * @return int
    */
   public int getFieldCount()
   {
      return objCollection.size();
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return int
    */
   public int getType(String fieldName)
   {
      fieldName = fieldName.toUpperCase();

      Field field = (Field) objCollection.get(fieldName);

      return field.getType();
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Object
    */
   public Object getValue(String fieldName)
   {
      fieldName = fieldName.toUpperCase();

      Field field = (Field) objCollection.get(fieldName);

      return field.getValue() != null ? field.getValue() : null;
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Timestamp
    */
   public Timestamp getDate(String fieldName)
   {
      fieldName = fieldName.toUpperCase();
      Field field = (Field) objCollection.get(fieldName);
      return field.getValue() != null ? (Timestamp) field.getValue() : null;
   }
   
   public Date getDateTime(String fieldName)
   {
      fieldName = fieldName.toUpperCase();
      Field field = (Field) objCollection.get(fieldName);
      return field.getValue() != null ? (Date) field.getValue() : null;
   }   

   /**
    * 
    * @param fieldName
    *            String
    * @return String
    */
   public String getString(String fieldName)
   {
      fieldName = fieldName.toUpperCase();
      Field field = (Field) objCollection.get(fieldName);

      // BUG: 同理其他的类似转换也有问题
      // return field.getValue() != null ? (String)field.getValue() : null;
      if (field != null)
      {
         return field.getValue() != null ? ((field.getValue() instanceof java.math.BigDecimal) ? ((java.math.BigDecimal) field
               .getValue()).toString()
               : field.getValue().toString())
               : "";
      }
      else
      {
         return "";
      }

   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Integer
    */
   public Integer getInt(String fieldName)
   {
      fieldName = fieldName.toUpperCase();
      Field field = (Field) objCollection.get(fieldName);
      return field.getValue() != null ? Integer.valueOf(field.getValue().toString().trim()) : null;
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Float
    */
   public Float getFloat(String fieldName)
   {
      fieldName = fieldName.toUpperCase();
      Field field = (Field) objCollection.get(fieldName);
      return field.getValue() != null ? (Float) field.getValue() : null;
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Double
    */
   public Double getDouble(String fieldName)
   {
      fieldName = fieldName.toUpperCase();
      Field field = (Field) objCollection.get(fieldName);
      return field.getValue() != null ? (Double) field.getValue() : null;
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Long
    */
   public Long getLong(String fieldName)
   {
      fieldName = fieldName.toUpperCase();
      Field field = (Field) objCollection.get(fieldName);
      return field.getValue() != null ? (Long) field.getValue() : null;
   }

   public int getType(int pos)
   {
      Field field = getField(pos);

      return field.getType();
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Object
    */
   public Object getValue(int pos)
   {
      Field field = getField(pos);

      return field.getValue() != null ? field.getValue() : null;
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Timestamp
    */
   public Timestamp getDate(int pos)
   {
      Field field = getField(pos);
      return field.getValue() != null ? (Timestamp) field.getValue() : null;
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return String
    */
   public String getString(int pos)
   {
      Field field = getField(pos);

      return field.getValue() != null ? ((field.getValue() instanceof java.math.BigDecimal) ? ((java.math.BigDecimal) field
            .getValue()).toString()
            : field.getValue().toString())
            : null;

      //  return field.getValue() != null ? (String) field.getValue() : null;
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Integer
    */
   public Integer getInt(int pos)
   {
      Field field = getField(pos);
      return field.getValue() != null ? Integer.valueOf(field.getValue().toString().trim()) : null;
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Float
    */
   public Float getFloat(int pos)
   {
      Field field = getField(pos);
      return field.getValue() != null ? (Float) field.getValue() : null;
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Double
    */
   public Double getDouble(int pos)
   {
      Field field = getField(pos);
      return field.getValue() != null ? (Double) field.getValue() : null;
   }

   /**
    * 
    * @param fieldName
    *            String
    * @return Long
    */
   public Long getLong(int pos)
   {
      Field field = getField(pos);
      return field.getValue() != null ? (Long) field.getValue() : null;
   }

}
