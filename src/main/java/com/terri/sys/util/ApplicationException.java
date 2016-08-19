package com.terri.sys.util;

public class ApplicationException extends Exception
{

   public ApplicationException()
   {
      super("应用异常");
   }

   public ApplicationException(String message)
   {
      super(message);
   }

   public ApplicationException(Throwable cause)
   {
      super(cause);
      // this.cause = cause;
   }

   public ApplicationException(String message, Throwable cause)
   {
      super(message, cause);
      // this.cause = cause;
   }

}
