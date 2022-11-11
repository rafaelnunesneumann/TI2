package service;

import java.util.Scanner;

import app.Aplicacao;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;


public class UsuarioService {

	public UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_CPF = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_SEXO = 3;
	
	
	public UsuarioService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Usuario(), FORM_ORDERBY_CPF);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Usuario(), orderBy);
	}

	
	public void makeForm(int tipo, Usuario usuario, int orderBy) {
		String nomeArquivo = "src/main/resources/controleusuario.html";
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
			umProduto += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/petfinder/usercontrol/list/1\">Novo Usuario</a></b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";
			umProduto += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/petfinder/usercontrol/";
			String name, nome, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Usuario";
				nome = "";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + usuario.getCPF();
				name = "Atualizar Usuario (CPF " + usuario.getCPF() + ")";
				nome = usuario.getNome();
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
			umProduto += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
			umProduto += "\t\t\t<td>Sobrenome: <input class=\"input--register\" type=\"text\" name=\"sobrenome\" value=\""+ usuario.getSobrenome() +"\"></td>";
			if (usuario.getSexo().equalsIgnoreCase("M")) {
				umProduto += "\t\t\t<td>Sexo: <input class=\"input--register\" type=\"radio\" name=\"sexo\" value=\""+ "feminino" +"\">Feminino</td>";
				umProduto += "\t\t\t<td><input class=\"input--register\" type=\"radio\" name=\"sexo\" checked value=\""+ "masculino" +"\">Masculino</td>";
			} else if (usuario.getSexo().equalsIgnoreCase("F")){
				umProduto += "\t\t\t<td>Sexo: <input class=\"input--register\" type=\"radio\" name=\"sexo\" checked value=\""+ "feminino" +"\">Feminino</td>";
				umProduto += "\t\t\t<td><input class=\"input--register\" type=\"radio\" name=\"sexo\" value=\""+ "masculino" +"\">Masculino</td>";
			} else {
				umProduto += "\t\t\t<td>Sexo: <input class=\"input--register\" type=\"radio\" name=\"sexo\" value=\""+ "feminino" +"\">Feminino</td>";
				umProduto += "\t\t\t<td><input class=\"input--register\" type=\"radio\" name=\"sexo\" value=\""+ "masculino" +"\">Masculino</td>";
			}
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;CPF: <input class=\"input--register\" type=\"text\" name=\"cpf\" maxlength=\"11\" pattern=\"[0-9]+$\" value=\""+ usuario.getCPF() + "\"></td>";
			if (!usuario.dateToString().equals(usuario.dateToString(LocalDate.now()))) {
				umProduto += "\t\t\t<td>Nascimento: <input class=\"input--register\" type=\"text\" name=\"dtnasc\" maxlength=\"10\" OnKeyPress=\"javascript:formatar('##/##/####', this)\" onBlur=\"javascript:showhide()\" value=\""+ usuario.dateToString() + "\"></td>";
			} else {
				umProduto += "\t\t\t<td>Nascimento: <input class=\"input--register\" type=\"text\" name=\"dtnasc\" maxlength=\"10\" OnKeyPress=\"javascript:formatar('##/##/####', this)\" onBlur=\"javascript:showhide()\" value=\""+ "" + "\"></td>";
			}
			umProduto += "\t\t\t<td>Telefone: <input class=\"input--register\" type=\"text\" name=\"telefone\" maxlength=\"13\" pattern=\"\\[0-9]{2}\\ [0-9]{4,6}-[0-9]{3,4}$\" OnKeyPress=\"javascript:formatar('## #####-####', this)\" value=\""+ usuario.getTelefone() + "\"></td>";
			umProduto += "\t\t\t<td>Email: <input class=\"input--register\" type=\"text\" name=\"email\" pattern=\"[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$\" value=\""+ usuario.getEmail() + "\"></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>CEP: <input class=\"input--register\" type=\"search\" name=\"cep\" id=\"cep\" maxlength=\"8\" pattern=\"[0-9]+$\" value=\""+ usuario.getCep() + "\"></td>";
			umProduto += "\t\t\t<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"javascript:pesquisacep(cep.value)\">Pesquisar</button></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>Rua: <input class=\"input--register\" type=\"text\" name=\"rua\" id=\"rua\" readonly=\"readonly\" value=\""+ usuario.getRua() + "\"></td>";
			if (usuario.getNumero() != -1) {
				umProduto += "\t\t\t<td>Numero: <input class=\"input--register\" type=\"text\" name=\"numero\" id=\"numero\" value=\""+ usuario.getNumero() + "\"></td>";
			} else {
				umProduto += "\t\t\t<td>Numero: <input class=\"input--register\" type=\"text\" name=\"numero\" id=\"numero\" value=\""+ "" + "\"></td>";
			}
			umProduto += "\t\t\t<td>Bairro: <input class=\"input--register\" type=\"text\" name=\"bairro\" id=\"bairro\" readonly=\"readonly\" value=\""+ usuario.getBairro() + "\"></td>";
			umProduto += "\t\t\t<td>Cidade: <input class=\"input--register\" type=\"text\" name=\"cidade\" id=\"cidade\" readonly=\"readonly\" value=\""+ usuario.getCidade() + "\"></td>";
			umProduto += "\t\t\t<td>Estado: <input class=\"input--register\" type=\"text\" name=\"estado\" id=\"estado\" readonly=\"readonly\" value=\""+ usuario.getEstado() + "\"></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>Senha: <input class=\"input--register\" type=\"password\" name=\"senha\" value=\""+ usuario.getSenha() + "\"></td>";
			umProduto += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";
			umProduto += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Usuario (ID " + usuario.getCPF() + ")</b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Nome: "+ usuario.getNome() +"</td>";
			umProduto += "\t\t\t<td>Sobrenome: "+ usuario.getSobrenome() +"</td>";
			umProduto += "\t\t\t<td>Sexo: "+ usuario.getSexo() +"</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Nascimento: "+ usuario.dateToString() + "</td>";
			umProduto += "\t\t\t<td>Telefone: "+ usuario.getTelefone() + "</td>";
			umProduto += "\t\t\t<td>Email: "+ usuario.getEmail() + "</td>";
			umProduto += "\t\t\t<td>CEP: "+ usuario.getCep() + "</td>";
			umProduto += "\t\t\t<td>Rua: "+ usuario.getRua() + "</td>";
			umProduto += "\t\t\t<td>Numero: "+ usuario.getNumero() + "</td>";
			umProduto += "\t\t\t<td>Bairro: "+ usuario.getBairro() + "</td>";
			umProduto += "\t\t\t<td>Cidade: "+ usuario.getCidade() + "</td>";
			umProduto += "\t\t\t<td>Estado: "+ usuario.getEstado() + "</td>";
			umProduto += "\t\t\t<td>Senha: "+ usuario.getSenha() + "</td>";
			umProduto += "\t\t\t<td>&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replace("<UM-USUARIO>", umProduto);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Usuarios</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/petfinder/usercontrol/list/" + FORM_ORDERBY_CPF + "\"><b>CPF</b></a></td>\n" +
        		"\t<td><a href=\"/petfinder/usercontrol/list/" + FORM_ORDERBY_NOME+ "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/petfinder/usercontrol/list/" + FORM_ORDERBY_SEXO + "\"><b>Sexo</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Usuario> users;
		if (orderBy == FORM_ORDERBY_CPF) {                 	users = usuarioDAO.getOrderByCPF();
		} else if (orderBy == FORM_ORDERBY_NOME) {		users = usuarioDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_SEXO) {			users = usuarioDAO.getOrderBySexo();
		} else {											users = usuarioDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Usuario p : users) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getCPF() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getSexo() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/petfinder/usercontrol/" + p.getCPF() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/petfinder/usercontrol/update/" + p.getCPF() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteUsuario('" + p.getCPF() + "', '" + p.getNome() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-USUARIO>", list);				
	}
	
	
	public Object insert(Request request, Response response) throws Exception {
		String nome = request.queryParams("nome");
		String sobrenome = request.queryParams("sobrenome");
		String cpf = request.queryParams("cpf");
		String data = request.queryParams("dtnasc");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(data, formatter);
		String s = request.queryParams("sexo");
		String sexo;
		if (s.equalsIgnoreCase("masculino")) {
			sexo = "M";
		} else {
			sexo = "F";
		}
		String telefone = request.queryParams("telefone");
		String email = request.queryParams("email");
		String cep = request.queryParams("cep");
		String rua = request.queryParams("rua");
		int numero = Integer.parseInt(request.queryParams("numero"));
		String bairro = request.queryParams("bairro");
		String cidade = request.queryParams("cidade");
		String estado = request.queryParams("estado");
		String senha = request.queryParams("senha");
		
		String resp = "";
		
		Usuario usuario = new Usuario(cpf, nome, sobrenome, sexo, date, email, telefone, cep, rua, numero, bairro, cidade, estado, Aplicacao.criptografarSenha(senha));
		
		if(usuarioDAO.insert(usuario) == true) {
            resp = "Usuario (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Usuario (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		String cpf = request.params(":cpf");	
		Usuario usuario = usuarioDAO.getByCPF(cpf);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, usuario, FORM_ORDERBY_CPF);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + cpf + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		String cpf = request.params(":cpf");	
		Usuario usuario = usuarioDAO.getByCPF(cpf);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_CPF);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + cpf + " não encontrado.";
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
		String cpf = request.params(":cpf");
		Usuario usuario = usuarioDAO.getByCPF(cpf);
        String resp = "";       

        if (usuario != null) {
        	usuario.setNome(request.queryParams("nome"));
        	usuario.setCPF(request.queryParams("cpf"));
        	usuario.setSobrenome(request.queryParams("sobrenome"));
    		String s = request.queryParams("sexo");
    		String sexo;
    		if (s.equalsIgnoreCase("masculino")) {
    			sexo = "M";
    		} else {
    			sexo = "F";
    		}
        	usuario.setSexo(sexo);
        	usuario.setDataNasc(usuario.stringToDate(request.queryParams("dtnasc")));
        	usuario.setEmail(request.queryParams("email"));
        	usuario.setTelefone(request.queryParams("telefone"));
        	usuario.setCep(request.queryParams("cep"));
        	usuario.setRua(request.queryParams("rua"));
        	usuario.setNumero(Integer.parseInt(request.queryParams("numero")));
        	usuario.setBairro(request.queryParams("bairro"));
        	usuario.setCidade(request.queryParams("cidade"));
        	usuario.setEstado(request.queryParams("estado"));
        	usuario.setSenha(request.queryParams("senha"));
        	usuarioDAO.update(usuario);
        	response.status(200); // success
            resp = "Usuario (CPF " + usuario.getCPF() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (CPF \" + usuario.getCPF() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	@SuppressWarnings("unused")
	public Object delete(Request request, Response response) {
		String cpf = request.params(":cpf");
        Usuario usuario = usuarioDAO.getByCPF(cpf);
        String resp = "";  
        if (Aplicacao.postService.postDAO.getPostsFromUser(usuario.getCPF()) != null) {
        	Aplicacao.postService.postDAO.delete(usuario.getCPF());
        }
        if (Aplicacao.petService.petDAO.getPetsFromUser(usuario.getCPF()) != null) {
        	Aplicacao.petService.petDAO.delete(usuario.getCPF());
        }
        if (usuario != null) {
            usuarioDAO.delete(cpf);
            response.status(200); // success
            resp = "Usuario (" + cpf + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (" + cpf + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}