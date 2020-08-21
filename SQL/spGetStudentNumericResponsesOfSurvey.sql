DELIMITER $$
DROP PROCEDURE IF EXISTS spGetStudentNumericResponsesOfSurvey $$
CREATE PROCEDURE `spGetStudentNumericResponsesOfSurvey`( IN surveyId BIGINT)
BEGIN
	SELECT *
	FROM table_student_response_numeric
    WHERE table_student_response_numeric.survey_id = surveyId;
END$$
DELIMITER ;