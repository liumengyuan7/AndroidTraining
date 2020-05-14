/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : smallpigeon

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2020-05-14 09:45:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `nickName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', '904569030@qq.com', '456789', '圆圆');
INSERT INTO `admin` VALUES ('2', '123456789@qq.com', '123456', '&#33457;&#33457;');

-- ----------------------------
-- Table structure for `collect`
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dynamic_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES ('3', '3', '46', '0');

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dynamics_id` int(11) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `comment_content` varchar(255) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `comment_zanNum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '2', '46', '笑什么', '2020-04-20 14:36:55', '0');
INSERT INTO `comment` VALUES ('2', '3', '44', '开心呀', '2020-04-20 14:36:50', '0');
INSERT INTO `comment` VALUES ('3', '2', '46', '项目实训', '2020-04-28 14:39:48', '0');
INSERT INTO `comment` VALUES ('4', '22', '46', '我爱你', '2020-04-28 14:44:36', '0');
INSERT INTO `comment` VALUES ('5', '3', '46', '加油加油', '2020-04-28 14:47:05', '0');
INSERT INTO `comment` VALUES ('6', '2', '46', '爱你', '2020-04-28 14:55:15', '0');

-- ----------------------------
-- Table structure for `dynamics`
-- ----------------------------
DROP TABLE IF EXISTS `dynamics`;
CREATE TABLE `dynamics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `push_time` datetime DEFAULT NULL,
  `push_content` varchar(255) DEFAULT NULL,
  `push_image` varchar(255) DEFAULT NULL,
  `zan_num` int(11) NOT NULL DEFAULT '0',
  `forward_id` int(11) NOT NULL DEFAULT '0',
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dynamics
-- ----------------------------
INSERT INTO `dynamics` VALUES ('2', '46', '2020-04-20 14:36:55', '哈哈哈哈哈哈', 'IMG_20200320_161235.jpg', '0', '0', '0');
INSERT INTO `dynamics` VALUES ('3', '46', '2020-04-20 14:45:45', '嘻嘻嘻', 'IMG_20200320_161235.jpg;IMG_20200310_205542.jpg', '0', '0', '0');
INSERT INTO `dynamics` VALUES ('22', '47', '2020-04-28 10:37:26', 'yyyyyyyyy', '', '1', '2', '1');

-- ----------------------------
-- Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `my_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('95', '46', '47');
INSERT INTO `friend` VALUES ('96', '47', '46');

-- ----------------------------
-- Table structure for `give_tu`
-- ----------------------------
DROP TABLE IF EXISTS `give_tu`;
CREATE TABLE `give_tu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dynamics_id` int(11) DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  `comment_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of give_tu
-- ----------------------------
INSERT INTO `give_tu` VALUES ('7', '22', '44', '0');
INSERT INTO `give_tu` VALUES ('8', '0', '46', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of interest
-- ----------------------------
INSERT INTO `interest` VALUES ('39', '43', '1', '0', '1', '0', '1', '1', '0', '0');
INSERT INTO `interest` VALUES ('40', '44', '1', '1', '1', '1', '1', '1', '1', '1');
INSERT INTO `interest` VALUES ('41', '45', '1', '1', '1', '1', '1', '1', '1', '1');
INSERT INTO `interest` VALUES ('42', '46', '0', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `interest` VALUES ('43', '47', '1', '0', '0', '0', '0', '0', '0', '0');

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
  `record_time` varchar(20) DEFAULT NULL,
  `record_distance` float DEFAULT NULL,
  `record_speed` float DEFAULT NULL,
  `record_date` datetime DEFAULT NULL,
  `record_points` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES ('1', '46', '16', '5', '2', '2019-12-17 14:41:48', '6');

-- ----------------------------
-- Table structure for `reply_msg`
-- ----------------------------
DROP TABLE IF EXISTS `reply_msg`;
CREATE TABLE `reply_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) DEFAULT NULL,
  `from_id` int(11) DEFAULT NULL,
  `to_id` int(11) DEFAULT NULL,
  `reply_content` varchar(255) DEFAULT NULL,
  `reply_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply_msg
-- ----------------------------
INSERT INTO `reply_msg` VALUES ('1', '2', '46', '46', '自己回复自己', '2020-04-20 14:36:55');
INSERT INTO `reply_msg` VALUES ('2', '1', '46', '46', '今天天气真好', '2020-04-28 19:04:13');
INSERT INTO `reply_msg` VALUES ('3', '4', '46', '46', '今天天气不好', '2020-04-28 19:04:59');
INSERT INTO `reply_msg` VALUES ('4', '3', '46', '46', '哎呀哎呀', '2020-04-28 19:11:32');

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
  `user_school` varchar(50) DEFAULT NULL,
  `user_sno` varchar(20) DEFAULT NULL,
  `identify_image` varchar(255) DEFAULT NULL,
  `user_points` int(5) DEFAULT '0',
  `is_accreditation` smallint(6) DEFAULT '0',
  `longitude` double(50,16) DEFAULT '0.0000000000000000',
  `latitude` double(50,16) DEFAULT '0.0000000000000000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('46', '溜溜', 'aa', 'e10adc3949ba59abbe56e057f20f883e', 'woman', '904569030@qq.com', '2020-04-30 15:13:16', '河北工业大学', '2017011856', 'x56N6e.jpg;uWSQfB.jpg', '0', '1', '0.0000000000000000', '0.0000000000000000');
INSERT INTO `user` VALUES ('47', '哈哈', '哈哈', 'e10adc3949ba59abbe56e057f20f883e', 'man', '156267102@qq.com', '2020-04-30 17:13:16', '河北师范大学操场', '2017011897', 'R5RhkQ.jpg;7h9sP8.jpg', '0', '0', '0.0000000000000000', '0.0000000000000000');
