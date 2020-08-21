DELIMITER $$
DROP PROCEDURE IF EXISTS spMapQuestionChoice $$
CREATE PROCEDURE `spMapQuestionChoice`(

    IN questionId BIGINT,
    IN choiceId BIGINT
    )
BEGIN
	INSERT
	INTO table_question_choice_mapper(question_id,choice_id)
	VALUES(questionId,choiceId);

END$$
DELIMITER ;