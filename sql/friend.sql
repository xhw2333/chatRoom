/*
Navicat MySQL Data Transfer

Source Server         : xhw
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : chatroom

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2021-07-03 04:08:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `my_id` int(255) unsigned NOT NULL,
  `friend_id` int(255) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('31', '3', '2');
INSERT INTO `friend` VALUES ('32', '2', '3');
INSERT INTO `friend` VALUES ('35', '3', '1');
INSERT INTO `friend` VALUES ('36', '1', '3');
