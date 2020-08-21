DELIMITER $$
DROP PROCEDURE IF EXISTS spGetQuestionRule $$
CREATE PROCEDURE `spGetQuestionRule`( IN ruleId BIGINT)
BEGIN
	SELECT *
	FROM table_group_formation_rules
    WHERE table_group_formation_rules.id = ruleId;
END$$
DELIMITER ;