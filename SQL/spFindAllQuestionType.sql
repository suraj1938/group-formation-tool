DELIMITER $$
DROP PROCEDURE IF EXISTS spFindAllQuestionType $$
CREATE PROCEDURE `spFindAllQuestionType`()
BEGIN
	SELECT id, type
	FROM table_question_type;
END$$
DELIMITER ;