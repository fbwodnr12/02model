package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.dao.ProductDAO;

public class GetProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Product product = new ProductDAO().findProduct(Integer.parseInt(request.getParameter("prodNo")));

		if (product == null) {
			product = (Product) request.getAttribute("product");

	}
		System.out.println(request.getParameter("menu"));

		request.setAttribute("menu", request.getParameter("menu"));
		request.setAttribute("product", product);

		if (request.getParameter("menu").equals("manage")) {
			return "forward:/product/updateProduct.jsp"; // manage로넘어왔을때
		} else {
			return "forward:/product/getProduct.jsp"; // search로 넘어왔을때
		}

		// getProduct.do
	}
}