/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : web

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2018-07-27 17:41:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for calc_count
-- ----------------------------
DROP TABLE IF EXISTS `calc_count`;
CREATE TABLE `calc_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `store_count` int(11) DEFAULT NULL COMMENT '库存',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='计算表';

-- ----------------------------
-- Records of calc_count
-- ----------------------------
INSERT INTO `calc_count` VALUES ('1', '0', '热干面', '0');
