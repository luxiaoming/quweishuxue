package com.example.sx.adapter;

/**
 * GradView œÓ
 * 
 * @author Administrator
 * 
 */
public class GridViewItem {
	public int bitmap;// Õº∆¨
	public String title; // ±ÍÃ‚

	public GridViewItem() {
	}

	public GridViewItem(int bitmap, String title) {
		super();
		this.bitmap = bitmap;
		this.title = title;
	}

	public int getBitmap() {
		return bitmap;
	}

	public void setBitmap(int bitmap) {
		this.bitmap = bitmap;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
