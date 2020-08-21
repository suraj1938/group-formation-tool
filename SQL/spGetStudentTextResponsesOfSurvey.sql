DELIMITER $$
DROP PROCEDURE IF EXISTS spGetStudentTextResponsesOfSurvey $$
CREATE PROCEDURE `spGetStudentTextResponsesOfSurvey`( IN surveyId BIGINT)
BEGIN
	SELECT *
	FROM table_student_response_text
    WHERE table_student_response_text.survey_id = surveyId;
END$$
DELIMITER ;