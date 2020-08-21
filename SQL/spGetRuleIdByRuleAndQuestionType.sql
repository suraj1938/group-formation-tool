DELIMITER $$
DROP PROCEDURE IF EXISTS getRuleIdByRuleAndQuestionType $$
CREATE PROCEDURE `getRuleIdByRuleAndQuestionType`( IN ruleName VARCHAR(45), IN questionTypeId BIGINT)
BEGIN

    SELECT * FROM table_group_formation_rules WHERE rule=ruleName AND question_type_id=questionTypeId;

END$$
DELIMITER ;
