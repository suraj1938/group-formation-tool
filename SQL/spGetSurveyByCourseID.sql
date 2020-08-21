DELIMITER $$
DROP PROCEDURE IF EXISTS spGetSurveyByCourseID $$
CREATE PROCEDURE `spGetSurveyByCourseID`( IN courseID INT )
BEGIN
	SELECT *
	FROM table_survey
	WHERE course_id = courseID;
END$$
DELIMITER ;
