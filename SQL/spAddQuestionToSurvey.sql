DELIMITER $$
DROP PROCEDURE IF EXISTS spAddQuestionToSurvey $$
CREATE PROCEDURE `spAddQuestionToSurvey`(
IN questionId BIGINT,
IN questionOrder BIGINT,
IN surveyId BIGINT,
IN rule_id BIGINT,
IN rule_value BIGINT,
OUT mapperId BIGINT)
BEGIN
	INSERT INTO `table_survey_questions_mapper` (`question_id`,`questionOrder`,`survey_id`,`rule_id`,`rule_value`) VALUES (questionId, questionOrder, surveyId, rule_id,rule_value);
    SET mapperId=last_insert_id();
END$$
DELIMITER ;

