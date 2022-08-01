<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" type="text/css" href='<c:url value="./styles.css"/>'>
	
	<title>Index</title>
</head>
<body>
	<br>
	<div align="center">
		<form action="webController" method="post">
			<table>
				<tr>
					<td>
						<input type="submit" id="botao" name="botao" value="Find All Clientes">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Find Cliente By ID">
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" id="botao" name="botao" value="Post Cliente">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Put Cliente">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Delete Cliente">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<br>
	<br>
	
	<c:if test="${not empty erro}">
		<div align="center">
			<h2><c:out value="${erro}"/></h2>
		</div>
	</c:if>
	
	<c:if test="${not empty saida}">
		<div align="center">
			<h2><c:out value="${saida}"/></h2>
		</div>
	</c:if>
	
	<c:if test="${not empty saidaFindAll}">
		<div align="center">
			<c:forEach items="${saidaFindAll}" var="cliente">
				<h3><c:out value="${cliente}"/></h3>
				<hr>
			</c:forEach>
		</div>
	</c:if>
	
	<c:if test="${not empty inputFindById && inputFindById.equals(true)}">
		<div align="center">
			<form action="webController" method="post">
				<input type="number" id="idCliente" name="idCliente" placeholder="ID" value="">
				<input type="submit" id="botao" name="botao" value="Get Cliente By ID">
			</form>
		</div>
	</c:if>
	
	<c:if test="${not empty inputPostCliente && inputPostCliente.equals(true)}">
		<div align="center">
			<form action="webController" method="post">
				<input type="number" id="idCliente" name="idCliente" placeholder="ID" value="">
				<input type="text" id="nomeCliente" name="nomeCliente" placeholder="Nome" value="">
				<input type="text" id="telefoneCliente" name="telefoneCliente" placeholder="Telefone" value="">
				<input type="submit" id="botao" name="botao" value="Save Cliente by POST">
			</form>
		</div>
	</c:if>
	
	<c:if test="${not empty inputPutCliente && inputPutCliente.equals(true)}">
		<div align="center">
			<form action="webController" method="post">
				<input type="number" id="idCliente" name="idCliente" placeholder="ID" value="">
				<input type="text" id="nomeCliente" name="nomeCliente" placeholder="Nome" value="">
				<input type="text" id="telefoneCliente" name="telefoneCliente" placeholder="Telefone" value="">
				<input type="submit" id="botao" name="botao" value="Save Cliente by PUT">
			</form>
		</div>
	</c:if>
	
	<c:if test="${not empty inputDeleteCliente && inputDeleteCliente.equals(true)}">
		<div align="center">
			<form action="webController" method="post">
				<input type="number" id="idCliente" name="idCliente" placeholder="ID" value="">
				<input type="submit" id="botao" name="botao" value="Delete Cliente By ID">
			</form>
		</div>
	</c:if>
</body>
</html>