DELIMITER $$
DROP PROCEDURE IF EXISTS spAddChoice $$
CREATE PROCEDURE `spAddChoice`(

    IN chocice_value VARCHAR(100),
    IN stored_value BIGINT,
    OUT IN_ID BIGINT
    )
BEGIN
	INSERT
	INTO table_choice(choice,stored_as)
	VALUES(chocice_value,stored_value);

    SET IN_ID = LAST_INSERT_ID();

END$$
DELIMITER ;