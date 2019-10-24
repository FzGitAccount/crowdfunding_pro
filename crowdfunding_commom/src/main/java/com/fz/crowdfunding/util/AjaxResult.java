package com.fz.crowdfunding.util;
/**
 * 返回消息结果
 * @author Administrator
 *
 */
public class AjaxResult {
	//是否成功
	private boolean success;
	//错误信息
	private String message;
	
	//将查询结果封装到page对象
	private Page page;
	
	private Object data;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
