package com.model2.mvc.view.product;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.produce.impl.ProductServiceImpl;
import com.model2.mvc.service.product.ProductService;


public class ListProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request,	HttpServletResponse response) throws Exception {
											
		Search search=new Search();
		
		int page=1;
		if(request.getParameter("courrentPage") != null)
			page=Integer.parseInt(request.getParameter("courrentPage"));
		
		search.setCurrentPage(page);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		String pageUnit=getServletContext().getInitParameter("pageSize");
		search.setPageSize(Integer.parseInt(pageUnit));
		
		ProductService service=new ProductServiceImpl();
		Map<String,Object> map=service.getProductList(search);
        System.out.println("page : " + search.getCurrentPage()   );
        System.out.println("pageunit : " + search.getPageSize()   );
		
		request.setAttribute("map", map);
		request.setAttribute("search", search);
		
		if(request.getParameter("menu").equals("search")){
			return "forward:/product/listProduct.jsp?menu=search";	
		}else {
			return "forward:/product/listProduct.jsp?menu=manage";	
		}
		
		
	}
}