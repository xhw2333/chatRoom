/*
Navicat MySQL Data Transfer

Source Server         : xhw
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : chatroom

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2021-07-03 04:08:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `group_id` int(255) NOT NULL,
  `member_id` int(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('62', '30', '3');
INSERT INTO `member` VALUES ('63', '30', '1');
INSERT INTO `member` VALUES ('64', '30', '2');
INSERT INTO `member` VALUES ('65', '31', '3');
INSERT INTO `member` VALUES ('66', '31', '2');
INSERT INTO `member` VALUES ('67', '31', '1');
INSERT INTO `member` VALUES ('68', '32', '2');
INSERT INTO `member` VALUES ('69', '32', '3');
