DELIMITER $$
DROP PROCEDURE IF EXISTS spSaveSurvey $$
CREATE PROCEDURE `spSaveSurvey`( IN courseId BIGINT, OUT IN_ID BIGINT)
BEGIN

    INSERT INTO `table_survey` (`course_id`,`publish_state`) VALUES (courseId, 0);
    SET IN_ID=last_insert_id();

END$$
DELIMITER ;
