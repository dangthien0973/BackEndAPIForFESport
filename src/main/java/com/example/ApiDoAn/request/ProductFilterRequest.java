package com.example.ApiDoAn.request;

import java.util.List;

public class ProductFilterRequest {
public int  priceFrom;
public int priceto;
public  int cateGoryID;
public List<Long> lstCateGory;
public List<Integer> lstBrandId;
public List<Integer> lstSize;
public List<Integer> lstgender;
public int pageIndex;
public int pageSize;
public double getPriceFrom() {
	return priceFrom;
}
public void setPriceFrom(int priceFrom) {
	this.priceFrom = priceFrom;
}
public double getPriceto() {
	return priceto;
}
public void setPriceto(int priceto) {
	this.priceto = priceto;
}
public int getCateGoryID() {
	return cateGoryID;
}
public void setCateGoryID(int cateGoryID) {
	this.cateGoryID = cateGoryID;
}
public int getPageIndex() {
	return pageIndex;
}
public void setPageIndex(int pageIndex) {
	this.pageIndex = pageIndex;
}
public int getPageSize() {
	return pageSize;
}
public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}
public List<Long> getLstCateGory() {
	return lstCateGory;
}
public void setLstCateGory(List<Long> lstCateGory) {
	this.lstCateGory = lstCateGory;
}
public List<Integer> getLstBrandId() {
	return lstBrandId;
}
public void setLstBrandId(List<Integer> lstBrandId) {
	this.lstBrandId = lstBrandId;
}
public List<Integer> getLstSize() {
	return lstSize;
}
public void setLstSize(List<Integer> lstSize) {
	this.lstSize = lstSize;
}
public List<Integer> getLstgender() {
	return lstgender;
}
public void setLstgender(List<Integer> lstgender) {
	this.lstgender = lstgender;
}

}
