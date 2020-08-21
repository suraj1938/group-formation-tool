package com.app.group15.GroupFormationManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.sun.mail.util.QEncoderStream;

public class MatrixGroupFormationAlgorithm extends GroupFormationAlgorithm {

	private ArrayList<Integer> studentsAdded = new ArrayList<>();
	private HashMap<Integer, ArrayList<Object>> studentResponseMaintainerMap = new HashMap<>();
	private HashMap<Integer, Integer> studentIdArrayIndexMap = new HashMap<>();
	private ArrayList<ArrayList<Integer>> studentGroupsFormed = new ArrayList();

	@Override
	public ArrayList<ArrayList<Integer>> formGroups(ArrayList<StudentResponseMaintainer> studentResponseList, int groupSize,
			HashMap<Integer, String> questionCriteria) {

		while (studentResponseList.size() > 0) {
			createStudentResponseMaintainerMap(studentResponseList);
			ArrayList<ArrayList> responseForProcessing = manipulateStudentIdArrayIndexMap(studentResponseList);
			ArrayList<Integer> groupList = formGroupByStudentResponse(responseForProcessing, groupSize,
					questionCriteria);
			ArrayList<Integer> studentGroup = addToStudentsAdded(groupList);
			studentResponseList = removeConsideredResponses(studentGroup, studentResponseList);
			this.studentIdArrayIndexMap.clear();
		}
		return this.studentGroupsFormed;

	}

	private ArrayList<StudentResponseMaintainer> removeConsideredResponses(ArrayList<Integer> studentGroup,
			ArrayList<StudentResponseMaintainer> studentResponseList) {
		ArrayList<StudentResponseMaintainer> newResponseList = new ArrayList<>();
		for (int i = 0; i < studentResponseList.size(); i++) {
			boolean doesNotContain = !studentGroup.contains(studentResponseList.get(i).getStudentId());
			if (doesNotContain) {
				newResponseList.add(studentResponseList.get(i));
			}
		}
		return newResponseList;
	}

	private ArrayList<Integer> addToStudentsAdded(ArrayList<Integer> groupList) {
		ArrayList<Integer> studentGroup = new ArrayList<>();
		for (int i = 0; i < groupList.size(); i++) {
			int studendtId = studentIdArrayIndexMap.get(groupList.get(i));
			studentsAdded.add(studendtId);
			studentGroup.add(studendtId);
		}
		ArrayList<Integer> newList = (ArrayList<Integer>) studentGroup.stream() 
                .distinct() 
                .collect(Collectors.toList()); 
		studentGroupsFormed.add(newList);
		return studentGroup;

	}

	private ArrayList<ArrayList> manipulateStudentIdArrayIndexMap(
			ArrayList<StudentResponseMaintainer> studentResponseList) {
		ArrayList<ArrayList> responseForProcessing = new ArrayList<>();
		for (int i = 0; i < studentResponseList.size(); i++) {
			responseForProcessing.add(studentResponseList.get(i).getResponseList());
			studentIdArrayIndexMap.put(responseForProcessing.size() - 1, studentResponseList.get(i).getStudentId());
		}
		return responseForProcessing;

	}

	private void createStudentResponseMaintainerMap(ArrayList<StudentResponseMaintainer> studentResponseList) {
		for (int i = 0; i < studentResponseList.size(); i++) {
			this.studentResponseMaintainerMap.put(studentResponseList.get(i).getStudentId(),
					studentResponseList.get(i).getResponseList());
		}
	}

	private ArrayList<Integer> formGroupByStudentResponse(ArrayList<ArrayList> studentResponseList, int groupSize,
			HashMap<Integer, String> questionCriteria) {
		int[][] similarityMatrix = formSimilarityMatrix(studentResponseList, questionCriteria);
		int[][] copySimilarityMatrix = Arrays.stream(similarityMatrix).map(int[]::clone).toArray(int[][]::new);

		int[][] sortedRowSimilarityMatrix = sortMatrixRowWise(copySimilarityMatrix);

		int[][] sortedAllSimilarityMatrix = sortMatrixRowWise(transpose(copySimilarityMatrix));

		int maxRank = sortedAllSimilarityMatrix[sortedAllSimilarityMatrix.length
				- 1][sortedAllSimilarityMatrix[0].length - 1];
		ArrayList<Integer> initialGroup = intitalGroupOfTwo(maxRank, similarityMatrix);
		HashMap<Integer, Integer> studentsRankMapDesc = getStudentsSortedByRankMeanDesc(initialGroup, similarityMatrix,
				maxRank);
		ArrayList<Integer> formedStudentGroup = addStudentsToInitialGroup(studentsRankMapDesc, initialGroup, groupSize);

		return formedStudentGroup;
	}

	private ArrayList<Integer> addStudentsToInitialGroup(HashMap<Integer, Integer> studentsRankMapDesc,
			ArrayList<Integer> initialGroup, int groupSize) {

		for (Map.Entry<Integer, Integer> entry : studentsRankMapDesc.entrySet()) {
			if (initialGroup.size() < groupSize) {
				initialGroup.add(entry.getKey());
			}
		}

		return initialGroup;
	}

	private HashMap<Integer, Integer> sortHashMapDescByValue(HashMap<Integer, Integer> hm) {

		List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(hm.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {

			@Override
			public int compare(Entry<Integer, Integer> arg0, Entry<Integer, Integer> arg1) {

				return (arg1.getValue()).compareTo(arg0.getValue());
			}
		});

		HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
		for (Map.Entry<Integer, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

	private HashMap<Integer, Integer> getStudentsSortedByRankMeanDesc(ArrayList<Integer> stduentGroup,
			int[][] similarityMatrix, int groupRank) {
		HashMap<Integer, Integer> rankMeanMap = new HashMap<Integer, Integer>();

		for (int i = 0; i < similarityMatrix.length; i++) {
			int rankMean = 0;
			for (int j = 0; j < stduentGroup.size(); j++) {
				if (i != stduentGroup.get(j)) {
					rankMean += similarityMatrix[i][stduentGroup.get(j)];
				}
			}
			rankMean = rankMean / 2;
			rankMeanMap.put(i, rankMean);

		}
		rankMeanMap = sortHashMapDescByValue(rankMeanMap);
		for (int j = 0; j < stduentGroup.size(); j++) {
			rankMeanMap.remove(stduentGroup.get(j));
		}
		return rankMeanMap;
	}

	private ArrayList<Integer> intitalGroupOfTwo(int maxRank, int[][] similarityMatrix) {
		ArrayList<Integer> studentGroup = new ArrayList<Integer>();
		for (int i = 0; i < similarityMatrix.length; i++) {
			for (int j = 0; j < similarityMatrix[i].length; j++) {

				if (studentGroup.size() < 2) {
					if (similarityMatrix[i][j] == maxRank) {
						studentGroup.add(i);
						studentGroup.add(j);

					}
				} else {
					break;
				}

			}

		}
		return studentGroup;

	}

	private int[][] sortMatrixRowWise(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			Arrays.sort(matrix[i]);
		}

		return matrix;
	}

	private int[][] transpose(int mat[][]) {
		for (int i = 0; i < mat.length; i++)
			for (int j = i + 1; j < mat[i].length; j++) {

				int temp = mat[i][j];
				mat[i][j] = mat[j][i];
				mat[j][i] = temp;
			}
		return mat;
	}

	private int[][] formSimilarityMatrix(ArrayList<ArrayList> studentResponseList,
			HashMap<Integer, String> questionCriteria) {
		int[][] similarityMatrix = new int[studentResponseList.size()][studentResponseList.size()];
		for (int i = 0; i < studentResponseList.size(); i++)
			for (int j = 0; j < studentResponseList.size(); j++) {
				similarityMatrix[i][j] = 0;
			}

		for (int studentIndex = 0; studentIndex < studentResponseList.size(); studentIndex++) {
			for (int otherStudentIndex = studentIndex + 1; otherStudentIndex < studentResponseList
					.size(); otherStudentIndex++) {
				for (int questionIndex = 0; questionIndex < studentResponseList.get(0).size(); questionIndex++) {
					if (questionCriteria.get(super.questionOrderNumbers.get(questionIndex)).equals("SIMILAR")) {
						if (studentResponseList.get(studentIndex).get(questionIndex)
								.equals(studentResponseList.get(otherStudentIndex).get(questionIndex))) {
							similarityMatrix[studentIndex][otherStudentIndex] = similarityMatrix[studentIndex][otherStudentIndex]
									+ 1;
							similarityMatrix[otherStudentIndex][studentIndex] = similarityMatrix[otherStudentIndex][studentIndex]
									+ 1;
						}
					} else {
						boolean dissimilar = !studentResponseList.get(studentIndex).get(questionIndex)
								.equals(studentResponseList.get(otherStudentIndex).get(questionIndex));
						if (dissimilar) {
							similarityMatrix[studentIndex][otherStudentIndex] = similarityMatrix[studentIndex][otherStudentIndex]
									+ 1;
							similarityMatrix[otherStudentIndex][studentIndex] = similarityMatrix[otherStudentIndex][studentIndex]
									+ 1;
						}

					}

				}

			}
		}
		return similarityMatrix;
	}

}
