<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
 <%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ page import="com.model2.mvc.common.*" %>
    
<%
	HashMap<String,Object> map=(HashMap<String,Object>)request.getAttribute("map"); // count(�� ���ڵ� ����)�� list(ProductVO�� ���)�� �޾ƿ� map
	Search search=(Search)request.getAttribute("search"); 
	
	int total=0;
	ArrayList<Product> list=null;
	if(map != null){
		total=((Integer)map.get("count")).intValue(); // �� ������ totla�� ����
		list=(ArrayList<Product>)map.get("list"); // ��� ���ڵ带 list�� ����
	}
	
	int currentPage=search.getCurrentPage(); 
	
	int totalPage=0;
	if(total > 0) {
		totalPage= total / search.getPageSize() ; //totalpage = ��ü���ڵ� �� / ����������(10���� �س���)
		if(total%search.getPageSize() >0)
			totalPage += 1;
	}
%>

<html>
<head>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
<!--
function fncGetProductList(){
	document.detailForm.submit();
}
-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">


<form name="detailForm" action="/listProduct.do?menu=manage"method="post">
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">��ǰ ����</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	<%
		if(search.getSearchCondition() != null) { // �˻� ������ �������� ���
	%>
		
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
			<%
				if(search.getSearchCondition().equals("0")){
			%>
				<option value="0"selected>��ǰ��ȣ</option>
				<option value="1">��ǰ��</option>
				<option value="2">��ǰ����</option>
					<%
				}else if(search.getSearchCondition().equals("1")){
		%>
				<option value="0">��ǰ��ȣ</option>
				<option value="1"selected>��ǰ��</option>
				<option value="2">��ǰ����</option>
		<%
				}else if(search.getSearchCondition().equals("2")){ %>
					<option value="0">��ǰ��ȣ</option>
					<option value="1">��ǰ��</option>
					<option value="2"selected>��ǰ����</option>
					<%} %>
				
			</select>
			<input type="text" name="searchKeyword"  value ="<%=search.getSearchKeyword() %>"class="ct_input_g" style="width:200px; height:19px" />
		</td>
		<%
		}else{
	%>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0">��ǰ��ȣ</option>
				<option value="1">��ǰ��</option>
				<option value="2">��ǰ����</option>
			</select>
			<input type="text" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px" >
		</td>
		<%
		}
	%>
	
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList();">�˻�</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >��ü <%=total %> �Ǽ�, ���� <%=currentPage %> ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<% 	
		int no=list.size();
	System.out.println("no : " +no);
		for(int i=0; i<list.size(); i++) {
			Product product = (Product)list.get(i);
	%>
		
	<tr class="ct_list_pop">
		<td align="center"><%=no-- %></td>
		<td></td>
		
			
				<% if(request.getParameter("menu") != null &&  request.getParameter("menu").equals("search") 
				&& session.getAttribute("user")!=null ) { %>
					<td align="left">
					<a href="/getProduct.do?prodNo=<%=product.getProdNo() %>&menu=<%=request.getParameter("menu")%>"><%= product.getProdName() %></a>
					
				</td>
				
				<%} else if(request.getParameter("menu").equals("manage") ){%>
					<td align="left">
					<a href="/getProduct.do?prodNo=<%=product.getProdNo() %>&menu=<%=request.getParameter("menu")%>"><%= product.getProdName() %></a>
					
				</td>
				<%}%> 
		<td></td>
		<td align="left"><%=product.getPrice() %></td>
		<td></td>
		<td align="left"><%=product.getRegDate() %></td>
		<td></td>		
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	<%} %>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<%for(int i=1;i<=totalPage;i++){
				if(search.getSearchCondition()==null||search.getSearchKeyword()==null){ %>
				<a href="/listProduct.do?page=<%=i%>&menu=search"><%=i %></a> <%--������ �ٲ� �� �ٽ� �ҷ��� --%>
				<%} else { %> 
			<a href="/listProduct.do?page=<%=i%>&menu=search&searchKeyword=<%=search.getSearchKeyword() %>&searchCondition=<%=search.getSearchCondition()%>">
			<%=i %></a> <%--������ �ٲ� �� �ٽ� �ҷ��� --%>
		<%
				}}
		%>	
		
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->


</form>

</div>
</body>
</html>