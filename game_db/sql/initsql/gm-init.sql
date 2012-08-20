CREATE DATABASE `tr_gm` DEFAULT CHARACTER SET utf8;

USE `tr_gm`;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
CREATE TABLE `t_sys_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `serverIds` varchar(255) DEFAULT NULL,
  `lastLogonDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('1', 'admin', 'ISMvKXpXpadDiUoOSoAfww==', 'super_admin', '1', '2009-11-18 11:07:19');
INSERT INTO `t_sys_user` VALUES ('4', 'gm', 'ISMvKXpXpadDiUoOSoAfww==', 'admin', '1', '2010-08-19 11:42:26');