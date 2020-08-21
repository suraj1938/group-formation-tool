DELIMITER $$
DROP PROCEDURE IF EXISTS spFindQuestionChoices $$
CREATE PROCEDURE `spFindQuestionChoices`(
   IN questionId BIGINT
)
BEGIN
SELECT * FROM table_question tq
	JOIN table_question_choice_mapper  tqc
	ON tq.id=tqc.question_id
	JOIN table_choice tc
	ON tc.id=tqc.choice_id
	WHERE tq.id=questionId;
END$$
DELIMITER ;