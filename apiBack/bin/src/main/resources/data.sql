DROP TABLE IF EXISTS financial;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
	pseudo VARCHAR(250) NOT NULL PRIMARY KEY,
	password VARCHAR(250) NOT NULL
);

CREATE TABLE financial (
	id INT AUTO_INCREMENT  PRIMARY KEY,
	mensual_net_income INT NULL,
	net_imposable DECIMAL(100,2) NULL,
	mensual_credit_amount DECIMAL(100,2) NULL,
	user_pseudo VARCHAR(250) NOT NULL,
	FOREIGN KEY (user_pseudo) REFERENCES user(pseudo)
);
 
INSERT INTO user (pseudo, password) VALUES
	('pseudo', 'BxWW2ph+a/Q110p0WerrIg=='), --pass
	('Bill', 'YLimXuRg7502HgdePmqnxA=='); --Gates
  
INSERT INTO financial (mensual_net_income, net_imposable ,mensual_credit_amount, user_pseudo) VALUES
	(2000, 32000.5, 700, 'pseudo'),
	(15000, 100000, 0, 'Bill');