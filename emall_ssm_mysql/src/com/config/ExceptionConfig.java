package com.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 统一处理业务异常 controller
 */
@ControllerAdvice
public class ExceptionConfig {
	
    /**
     * 业务异常
     * @param exception
     * @return
     */
    @ExceptionHandler(MyException.class)
    public String exception(MyException exception, HttpServletRequest request){
    	request.setAttribute("msg", exception.getMessage());
    	return "/index/error.jsp";
    }
    
    /**
     * 默认异常
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String exception(Exception exception, HttpServletRequest request){
    	request.setAttribute("msg", "系统错误");
    	return "/index/error.jsp";
    }
    
    
    /**
     * 自定义异常
     */
    @SuppressWarnings("serial")
    public static class MyException extends Exception {
    	public MyException(String msg) {super(msg);}
    }
    
}

