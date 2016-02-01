package model.dao;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.FoodBean;


public class FoodJDBC {
//	private static final String URL = "jdbc:sqlserver://localhost:1433;database=java";
//	private static final String USERNAME = "sa";
//	private static final String PASSWORD = "sa123456";
	
	private DataSource dataSource = null;
	public FoodJDBC() {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/xxx");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String SELECT_BY_ID = "select * from food where id=?";
	public static void main(String[] args) {
		FoodJDBC dao = new FoodJDBC();
		List<FoodBean> beans = dao.select();
		System.out.println(beans);
//		
////		ProductBean bean = new ProductBean();
////		bean.setId(11);
////		dao.insert(bean);
////		dao.update("xxx", 123, new java.util.Date(), 456, 11);
//		dao.delete(11);
//		
//		ProductBean select = dao.select(11);
//		System.out.println(select);
	}
	
	public FoodBean select(int id) {
		FoodBean result = null;
		ResultSet rset = null;
		try(//Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Connection conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID);) {
			stmt.setInt(1, id);
			rset = stmt.executeQuery();
			if(rset.next()) {
				result = new FoodBean();
				result.setId(rset.getInt("id"));
				result.setName(rset.getString("name"));
				result.setPrice(rset.getInt("price"));
				result.setImg(rset.getBlob("img"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rset!=null) {
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
		return result;
	}
	private static final String SELECT_ALL = "select * from food";
	
	public List<FoodBean> select() {
		List<FoodBean> result = null;
		try(//Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Connection conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
			ResultSet rset = stmt.executeQuery();) {

			result = new ArrayList<FoodBean>();
			while(rset.next()) {
				FoodBean bean = new FoodBean();
				bean.setId(rset.getInt("id"));
				bean.setName(rset.getString("name"));
				bean.setPrice(rset.getInt("price"));
				bean.setImg(rset.getBlob("img"));
				
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	private static final String UPDATE =
			"update food set name=?, price=?, img=? where id=?";
	
	public FoodBean update(String name,
			int price, Blob img, int id) {
		FoodBean result = null;
		try(//Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Connection conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(UPDATE);) {
			
			stmt.setString(1, name);
			stmt.setInt(2, price);
			stmt.setBlob(2, img);
			stmt.setInt(5, id);
			
			int i = stmt.executeUpdate();
			if(i==1) {
				result = this.select(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static final String INSERT =
			"insert into food (id, name, price, img) values (?, ?, ?, ?)";
	
	public FoodBean insert(FoodBean bean, InputStream is, long size) {
		FoodBean result = null;
		try(//Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Connection conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(INSERT);) {
			
			if(bean!=null) {
				stmt.setInt(1, bean.getId());
				stmt.setString(2, bean.getName());
				stmt.setInt(3, bean.getPrice());
				stmt.setBinaryStream(4, is, size);
				
				int i = stmt.executeUpdate();
				if(i==1) {
					result = bean;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	private static final String DELETE = "delete from food where id=?";
	
	public int delete(int id) {
		try(//Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Connection conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(DELETE);) {
			stmt.setInt(1, id);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}

