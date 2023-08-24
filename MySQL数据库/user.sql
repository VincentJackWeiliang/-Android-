/*
Navicat MySQL Data Transfer

Source Server         : Dailyrental
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : user

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2021-05-22 12:02:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for information
-- ----------------------------
DROP TABLE IF EXISTS `information`;
CREATE TABLE `information` (
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of information
-- ----------------------------
INSERT INTO `information` VALUES ('k', 'k');
INSERT INTO `information` VALUES ('s', 's');
INSERT INTO `information` VALUES ('微凉', '123456');
INSERT INTO `information` VALUES ('s', '123456');
INSERT INTO `information` VALUES ('Gladys', '123456');
INSERT INTO `information` VALUES ('寓见', '123456');
INSERT INTO `information` VALUES ('Aimee', '123456');
INSERT INTO `information` VALUES ('十苇', '123456');
INSERT INTO `information` VALUES ('Ann', '123456');
INSERT INTO `information` VALUES ('徐同学', '123456');
INSERT INTO `information` VALUES ('仙女', '123456');
INSERT INTO `information` VALUES ('可甜', '123456');
INSERT INTO `information` VALUES ('ann', 'ann');
INSERT INTO `information` VALUES ('android', 'android');
INSERT INTO `information` VALUES ('x', 'x');
INSERT INTO `information` VALUES ('?', '123');
