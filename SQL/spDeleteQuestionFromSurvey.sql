DELIMITER $$
DROP PROCEDURE IF EXISTS spDeleteQuestionFromSurvey $$
CREATE PROCEDURE `spDeleteQuestionFromSurvey`( IN questionId BIGINT, IN surveyId BIGINT)
BEGIN

    DELETE FROM table_survey_questions_mapper WHERE question_id=questionId AND survey_id=surveyId;


END$$
DELIMITER ;
