package com.fz.crowdfunding.exception;

public class LoginFailException extends RuntimeException{

	public LoginFailException(){};
	//自定义异常只需要写一个带参构造器,然后将异常消息传给父类即可
	public LoginFailException(String message){
		super(message);
	}
}
