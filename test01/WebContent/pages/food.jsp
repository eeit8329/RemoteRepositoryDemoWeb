<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="../css/main.css" />

<title>Food</title>
<script type="text/javascript">
function clearForm() {
	var inputs = document.getElementsByTagName("input");
	for(var i=0; i<inputs.length; i++) {
		if(inputs[i].type=="text") {
			inputs[i].value="";
		}
	}
}
</script>
</head>
<body>

<h3>Welcome </h3>

<h3>Food Table</h3>
  <div id="afterFoodInsert">
     <font color='red' size='-1'>${successMsg.success}${ErrMsg.Exception}</font><br/>
  </div>
<form action="<c:url value="/pages/food.controller" />" method="post" enctype="multipart/form-data">
<table>
	<tr>
		<td>ID : </td>
		<td><input type="text" name="id" value="${param.id}"></td>
		<td>${error.id}</td>
	</tr>
	<tr>
		<td>Name : </td>
		<td><input type="text" name="name" value="${param.name}"></td>
		<td></td>
	</tr>

	<tr>
		<td>Price : </td>
		<td><input type="text" name="price" value="${param.price}"></td>
		<td>${error.price}</td>
	</tr>
	<tr>
		<td>Imeage : </td>
		<td><input type="file" name="img" value="${param.img}"></td>
		<td>${error.img}</td>
	</tr>
	<tr>
		<td>
			<input type="submit" name="prodaction" value="Insert">
			<input type="submit" name="prodaction" value="Update">
		</td>
		<td>
			<input type="submit" name="prodaction" value="Delete">
			<input type="submit" name="prodaction" value="Select">
			<input type="button" value="Clear" onclick="clearForm()">
		</td>
	</tr>
</table>

</form>

<h3><span class="error">${error.action}</span></h3>

<c:if test="${not empty delete and delete}">
<h3>Delete Product Table Success</h3>
<script type="text/javascript">clearForm();</script>
</c:if>

<c:if test="${not empty insert}">
<h3>Insert Product Table Success</h3>
<table border="1">
	<tr><td>Id</td><td>${insert.id}</td></tr>
	<tr><td>Name</td><td>${insert.name}</td></tr>
	<tr><td>Price</td><td>${insert.price}</td></tr>
	<tr><td>Img</td><td>${insert.img}</td></tr>
</table>
<script type="text/javascript">clearForm();</script>
</c:if>

<c:if test="${not empty update}">
<h3>Update Product Table Success</h3>
<table border="1">
	<tr><td>Id</td><td>${update.id}</td></tr>
	<tr><td>Name</td><td>${update.name}</td></tr>
	<tr><td>Price</td><td>${update.price}</td></tr>
	<tr><td>Img</td><td>${update.img}</td></tr>
</table>
<script type="text/javascript">clearForm();</script>
</c:if>

</body>
</html>