DELIMITER $$
DROP PROCEDURE IF EXISTS spFindAllInstructorQuestions $$
CREATE PROCEDURE `spFindAllInstructorQuestions`( IN instructorId BIGINT)
BEGIN
	SELECT *
	FROM table_question WHERE instructor_id=instructorId;
END$$
DELIMITER ;