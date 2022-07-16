package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.Contato;
import model.DAO;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DAO dao = new DAO();
	Contato contato = new Contato();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		switch (action) {
		case "/main":
			contatos(request, response);
			break;
		case "/insert":
			adicionarContato(request, response);
			break;
		case "/select":
			listarContato(request, response);
			break;
		case "/update":
			editarContato(request, response);
			break;
		case "/delete":
			deletarContato(request, response);
			break;
		case "/report":
			gerarRelatorio(request, response);
			break;
		default:
			response.sendRedirect("index.html");
			break;
		}
	}

	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Contato> contatos = dao.getAll();
		request.setAttribute("contatosList", contatos);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

	protected void adicionarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		contato.setNome(request.getParameter("nome"));
		contato.setTelefone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		dao.save(contato);
		response.sendRedirect("main");
	}

	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		contato.setIdcon(Integer.valueOf(request.getParameter("idcon")));
		dao.select(contato);

		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getTelefone());
		request.setAttribute("email", contato.getEmail());

		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		contato.setIdcon(Integer.valueOf(request.getParameter("idcon")));
		contato.setNome(request.getParameter("nome"));
		contato.setTelefone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		dao.update(contato);
		response.sendRedirect("main");
	}

	protected void deletarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		contato.setIdcon(Integer.valueOf(request.getParameter("idcon")));
		dao.delete(contato);
		response.sendRedirect("main");
	}

	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();

		try {
			// tipo de conteudo
			response.setContentType("apllication/pdf");
			// nome do documento
			response.addHeader("Content-Disposition", "inline;filename=" + "contatos.pdf");
			// Cria o documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			// abrir o documento
			documento.open();
			documento.add(new Paragraph("Lista de contatos: "));
			documento.add(new Paragraph(" "));
			// Criar uma tabela
			PdfPTable tabela = new PdfPTable(3);
			// cabeçalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			// popular a tabela com os contatos
			List<Contato> contatos = dao.getAll();
			for (int i = 0; i < contatos.size(); i++) {
				tabela.addCell(contatos.get(i).getNome());
				tabela.addCell(contatos.get(i).getTelefone());
				tabela.addCell(contatos.get(i).getEmail());
			}
			documento.add(tabela);
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}

}
