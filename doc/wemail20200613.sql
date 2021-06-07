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

 Date: 13/06/2020 21:34:40
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
  INDEX `FK2`(`file_id`) USING BTREE,
  CONSTRAINT `FK1` FOREIGN KEY (`mail_id`) REFERENCES `mail` (`mail_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK2` FOREIGN KEY (`file_id`) REFERENCES `file` (`file_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
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
  CONSTRAINT `FK_ANAMY` FOREIGN KEY (`enemy_account`) REFERENCES `user` (`account`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_HOST` FOREIGN KEY (`host_account`) REFERENCES `user` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blacklist_account
-- ----------------------------
INSERT INTO `blacklist_account` VALUES ('1111', '1');
INSERT INTO `blacklist_account` VALUES ('miao', '111');
INSERT INTO `blacklist_account` VALUES ('miao', '2');
INSERT INTO `blacklist_account` VALUES ('qingshuang', 'miao');
INSERT INTO `blacklist_account` VALUES ('miao', 'qiao');

-- ----------------------------
-- Table structure for blacklist_ip
-- ----------------------------
DROP TABLE IF EXISTS `blacklist_ip`;
CREATE TABLE `blacklist_ip`  (
  `host_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enemy_IP` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`host_account`, `enemy_IP`) USING BTREE,
  CONSTRAINT `FK_MAIN_ACCOUNT` FOREIGN KEY (`host_account`) REFERENCES `user` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blacklist_ip
-- ----------------------------
INSERT INTO `blacklist_ip` VALUES ('miao', 'qiao@wemail.com');

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
  CONSTRAINT `FK_friend_account` FOREIGN KEY (`friend_account`) REFERENCES `user` (`account`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_host_account` FOREIGN KEY (`host_account`) REFERENCES `user` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ͨѶ¼' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contact
-- ----------------------------
INSERT INTO `contact` VALUES (4, '1111', '1');
INSERT INTO `contact` VALUES (5, '1111', '2');
INSERT INTO `contact` VALUES (7, '1', '1');
INSERT INTO `contact` VALUES (8, '1', '1111');
INSERT INTO `contact` VALUES (10, '1111', '1');
INSERT INTO `contact` VALUES (12, 'qingshuang', 'gaofei');

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `file_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mail
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail`  (
  `mail_id` int(11) NOT NULL AUTO_INCREMENT,
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
  CONSTRAINT `FK_recever_account` FOREIGN KEY (`receiver_account`) REFERENCES `user` (`account`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_sender_account` FOREIGN KEY (`sender_account`) REFERENCES `user` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mail
-- ----------------------------
INSERT INTO `mail` VALUES (5, 'miao', 'qiao', '2020-04-25 18:12:41', 'test7', 'Hello word', 2, 10);
INSERT INTO `mail` VALUES (6, 'miao', 'qiao', '2020-04-25 18:20:09', 'Test', 'Hello  Mr qiao\r\nI\'m miaozhihao', 2, 30);
INSERT INTO `mail` VALUES (7, 'qiao', 'miao', '2020-04-25 18:29:32', 'hhh', 'I have received!', 1, 16);
INSERT INTO `mail` VALUES (8, 'miao', 'qiao', '2020-04-25 18:49:47', 'test', 'kkkkkkk', 1, 7);
INSERT INTO `mail` VALUES (9, 'miao', 'qiao', '2020-04-25 18:49:51', ':test7', 'Hello word', 1, 10);
INSERT INTO `mail` VALUES (10, 'miao', 'qiao', '2020-04-25 18:49:51', ':Test', 'Hello  Mr qiaoI\'m miaozhihao', 1, 28);
INSERT INTO `mail` VALUES (11, 'miao', 'qiao', '2020-04-25 18:49:51', ':test', 'kkkkkkk', 1, 7);
INSERT INTO `mail` VALUES (12, 'miao', 'qiao', '2020-04-25 18:51:22', 'test', 'kkkkkkk', 1, 7);
INSERT INTO `mail` VALUES (13, 'miao', 'qiao', '2020-04-25 18:51:22', ':test', 'kkkkkkk', 1, 7);
INSERT INTO `mail` VALUES (22, 'hnu2021', 'hnu2021', '2020-04-25 22:07:28', 'hnu2021', 'hnu2021', 1, 7);
INSERT INTO `mail` VALUES (23, 'hnu2021', 'hnu2021', '2020-04-25 22:07:29', 'hnu2021', 'hnu2021', 1, 7);
INSERT INTO `mail` VALUES (24, 'miao', 'qiao', '2020-04-25 23:21:24', 'study', 'kkkkkkk', 1, 12);
INSERT INTO `mail` VALUES (25, 'miao', 'hnu2021', '2020-04-25 23:21:24', 'study', 'kkkkkkk', 1, 12);
INSERT INTO `mail` VALUES (26, 'miao', 'qiao', '2020-04-25 23:21:25', 'study', 'kkkkkkk', 1, 7);
INSERT INTO `mail` VALUES (27, 'miao', 'hnu2021', '2020-04-25 23:21:25', 'study', 'kkkkkkk', 1, 7);
INSERT INTO `mail` VALUES (28, 'miao', 'qiao', '2020-04-26 00:00:04', 'fffff', '111111', 1, 11);
INSERT INTO `mail` VALUES (29, 'miao', 'hnu2021', '2020-04-26 00:00:04', 'fffff', '111111', 1, 11);
INSERT INTO `mail` VALUES (30, 'miao', 'qiao', '2020-04-26 00:00:05', 'fffff', '111111', 1, 6);
INSERT INTO `mail` VALUES (31, 'miao', 'hnu2021', '2020-04-26 00:00:06', 'fffff', '111111', 1, 6);
INSERT INTO `mail` VALUES (32, 'miao', 'hnu2021', '2020-04-26 00:02:59', 'helloevery', 'higaofei', 1, 18);
INSERT INTO `mail` VALUES (33, 'miao', 'hnu2021', '2020-04-26 00:02:59', 'helloevery', 'higaofei', 1, 8);
INSERT INTO `mail` VALUES (34, 'miao', 'qiao', '2020-04-26 00:04:32', 'today', '11111', 1, 10);
INSERT INTO `mail` VALUES (35, 'miao', 'hnu2021', '2020-04-26 00:04:32', 'today', '11111', 1, 10);
INSERT INTO `mail` VALUES (36, 'miao', 'qiao', '2020-04-26 00:04:32', 'today', '11111', 1, 5);
INSERT INTO `mail` VALUES (37, 'miao', 'hnu2021', '2020-04-26 00:04:32', 'today', '11111', 1, 5);
INSERT INTO `mail` VALUES (38, 'miao', 'qiao', '2020-04-26 00:08:28', 'TEST1', '1111111', 1, 12);
INSERT INTO `mail` VALUES (39, 'miao', 'hnu2021', '2020-04-26 00:08:28', 'TEST1', '1111111', 1, 12);
INSERT INTO `mail` VALUES (40, 'miao', 'qiao', '2020-04-26 00:13:38', 'TEST1', '1111111', 1, 12);
INSERT INTO `mail` VALUES (41, 'miao', 'hnu2021', '2020-04-26 00:13:38', 'TEST1', '1111111', 1, 12);
INSERT INTO `mail` VALUES (45, 'miao', 'qiao', '2020-04-26 11:48:10', 'ggg', 'jjjjjjjj', 2, 11);
INSERT INTO `mail` VALUES (52, '123456', '2', '2020-04-26 13:28:21', '主题', '123456发给2的内容', 2, 14);
INSERT INTO `mail` VALUES (55, 'miao', '123456', '2020-05-31 16:14:15', '111', '1111', 1, 7);
INSERT INTO `mail` VALUES (58, 'miao', '123456', '2020-05-31 20:12:29', 'ceshi', 'hello world', 2, 16);
INSERT INTO `mail` VALUES (64, '2020company', 'miao', '2020-05-31 22:44:34', 'test', '123344', 1, 10);
INSERT INTO `mail` VALUES (65, '2020company', 'qiao', '2020-05-31 22:44:34', 'test', '123344', 1, 10);
INSERT INTO `mail` VALUES (66, 'miao', 'qiao', '2020-05-31 23:22:23', '11', '111', 1, 5);
INSERT INTO `mail` VALUES (67, 'miao', 'qiao', '2020-05-31 23:23:01', '111', '111', 1, 6);
INSERT INTO `mail` VALUES (68, 'miao', 'qiao', '2020-05-31 23:23:44', 'hhh', '1111', 2, 7);
INSERT INTO `mail` VALUES (69, 'miao', 'qiao', '2020-05-31 23:48:04', 'test', 'hello world', 1, 15);
INSERT INTO `mail` VALUES (70, 'miao', '111', '2020-05-31 23:48:04', 'test', 'hello world', 1, 15);
INSERT INTO `mail` VALUES (72, 'miao', '2020company', '2020-05-31 23:48:04', 'test', 'hello world', 1, 15);
INSERT INTO `mail` VALUES (73, 'miao', 'qiao', '2020-05-31 23:49:30', 'TEST', 'Hello World', 1, 15);
INSERT INTO `mail` VALUES (74, 'miao', '111', '2020-05-31 23:49:30', 'TEST', 'Hello World', 1, 15);
INSERT INTO `mail` VALUES (75, 'miao', '2020company', '2020-05-31 23:49:30', 'TEST', 'Hello World', 1, 15);
INSERT INTO `mail` VALUES (76, 'miao', 'qiao', '2020-06-01 01:13:45', 'æµè¯', 'Hello world', 1, 17);
INSERT INTO `mail` VALUES (77, 'miao', 'miao1', '2020-06-01 01:13:45', 'æµè¯', 'Hello world', 1, 17);
INSERT INTO `mail` VALUES (79, 'miao', 'qiao', '2020-06-01 01:15:35', 'subject', 'Hello!', 2, 13);
INSERT INTO `mail` VALUES (80, 'miao', 'miao1', '2020-06-01 01:15:35', 'subject', 'Hello!', 2, 13);
INSERT INTO `mail` VALUES (81, 'miao', '2020company', '2020-06-01 01:15:35', 'subject', 'Hello!', 1, 13);
INSERT INTO `mail` VALUES (82, 'miao', 'qiao', '2020-06-01 01:17:37', 'Test', 'Hello', 2, 9);
INSERT INTO `mail` VALUES (84, 'miao', '2020company', '2020-06-01 01:17:37', 'Test', 'Hello', 1, 9);
INSERT INTO `mail` VALUES (85, 'miao', 'qiao', '2020-06-01 07:40:06', 'TEST', 'Hello ', 2, 10);
INSERT INTO `mail` VALUES (86, 'miao', '2020company', '2020-06-01 07:40:06', 'TEST', 'Hello ', 1, 10);
INSERT INTO `mail` VALUES (87, 'miao', 'qiao', '2020-06-01 07:41:09', '1', '1', 0, 2);
INSERT INTO `mail` VALUES (88, '1', '1111', '2020-06-01 08:47:44', '1发给1111的主题', '你好啊', 1, 13);
INSERT INTO `mail` VALUES (89, '1111', '1', '2020-06-01 09:00:52', '1111回复1', 'text', 1, 11);
INSERT INTO `mail` VALUES (90, '1111', '1', '2020-06-01 09:40:34', '1111 to 1 from friend', '123', 1, 24);
INSERT INTO `mail` VALUES (91, '1111', '1', '2020-06-01 10:04:08', '1111给1快捷发送', '1111给1快捷发送', 1, 20);
INSERT INTO `mail` VALUES (92, '1', '1', '2020-06-01 10:57:51', ' ', ' ', 1, 2);
INSERT INTO `mail` VALUES (93, '1', '1111', '2020-06-01 11:03:26', ' ', ' ', 1, 2);
INSERT INTO `mail` VALUES (94, '1', '1', '2020-06-01 11:04:54', 'test1', 'hello', 1, 10);
INSERT INTO `mail` VALUES (95, 'gaofei', 'miao', '2020-06-01 13:48:18', 'test', 'testContent\n', 1, 16);
INSERT INTO `mail` VALUES (96, 'miao', 'gaofei', '2020-06-01 14:02:02', 'copy', 'copyThat\n', 1, 13);

-- ----------------------------
-- Table structure for parameter
-- ----------------------------
DROP TABLE IF EXISTS `parameter`;
CREATE TABLE `parameter`  (
  `SMTP_state` int(11) NOT NULL,
  `SMTP_port` int(11) NOT NULL DEFAULT 25,
  `POP3_state` int(11) NOT NULL,
  `POP3_port` int(11) NOT NULL DEFAULT 110,
  `domain_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'test.com',
  `user_size` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parameter
-- ----------------------------
INSERT INTO `parameter` VALUES (1, 24, 1, 111, 'wemail.com', 104857601);

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
INSERT INTO `user` VALUES ('1', '1', '1', 2, 1, 1, 0);
INSERT INTO `user` VALUES ('111', '111', 'miaozhihao', 2, 1, 1, 0);
INSERT INTO `user` VALUES ('1111', '1111', '1111', 2, 1, 1, 0);
INSERT INTO `user` VALUES ('123456', '123456', '123456', 0, 1, 1, 59);
INSERT INTO `user` VALUES ('用户2', '2', '2', 1, 1, 1, 12);
INSERT INTO `user` VALUES ('2020company', '2020company', '123456789', 1, 1, 1, 10);
INSERT INTO `user` VALUES ('987654', '987654', '123456', 1, 1, 1, 0);
INSERT INTO `user` VALUES ('gao', 'gaofei', 'gaofei', 1, 1, 1, 0);
INSERT INTO `user` VALUES ('goodman', 'goodman', '1234567', 1, 1, 1, 0);
INSERT INTO `user` VALUES ('hnu2021', 'hnu2021', 'hnu2021', 1, 1, 1, 7);
INSERT INTO `user` VALUES ('志豪酱', 'miao', '123456', 2, 1, 1, 395);
INSERT INTO `user` VALUES ('miao1', 'miao1', '1234567', 1, 1, 1, 11);
INSERT INTO `user` VALUES ('my1919', 'my1919', '191954', 1, 1, 1, 0);
INSERT INTO `user` VALUES ('qiao', 'qiao', '123456', 1, 1, 1, 125);
INSERT INTO `user` VALUES ('qing', 'qingshuang', 'yeqingshuang', 1, 1, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
