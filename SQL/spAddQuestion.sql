DELIMITER $$
DROP PROCEDURE IF EXISTS spAddQuestion $$
CREATE PROCEDURE `spAddQuestion`(

    IN questionTitle VARCHAR(40),
    IN questionTypeId BIGINT,
    IN instructorId BIGINT,
    IN questionText VARCHAR(200),
    IN questionDate DATE,
    OUT IN_ID BIGINT
    )
BEGIN
	INSERT
	INTO table_question(title,type_id,instructor_id,question_text,question_date)
	VALUES(questionTitle,questionTypeId,instructorId,questionText,questionDate);

    SET IN_ID = LAST_INSERT_ID();

END$$
DELIMITER ;