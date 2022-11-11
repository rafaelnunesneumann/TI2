package service;

import java.util.Scanner;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.PostDAO;
import model.Post;
import spark.Request;
import spark.Response;


public class PostService {
	
	public PostDAO postDAO = new PostDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_CPF = 2;
	private final int FORM_ORDERBY_PET = 3;
	
	
	public PostService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Post(), FORM_ORDERBY_ID);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Post(), orderBy);
	}

	
	public void makeForm(int tipo, Post post, int orderBy) {
		String nomeArquivo = "src/main/resources/controlepost.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umProduto = "";
		if(tipo != FORM_INSERT) {
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/petfinder/postcontrol/list/1\">Novo Post</a></b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";
			umProduto += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/petfinder/postcontrol/";
			String name, descricao, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Post";
				descricao = "";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + post.getId();
				name = "Atualizar Post (ID " + post.getId() + ")";
				descricao = post.getDescricao();
				buttonLabel = "Atualizar";
			}
			umProduto += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Descricao: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ descricao +"\"></td>";
			if (tipo == FORM_INSERT) {
				umProduto += "\t\t\t<td>Usuario: <input class=\"input--register\" type=\"text\" name=\"usuario\" value=\""+ post.getUsuario_Cpf() +"\"></td>";
				umProduto += "\t\t\t<td>Pet: <input class=\"input--register\" type=\"text\" name=\"pet\" value=\""+ "" +"\"></td>";

			} else {
				umProduto += "\t\t\t<td>Usuario: <input class=\"input--register\" type=\"text\" name=\"usuario\" readonly=\"readonly\" value=\""+ post.getUsuario_Cpf() +"\"></td>";
				umProduto += "\t\t\t<td>Pet: <input class=\"input--register\" type=\"text\" name=\"pet\" readonly=\"readonly\" value=\""+ post.getPet_Id() +"\"></td>";

			}
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			if (tipo == FORM_UPDATE) {
				umProduto += "\t\t\t<td>Data: <input class=\"input--register\" type=\"text\" name=\"data\" maxlength=\"10\" OnKeyPress=\"javascript:formatar('##/##/####', this)\" onBlur=\"javascript:showhide()\" value=\""+ post.dateToString() + "\"></td>";
			} else {
				umProduto += "\t\t\t<td>Data: <input class=\"input--register\" type=\"text\" name=\"data\" maxlength=\"10\" OnKeyPress=\"javascript:formatar('##/##/####', this)\" onBlur=\"javascript:showhide()\" value=\""+ "" + "\"></td>";
			}
			umProduto += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";
			umProduto += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Post (ID " + post.getId() + ")</b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Id: "+ post.getId() +"</td>";
			umProduto += "\t\t\t<td>Descricao: "+ post.getDescricao() +"</td>";
			umProduto += "\t\t\t<td>Data: "+ post.dateToString() +"</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Usuario: "+ post.getUsuario_Cpf() + "</td>";
			umProduto += "\t\t\t<td>Pet: "+ post.getPet_Id() + "</td>";
			umProduto += "\t\t\t<td>&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replace("<UM-POST>", umProduto);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Posts</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/petfinder/postcontrol/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/petfinder/postcontrol/list/" + FORM_ORDERBY_CPF+ "\"><b>Usuario</b></a></td>\n" +
        		"\t<td><a href=\"/petfinder/postcontrol/list/" + FORM_ORDERBY_PET + "\"><b>Pet</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Post> posts;
		if (orderBy == FORM_ORDERBY_ID) {                 	posts = postDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_CPF) {		posts = postDAO.getOrderByCPF();
		} else if (orderBy == FORM_ORDERBY_PET) {			posts = postDAO.getOrderByPet();
		} else {											posts = postDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Post p : posts) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getUsuario_Cpf() + "</td>\n" +
            		  "\t<td>" + p.getPet_Id() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/petfinder/postcontrol/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/petfinder/postcontrol/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeletePost('" + p.getId() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-POST>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String descricao = request.queryParams("descricao");
		String usuario_cpf = request.queryParams("usuario");
		int pet_id = Integer.parseInt(request.queryParams("pet"));
		String data = request.queryParams("data");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(data, formatter);
		
		String resp = "";
		
		Post post = new Post(-1, descricao, date, pet_id, usuario_cpf);
		
		if(postDAO.insert(post) == true) {
            resp = "Post inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Usuario nao inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Post post = postDAO.get(id);
		
		if (post != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, post, FORM_ORDERBY_ID);
        } else {
            response.status(404); // 404 Not found
            String resp = "Post " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Post post = postDAO.get(id);
		
		if (post != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, post, FORM_ORDERBY_ID);
        } else {
            response.status(404); // 404 Not found
            String resp = "Post " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Post post = postDAO.get(id);
        String resp = "";       

        if (post != null) {
        	post.setDescricao(request.queryParams("descricao"));
        	post.setData(post.stringToDate(request.queryParams("data")));
        	postDAO.update(post);
        	response.status(200); // success
            resp = "Post atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Post nao atualizado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Post post = postDAO.get(id);
        String resp = "";       

        if (post != null) {
            postDAO.delete(Integer.valueOf(id));
            response.status(200); // success
            resp = "Post excluido!";
        } else {
            response.status(404); // 404 Not found
            resp = "Post nao excluido!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}