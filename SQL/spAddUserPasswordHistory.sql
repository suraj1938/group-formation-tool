DELIMITER $$
DROP PROCEDURE IF EXISTS spAddUserPasswordHistory $$
CREATE PROCEDURE `spAddUserPasswordHistory`( IN userId BIGINT, IN password VARCHAR(100))
BEGIN
	INSERT INTO table_user_password_history(password,user_id) VALUES(password,userId);
END$$
DELIMITER ;