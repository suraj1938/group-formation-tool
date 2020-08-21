DELIMITER $$
DROP PROCEDURE IF EXISTS spGetSurveyQuestionByInstructorID $$
CREATE PROCEDURE `spGetSurveyQuestionByInstructorID`( IN instructorID INT )
BEGIN
	SELECT *
	FROM table_question
    WHERE instructor_id = instructorID;
END$$
DELIMITER ;
