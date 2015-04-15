package ese05_iscritticorsiDB.model;

import java.util.ArrayList;

import ese05_iscritticorsiDB.db.DAOcourse;

public class iscrittiCorsiModel {

	
	
	
	public ArrayList<Course> getCoursesOf(int student_id){
		DAOcourse daoc = new DAOcourse();
		return daoc.read(student_id);
	}
	
	
	
	public static void main(String[] args) {
		
		//ArrayList<Course> courses = getCoursesOf(student_id)
		iscrittiCorsiModel mod = new iscrittiCorsiModel();
		ArrayList<Course> list = mod.getCoursesOf(161245);
		for(Course c : list)
			System.out.println(c.getNome());
	}

}
