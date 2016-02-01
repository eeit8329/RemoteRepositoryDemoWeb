package model;

import java.sql.Blob;

public class FoodBean {
	private int id;
	private String name;
	private int price;
	private Blob img;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Blob getImg() {
		return img;
	}
	public void setImg(Blob img) {
		this.img = img;
	}
	public FoodBean(int id, String name, int price, Blob img) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.img = img;
	}
	public FoodBean() {
		super();
	}	
}
