/*
Navicat MySQL Data Transfer

Source Server         : xhw
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : chatroom

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2021-07-03 04:08:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `group_id` int(255) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) NOT NULL,
  `owner_id` int(255) NOT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES ('1', 'test', '1');
INSERT INTO `group` VALUES ('30', 'java课设讨论群', '3');
INSERT INTO `group` VALUES ('31', '假期去哪了？', '3');
INSERT INTO `group` VALUES ('32', 'xhw粉丝群', '2');
