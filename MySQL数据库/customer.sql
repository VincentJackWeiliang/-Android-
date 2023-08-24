/*
Navicat MySQL Data Transfer

Source Server         : Dailyrental
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : customer

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2021-05-22 12:01:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for information
-- ----------------------------
DROP TABLE IF EXISTS `information`;
CREATE TABLE `information` (
  `id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `order_name` varchar(255) DEFAULT NULL,
  `service` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of information
-- ----------------------------
INSERT INTO `information` VALUES (null, 'android', 'ann', 'wireless network', 'Shanghai');
INSERT INTO `information` VALUES (null, 'k', '天天', '无线网络，厨房，洗发水，吹风机，生活必需品', '合肥市');
INSERT INTO `information` VALUES (null, 'k', '星际归途', '无线网络', '桐梓林地铁站|LOFT复式|科华北路|王府井百货');
INSERT INTO `information` VALUES (null, 's', '海蓝', '无线网络，厨房，洗发水，吹风机，生活必需品', '南京市');
INSERT INTO `information` VALUES (null, 's', '海蓝', '无线网络，厨房，洗发水，吹风机，生活必需品', '南京市');
INSERT INTO `information` VALUES (null, 'android', 'ss', 'serevi', '南京市');
