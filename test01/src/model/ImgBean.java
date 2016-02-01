package model;

import java.sql.Blob;

public class ImgBean {
	private int id;
	private Blob img;

	public ImgBean(int id, Blob img) {
		this.id = id;
		this.img = img;
	}
	
	public ImgBean(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Blob getImg() {
		return img;
	}

	public void setImg(Blob img) {
		this.img = img;
	}

}
