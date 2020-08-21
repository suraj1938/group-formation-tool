DELIMITER $$
CREATE PROCEDURE `spGetRemainingQuestionForSurvey`( IN courseId BIGINT, IN instructorId BIGINT)
BEGIN

    SELECT * FROM table_question WHERE instructor_id=instructorId AND id not in
    (
    SELECT tqs.question_id FROM table_survey_questions_mapper tqs
    JOIN table_survey ts ON ts.id=tqs.survey_id
    WHERE ts.course_id=courseId
    );

END$$
DELIMITER ;
