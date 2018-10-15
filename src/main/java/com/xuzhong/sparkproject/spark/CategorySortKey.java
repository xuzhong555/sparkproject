package com.xuzhong.sparkproject.spark;

import java.io.Serializable;

import scala.math.Ordered;
/**
 * 品类二次排序key
 * @author xz
 *
 */

public class CategorySortKey implements Ordered<CategorySortKey> , Serializable{
	
	private static final long serialVersionUID = -3109451091369454437L;
	
	private long clickCount;
	private long orderCount;
	private long payCount;
	
	

	public CategorySortKey(long clickCount, long orderCount, long payCount) {
		super();
		this.clickCount = clickCount;
		this.orderCount = orderCount;
		this.payCount = payCount;
	}

	@Override
	public boolean $greater(CategorySortKey obj) {
		if(clickCount > obj.clickCount){
			return true;
		}else if(clickCount == obj.clickCount && orderCount > obj.orderCount){
			return true;
		}else if(clickCount == obj.clickCount && orderCount == obj.orderCount && payCount > obj.payCount){
			return true;
		}
		return false;
	}

	@Override
	public boolean $greater$eq(CategorySortKey obj) {
		if($greater(obj)){
			return true;
		}else if(clickCount == obj.clickCount && orderCount == obj.orderCount && payCount == obj.payCount){
			return true;
		}
		return false;
	}

	@Override
	public boolean $less(CategorySortKey obj) {
		if(clickCount < obj.clickCount){
			return true;
		}else if(clickCount == obj.clickCount && orderCount < obj.orderCount){
			return true;
		}else if(clickCount == obj.clickCount && orderCount == obj.orderCount && payCount < obj.payCount){
			return true;
		}
		return false;
	}

	@Override
	public boolean $less$eq(CategorySortKey obj) {
		if($less(obj)){
			return true;
		}else if(clickCount == obj.clickCount && orderCount == obj.orderCount && payCount == obj.payCount){
			return true;
		}
		return false;
	}

	@Override
	public int compare(CategorySortKey obj) {
		if(clickCount - obj.clickCount != 0){
			return (int) (clickCount - obj.clickCount);
		}else if(orderCount - obj.orderCount != 0){
			return (int) (orderCount - obj.orderCount);
		}else if(payCount - obj.payCount != 0){
			return (int) (payCount - obj.payCount);
		}
		return 0;
	}

	@Override
	public int compareTo(CategorySortKey obj) {
		if(clickCount - obj.clickCount != 0){
			return (int) (clickCount - obj.clickCount);
		}else if(orderCount - obj.orderCount != 0){
			return (int) (orderCount - obj.orderCount);
		}else if(payCount - obj.payCount != 0){
			return (int) (payCount - obj.payCount);
		}
		return 0;
	}

	public long getClickCount() {
		return clickCount;
	}

	public void setClickCount(long clickCount) {
		this.clickCount = clickCount;
	}

	public long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(long orderCount) {
		this.orderCount = orderCount;
	}

	public long getPayCount() {
		return payCount;
	}

	public void setPayCount(long payCount) {
		this.payCount = payCount;
	}

	


}
