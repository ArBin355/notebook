/*
 Navicat MySQL Data Transfer

 Source Server         : sshcrud
 Source Server Version : 50711
 Source Host           : localhost
 Source Database       : notebookswing

 Target Server Version : 50711
 File Encoding         : utf-8

 Date: 06/22/2016 18:06:14 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `_diary`
-- ----------------------------
DROP TABLE IF EXISTS `_diary`;
CREATE TABLE `_diary` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `date` varchar(32) NOT NULL,
  `weather` varchar(32) NOT NULL,
  `mood` varchar(32) NOT NULL,
  `title` varchar(64) NOT NULL,
  `content` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `_diary`
-- ----------------------------
BEGIN;
INSERT INTO `_diary` VALUES ('63d85d68-c999-441f-a62c-f36e301b54bd', 'a795eed1-9a74-4fa2-bf70-36758a6b569f', '2016-06-22 18:00', '晴', '好', '天天颓废撒点', '认为发生地方'), ('7f1afe9b-577c-4b14-abf1-a9bcc8b71790', 'a795eed1-9a74-4fa2-bf70-36758a6b569f', '2016-06-22 12:30', '晴', '差', 'test title 1', 'content 1'), ('9ac63aba-deda-44d6-b022-b6c8e659a725', 'a795eed1-9a74-4fa2-bf70-36758a6b569f', '2016-06-22 17:06', '阴', '好', '阴天了', '真不爽'), ('c56525a5-81d6-498a-9f56-1d64c228fa38', 'a795eed1-9a74-4fa2-bf70-36758a6b569f', '2016-06-22 17:03', '雨', '差', 'ttt', '今天心情很糟糕'), ('ec618ea0-0eaf-4b28-8c1f-a9e0390ff80f', 'a795eed1-9a74-4fa2-bf70-36758a6b569f', '2016-06-22 12:31', '晴', '好', 'title 2', 'content 2');
COMMIT;

-- ----------------------------
--  Table structure for `_user`
-- ----------------------------
DROP TABLE IF EXISTS `_user`;
CREATE TABLE `_user` (
  `id` varchar(36) NOT NULL,
  `name` varchar(32) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `nickname` varchar(32) NOT NULL,
  `question` varchar(128) NOT NULL,
  `answer` varchar(128) NOT NULL,
  `email` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `_user`
-- ----------------------------
BEGIN;
INSERT INTO `_user` VALUES ('a795eed1-9a74-4fa2-bf70-36758a6b569f', '111', '222', '111', '你父亲的名字', '111', '111'), ('adfe8abf-a382-442d-8128-e1372d45c7c7', '333', '333', '333', '你父亲的名字', '333', '333'), ('b4f69e90-1533-41f3-95a3-0e6dbbe232a8', '444', '444', '444', '你父亲的名字', '444', '444@qq.com');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
