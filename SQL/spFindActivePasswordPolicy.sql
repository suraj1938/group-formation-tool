DELIMITER $$
DROP PROCEDURE IF EXISTS spFindActivePasswordPolicy $$
CREATE PROCEDURE `spFindActivePasswordPolicy`()
BEGIN
	SELECT id, policy_name, policy_desc,policy_value, is_active
	FROM table_password_policy_selector
    WHERE table_password_policy_selector.is_active = 1;
END$$
DELIMITER ;