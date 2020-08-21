DELIMITER $$
DROP PROCEDURE IF EXISTS `spUnPublishSurvey` $$
CREATE PROCEDURE `spUnPublishSurvey`( IN surveyId BIGINT)
BEGIN

	UPDATE `table_survey` SET `publish_state` = '0' WHERE (`id` = surveyId);

END$$
DELIMITER ;
