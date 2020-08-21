DELIMITER $$
DROP PROCEDURE IF EXISTS spGetSurveyQuestionsInOrder $$
CREATE PROCEDURE `spGetSurveyQuestionsInOrder`( IN surveyId BIGINT)
BEGIN
	SELECT *
	FROM table_survey_questions_mapper
    WHERE table_survey_questions_mapper.survey_id = surveyId
    ORDER BY table_survey_questions_mapper.questionOrder;
END$$
DELIMITER ;