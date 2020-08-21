DELIMITER $$
DROP PROCEDURE IF EXISTS spAddSurveyTextResponse $$
CREATE PROCEDURE `spAddSurveyTextResponse`(

    IN questionId BIGINT,
    IN surveyId BIGINT,
    IN textResponse VARCHAR(100),
    IN userId BIGINT
    )
BEGIN
INSERT
	INTO table_student_response_text(question_id,survey_id,text,student_id)
	VALUES(questionId,surveyId,textResponse,userId);
END$$
DELIMITER ;

