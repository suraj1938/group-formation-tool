DELIMITER $$
DROP PROCEDURE IF EXISTS spAddSurveyChoiceResponse $$
CREATE PROCEDURE `spAddSurveyChoiceResponse`(

    IN questionId BIGINT,
    IN surveyId BIGINT,
    IN choiceId BIGINT,
    IN userId BIGINT
    )
BEGIN
INSERT
	INTO table_student_response_choice(question_id,survey_id,choice_id,student_id)
	VALUES(questionId,surveyId,choiceId,userId);
END$$
DELIMITER ;