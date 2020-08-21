DELIMITER $$
DROP PROCEDURE IF EXISTS spGetStudentChoiceResponsesOfSurvey $$
CREATE PROCEDURE `spGetStudentChoiceResponsesOfSurvey`( IN surveyId BIGINT)
BEGIN
	SELECT *
	FROM table_student_response_choice
    WHERE table_student_response_choice.survey_id = surveyId;
END$$
DELIMITER ;