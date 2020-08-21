DELIMITER $$
DROP PROCEDURE IF EXISTS spUpdatePolicy $$
CREATE PROCEDURE `spUpdatePolicy`(IN p_id INT , IN p_state INT , IN p_policy_value VARCHAR(20) )
BEGIN
    UPDATE table_password_policy_selector
    SET    is_active = p_state,
           policy_value = p_policy_value
    WHERE  id = p_id;
END$$
DELIMITER ;