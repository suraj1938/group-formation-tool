package com.app.group15.GroupFormationManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.app.group15.Config.ServiceConfigForTest;

public class MatrixGroupFormationAlgorithmTest {
	
	private GroupFormationAlgorithm algorithm=ServiceConfigForTest.getInstance().getAlgorithm();

	@Test
	public void formGroups() {
		algorithm.questionOrderNumbers=new ArrayList<Integer>();
		for(int i=1;i<9;i++) {
			algorithm.questionOrderNumbers.add(i);
		}
		List<ArrayList<Integer>> list=algorithm.formGroups(getStudentResponses(), 2, getQuestionCriteriaMap());
		assertEquals(list.size(),5);
	}
	public HashMap<Integer, String> getQuestionCriteriaMap() {
		HashMap<Integer, String> questionCriteria = new HashMap<Integer, String>();

		questionCriteria.put(1, "DISSIMILAR");
		questionCriteria.put(2, "SIMILAR");
		questionCriteria.put(3, "DISSIMILAR");
		questionCriteria.put(4, "SIMILAR");
		questionCriteria.put(5, "DISSIMILAR");
		questionCriteria.put(6, "SIMILAR");
		questionCriteria.put(7, "SIMILAR");
		questionCriteria.put(8, "SIMILAR");
		return questionCriteria;

	}
	
	
	public  ArrayList<StudentResponseMaintainer> getStudentResponses() {
		ArrayList<Object> s1 = new ArrayList();
		ArrayList<Object> s2 = new ArrayList();
		ArrayList<Object> s3 = new ArrayList();
		ArrayList<Object> s4 = new ArrayList();
		ArrayList<Object> s5 = new ArrayList();
		ArrayList<Object> s6 = new ArrayList();
		ArrayList<Object> s7 = new ArrayList();
		ArrayList<Object> s8 = new ArrayList();
		ArrayList<Object> s9 = new ArrayList();
		ArrayList<Object> s10 = new ArrayList();
		s1.add(0, 1);
		s1.add(1, 5);
		s1.add(2, 3);
		s1.add(3, "Yes i have");
		s1.add(4, 4);
		s1.add(5, "No i have");
		s1.add(6, "Yes I do");
		s1.add(7, -1);
		s2.add(0, 1);
		s2.add(1, 2);
		s2.add(2, 3);
		s2.add(3, "Yes  have");
		s2.add(4, 6);
		s2.add(5, "No i have");
		s2.add(6, "Yes I do");
		s2.add(7, -5);
		s3.add(0, 1);
		s3.add(1, 2);
		s3.add(2, 3);
		s3.add(3, "Yes i have");
		s3.add(4, 3);
		s3.add(5, "No i have");
		s3.add(6, "No I do");
		s3.add(7, -3);
		s4.add(0, 1);
		s4.add(1, 3);
		s4.add(2, 3);
		s4.add(3, "Yes i have");
		s4.add(4, 4);
		s4.add(5, "No have");
		s4.add(6, "Yes I do");
		s4.add(7, -1);
		s5.add(0, 7);
		s5.add(1, 1);
		s5.add(2, 3);
		s5.add(3, "Some shit");
		s5.add(4, 4);
		s5.add(5, "No shit");
		s5.add(6, "Yes I do");
		s5.add(7, -1);
		s6.add(0, 4);
		s6.add(1, 1);
		s6.add(2, 2);
		s6.add(3, "Some Shit");
		s6.add(4, 4);
		s6.add(5, "No i have");
		s6.add(6, "Yes I do");
		s6.add(7, -3);
		s7.add(0, 2);
		s7.add(1, 4);
		s7.add(2, 3);
		s7.add(3, "Some Random");
		s7.add(4, 4);
		s7.add(5, "No Random");
		s7.add(6, "Yes I do");
		s7.add(7, -1);
		s8.add(0, 1);
		s8.add(1, 2);
		s8.add(2, 7);
		s8.add(3, "Yes i have");
		s8.add(4, 6);
		s8.add(5, "No");
		s8.add(6, "Yes I do");
		s8.add(7, -1);
		;
		s9.add(0, 1);
		s9.add(1, 2);
		s9.add(2, 3);
		s9.add(3, "Yes i have");
		s9.add(4, 5);
		s9.add(5, "No i have");
		s9.add(6, "Yes I do");
		s9.add(7, 4);
		s10.add(0, 3);
		s10.add(1, -1);
		s10.add(2, 2);
		s10.add(3, "You know");
		s10.add(4, 4);
		s10.add(5, "I know");
		s10.add(6, "Yes I do");
		s10.add(7, -1);
		ArrayList<StudentResponseMaintainer> studentResponseList = new ArrayList();
		
		StudentResponseMaintainer s11=new StudentResponseMaintainer();
		StudentResponseMaintainer s12=new StudentResponseMaintainer();
		StudentResponseMaintainer s13=new StudentResponseMaintainer();
		StudentResponseMaintainer s14=new StudentResponseMaintainer();
		StudentResponseMaintainer s15=new StudentResponseMaintainer();
		StudentResponseMaintainer s16=new StudentResponseMaintainer();
		StudentResponseMaintainer s17=new StudentResponseMaintainer();
		StudentResponseMaintainer s18=new StudentResponseMaintainer();
		StudentResponseMaintainer s19=new StudentResponseMaintainer();
		StudentResponseMaintainer s20=new StudentResponseMaintainer();
		
		s11.setStudentId(11);
		s11.setResponseList(s1);
		s12.setStudentId(12);
		s12.setResponseList(s2);
		s13.setStudentId(13);
		s13.setResponseList(s3);
		s14.setStudentId(14);
		s14.setResponseList(s4);
		s15.setStudentId(15);
		s15.setResponseList(s5);
		s16.setStudentId(16);
		s16.setResponseList(s6);
		s17.setStudentId(17);
		s17.setResponseList(s7);
		s18.setStudentId(18);
		s18.setResponseList(s8);
		s19.setStudentId(19);
		s19.setResponseList(s9);
		s20.setStudentId(20);
		s20.setResponseList(s10);
		
		
		studentResponseList.add(s11);
		studentResponseList.add(s12);
		studentResponseList.add(s13);
		studentResponseList.add(s14);
		studentResponseList.add(s15);
		studentResponseList.add(s16);
		studentResponseList.add(s17);
		studentResponseList.add(s18);
		studentResponseList.add(s19);
		studentResponseList.add(s20);
		
		
		
		return studentResponseList;
	}

}
