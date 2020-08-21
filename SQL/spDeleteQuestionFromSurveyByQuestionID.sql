DELIMITER $$
DROP PROCEDURE IF EXISTS spDeleteQuestionFromSurveyByQuestionID $$
CREATE PROCEDURE `spDeleteQuestionFromSurveyByQuestionID`( IN questionID INT )
BEGIN
	DELETE FROM table_survey_questions_mapper WHERE question_id = questionID;
END$$
DELIMITER ;

