<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="model.Contato"%>
<%@page import="java.util.List"%>
<%
@ SuppressWarnings ("unchecked")
List<Contato> lista = (ArrayList<Contato>) request.getAttribute("contatosList");
%>

<html lang="pt-br">
<head>
<title>Agenda de Contatos</title>
<link rel="icon" href="imagens/phone.png">
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Agenda de Contatos</h1>
	<a href="novo.html" class="Botao1">Novo contato</a>
	<a href="report" class="Botao2">Relatório</a>
	<table id="tabela">
		<thead>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Fone</th>
				<th>E-mail</th>
				<th>Opções</th>
			</tr>
		<thead>
		<tbody>
			<%
			for (int i = 0; i < lista.size(); i++) {
			%>
			<tr>
				<td><%=lista.get(i).getIdcon()%></td>
				<td><%=lista.get(i).getNome()%></td>
				<td><%=lista.get(i).getTelefone()%></td>
				<td><%=lista.get(i).getEmail()%></td>
				<td>
					<a href="select?idcon=<%=lista.get(i).getIdcon()%>" class="Botao1"> Editar </a>
					<a href="javascript: confirmar(<%=lista.get(i).getIdcon()%>)" class="Botao2"> Excluir </a>
				</td>
				
			</tr>
			<%
			}
			%>
		</tbody>

	</table>
<script src="scripts/confirmador.js"></script>

</body>
</html>