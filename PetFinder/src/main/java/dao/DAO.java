package dao;

import java.sql.*;

public class DAO {
	protected Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "petfinder123.postgres.database.azure.com";
		String mydatabase = "PetFinderDB";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "adm@petfinder123";
		String password = "@Petfinder";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			Statement stn = conexao.createStatement();
			String sql;
			sql = "CREATE SCHEMA IF NOT EXISTS petfinder";
			stn.execute(sql);
			sql = "CREATE TABLE IF NOT EXISTS petfinder.usuario (cpf CHAR(11) NOT NULL, nome VARCHAR(80) NOT NULL, sobrenome VARCHAR(80) NOT NULL,"
					+ " sexo CHAR(1) NOT NULL, data_nasc DATE NOT NULL, email VARCHAR(45) NOT NULL, telefone VARCHAR(45) NOT NULL,"
					+ " cep CHAR(8) NOT NULL, rua VARCHAR(45) NOT NULL, numero INTEGER NOT NULL, bairro VARCHAR(45) NOT NULL,"
					+ " cidade VARCHAR(45) NOT NULL, estado VARCHAR(45) NOT NULL, senha VARCHAR(45) NOT NULL, PRIMARY KEY (cpf));";
			stn.execute(sql);
			sql = "CREATE TABLE IF NOT EXISTS petfinder.pet (id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY, nome VARCHAR(20) NOT NULL,"
					+ " cor_olho VARCHAR(11) NOT NULL, sexo CHAR(1) NOT NULL, pelagem VARCHAR(45) NOT NULL, tipo VARCHAR(15) NOT NULL, "
					+ "raca VARCHAR(45) NOT NULL, usuario_cpf CHAR(11) NOT NULL, CONSTRAINT fk_pet_usuario FOREIGN KEY (usuario_cpf) REFERENCES petfinder.usuario(cpf)"
					+ " ON DELETE NO ACTION ON UPDATE NO ACTION);";
			stn.execute(sql);
			sql = "CREATE TABLE IF NOT EXISTS petfinder.post (idPost INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY, descricao VARCHAR(200) NOT NULL,"
					+ " data DATE NOT NULL, usuario_cpf CHAR(11) NOT NULL, pet_id INT NOT NULL, CONSTRAINT fk_usuario_cpf FOREIGN KEY (usuario_cpf) REFERENCES petfinder.usuario(cpf)"
					+ " ON DELETE NO ACTION ON UPDATE NO ACTION, CONSTRAINT fk_usuario_pet FOREIGN KEY (pet_id) REFERENCES petfinder.pet(id) "
					+ "ON DELETE NO ACTION ON UPDATE NO ACTION);";
			stn.execute(sql);
			stn.close();
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
}