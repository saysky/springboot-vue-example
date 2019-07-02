/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50641
 Source Host           : localhost
 Source Database       : server_manager

 Target Server Type    : MySQL
 Target Server Version : 50641
 File Encoding         : utf-8

 Date: 07/02/2019 16:15:40 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `message`
-- ----------------------------
BEGIN;
INSERT INTO `message` VALUES ('28', 'read', '您为【111111】申请的服务器已到期，已自动释放！', '1', '1557418080004'), ('29', 'read', '您为【xxx项目】申请的服务器已到期，已自动释放！', '1', '1557418080004'), ('30', 'read', '您为【xxx项目】申请的服务器已到期，已自动释放！', '1', '1557418320009'), ('31', 'unread', '您为【ssss2】申请的服务器已到期，已自动释放！', '2', '1557564420001'), ('32', 'read', '您为【ssss2】申请的服务器已到期，已自动释放！', '1', '1557577140001'), ('33', 'unread', '您为【xxx】申请的服务器已到期，已自动释放！', '2', '1560315120006'), ('34', 'unread', '您为【失误失误】申请的服务器已到期，已自动释放！', '2', '1560315120006'), ('35', 'unread', '您为【测试环境】申请的服务器已到期，已自动释放！', '2', '1560315120006'), ('36', 'read', '您为【ssss2】申请的服务器已到期，已自动释放！', '1', '1560319320005'), ('37', 'deleted', '您为【xxxx】申请的服务器已到期，已自动释放！', '1', '1560319320005'), ('38', 'deleted', '您为【111111】申请的服务器已到期，已自动释放！', '1', '1560319320005'), ('39', 'deleted', '您为【xxx项目】申请的服务器已到期，已自动释放！', '1', '1560319320005'), ('41', 'read', '您为【11】申请的服务器已到期，已自动释放！', '1', '1560336840001'), ('42', 'read', '您为【111】申请的服务器已到期，已自动释放！', '1', '1560336904093'), ('43', 'read', '您为【11】申请的服务器已到期，已自动释放！', '1', '1560337260006'), ('44', 'read', '您为【222】申请的服务器已到期，已自动释放！', '1', '1560337260006'), ('45', 'unread', '您为【11111】申请的服务器已到期，已自动释放！', '1', '1560355200004'), ('46', 'unread', '您为【sss】申请的服务器已到期，已自动释放！', '1', '1560355200004'), ('47', 'unread', '您为【sw】申请的服务器已到期，已自动释放！', '1', '1560355200004');
COMMIT;

-- ----------------------------
--  Table structure for `server`
-- ----------------------------
DROP TABLE IF EXISTS `server`;
CREATE TABLE `server` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `apply_user_name` varchar(255) DEFAULT NULL,
  `server_name` varchar(255) DEFAULT NULL,
  `server_status` varchar(255) DEFAULT NULL,
  `apply_time` bigint(20) DEFAULT NULL,
  `release_time` bigint(20) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `server`
-- ----------------------------
BEGIN;
INSERT INTO `server` VALUES ('20', null, '3333', 'free', null, null, null), ('25', null, '2222222', 'free', null, null, null), ('26', null, '3333333', 'free', null, null, null), ('27', null, '1111', 'free', null, null, null), ('30', null, '222', 'free', null, null, null), ('33', 'admin', '555', 'used', '1560351444000', '1560960000000', '111'), ('34', null, '666', 'free', null, null, null), ('35', null, '11', 'free', null, null, null), ('36', null, '12', null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `user_pass` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'admin', '123456', 'admin'), ('2', 'zhangsan', '123456', 'user'), ('3', 'lisi', '123456', 'user'), ('4', 'wangwu', '123456', 'user');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
