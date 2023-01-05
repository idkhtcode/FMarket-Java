CREATE TABLE `fudanmarket`.`goods` (
                                       `id` INT NOT NULL AUTO_INCREMENT,
                                       `name` VARCHAR(45) NOT NULL,
                                       `remark` VARCHAR(45) NULL,
                                       `price` VARCHAR(45) NOT NULL,
                                       `sortByGoods` VARCHAR(45) NOT NULL,
                                       `uid` VARCHAR(45) NOT NULL,
                                       `image` VARCHAR(45) NOT NULL,
                                       `sortByMajor` VARCHAR(45),
                                       PRIMARY KEY (`id`));
CREATE TABLE `fudanmarket`.`user` (
                                      `id` INT NOT NULL AUTO_INCREMENT,
                                      `username` VARCHAR(45) NOT NULL,
                                      `password` VARCHAR(45) NOT NULL,
                                      `major` VARCHAR(45) NOT NULL,
                                      `year` VARCHAR(45) NOT NULL,
                                      `usertype` VARCHAR(45) NOT NULL,
                                      `registerTime` VARCHAR(45) NOT NULL,
                                      PRIMARY KEY (`id`));

