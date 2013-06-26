
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `blog_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `comment_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `blog_id` (`blog_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `tb_comment_ibfk_1` FOREIGN KEY (`blog_id`) REFERENCES `tb_blog` (`id`),
  CONSTRAINT `tb_comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) 

DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(60) not null unique,
  `user_password` varchar(64) NOT NULL,
  `head_protrait` blob,
   `isAdmin` tinyint(1) not null default 0, 
  PRIMARY KEY (`id`)
) 
//new
DROP TABLE IF EXISTS `tb_follow`;
CREATE TABLE `tb_follow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `follow_from` BIGINT NOT NULL,
  `follow_to` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `follow_from` (`follow_from`),
  KEY `follow_to` (`follow_to`),
  CONSTRAINT `tb_follow_ibfk_1` FOREIGN KEY (`follow_from`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `tb_follow_ibfk_2` FOREIGN KEY (`follow_to`) REFERENCES `tb_user` (`id`)
) 

//new
DROP TABLE IF EXISTS `tb_blog`;
CREATE TABLE `tb_blog` (

  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `type` varchar(10) NOT NULL,
  `from` int(11) DEFAULT NULL,
  `title` text,
  `content` longtext,
  `release_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `tb_blog_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
)
