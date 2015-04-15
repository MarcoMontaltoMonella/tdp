package ese05_iscritticorsiDB.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ese05_iscritticorsiDB.model.Course;

public class DAOcourse implements DAO<Course> {

	
	public ArrayList<Course> read(int id) {
		Connection conn = DBConnect.getConnection();
		ResultSet res = null;
		ArrayList<Course> courses = new ArrayList<>();
		
		try {
			Statement st = conn.createStatement();
			String query = "SELECT corso.codins, crediti, nome, pd "
					+ "FROM iscrizione, corso "
					+ "WHERE matricola='"+id+"' "
					+ "AND iscrizione.codins = corso.codins;";
			
			res = st.executeQuery(query);
			
			while(res.next()){
				String codins = res.getString("codins");
				int crediti = res.getInt("crediti");
				String nome = res.getString("nome");
				int pd = res.getInt("pd");
				courses.add(new Course(codins,nome,crediti,pd));
			}
			
			res.close();
			conn.close();
			return courses;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void update() {
		
	}

	@Override
	public void delete() {
		
		
	}

	@Override
	public void create() {
		
	}

	
	@Override
	public Course read() {
		return null;
	}

	

}
