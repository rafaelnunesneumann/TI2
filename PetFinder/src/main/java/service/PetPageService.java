package service;

import java.text.ParseException;

import app.Aplicacao;
import dao.PetDAO;
import model.Pet;
import spark.Request;
import spark.Response;


public class PetPageService {

	private PetDAO petDAO = new PetDAO();
	private String form;
	
	
	public PetPageService() {
	}

	
	
	public Object insert(Request request, Response response) throws ParseException {
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
		Aplicacao.makeForm(5);
		return Aplicacao.form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
			
	}

	
	public void get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Pet pet = (Pet) petDAO.get(id);
		
		if (pet != null) {
			response.status(200); // success
			//makeForm(FORM_DETAIL, carro, FORM_ORDERBY_MODELO);
        } else {
            response.status(404); // 404 Not found
          //  String resp = "usuario " + cpf + " não encontrado.";
    		//makeForm();
    		//form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		//return form;
	}

	
	public void getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Pet pet = (Pet) petDAO.get(id);
		
		if (pet != null) {
			response.status(200); // success
			//makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_MODELO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Pet " + id + " não encontrado.";
    		//makeForm();
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
		Pet pet = petDAO.get(id);
		String resp = "";
        if (pet != null) {
        	pet.setNome(request.queryParams("nome"));
        	pet.setCorOlho(request.queryParams("cor_olho"));
        	System.out.println(pet.getCorOlho());
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
            resp = "Pet (" + pet.getNome() + ") atualizado!";
            System.out.println(pet.getNome() + " " + pet.getTipo() + " " + pet.getSexo() + " " + pet.getCorOlho() + " " + pet.getPelagem() + " " + pet.getRaca());
        } else {
            response.status(404); // 404 Not found
            resp = "Pet (\" + usuario.getCPF() + \") não encontrado!";
        }
        Aplicacao.makeForm(5);
		return Aplicacao.form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
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
            resp = "Pet (" + pet.getNome() + ") excluído!";
        } else {
            response.status(404); // 404 Not found
        }
		Aplicacao.makeForm(5);
		return Aplicacao.form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
}