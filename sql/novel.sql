/*
Navicat MySQL Data Transfer

Source Server         : 193.112.34.230
Source Server Version : 50641
Source Host           : 193.112.34.230:3306
Source Database       : novel

Target Server Type    : MYSQL
Target Server Version : 50641
File Encoding         : 65001

Date: 2018-10-11 23:46:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `categoryId` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(20) NOT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('0', '推荐');
INSERT INTO `category` VALUES ('1', '修真');
INSERT INTO `category` VALUES ('2', '都市');
INSERT INTO `category` VALUES ('3', '穿越');
INSERT INTO `category` VALUES ('4', '玄幻');
INSERT INTO `category` VALUES ('5', '科幻');
INSERT INTO `category` VALUES ('6', '网游');

-- ----------------------------
-- Table structure for chapter
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节Id',
  `novelId` int(11) NOT NULL,
  `chapter` varchar(40) NOT NULL,
  `content` text COMMENT '小说内容',
  `chapterUrl` varchar(100) NOT NULL,
  PRIMARY KEY (`chapterId`,`novelId`),
  KEY `novelId` (`novelId`) USING BTREE,
  KEY `chapterId` (`chapterId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter
-- ----------------------------

-- ----------------------------
-- Table structure for chapter0
-- ----------------------------
DROP TABLE IF EXISTS `chapter0`;
CREATE TABLE `chapter0` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节Id',
  `novelId` bigint(20) NOT NULL,
  `chapter` varchar(40) DEFAULT NULL,
  `content` text COMMENT '小说内容',
  `chapterUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chapterId`),
  KEY `novelId` (`novelId`) USING BTREE,
  KEY `chapterId` (`chapterId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter0
-- ----------------------------

-- ----------------------------
-- Table structure for chapter1
-- ----------------------------
DROP TABLE IF EXISTS `chapter1`;
CREATE TABLE `chapter1` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节Id',
  `novelId` bigint(20) NOT NULL,
  `chapter` varchar(40) DEFAULT NULL,
  `content` text COMMENT '小说内容',
  `chapterUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chapterId`),
  KEY `chapterId` (`chapterId`) USING BTREE,
  KEY `novelId` (`novelId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter1
-- ----------------------------

-- ----------------------------
-- Table structure for chapter2
-- ----------------------------
DROP TABLE IF EXISTS `chapter2`;
CREATE TABLE `chapter2` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节Id',
  `novelId` bigint(20) NOT NULL,
  `chapter` varchar(40) DEFAULT NULL,
  `content` text COMMENT '小说内容',
  `chapterUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chapterId`),
  KEY `novelId` (`novelId`) USING BTREE,
  KEY `chapterId` (`chapterId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter2
-- ----------------------------

-- ----------------------------
-- Table structure for chapter3
-- ----------------------------
DROP TABLE IF EXISTS `chapter3`;
CREATE TABLE `chapter3` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节Id',
  `novelId` bigint(20) NOT NULL,
  `chapter` varchar(40) DEFAULT NULL,
  `content` text COMMENT '小说内容',
  `chapterUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chapterId`),
  KEY `novelId` (`novelId`) USING BTREE,
  KEY `chapterId` (`chapterId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter3
-- ----------------------------

-- ----------------------------
-- Table structure for chapter4
-- ----------------------------
DROP TABLE IF EXISTS `chapter4`;
CREATE TABLE `chapter4` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节Id',
  `novelId` bigint(20) NOT NULL,
  `chapter` varchar(40) DEFAULT NULL,
  `content` text COMMENT '小说内容',
  `chapterUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chapterId`),
  KEY `chapterId` (`chapterId`) USING BTREE,
  KEY `novelId` (`novelId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter4
-- ----------------------------

-- ----------------------------
-- Table structure for chapter5
-- ----------------------------
DROP TABLE IF EXISTS `chapter5`;
CREATE TABLE `chapter5` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节Id',
  `novelId` bigint(20) NOT NULL,
  `chapter` varchar(40) DEFAULT NULL,
  `content` text COMMENT '小说内容',
  `chapterUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chapterId`),
  KEY `chapterId` (`chapterId`) USING BTREE,
  KEY `novelId` (`novelId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter5
-- ----------------------------

-- ----------------------------
-- Table structure for chapter6
-- ----------------------------
DROP TABLE IF EXISTS `chapter6`;
CREATE TABLE `chapter6` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节Id',
  `novelId` bigint(20) NOT NULL,
  `chapter` varchar(40) DEFAULT NULL,
  `content` text COMMENT '小说内容',
  `chapterUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chapterId`),
  KEY `chapterId` (`chapterId`) USING BTREE,
  KEY `novelId` (`novelId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter6
-- ----------------------------

-- ----------------------------
-- Table structure for chapter7
-- ----------------------------
DROP TABLE IF EXISTS `chapter7`;
CREATE TABLE `chapter7` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节Id',
  `novelId` bigint(20) NOT NULL,
  `chapter` varchar(40) DEFAULT NULL,
  `content` text COMMENT '小说内容',
  `chapterUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chapterId`),
  KEY `novelId` (`novelId`) USING BTREE,
  KEY `chapterId` (`chapterId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter7
-- ----------------------------

-- ----------------------------
-- Table structure for chapter8
-- ----------------------------
DROP TABLE IF EXISTS `chapter8`;
CREATE TABLE `chapter8` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节Id',
  `novelId` bigint(20) NOT NULL,
  `chapter` varchar(40) DEFAULT NULL,
  `content` text COMMENT '小说内容',
  `chapterUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chapterId`),
  KEY `chapterId` (`chapterId`) USING BTREE,
  KEY `novelId` (`novelId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter8
-- ----------------------------

-- ----------------------------
-- Table structure for chapter9
-- ----------------------------
DROP TABLE IF EXISTS `chapter9`;
CREATE TABLE `chapter9` (
  `chapterId` int(11) NOT NULL AUTO_INCREMENT COMMENT '章节Id',
  `novelId` bigint(20) NOT NULL,
  `chapter` varchar(40) DEFAULT NULL,
  `content` text COMMENT '小说内容',
  `chapterUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`chapterId`),
  KEY `chapterId` (`chapterId`) USING BTREE,
  KEY `novelId` (`novelId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter9
-- ----------------------------

-- ----------------------------
-- Table structure for novel
-- ----------------------------
DROP TABLE IF EXISTS `novel`;
CREATE TABLE `novel` (
  `novelId` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '小说id',
  `novelName` varchar(20) NOT NULL COMMENT '小说名字',
  `author` varchar(20) NOT NULL COMMENT '作者',
  `categoryId` int(20) NOT NULL COMMENT '小说类别id',
  `brief` text COMMENT '简介',
  `status` varchar(10) NOT NULL COMMENT '状态（连载/完结',
  `lastTime` varchar(30) NOT NULL COMMENT '最后更新时间',
  `novelUrl` varchar(100) NOT NULL COMMENT '小说链接',
  `chapterSize` int(10) unsigned zerofill NOT NULL COMMENT '用来显示这本小说多少章',
  PRIMARY KEY (`novelId`),
  KEY `category` (`categoryId`) USING BTREE,
  KEY `novelId` (`novelId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of novel
-- ----------------------------
