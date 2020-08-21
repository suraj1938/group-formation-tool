DELIMITER $$
DROP PROCEDURE IF EXISTS spGetSurveyQuestionByCourseID $$
CREATE PROCEDURE `spGetSurveyQuestionByCourseID`( IN courseID INT )
BEGIN
	SELECT table_question.*,,table_survey_questions_mapper.survey_id FROM table_survey
    JOIN table_survey_questions_mapper ON table_survey.id = table_survey_questions_mapper.survey_id
    JOIN table_question ON table_question.id = table_survey_questions_mapper.question_id
    WHERE table_survey.course_id = courseID
    ORDER BY table_survey_questions_mapper.questionOrder;;
END$$
DELIMITER ;
