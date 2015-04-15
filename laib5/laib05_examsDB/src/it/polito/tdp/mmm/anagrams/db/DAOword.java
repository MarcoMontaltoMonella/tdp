/**
 * 15 Apr 2015, 11:38:25
 */
package it.polito.tdp.mmm.anagrams.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
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
	
	public List<String> getWordsStartingWith(String begin){
		Connection conn = DBConnect.getConnection();
		ResultSet res = null;
		
		List<String> list = new LinkedList<>();
		
		try {
			String template = "SELECT nome "+
					"FROM dizionario.parola "+
					"WHERE nome LIKE ?";
					
			PreparedStatement st = conn.prepareStatement(template);
			
			st.setString(1, begin+"%");
			
			res = st.executeQuery();
			
			while(res.next()){
				list.add(res.getString("nome"));
			}
			
			res.close();
			conn.close();
			
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getWordsOfLenght(int x){
		Connection conn = DBConnect.getConnection();
		ResultSet res = null;
		
		try {
			Statement st = conn.createStatement();
			
			StringBuilder lowLines = new StringBuilder();
			for(int i=0; i<x; i++)
				lowLines.append("_");
			
			String query = "SELECT nome "+
					"FROM dizionario.parola "+
					"WHERE nome LIKE \""+lowLines.toString()+"\"";

			res = st.executeQuery(query);
			
			List<String> list = new LinkedList<>();
			
			while(res.next()){
				list.add(res.getString("nome"));
			}
			res.close();
			conn.close();
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean contains(String word){
		Connection conn = DBConnect.getConnection();
		ResultSet res = null;
		
		try {
			String template = "SELECT nome "+
					"FROM dizionario.parola "+
					"WHERE nome=?";
					
			PreparedStatement st = conn.prepareStatement(template);
			
			st.setString(1, word);
			
			res = st.executeQuery();
			
			boolean result = res.first();
			
			res.close();
			conn.close();
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean containsWordStartingWith(String word){
		Connection conn = DBConnect.getConnection();
		ResultSet res = null;
		
		try {
			String template = "SELECT nome "+
					"FROM dizionario.parola "+
					"WHERE nome LIKE ?";
					
			PreparedStatement st = conn.prepareStatement(template);
			
			st.setString(1, word+"%");
			
			res = st.executeQuery();
			
			boolean result = res.first();
			
			res.close();
			conn.close();
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
