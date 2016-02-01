package model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import model.dao.FoodJDBC;


public class FoodService {
	private FoodJDBC foodDao = new FoodJDBC();
	public List<FoodBean> select(FoodBean bean) {
		List<FoodBean> result = null;
		if(bean!=null && bean.getId()!=0) {
			FoodBean temp = foodDao.select(bean.getId());
			if(temp!=null) {
				result = new ArrayList<FoodBean>();
				result.add(temp);
			}
		} else {
			result = foodDao.select(); 
		}
		return result;
	}
	public FoodBean insert(FoodBean bean, InputStream is, long size) {
		FoodBean result = null;
		if(bean!=null) {
			result = foodDao.insert(bean, is, size);
		}
		return result;
	}
	public FoodBean update(FoodBean bean) {
		FoodBean result = null;
		if(bean!=null) {
			result = foodDao.update(bean.getName(), bean.getPrice(),
					bean.getImg(), bean.getId());
		}
		return result;
	}
	public boolean delete(FoodBean bean) {
		if(bean!=null) {
			int i = foodDao.delete(bean.getId());
			if(i==1) {
				return true;
			}
		}
		return false;
	}
	public static void main(String[] args) {
		FoodService service = new FoodService();
		List<FoodBean> beans = service.select(null);
		System.out.println("beans="+beans);
	}
}
