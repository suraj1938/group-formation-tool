DELIMITER $$
DROP PROCEDURE IF EXISTS `spGetHighestOrderValue` $$
CREATE PROCEDURE `spGetHighestOrderValue`( IN surveyId BIGINT)
BEGIN

	SELECT MAX(questionOrder) from table_survey_questions_mapper WHERE survey_id=surveyId;
END$$
DELIMITER ;
