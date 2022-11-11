package service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import app.Aplicacao;
import dao.PetDAO;
import dao.PostDAO;
import model.Pet;
import model.Post;
import spark.Request;
import spark.Response;


public class PostPageService {

	private PostDAO postDAO = new PostDAO();
	private PetDAO petDAO = new PetDAO();
	private String form;
	
	
	public PostPageService() {
	}

	
	
	public Object insert(Request request, Response response) throws ParseException {
		String descricao = request.queryParams("descricao");
		String usuario_cpf = LoginService.currentUser.getCPF();
		int pet_id = Integer.parseInt(request.queryParams("pet"));
		String data = dateToString(LocalDate.now());
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
		Aplicacao.makeForm(3);
		return Aplicacao.form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
			
	}
	
	public String dateToString(LocalDate date) {
	    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String text = date.format(formatters);
	    return text;
	}
	
	public LocalDate stringToDate(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(data,formatter);
        return date;

	}

	
	public void get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Post post = postDAO.get(id);
		
		if (post != null) {
			response.status(200); // success
        } else {
            response.status(404); // 404 Not found
            String resp = "Post " + id + " não encontrado.";
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		//return form;
	}

	
	public void getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Post post = postDAO.get(id);
		
		if (post != null) {
			response.status(200); // success
        } else {
            response.status(404); // 404 Not found
            String resp = "Post " + id + " não encontrado.";
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }


		//return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		//int orderBy = Integer.parseInt(request.params(":orderby"));
		//makeForm(orderBy);
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
        	postDAO.update(post);
        	response.status(200); // success
            resp = "Post atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Post nao atualizado!";
        }
        Aplicacao.makeForm(3);
		return Aplicacao.form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
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
		Aplicacao.makeForm(3);
		return Aplicacao.form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	public boolean compare(Request request, Response response) {
		System.out.println();
		System.out.println("=-=-=-=-=- COMPARAÇOES -=-=-=-=-=");
		System.out.println();
		boolean resp = false;
		int points = 0;
        int id = Integer.parseInt(request.params(":id"));
		Pet pet = petDAO.get(id);
    	String cor_olho = request.queryParams("cor_olho");
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
    	
    	if (raca.equalsIgnoreCase(pet.getRaca())) {
    		points+=20;
        	System.out.println("RAÇA: " + raca + " --- " + pet.getRaca() + " (+20)");
    	} else {
        	System.out.println("RAÇA: " + raca + " --- " + pet.getRaca() + " (+0)");

    	}
    	if (tipo.equalsIgnoreCase(pet.getTipo())) {
    		points+=20;
        	System.out.println("TIPO: " + tipo + " --- " + pet.getTipo() + " (+20)");
    	} else {
        	System.out.println("TIPO: " + tipo + " --- " + pet.getTipo() + " (+0)");

    	}
    	
    	if (cor_olho.equalsIgnoreCase(pet.getCorOlho())) {
    		points+=20;
        	System.out.println("COR DO OLHO: " + cor_olho + " --- " + pet.getCorOlho() + " (+20)");
    	} else {
        	System.out.println("COR DO OLHO: " + cor_olho + " --- " + pet.getCorOlho() + " (+0)");

    	}
    	if (sexo.equalsIgnoreCase(pet.getSexo())) {
    		points+=20;
        	System.out.println("SEXO: " + sexo + " --- " + pet.getSexo() + " (+20)");
    	} else {
        	System.out.println("SEXO: " + sexo + " --- " + pet.getSexo() + " (+0)");

    	}
    	if (pelagem.split(" ") != null) {
    		int remover = 0;
    		for (int i = 0; i < pelagem.split(" ").length; i++) {
    			if (pelagem.split(" ")[i].equalsIgnoreCase("e")) {
    				remover+=1;
    			}
    		}
    		int quantidade = 20/(pelagem.split(" ").length - remover);
    		int total = 0;
    		for (int i = 0; i < pelagem.split(" ").length; i++) {
    			if (!pelagem.split(" ")[i].equalsIgnoreCase("e")) {
    				if (pet.getPelagem().contains(pelagem.split(" ")[i])) {
    					total+=quantidade;
    				}
    			}
    		}
    		points+=total;
        	System.out.println("PELAGEM: " + pelagem + " --- " + pet.getPelagem() + " (+" + total + ")");
        	System.out.println();
        	System.out.println("Total: " + points);
        	System.out.println();
    	} else {
    		if (pet.getPelagem().equalsIgnoreCase(pelagem)) {
    			points+=20;
            	System.out.println("PELAGEM: " + pelagem + " --- " + pet.getPelagem() + " (+20)");
            	System.out.println();
            	System.out.println("Total: " + points);
            	System.out.println();
    		} else {
            	System.out.println("PELAGEM: " + pelagem + " --- " + pet.getPelagem() + " (+0)");
            	System.out.println();
            	System.out.println("Total: " + points);
            	System.out.println();
    		}
    	}
    	System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    	if (points >= 80) {
    		resp = true;
    	}
		return resp;
	}
}