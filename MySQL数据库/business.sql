/*
Navicat MySQL Data Transfer

Source Server         : Dailyrental
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : business

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2021-05-22 12:01:35
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
  `number` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `service` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of information
-- ----------------------------
INSERT INTO `information` VALUES ('1', '微凉', '天天', '10', '120', '无线网络，厨房，洗发水，吹风机，生活必需品', '合肥市');
INSERT INTO `information` VALUES ('2', '微凉', '海蓝', '10', '120', '无线网络，厨房，洗发水，吹风机，生活必需品', '南京市');
INSERT INTO `information` VALUES ('3', 's', '星际归途', '11', '166', '无线网络', '桐梓林地铁站|LOFT复式|科华北路|王府井百货');
INSERT INTO `information` VALUES ('4', 'Gladys', '清空', '10', '166', '网络电视', '春熙路||太古里||市二医院地铁口');
INSERT INTO `information` VALUES ('5', '寓见', '观景', '2', '178', '无线网络，厨房，洗发水，吹风机，生活必需品', '成都市');
INSERT INTO `information` VALUES ('6', 'Aimee', '漫漫', '3', '151', '网络电视', '成都市');
INSERT INTO `information` VALUES ('7', '十苇', '夏夜', '5', '174', '无线网络，厨房，洗发水，吹风机，生活必需品', '成都市');
INSERT INTO `information` VALUES ('8', 'Ann', 'Sherry', '4', '150', '网络电视', '成都市文殊院地铁站');
INSERT INTO `information` VALUES ('9', '徐同学', '温馨', '5', '314', '厨房', '北京南站');
INSERT INTO `information` VALUES ('10', '仙女', '仙女屋', '10', '374', '免费停车位', '北京市');
INSERT INTO `information` VALUES ('11', '可甜', '简约', '10', '218', '无线网络', '北京市');
INSERT INTO `information` VALUES (null, 'android', 'ann', '11', '100', 'wireless network', 'Shanghai');
INSERT INTO `information` VALUES (null, 's', '上飞', '11', '232', '无线网', '南京市');
INSERT INTO `information` VALUES (null, 's', 'ss', '11', '123', 'serevi', '南京市');
