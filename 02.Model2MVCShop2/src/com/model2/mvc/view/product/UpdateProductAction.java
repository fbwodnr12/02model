package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.produce.impl.ProductServiceImpl;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;


public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,HttpServletResponse response) throws Exception {

		
		Product product=new Product();
		System.out.println(request.getParameter("prodNo"));
		product.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		
		ProductService service=new ProductServiceImpl();
		
		new ProductDAO().UpdateProduct(product);
		System.out.println("�ѹ��ᵵ �����ֳ�?" + request.getParameter("prodNo"));
		request.setAttribute("productvo", product);
		
		service.updateProduct(product);
	
		
		
		return "forward:/getProduct.do?menu=search" ;
	}
}