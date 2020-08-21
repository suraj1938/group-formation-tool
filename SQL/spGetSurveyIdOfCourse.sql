DELIMITER $$
DROP PROCEDURE IF EXISTS spGetSurveyIdOfCourse $$
CREATE PROCEDURE `spGetSurveyIdOfCourse`( IN courseId BIGINT)
BEGIN
	SELECT *
	FROM table_survey
    WHERE table_survey.course_id = courseId;
END$$
DELIMITER ;