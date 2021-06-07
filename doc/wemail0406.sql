/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : wemail

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 06/04/2020 13:58:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment`  (
  `mail_id` int(11) NOT NULL,
  `attach_order` int(11) NOT NULL,
  `file_id` int(11) NOT NULL,
  PRIMARY KEY (`mail_id`, `attach_order`) USING BTREE,
  INDEX `FK_attach`(`file_id`) USING BTREE,
  CONSTRAINT `FK_attach` FOREIGN KEY (`file_id`) REFERENCES `file` (`file_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_contain` FOREIGN KEY (`mail_id`) REFERENCES `mail` (`mail_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blacklist_account
-- ----------------------------
DROP TABLE IF EXISTS `blacklist_account`;
CREATE TABLE `blacklist_account`  (
  `host_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enemy_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`host_account`, `enemy_account`) USING BTREE,
  INDEX `FK_ANAMY`(`enemy_account`) USING BTREE,
  CONSTRAINT `FK_ANAMY` FOREIGN KEY (`enemy_account`) REFERENCES `user` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_HOST` FOREIGN KEY (`host_account`) REFERENCES `user` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blacklist_ip
-- ----------------------------
DROP TABLE IF EXISTS `blacklist_ip`;
CREATE TABLE `blacklist_ip`  (
  `host_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enemy_IP` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`host_account`, `enemy_IP`) USING BTREE,
  CONSTRAINT `FK_MAIN_ACCOUNT` FOREIGN KEY (`host_account`) REFERENCES `user` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact`  (
  `con_id` int(11) NOT NULL AUTO_INCREMENT,
  `host_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `friend_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`con_id`) USING BTREE,
  INDEX `FK_friend_account`(`friend_account`) USING BTREE,
  INDEX `FK_host_account`(`host_account`) USING BTREE,
  CONSTRAINT `FK_friend_account` FOREIGN KEY (`friend_account`) REFERENCES `user` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_host_account` FOREIGN KEY (`host_account`) REFERENCES `user` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ͨѶ¼' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `file_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_id` int(11) NOT NULL,
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mail
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail`  (
  `mail_id` int(11) NOT NULL,
  `sender_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `receiver_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `date_time` datetime(0) NOT NULL,
  `subject` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mail_state` int(11) NOT NULL,
  `size` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`mail_id`) USING BTREE,
  INDEX `FK_recever_account`(`receiver_account`) USING BTREE,
  INDEX `FK_sender_account`(`sender_account`) USING BTREE,
  CONSTRAINT `FK_recever_account` FOREIGN KEY (`receiver_account`) REFERENCES `user` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_sender_account` FOREIGN KEY (`sender_account`) REFERENCES `user` (`account`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for parameter
-- ----------------------------
DROP TABLE IF EXISTS `parameter`;
CREATE TABLE `parameter`  (
  `SMTP_state` int(11) NOT NULL,
  `SMTP_port` int(11) NOT NULL DEFAULT 25,
  `POP3_state` int(11) NOT NULL,
  `POP3_port` int(11) NOT NULL DEFAULT 110,
  `domain_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'test.com'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` int(11) NULL DEFAULT 1,
  `smtpstate` int(11) NULL DEFAULT NULL,
  `pop3state` int(11) NULL DEFAULT NULL,
  `usedsize` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('乔', 'qiao@wemail.com', 'qiao', 1, 1, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
