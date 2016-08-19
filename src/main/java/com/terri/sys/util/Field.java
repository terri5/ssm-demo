package com.terri.sys.util;


public class Field
{

   private String name;
   private int type;
   private Object value;
   private int pos;

   public Field()
   {
   }

   public Field(Field field)
   {
      name = field.getName();
      type = field.getType();
      value = field.getValue();
      pos = field.getPos();
   }

   public Field(String name, int type, Object value, int pos)
   {
      this.name = name;
      this.type = type;
      this.value = value;
      this.pos = pos;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public int getType()
   {
      return type;
   }

   public void setType(int type)
   {
      this.type = type;
   }

   public Object getValue()
   {
      return value;
   }

   public void setValue(Object value)
   {
      this.value = value;
   }

   public int getPos()
   {
      return pos;
   }

   public void setPos(int pos)
   {
      this.pos = pos;

   }

}
