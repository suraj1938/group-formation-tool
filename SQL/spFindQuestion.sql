DELIMITER $$
DROP PROCEDURE IF EXISTS spFindQuestion $$
CREATE PROCEDURE `spFindQuestion`( IN questionId BIGINT)
BEGIN
	SELECT *
	FROM table_question WHERE id=questionId;
END$$
DELIMITER ;