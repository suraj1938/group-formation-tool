DELIMITER $$
DROP PROCEDURE IF EXISTS spGetUserPasswordHistory $$
CREATE PROCEDURE `spGetUserPasswordHistory`( IN userId BIGINT)
BEGIN
	SELECT *
	FROM table_user_password_history
    WHERE table_user_password_history.user_id = userId;
END$$
DELIMITER ;