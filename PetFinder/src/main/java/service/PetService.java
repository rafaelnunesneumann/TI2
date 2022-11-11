package service;

import java.util.Scanner;

import app.Aplicacao;

import java.io.File;
import java.util.List;

import dao.PetDAO;
import model.Pet;
import spark.Request;
import spark.Response;


public class PetService {

	public PetDAO petDAO = new PetDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_CPF = 2;
	private final int FORM_ORDERBY_NOME = 3;
	
	
	
	public PetService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Pet(), FORM_ORDERBY_CPF);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Pet(), orderBy);
	}

	
	public void makeForm(int tipo, Pet pet, int orderBy) {
		String nomeArquivo = "src/main/resources/controlepets.html";
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
			umProduto += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/petfinder/petcontrol/list/1\">Novo Pet</a></b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";
			umProduto += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/petfinder/petcontrol/";
			String name, nome, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Pet";
				nome = "";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + pet.getId();
				name = "Atualizar Pet (ID " + pet.getId() + ")";
				nome = pet.getNome();
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
			umProduto += "\t\t\t<td>Cor dos olhos: <input class=\"input--register\" type=\"text\" name=\"cor_olho\" value=\""+ pet.getCorOlho() +"\"></td>";
			if (pet.getSexo().equalsIgnoreCase("M")) {
				umProduto += "\t\t\t<td>Sexo: <input class=\"input--register\" type=\"radio\" name=\"sexo\" value=\""+ "femea" +"\">Femea</td>";
				umProduto += "\t\t\t<td><input class=\"input--register\" type=\"radio\" name=\"sexo\" checked value=\""+ "macho" +"\">Macho</td>";
			} else if (pet.getSexo().equalsIgnoreCase("F")){
				umProduto += "\t\t\t<td>Sexo: <input class=\"input--register\" type=\"radio\" name=\"sexo\" checked value=\""+ "femea" +"\">Femea</td>";
				umProduto += "\t\t\t<td><input class=\"input--register\" type=\"radio\" name=\"sexo\" value=\""+ "macho" +"\">Macho</td>";
			} else {
				umProduto += "\t\t\t<td>Sexo: <input class=\"input--register\" type=\"radio\" name=\"sexo\" value=\""+ "femea" +"\">Femea</td>";
				umProduto += "\t\t\t<td><input class=\"input--register\" type=\"radio\" name=\"sexo\" value=\""+ "macho" +"\">Macho</td>";
			}
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Pelagem: <input class=\"input--register\" type=\"text\" name=\"pelagem\" value=\""+ pet.getPelagem() + "\"></td>";
			umProduto += "\t\t\t<td>Tipo: <input class=\"input--register\" type=\"text\" name=\"tipo\" value=\""+ pet.getTipo() + "\"></td>";
			umProduto += "\t\t\t<td>Raça: <input class=\"input--register\" type=\"text\" name=\"raca\" value=\""+ pet.getRaca() + "\"></td>";
			if (tipo == FORM_INSERT) {
				umProduto += "\t\t\t<td>Usuario: <input class=\"input--register\" type=\"text\" name=\"usuario\" value=\""+ pet.getCPF() + "\"></td>";
			} else {
				umProduto += "\t\t\t<td>Usuario: <input class=\"input--register\" type=\"text\" name=\"usuario\" readonly=\"readonly\" value=\""+ pet.getCPF() + "\"></td>";
			}
			umProduto += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";
			umProduto += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Pet (ID " + pet.getId() + ")</b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Nome: "+ pet.getNome() +"</td>";
			umProduto += "\t\t\t<td>Cor dos Olhos: "+ pet.getCorOlho() +"</td>";
			umProduto += "\t\t\t<td>Sexo: "+ pet.getSexo() +"</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Pelagem "+ pet.getPelagem() + "</td>";
			umProduto += "\t\t\t<td>Tipo: "+ pet.getTipo() + "</td>";
			umProduto += "\t\t\t<td>Raça: "+ pet.getRaca() + "</td>";
			umProduto += "\t\t\t<td>Usuario: "+ pet.getCPF() + "</td>";
			umProduto += "\t\t\t<td>&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PET>", umProduto);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Pets</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/petfinder/petcontrol/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/petfinder/petcontrol/list/" + FORM_ORDERBY_CPF+ "\"><b>Usuario</b></a></td>\n" +
        		"\t<td><a href=\"/petfinder/petcontrol/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Pet> pets;
		if (orderBy == FORM_ORDERBY_ID) {                 	pets = petDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_CPF) {		pets = petDAO.getOrderByCPF();
		} else if (orderBy == FORM_ORDERBY_NOME) {			pets = petDAO.getOrderByNome();
		} else {											pets = petDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Pet p : pets) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getCPF() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/petfinder/petcontrol/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/petfinder/petcontrol/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeletePet('" + p.getId() + "', '" + p.getNome() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PET>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		String corOlhos = request.queryParams("cor_olho");
		String s = request.queryParams("sexo");
		String sexo;
		if (s.equalsIgnoreCase("macho")) {
			sexo = "M";
		} else {
			sexo = "F";
		}
		String pelagem = request.queryParams("pelagem");
		String tipo = request.queryParams("tipo");
		String raca = request.queryParams("raca");
		String usuario = request.queryParams("usuario");
		
		String resp = "";
		
		Pet pet = new Pet(-1, nome, corOlhos, sexo, pelagem, tipo, raca, usuario);
		
		if(petDAO.insert(pet) == true) {
            resp = "Pet (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Pet (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Pet pet = (Pet) petDAO.get(id);
		
		if (pet != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, pet, FORM_ORDERBY_CPF);
        } else {
            response.status(404); // 404 Not found
            String resp = "Pet " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Pet pet = (Pet) petDAO.get(id);
		
		if (pet != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, pet, FORM_ORDERBY_CPF);
        } else {
            response.status(404); // 404 Not found
            String resp = "Pet " + id + " não encontrado.";
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
		Pet pet = petDAO.get(id);
        String resp = "";       

        if (pet != null) {
        	pet.setNome(request.queryParams("nome"));
        	pet.setCorOlho(request.queryParams("cor_olho"));
    		String s = request.queryParams("sexo");
    		String sexo;
    		if (s.equalsIgnoreCase("macho")) {
    			sexo = "M";
    		} else {
    			sexo = "F";
    		}
        	pet.setSexo(sexo);
        	pet.setPelagem(request.queryParams("pelagem"));
        	pet.setTipo(request.queryParams("tipo"));
        	pet.setRaca(request.queryParams("raca"));
        	pet.setCPF(request.queryParams("usuario"));
        	petDAO.update(pet);
        	response.status(200); // success
            resp = "Pet (ID " + pet.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Pet (ID \" + pet.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	@SuppressWarnings("unused")
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Pet pet = petDAO.get(id);
        String resp = "";  
        
        if (Aplicacao.postService.postDAO.getPostsFromUser(pet.getCPF()) != null) {
        	Aplicacao.postService.postDAO.delete(pet.getCPF());
        }

        if (pet != null) {
            petDAO.delete(id);
            response.status(200); // success
            resp = "Pet (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Pet (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}