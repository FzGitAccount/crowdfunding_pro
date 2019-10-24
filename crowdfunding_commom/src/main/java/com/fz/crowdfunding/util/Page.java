package com.fz.crowdfunding.util;

import java.util.List;
/**
 * 封装分页数据到Page对象
 * @author Administrator
 *
 */
public class Page {
	
	//当前页
	private Integer pageno;
	//一页显示多少条
	private Integer pagesize;
	//当前页数据
	private List datas;
	//总记录数
	private Integer totalsize;
	//总页数
	private Integer totalno;
	
	public Page(Integer pageno,Integer pagesize){
		if(pageno<=0){
			pageno=1;
		}else{
			this.pageno = pageno;
		} 
		if(pagesize<=0){
			pagesize=10;
		}else{
			this.pagesize = pagesize;
		} 
	}
	public Integer getPageno() {
		return pageno;
	}
	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}
	public Integer getPagesize() {
		return pagesize;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	public List getDatas() {
		return datas;
	}
	public void setDatas(List datas) {
		this.datas = datas;
	}
	public Integer getTotalsize() {
		return totalsize;
	}
	public void setTotalsize(Integer totalsize) {
		this.totalsize = totalsize;
		//计算总页数
		this.totalno = (totalsize%pagesize)==0 ? (totalsize/pagesize) : (totalsize/pagesize+1);
	}
	public Integer getTotalno() {
		return totalno;
	}
	//总页数自己计算，不允许其他人访问
	private void setTotalno(Integer totalno) {
		this.totalno = totalno;
	}
	
	//开始索引
	public Integer getStartIndex(){
		return (this.pageno-1)*pagesize;
	}
	
}
