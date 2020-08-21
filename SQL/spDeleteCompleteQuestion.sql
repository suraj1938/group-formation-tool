DELIMITER $$
DROP PROCEDURE IF EXISTS spDeleteCompleteQuestion $$
CREATE PROCEDURE `spDeleteCompleteQuestion`(
   IN questionId BIGINT
)
BEGIN
	create temporary table if not exists tmpChoicesTable SELECT * FROM table_question_choice_mapper where question_id = questionId;
    SELECT choice_id from tmpChoicesTable;
    DELETE table_question_choice_mapper from table_question_choice_mapper where question_id=questionId;
    DELETE tc from table_choice tc where id in (select choice_id from tmpChoicesTable);
    DELETE table_question from table_question where id=questionId;
    DROP temporary TABLE IF EXISTS tmpChoicesTable;

END$$
DELIMITER ;