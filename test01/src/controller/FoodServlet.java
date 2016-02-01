package controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.FoodBean;
import model.FoodService;

@WebServlet(urlPatterns = { "/pages/food.controller" })
//@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 500, maxRequestSize = 1024
//		* 1024 * 500 * 5)

public class FoodServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
	private FoodService foodService = new FoodService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 接收資料
		String temp1 = request.getParameter("id");
		String name = request.getParameter("name");
		String temp2 = request.getParameter("price");
		String temp3 = request.getParameter("img");
		String prodaction = request.getParameter("prodaction");

		int id = 0;
		int price = 0;
		String fileName = "";
		long sizeInBytes = 0;
		InputStream is = null;
		Blob img = null;
		long size = 0;

		// 轉換資料
		Map<String, String> error = new HashMap<String, String>();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		request.setAttribute("error", error);

		Collection<Part> parts = request.getParts();

//		if (parts != null) { // 如果這是一個上傳資料的表單
//			for (Part p : parts) {
//				String fldName = p.getName();
//				String value = request.getParameter(fldName);

				if (temp1 != null && temp1.length() != 0) {
					try {
						id = Integer.parseInt(temp1);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						error.put("id", "Id must be an integer");
					}
				}

				if (temp2 != null && temp2.length() != 0) {
					try {
						price = Integer.parseInt(temp2);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						error.put("price", "Price must be a number");
					}
				}
				
//				if (temp3 != null && temp3.length() != 0) {
//					try {
//						sizeInBytes = p.getSize();
//						is = p.getInputStream();
//					} catch (NumberFormatException e) {
//						e.printStackTrace();
//						error.put("img", "必須挑選圖片檔");
//					}
//				}
//				
//			}
//		}

		// 驗證資料
		if ("Insert".equals(prodaction) || "Update".equals(prodaction) || "Delete".equals(prodaction)) {
			if (id == 0) {
				error.put("id", "Please enter Id for " + prodaction);
			}
		}
		if (error != null && !error.isEmpty()) {
			request.getRequestDispatcher("/pages/food.jsp").forward(request, response);
			return;
		}
		// 呼叫model
		FoodBean bean = new FoodBean();
		bean.setId(id);
		bean.setName(name);
		bean.setPrice(price);
		bean.setImg(img);

		// 根據model執行結果顯示view
		if ("Select".equals(prodaction)) {
			List<FoodBean> result = foodService.select(bean);
			request.setAttribute("select", result);
			request.getRequestDispatcher("/pages/food.jsp").forward(request, response);
		} else if ("Insert".equals(prodaction)) {
			FoodBean result = foodService.insert(bean, is, size);
			if (result == null) {
				error.put("action", "Insert failed");
			} else {
				request.setAttribute("insert", result);
			}
			request.getRequestDispatcher("/pages/food.jsp").forward(request, response);
		} else if ("Update".equals(prodaction)) {
			FoodBean result = foodService.update(bean);
			if (result == null) {
				error.put("action", "Update failed");
			} else {
				request.setAttribute("update", result);
			}
			request.getRequestDispatcher("/pages/food.jsp").forward(request, response);
		} else if ("Delete".equals(prodaction)) {
			boolean result = foodService.delete(bean);
			if (result) {
				request.setAttribute("delete", result);
			} else {
				error.put("action", "Delete failed");
			}
			request.getRequestDispatcher("/pages/food.jsp").forward(request, response);
		} else {
			error.put("action", "Unknown action: " + prodaction);
			request.getRequestDispatcher("/pages/food.jsp").forward(request, response);
		}
	}

}
