package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;


public class ProductDAO {
	




	public ProductDAO(){
	}

	public void insertproduct(Product Product) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into product values (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, Product.getProdDetail());
		stmt.setString(2, Product.getManuDate());
		stmt.setInt(3, Product.getPrice() );
		stmt.setString(4, Product.getFileName());
		stmt.setDate(5, Product.getRegDate());
	
		stmt.executeQuery();
	
	
		con.close();
	}//////////////////////////////////////////////////////////////��ǰ��� end
	

	
public Product findProduct(int prodNo) throws Exception {
	
	Connection con = DBUtil.getConnection();

	String sql = "select * from product where PROD_NO=?";
	
	PreparedStatement stmt = con.prepareStatement(sql);
	stmt.setInt(1, prodNo);

	ResultSet rs = stmt.executeQuery();

	Product product = null;
	while (rs.next()) {
		product = new Product();
		product.setProdNo(rs.getInt("PROD_NO"));
		product.setProdName(rs.getString("PROD_NAME"));
		product.setProdDetail(rs.getString("PROD_DETAIL"));
		product.setManuDate(rs.getString("MANUFACTURE_DAY"));
		product.setPrice(rs.getInt("PRICE"));
		product.setFileName(rs.getString("IMAGE_FILE"));
		product.setRegDate(rs.getDate("REG_DATE"));
	}
//PROD_NO	PROD_NAME	PROD_DETAIL	MANUFACTURE_DAY	PRICE	IMAGE_FILE	REG_DATE

	con.close();
	
	return product;
}


	
//////////////��ǰ��� ��ȸ end 

public Map<String,Object> getProductList(Search search) throws Exception {
	
	Map<String,Object> map = new HashMap<String, Object>();
	ArrayList<Product> list = new ArrayList<Product>();
	
	
	Connection con = DBUtil.getConnection();
	
	String sql = "select * from Product ";
	
	if (search.getSearchCondition() != null) {
		if (search.getSearchCondition().equals("0")) {//0:��ǰ�ϰ��
		sql += " where PROD_NO='" + search.getSearchKeyword()	+ "'";
			
				
		} else if (search.getSearchCondition().equals("1")) {//1:��ǰ���ϰ��
			sql += " where PROD_NAME like'%" + search.getSearchKeyword()+ "%'";
				
		}else if (search.getSearchCondition().equals("2")) {//1:��ǰ������ ���
			sql += " where PROD_PRICE='" + Integer.parseInt(search.getSearchKeyword())+ "'";

			
		}
	}
	sql += " order by PROD_NO";

	PreparedStatement stmt = 
		con.prepareStatement(	sql,
													ResultSet.TYPE_SCROLL_INSENSITIVE,//Ŀ�� �̵��� �����Ӱ�
													ResultSet.CONCUR_UPDATABLE);//�����ͺ����� �����ϵ���
	ResultSet rs = stmt.executeQuery();

	rs.last();
	int total = rs.getRow();//row�� ���ϱ�
	System.out.println("�ο��� ��:" + total);

	map.put("count", new Integer(total));//�Խñ� �� ������ count��
	

	rs.absolute(search.getCurrentPage() * search.getPageSize() - search.getPageSize()+1);
	System.out.println("search.getPage():" + search.getCurrentPage());
	System.out.println("search.getPageUnit():" + search.getPageSize());
	
	if (total > 0) {
		for (int i = 0; i < search.getPageSize(); i++) {//9�� �߰� ������ 10���� �س���
			Product Product= new Product();
			Product.setProdNo(rs.getInt("PROD_NO"));
			Product.setProdName(rs.getString("PROD_NAME"));
			Product.setProdDetail(rs.getString("PROD_DETAIL"));
			Product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			Product.setPrice(rs.getInt("PRICE"));
			Product.setFileName(rs.getString("IMAGE_FILE"));
			Product.setRegDate(rs.getDate("REG_DATE"));
			list.add(Product);
			if (!rs.next())
				break;
		}
	}
	System.out.println("List.size() : "+ list.size());
	map.put("list", list);
	System.out.println("Map().size() : "+ map.size());

	con.close();
		
	return map;
}



public void UpdateProduct(Product product) throws Exception {
	
	Connection con = DBUtil.getConnection();

     String sql = "update Product set prod_name=?,prod_detail=?,manufacture_day=?,price=?,image_file=? WHERE prod_no=?";
	
	PreparedStatement stmt = con.prepareStatement(sql);
	stmt.setString(1,product.getProdName());
	stmt.setString(2, product.getProdDetail());
	stmt.setString(3, product.getManuDate());
	stmt.setInt(4, product.getPrice());
	stmt.setString(5, product.getFileName());
	stmt.setInt(6, product.getProdNo());
	stmt.executeUpdate();
	
	con.close();
}//update end


}





