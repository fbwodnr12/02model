package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.produce.impl.ProductServiceImpl;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class UpdateproductViewAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,HttpServletResponse response) throws Exception {
		
ProductService service=new ProductServiceImpl();
Product product = (Product)request.getAttribute("product");

	    request.setAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
}
//service.addProduct(productvo);