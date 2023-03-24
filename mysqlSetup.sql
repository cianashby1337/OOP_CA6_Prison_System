DROP TABLE IF EXISTS prisoners;

CREATE TABLE `prisoners`
(
    `prisoner_id`         int(11)     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name`          varchar(30) NOT NULL,
    `last_name`           varchar(30) NOT NULL,
    `level_of_misconduct` float       NOT NULL,
    `imprisonment_date`   date        NOT NULL,
    `release_date`        date DEFAULT NULL
);

INSERT INTO `prisoners`(`prisoner_id`, `first_name`, `last_name`, `level_of_misconduct`, `imprisonment_date`, `release_date`) VALUES
(0,'Darren','Hughes',1.15,'23-03-01','23-06-01'),
(0,'Mick','Smith',1.55,'23-03-01','23-07-01'),
(0,'Abel','Gordon',3.15,'23-03-01','25-06-01'),
(0,'Jonah','Cawthorne',2.55,'23-03-01','24-04-01'),
(0,'Henry','Hughes',1.10,'23-03-01','23-05-26'),
(0,'Shane','Quinn',3.10,'23-03-01','27-11-21'),
(0,'Diego','Sanchez',1.15,'23-03-01','23-06-01'),
(0,'John','Doe',3.15,'23-03-01','25-05-01'),
(0,'Andrew','Anderson',1.10,'23-03-01','23-06-01'),
(0,'Charlie','Hughes',4.15,'23-03-01','43-01-01');