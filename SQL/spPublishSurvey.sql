DELIMITER $$
DROP PROCEDURE IF EXISTS `spPublishSurvey` $$
CREATE PROCEDURE `spPublishSurvey`( IN surveyId BIGINT)
BEGIN

	UPDATE `table_survey` SET `publish_state` = '1' WHERE (`id` = surveyId);

END$$
DELIMITER ;
