/*
Navicat MySQL Data Transfer

Source Server         : xhw
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : chatroom

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2021-07-03 04:08:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `send_id` int(255) NOT NULL,
  `accept_id` int(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `target` int(255) NOT NULL COMMENT '0-私聊\r\n1-群聊',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('48', '3', '2', '你好', '2021-07-02', '0');
INSERT INTO `message` VALUES ('49', '2', '3', '你好', '2021-07-02', '0');
INSERT INTO `message` VALUES ('50', '3', '2', '课设写好没？', '2021-07-02', '0');
INSERT INTO `message` VALUES ('51', '3', '31', '大家好', '2021-07-02', '1');
INSERT INTO `message` VALUES ('52', '1', '31', '好！', '2021-07-02', '1');
INSERT INTO `message` VALUES ('53', '2', '31', '嗯好', '2021-07-02', '1');
INSERT INTO `message` VALUES ('54', '2', '3', '快上号', '2021-07-03', '0');
INSERT INTO `message` VALUES ('55', '3', '2', '号', '2021-07-03', '0');
INSERT INTO `message` VALUES ('56', '2', '30', 'java课设写好了没', '2021-07-03', '1');
INSERT INTO `message` VALUES ('57', '3', '30', '还没', '2021-07-03', '1');
INSERT INTO `message` VALUES ('58', '1', '30', '写好了', '2021-07-03', '1');
INSERT INTO `message` VALUES ('59', '1', '30', '嘻嘻', '2021-07-03', '1');
