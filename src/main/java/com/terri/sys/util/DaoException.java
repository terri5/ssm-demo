
package com.terri.sys.util;


public class DaoException extends Exception {
    
    private static final long serialVersionUID = 1L;

    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public DaoException(String msg, Exception e, String reason){
        super(msg,e);
        this.reason = reason;
    }   
    public DaoException(String msg) {
        super(msg);
    }

    public DaoException(Exception e) {
        super(e);
    }
    
}

