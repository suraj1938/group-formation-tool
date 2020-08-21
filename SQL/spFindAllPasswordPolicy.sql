DELIMITER $$
DROP PROCEDURE IF EXISTS spFindAllPasswordPolicy $$
CREATE PROCEDURE `spFindAllPasswordPolicy`()
BEGIN
	SELECT id, policy_name, policy_desc,is_active,policy_value
	FROM table_password_policy_selector;
END$$
DELIMITER ;