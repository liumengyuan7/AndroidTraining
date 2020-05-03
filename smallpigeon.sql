/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : smallpigeon

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2020-05-03 13:06:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `dynamics_id` int(11) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `comment_content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `dynamics`
-- ----------------------------
DROP TABLE IF EXISTS `dynamics`;
CREATE TABLE `dynamics` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `push_time` datetime DEFAULT NULL,
  `push_content` varchar(255) DEFAULT NULL,
  `push_image` varchar(255) DEFAULT NULL,
  `zan_num` int(11) DEFAULT NULL,
  `forward_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dynamics
-- ----------------------------

-- ----------------------------
-- Table structure for `give_tu`
-- ----------------------------
DROP TABLE IF EXISTS `give_tu`;
CREATE TABLE `give_tu` (
  `id` int(11) NOT NULL,
  `dynamics_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of give_tu
-- ----------------------------

-- ----------------------------
-- Table structure for `interest`
-- ----------------------------
DROP TABLE IF EXISTS `interest`;
CREATE TABLE `interest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `outdoor` int(1) DEFAULT '0',
  `music` int(1) DEFAULT '0',
  `film` int(1) DEFAULT '0',
  `society` int(1) DEFAULT '0',
  `delicacy` int(1) DEFAULT '0',
  `science` int(1) DEFAULT '0',
  `star` int(1) DEFAULT '0',
  `comic` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of interest
-- ----------------------------
INSERT INTO `interest` VALUES ('40', '44', '0', '1', '1', '1', '1', '1', '1', '1');
INSERT INTO `interest` VALUES ('41', '45', '1', '0', '0', '1', '0', '0', '1', '1');
INSERT INTO `interest` VALUES ('42', '46', '1', '1', '0', '0', '0', '1', '0', '0');

-- ----------------------------
-- Table structure for `plan`
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `companion_id` int(11) NOT NULL,
  `plan_time` datetime DEFAULT NULL,
  `plan_address` varchar(255) DEFAULT NULL,
  `plan_status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plan
-- ----------------------------

-- ----------------------------
-- Table structure for `record`
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `record_time` datetime DEFAULT NULL,
  `record_distance` float DEFAULT NULL,
  `record_speed` float DEFAULT NULL,
  `record_date` datetime DEFAULT NULL,
  `record_points` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of record
-- ----------------------------

-- ----------------------------
-- Table structure for `reply_msg`
-- ----------------------------
DROP TABLE IF EXISTS `reply_msg`;
CREATE TABLE `reply_msg` (
  `id` int(11) NOT NULL,
  `comment_id` int(11) DEFAULT NULL,
  `from_id` int(11) DEFAULT NULL,
  `to_id` int(11) DEFAULT NULL,
  `reply_content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply_msg
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `user_nickname` varchar(20) DEFAULT NULL,
  `user_password` varchar(50) DEFAULT NULL,
  `user_sex` char(10) DEFAULT NULL,
  `user_email` varchar(20) DEFAULT NULL,
  `user_register_time` datetime DEFAULT NULL,
  `user_sno` varchar(10) DEFAULT NULL,
  `user_points` int(5) DEFAULT '0',
  `is_accreditation` smallint(6) DEFAULT NULL,
  `longitude` double(50,16) DEFAULT '0.0000000000000000',
  `latitude` double(50,16) DEFAULT '0.0000000000000000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('44', null, '酷酷的羊', 'e10adc3949ba59abbe56e057f20f883e', 'man', '45208943@qq.com', '2019-12-09 14:37:40', null, '10', null, '121.2997703991014300', '28.4469128586503700');
INSERT INTO `user` VALUES ('45', null, '还好吧好不好', '4607e782c4d86fd5364d7e4508bb10d9', 'man', '2032920335@qq.com', '2019-12-10 08:35:09', null, '0', null, '121.4997703991014400', '28.6469128586503740');
INSERT INTO `user` VALUES ('46', null, '飞~', 'e10adc3949ba59abbe56e057f20f883e', 'man', '156267102@qq.com', '2020-04-30 18:06:12', null, '0', null, '121.3997703991014400', '28.5469128586503730');
