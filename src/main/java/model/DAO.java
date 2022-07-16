package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAO {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASS = "q1w2e3";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("Erro na conexão com o banco de dados", e);
        }
    }
    
    public void save(Contato contato) {
    	String sql = "insert into contatos (nome,fone,email) values (?,?,?)";
    	
    	try(Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, contato.getNome());
			ps.setString(2, contato.getTelefone());
			ps.setString(3, contato.getEmail());
			ps.execute();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao salvar contato " + e.getMessage(), e);
		}
    }
    
    public List<Contato> getAll(){
    	String sql = "select * from contatos order by nome";
    	
    	List<Contato> contatos = new ArrayList<Contato>();
    	
    	try(Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.executeQuery();
			try (ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					Contato contato = new Contato();
					contato.setIdcon(rs.getInt("idcon"));
					contato.setNome(rs.getString("nome"));
					contato.setTelefone(rs.getString("fone"));
					contato.setEmail(rs.getString("email"));
					contatos.add(contato);
				}
				
			} catch (Exception e) {
				e.getMessage();
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao listar contatos " + e.getMessage(), e);
		}
    	return contatos;
    }
    
	public void select(Contato contato) {
		String sql = "select * from contatos where idcon = ?";

		try(Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setInt(1, contato.getIdcon());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				contato.setIdcon(rs.getInt("idcon"));
				contato.setNome(rs.getString("nome"));
				contato.setTelefone(rs.getString("fone"));
				contato.setEmail(rs.getString("email"));
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar contato " + e.getMessage(), e);
		}
		
	}
	
	public void update(Contato contato) {
		String sql = "update contatos set nome = ?,fone = ?, email = ? where idcon = ?";
		
		try(Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setString(1, contato.getNome());
			ps.setString(2, contato.getTelefone());
			ps.setString(3, contato.getEmail());
			ps.setInt(4, contato.getIdcon());
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao atualizar contato " + e.getMessage(), e);
		}
	}
	
	public void delete(Contato contato) {
		String sql = "delete from contatos where idcon = ?";
		
		try(Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setInt(1, contato.getIdcon());
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao deletar contato " + e.getMessage(), e);
		}
	}
}
