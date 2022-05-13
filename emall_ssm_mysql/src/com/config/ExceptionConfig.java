package com.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ͳһ����ҵ���쳣 controller
 */
@ControllerAdvice
public class ExceptionConfig {
	
    /**
     * ҵ���쳣
     * @param exception
     * @return
     */
    @ExceptionHandler(MyException.class)
    public String exception(MyException exception, HttpServletRequest request){
    	request.setAttribute("msg", exception.getMessage());
    	return "/index/error.jsp";
    }
    
    /**
     * Ĭ���쳣
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String exception(Exception exception, HttpServletRequest request){
    	request.setAttribute("msg", "ϵͳ����");
    	return "/index/error.jsp";
    }
    
    
    /**
     * �Զ����쳣
     */
    @SuppressWarnings("serial")
    public static class MyException extends Exception {
    	public MyException(String msg) {super(msg);}
    }
    
}

