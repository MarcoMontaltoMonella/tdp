/**
 * 15 Apr 2015, 11:38:25
 */
package it.polito.tdp.mmm.anagrams.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;



public class DAOword {
	
	public int countWords(){
		Connection conn = DBConnect.getConnection();
		ResultSet res = null;
		
		try {
			Statement st = conn.createStatement();
			String query = "SELECT COUNT(nome) AS numberOfRows "+
					"FROM dizionario.parola";

			res = st.executeQuery(query);
			
			res.first();
			int count = res.getInt("numberOfRows");
			
			res.close();
			conn.close();
			return count;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public boolean existsWordStartingWith(String begin){
		
		
		return false;
	}
	
	public List<String> getWordsStartingWith(String begin){
		
		return null;
	}
	
	public boolean contains(String word){
		
		return false;
	}
}
