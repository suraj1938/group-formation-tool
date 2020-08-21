DELIMITER $$
DROP PROCEDURE IF EXISTS spValidateUserAppearedSurvey $$
CREATE PROCEDURE `spValidateUserAppearedSurvey`(
    IN userId BIGINT,
    IN surveyId BIGINT
    )
BEGIN

	select * FROM
	(SELECT COUNT(*) as count_1 FROM table_student_response_choice where student_id = userId and survey_id = surveyId) as tb_1,
	(SELECT COUNT(*) as count_2 FROM table_student_response_numeric where student_id = userId and survey_id = surveyId) as tb_2,
	(SELECT COUNT(*) as count_3 FROM table_student_response_text where student_id = userId and survey_id = surveyId) as tb_3;

END$$
DELIMITER ;



