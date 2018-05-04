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
	}//////////////////////////////////////////////////////////////상품등록 end
	

	
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


	
//////////////상품목록 조회 end 

public Map<String,Object> getProductList(Search search) throws Exception {
	
	Map<String,Object> map = new HashMap<String, Object>();
	ArrayList<Product> list = new ArrayList<Product>();
	
	
	Connection con = DBUtil.getConnection();
	
	String sql = "select * from Product ";
	
	if (search.getSearchCondition() != null) {
		if (search.getSearchCondition().equals("0")) {//0:상품일경우
		sql += " where PROD_NO='" + search.getSearchKeyword()	+ "'";
			
				
		} else if (search.getSearchCondition().equals("1")) {//1:상품명일경우
			sql += " where PROD_NAME like'%" + search.getSearchKeyword()+ "%'";
				
		}else if (search.getSearchCondition().equals("2")) {//1:상품가격일 경우
			sql += " where PROD_PRICE='" + Integer.parseInt(search.getSearchKeyword())+ "'";

			
		}
	}
	sql += " order by PROD_NO";

	PreparedStatement stmt = 
		con.prepareStatement(	sql,
													ResultSet.TYPE_SCROLL_INSENSITIVE,//커서 이동을 자유롭게
													ResultSet.CONCUR_UPDATABLE);//데이터변경이 가능하도록
	ResultSet rs = stmt.executeQuery();

	rs.last();
	int total = rs.getRow();//row수 구하기
	System.out.println("로우의 수:" + total);

	map.put("count", new Integer(total));//게시글 총 갯수를 count로
	

	rs.absolute(search.getCurrentPage() * search.getPageSize() - search.getPageSize()+1);
	System.out.println("search.getPage():" + search.getCurrentPage());
	System.out.println("search.getPageUnit():" + search.getPageSize());
	
	if (total > 0) {
		for (int i = 0; i < search.getPageSize(); i++) {//9번 추가 유닛을 10으로 해놔서
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





