/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.0.96-community-nt : Database - emall
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`emall` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `emall`;

/*Table structure for table `admins` */

DROP TABLE IF EXISTS `admins`;

CREATE TABLE `admins` (
  `id` int(11) NOT NULL auto_increment COMMENT 'ID',
  `username` varchar(255) default NULL COMMENT '用户名',
  `password` varchar(255) default NULL COMMENT '密码',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='管理员';

/*Data for the table `admins` */

insert  into `admins`(`id`,`username`,`password`) values (1,'admin','tuShOfiBrA8+br7ENrMS8A=='),(2,'rwj','HAMVRZRssPCADKqGjGWJtQ==');

/*Table structure for table `carts` */

DROP TABLE IF EXISTS `carts`;

CREATE TABLE `carts` (
  `id` int(11) NOT NULL auto_increment,
  `amount` int(11) NOT NULL default '0' COMMENT '数量',
  `good_id` int(11) NOT NULL COMMENT '商品ID',
  `user_id` int(11) NOT NULL default '0' COMMENT '用户ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='购物车';

/*Data for the table `carts` */

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` int(11) unsigned NOT NULL auto_increment COMMENT 'ID',
  `cover` varchar(255) default NULL COMMENT '封面',
  `name` varchar(255) default NULL COMMENT '名称',
  `intro` varchar(255) default NULL COMMENT '介绍',
  `spec` varchar(255) default NULL COMMENT '规格',
  `price` int(11) NOT NULL default '0' COMMENT '价格',
  `stock` int(11) NOT NULL default '0' COMMENT '库存',
  `sales` int(11) NOT NULL default '0' COMMENT '销量',
  `content` text COMMENT '详情',
  `type_id` int(11) NOT NULL default '0' COMMENT '分类ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品';

/*Data for the table `goods` */

insert  into `goods`(`id`,`cover`,`name`,`intro`,`spec`,`price`,`stock`,`sales`,`content`,`type_id`) values (40,'../upload/20200516130430018.jpg','仿古工艺品摆件 广西铜鼓 青铜器 中国特色礼品艺术品软装铜饰品','仿古工艺品摆件 广西铜鼓 青铜器 中国特色礼品艺术品软装铜饰品','17*23*23',99,5,0,'',1),(41,'../upload/20200516130634926.jpg','广西少数民族手工艺术蜡染壁挂','广西少数民族手工艺术蜡染壁挂','90*56',66,4,1,'<img src=\"/upload/20200516130629327.jpg\" alt=\"\" />',1),(42,'../upload/20200516130941827.jpg','广西桂林特产御苑漓江醉鱼零食阳朔啤酒鱼特色各地美食小吃香辣味','广西桂林特产御苑漓江醉鱼零食阳朔啤酒鱼特色各地美食小吃香辣味','包',25,98,2,'<img src=\"/upload/20200516130939539.jpg\" alt=\"\" />',3),(43,'../upload/20200516131200521.jpg','广西桂林特产桂花糕传统糕点板栗绿豆糕老年人食品适合吃的软零食','广西桂林特产桂花糕传统糕点板栗绿豆糕老年人食品适合吃的软零食','包',20,99,1,'<img src=\"/upload/20200516131153036.jpg\" alt=\"\" />',3),(44,'../upload/20200516131353394.jpg','广西桂林特产盛兴龙柚子酥218克传统糕点心软式包馅水果酥特价','广西桂林特产盛兴龙柚子酥218克传统糕点心软式包馅水果酥特价','包',10,99,1,'',3),(45,'../upload/20200516131638928.jpg','广西少数民族民俗风情 民间手工刺绣挂帘门帘 老绣花艺术品收藏品','广西少数民族民俗风情 民间手工刺绣挂帘门帘 老绣花艺术品收藏品','99*99',100,100,0,'<img src=\"/upload/20200516131637620.jpg\" alt=\"\" />',1),(46,'../upload/20200516131754496.jpg','桂林大字牌 湖南跑胡子 81张棍棍 长牌纸牌 三A牌 煨胡子 二七十','桂林大字牌 湖南跑胡子 81张棍棍 长牌纸牌 三A牌 煨胡子 二七十','副',10,98,2,'<img src=\"/upload/20200516131752603.jpg\" alt=\"\" />',5),(47,'../upload/20200516132124752.jpg','桂林旅游阳朔漓江竹筏漂流一日游纯玩杨堤到兴坪银子岩游船票小团','桂林旅游阳朔漓江竹筏漂流一日游纯玩杨堤到兴坪银子岩游船票小团','位',288,50,0,'<img src=\"/upload/20200516132107793.jpg\" alt=\"\" /><img src=\"/upload/20200516132113769.jpg\" alt=\"\" /><img src=\"/upload/20200516132120897.gif\" alt=\"\" />',2),(48,'../upload/20200516132233743.jpg','可阳朔往返接送 燕莎热气球滑翔伞飞行体验 遇龙河桂林旅游门票','可阳朔往返接送 燕莎热气球滑翔伞飞行体验 遇龙河桂林旅游门票','位',99,50,0,'<img src=\"/upload/20200516132225723.jpg\" alt=\"\" /><img src=\"/upload/20200516132232047.jpg\" alt=\"\" />',2),(49,'../upload/20210808111120457.jpg','发','发扣','?',555,100,0,'',2),(50,'../upload/20210808111057341.jpg','方法','福建安徽','?',366,50,0,'',2),(51,'../upload/20210808111020720.jpg','发发','发卡机','?',999,89,11,'',2),(52,'../upload/20210808110958243.jpg','撒','发觉','?',100,99,1,'',2),(53,'../upload/20210808110943746.jpg','发掘','JFK拉萨','100*50',100,96,4,'',4),(54,'../upload/20210808110930293.jpg','负胡','发技术发','60*180',599,100,0,'',4),(55,'../upload/20210808110901930.jpg','合法化','零九分','90*200',298,50,0,'',4),(56,'../upload/20210808110849053.jpg','快了','发卡机','60*180',100,96,4,'',4),(57,'../upload/20210808110916128.jpg','发','夹发','?',99,94,6,'',3),(58,'../upload/20210808110835295.jpg','可乐','饮料','?',49,100,0,'',3),(59,'../upload/20210808110820104.png','安徽发货','夫拉进来','?',99,99,1,'',2),(60,'../upload/20210808110805074.png','哇哈哈','空拿','?',239,5,0,'<div id=\"page\">\r\n	<div id=\"content\">\r\n		<div id=\"detail\">\r\n			<div id=\"J_DetailMeta\" class=\"tm-detail-meta tm-clear\">\r\n				<div class=\"tm-clear\">\r\n					<div class=\"tb-property\">\r\n						<div class=\"tb-wrap\">\r\n							<div class=\"tb-detail-hd\">\r\n								<h1>\r\n									<br />\r\n								</h1>\r\n							</div>\r\n						</div>\r\n					</div>\r\n				</div>\r\n			</div>\r\n		</div>\r\n	</div>\r\n</div>',1),(61,'../upload/20210808110741806.jpg','饮料','饮料','2',108,91,9,'',1);

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `id` int(11) NOT NULL auto_increment COMMENT 'ID',
  `price` int(11) NOT NULL default '0' COMMENT '购买时价格',
  `amount` int(11) NOT NULL default '0' COMMENT '购买数量',
  `order_id` int(11) NOT NULL default '0' COMMENT '订单ID',
  `good_id` int(11) NOT NULL default '0' COMMENT '商品ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单项';

/*Data for the table `items` */

insert  into `items`(`id`,`price`,`amount`,`order_id`,`good_id`) values (58,10,1,46,46),(59,108,1,47,61),(60,999,1,48,51);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int(11) NOT NULL auto_increment COMMENT 'ID',
  `total` int(11) NOT NULL default '0' COMMENT '订单金额',
  `amount` int(11) NOT NULL default '0' COMMENT '商品总数',
  `status` tinyint(4) NOT NULL default '0' COMMENT '订单状态(1未付款/2已付款/3已发货/4已完成)',
  `paytype` tinyint(4) NOT NULL default '0' COMMENT '支付方式 (1微信/2支付宝/3积分)',
  `name` varchar(255) default NULL COMMENT '收货人',
  `phone` varchar(255) default NULL COMMENT '收货电话',
  `address` varchar(255) default NULL COMMENT '收货地址',
  `systime` datetime default NULL COMMENT '下单时间',
  `user_id` int(11) NOT NULL default '0' COMMENT '下单用户ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单';

/*Data for the table `orders` */

insert  into `orders`(`id`,`total`,`amount`,`status`,`paytype`,`name`,`phone`,`address`,`systime`,`user_id`) values (46,10,1,2,1,'3213','231123','13212','2021-08-03 17:01:56',3),(47,108,1,1,0,NULL,NULL,NULL,'2021-08-08 11:20:42',4),(48,999,1,2,1,'faf','178947891738','??','2021-08-08 11:21:26',4);

/*Table structure for table `tops` */

DROP TABLE IF EXISTS `tops`;

CREATE TABLE `tops` (
  `id` int(11) NOT NULL auto_increment,
  `type` tinyint(4) NOT NULL default '0' COMMENT '推荐类型(1今日推荐)',
  `good_id` int(11) NOT NULL default '0' COMMENT '商品ID',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='推荐商品';

/*Data for the table `tops` */

insert  into `tops`(`id`,`type`,`good_id`) values (20,1,40),(21,1,42),(22,1,41),(23,1,53),(24,1,57),(27,1,51),(29,1,61);

/*Table structure for table `types` */

DROP TABLE IF EXISTS `types`;

CREATE TABLE `types` (
  `id` int(11) NOT NULL auto_increment COMMENT 'ID',
  `name` varchar(50) default NULL COMMENT '名称',
  `num` int(11) default '0' COMMENT '排序号 (从小到大)',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='分类';

/*Data for the table `types` */

insert  into `types`(`id`,`name`,`num`) values (1,'饮料',1),(2,'玩具',2),(3,'文具',3),(4,'零食',4),(5,'学习用品',5);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL auto_increment COMMENT 'ID',
  `username` varchar(255) default NULL COMMENT '用户名',
  `password` varchar(255) default NULL COMMENT '密码',
  `name` varchar(255) default NULL COMMENT '收货人',
  `phone` varchar(255) default NULL COMMENT '收货电话',
  `address` varchar(255) default NULL COMMENT '收货地址',
  `point` double(11,2) unsigned zerofill default '00000000.00' COMMENT '积分',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户';

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`password`,`name`,`phone`,`address`,`point`) values (1,'user','R8FVKb/D6MgqaUITDhjuYQ==','','','',00000214.12),(2,'rwj','HAMVRZRssPCADKqGjGWJtQ==','','','',00000092.00),(3,'m','yJOXpxCuYkm+zIQrRHPdpQ==','3213','231123','13212',00000000.00),(4,'fa','yJOXpxCuYkm+zIQrRHPdpQ==','faf','178947891738','??',00000009.00);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
