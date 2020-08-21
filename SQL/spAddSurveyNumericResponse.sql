DELIMITER $$
DROP PROCEDURE IF EXISTS spAddSurveyNumericResponse $$
CREATE PROCEDURE `spAddSurveyNumericResponse`(

    IN questionId BIGINT,
    IN surveyId BIGINT,
    IN numericResponse BIGINT,
    IN userId BIGINT
    )
BEGIN
INSERT
	INTO table_student_response_numeric(question_id,survey_id,numeric_response,student_id)
	VALUES(questionId,surveyId,numericResponse,userId);
END$$
DELIMITER ;