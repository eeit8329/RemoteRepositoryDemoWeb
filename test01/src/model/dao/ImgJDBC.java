package model.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import model.ImgBean;

public class ImgJDBC {
	
	private DataSource dataSource = null;
	public ImgJDBC() {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/xxx");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}	
	
	
//	private static final String INSERT = "insert into photo (id, img) values (?, ?)";
//	
//	public ImgBean insert(ImgBean bean) {
//		ImgBean result = null;
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		
//		try {conn = dataSource.getConnection();
//			stmt = conn.prepareStatement(INSERT);
//			
//			stmt.setInt(1, bean.getId());
//			stmt.setBlob(2, bean.getImg());
//
//			int i = stmt.executeUpdate();
//
//			if (i == 1) {
//				System.out.println("INSERT Success!");
//				// return result;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (stmt != null) {
//				try {
//					stmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return result;
//	}			


//新增一筆記錄
	public int insertImg(ImgBean bean, InputStream is, long size) 
	                                   throws SQLException {
		int n = 0;
		Connection connection = null;
		PreparedStatement pStmt = null;
		try {
			String inserteBook = "insert into photo (id, img) values (?, ?)";

			connection = dataSource.getConnection();
			pStmt = connection.prepareStatement(inserteBook);
			pStmt.setInt(1, bean.getId());			
			pStmt.setBinaryStream(2, is, size);
			n = pStmt.executeUpdate();
		} finally {
			if (pStmt != null) {
				try {
					pStmt.close();
				} catch(SQLException e){
				   e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return n;
	}
}
