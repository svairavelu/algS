package com.me.problems.leetcode;

public class LRUCache {

	private int capacity;
	private List cache;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		this.cache = empty;
	}

	public int get(int key) {
		return getAndTrunc(empty, cache, key);
	}

	public int getAndTrunc(List nodes, List list, int key) {
		if (nodes.length() >= capacity || list.length() == 0) {
			this.cache = nodes;
			return -1;
		}

		Node node = list.node();
		if (node.key == key) {
			this.cache = nodes.preAppend(node).link(list.next());
			return node.value;
		}

		return getAndTrunc(nodes.postAppend(node), list.next(), key);
	}

	public void set(int key, int value) {
		cache = set(empty, cache, key, value);
	}

	private List set(List pre, List list, int key, int value) {
		if (list == empty) {
			return pre.preAppend(new Node(key, value));
		} else {
			Node node = list.node();
			if (node.key == key) {
				node.value = value;
				return pre.preAppend(node).link(list.next());
			} else {
				return set(pre.postAppend(node), list.next(), key, value);
			}
		}
	}

	class Node {
		final int key;
		int value;

		public Node(int key, int value) {
			this.key = key;
			this.value = value;
		}

		public String toString() {
			return "[" + key + ":" + value + "]";
		}
	}

	interface List {
		public List preAppend(Node node);

		public List postAppend(Node node);

		public List link(List list);

		public List next();

		public Node node();

		public int length();
	}

	static class NonEmpty implements List {

		public final Node node;
		final List next;

		public NonEmpty(Node node, List next) {
			this.node = node;
			this.next = next;
		}

		@Override
		public List preAppend(Node node) {
			return new NonEmpty(node, this);
		}

		public List postAppend(Node node) {
			return new NonEmpty(this.node, next.postAppend(node));
		}

		@Override
		public List link(List list) {
			return new NonEmpty(node, next.link(list));
		}

		@Override
		public List next() {
			return next;
		}

		public Node node() {
			return node;
		}

		@Override
		public int length() {
			return 1 + next.length();
		}

		@Override
		public String toString() {
			return node.toString() + "->" + next.toString();
		}

	}

	public static final Empty empty = new Empty();

	static class Empty implements List {

		private Empty() {

		}

		@Override
		public List preAppend(Node node) {
			return new NonEmpty(node, empty);
		}

		public List postAppend(Node node) {
			return new NonEmpty(node, empty);
		}

		@Override
		public List link(List list) {
			return list;
		}

		@Override
		public List next() {
			throw new UnsupportedOperationException("can't call next on Empty");
		}

		@Override
		public Node node() {
			throw new UnsupportedOperationException("can't call next on Empty");
		}

		@Override
		public int length() {
			return 0;
		}

		@Override
		public String toString() {
			return ".";
		}

	}

	public static void main(String[] args) {
		LRUCache cache = new LRUCache(512);
		String operations = "set(195,835),get(494),set(301,162),get(36),get(444),set(138,1088),set(200,1138),get(335),get(263),get(26),get(527),set(337,98),set(608,746),get(158),set(460,1226),get(181),get(31),set(178,86),set(71,1378),set(584,362),set(516,783),set(230,172),set(302,228),set(654,1416),get(217),set(264,1118),get(370),set(7,228),get(418),set(199,1281),set(56,1091),get(18),get(551),set(512,1014),get(459),set(59,1491),get(612),get(64),set(424,478),get(68),get(148),get(167),get(37),set(451,237),set(100,649),get(19),get(648),get(641),set(505,500),get(505),set(432,1286),get(225),set(1,533),get(114),get(9),set(257,585),set(467,629),set(455,1348),set(75,378),set(473,1313),get(101),get(78),set(221,1474),set(435,344),get(542),set(433,161),set(387,548),get(377),set(408,974),get(106),get(256),set(208,1105),set(426,731),set(98,1247),get(298),get(283),set(467,42),set(263,277),set(415,367),set(197,780),get(425),get(428),set(463,254),get(622),set(546,745),set(383,398),set(577,860),get(335),get(119),set(311,407),get(343),set(325,1380),get(571),get(260),set(373,72),set(509,203),set(189,583),get(320),get(490),set(232,1374),set(296,1303),set(135,572),get(7),set(102,755),set(238,361),set(442,1152),get(5),set(361,1531),set(76,892),get(129),set(649,1056),get(33),get(233),get(322),get(525),set(403,336),set(342,621),set(660,1374),set(158,69),get(183),set(250,630),get(321),get(33),set(283,88),get(479),get(595),get(598),set(443,1013),set(322,955),set(363,127),get(423),set(546,1220),set(45,19),set(228,83),get(364),set(389,565),get(108),set(236,877),get(6),get(426),set(589,1487),get(478),set(258,957),set(187,1447),set(582,1010),set(296,583),set(87,633),set(198,907),get(414),set(514,945),get(590),get(458),set(536,166),get(4),set(579,810),set(452,237),set(251,925),set(436,1336),set(524,957),get(178),get(329),get(170),set(221,539),set(43,1300),set(439,475),set(425,576),set(183,1437),set(206,832),set(329,863),get(429),get(630),set(362,785),set(637,117),get(623),set(11,215),get(420),get(529),get(209),set(336,161),set(601,918),get(349),get(573),set(551,163),set(460,54),set(513,508),set(596,1153),set(535,409),set(190,752),get(550),set(533,888),set(461,155),set(507,623),get(296),set(363,48),set(473,376),get(59),get(293),set(357,650),set(288,356),set(30,125),set(631,1090),get(465),get(20),get(578),set(652,527),set(326,930),get(21),set(111,251),set(132,817),set(133,1118),set(408,680),get(244),get(266),get(458),set(517,72),get(128),set(643,701),get(409),set(124,1183),set(189,814),set(585,25),set(611,365),set(167,118),set(171,1189),get(583),set(172,93),set(619,393),get(424),get(349),get(163),get(289),set(560,1268),get(337),get(554),set(176,285),set(119,1282),get(239),get(646),set(241,793),set(284,1409),set(491,259),get(604),get(593),get(214),get(272),get(20),get(126),set(371,48),set(251,780),get(54),set(529,1021),set(211,1016),set(511,142),get(120),set(584,976),set(130,218),get(106),set(568,212),set(115,60),set(84,251),set(610,1115),set(500,1329),get(226),get(359),set(159,853),get(260),set(552,202),set(222,189),set(369,510),get(479),set(209,810),get(603),set(454,643),set(422,914),get(54),get(243),get(273),get(24),get(113),get(400),get(134),set(529,1137),set(329,678),set(535,498),set(583,958),set(547,357),get(247),get(373),get(345),get(329),get(664),set(4,117),set(539,780),get(501),set(426,1),set(201,1427),get(648),get(652),set(283,407),get(318),set(440,720),set(141,1426),get(139),get(228),get(110),get(525),set(199,471),get(401),set(368,533),set(30,1272),set(406,1208),set(370,1390),get(217),set(116,1237),get(321),set(486,827),get(30),get(423),set(162,511),get(363),set(221,714),set(433,328),set(159,852),get(563),get(39),get(61),set(348,1532),get(67),get(12),set(54,1004),set(142,1502),get(280),set(622,476),get(93),get(40),get(357),get(649),set(361,441),set(256,465),set(217,1058),get(213),set(606,433),set(448,426),get(174),set(449,607),set(84,843),set(564,1254),set(46,1162),set(176,657),set(618,221),get(373),get(437),set(436,466),get(485),get(130),set(14,1136),set(467,1376),get(243),set(49,610),set(260,927),set(299,1337),get(425),get(150),set(369,1408),set(294,247),get(636),get(59),set(361,937),set(485,1275),set(272,853),get(18),get(228),get(132),set(81,351),get(141),set(43,657),set(113,1229),get(361),set(520,1104),set(346,1194),get(605),get(545),get(277),get(417),get(45),get(8),get(210),get(574),get(340),get(95),set(363,1466),set(387,872),get(96),set(516,792),get(474),set(200,643),get(70),set(284,135),set(224,723),set(198,688),get(381),get(17),set(582,77),set(398,724),set(594,418),get(548),get(232),get(422),set(294,1060),set(238,571),set(222,1400),set(312,248),get(56),set(495,1262),set(47,328),get(33),set(406,1522),get(126),get(262),set(403,986),set(214,284),get(454),set(563,398),get(286),set(150,891),set(313,1113),get(81),get(531),get(160),get(8),get(236),set(389,1258),set(104,586),get(101),set(124,641),get(616),get(70),set(241,921),get(485),set(88,1058),get(448),set(229,1342),set(635,561),get(570),set(3,266),get(245),set(579,399),get(598),set(643,1309),get(581),set(179,198),get(374),get(486),get(337),get(87),set(59,584),get(397),get(80),set(422,1331),get(96),set(627,24),get(516),get(369),set(188,43),set(325,303),get(181),get(346),set(379,463),get(176),get(113),set(580,110),set(57,218),get(114),set(530,166),set(464,1524),set(377,1342),get(487),set(230,829),set(642,578),set(193,663),set(74,1338),get(502),get(520),get(17),get(573),get(127),set(51,934),set(291,1491),set(154,1412),set(609,359),get(658),set(320,27),get(392),set(649,10),set(197,1199),get(156),set(366,13),set(315,126),get(614),get(59),set(436,549),set(155,1118),get(42),set(64,1027),set(123,359),set(510,955),set(643,370),set(9,505),set(577,110),get(78),get(594),get(151),set(573,1266),set(369,122),get(506),get(580),get(94),set(451,572),set(460,154),get(381),set(244,654),set(481,297),set(119,650),get(259),set(471,311),get(115),set(598,1237),set(41,973),set(510,82),set(448,701),set(22,49),set(579,1138),get(328),set(75,1134),set(632,725),set(139,706),set(101,1406),get(520),get(105),get(11),set(214,392),get(574),set(116,1398),get(532),get(559),set(289,34),get(184),get(655),set(613,1532),set(490,1121),get(34),get(227),get(423),get(395),set(572,879),get(655),set(654,726),set(499,812),get(72),set(334,272),get(494),get(385),get(359),get(478),get(199),set(413,1071),get(173),get(451),get(531),set(607,1535),get(212),get(541),get(508),get(289),set(99,1386),set(73,1285),set(567,288),set(657,1137),set(144,1498),set(184,241),set(473,1243),get(182),get(584),set(71,1337),set(377,995),set(179,470),set(590,1211),set(15,573),get(35),get(424),get(498),get(570),get(412),set(439,161),get(453),set(261,475),set(265,1169),set(599,1111),set(549,1068),set(593,328),get(245),set(459,295),set(285,958),get(187),get(546),get(128),get(247),set(661,1275),set(423,1280),set(411,533),set(649,936),get(199),get(627),set(374,38),get(252),get(121),set(629,625),set(261,770),set(501,1048),set(403,725),set(412,249),set(642,872),set(213,538),set(408,141),get(142),get(514),get(500),set(486,85),get(344),get(158),get(614),get(589),set(647,1456),set(621,495),set(574,1125),set(230,604),set(474,3),set(282,813),set(506,92),get(423),get(494),get(80),get(206),get(436),get(18),get(652),set(173,655),get(545),get(601),get(72),set(482,935),set(68,1032),set(136,47),set(375,881),set(34,693),get(644),get(309),set(139,1082),set(60,1255),get(631),set(238,612),set(300,90),get(7),get(518),set(96,456),set(536,801),get(659),get(351),set(584,160),get(388),get(158),get(99),set(603,812),get(200),get(121),set(259,437),set(155,199),get(339),get(25),set(57,999),set(387,1499),get(201),set(485,56),set(483,862),get(612),set(382,1327),get(15),set(465,746),get(156),set(70,825),set(497,96),get(584),get(544),set(187,832),set(523,316),get(337),get(304),get(533),get(294),get(22),get(24),set(460,72),get(233),set(459,974),set(414,1000),set(601,464),set(306,1200),get(364),set(371,681),set(633,430),get(54),get(453),set(334,878),get(29),set(62,803),get(464),get(616),get(456),set(409,889),set(60,1207),set(335,1479),get(26),set(526,732),get(350),set(318,893),get(478),set(130,157),set(178,584),set(241,294),set(266,548),set(38,62),get(276),set(1,972),set(331,434),get(480),get(92),get(482),get(21),get(648),set(242,588),set(438,922),set(126,250),get(149),set(559,372),get(468),get(272),get(650),set(129,490),set(590,549),set(67,471),set(153,24),get(42),set(262,921),set(181,504),set(242,1042),get(339),set(235,352),set(90,421),set(436,217),set(47,469),set(449,760),get(599),get(135),set(35,1221),set(148,1377),set(529,1273),get(501),set(42,255),set(430,1131),get(251),set(128,1011),get(514),set(399,446),set(663,529),get(326),get(197),get(647),set(342,1441),set(611,239),set(429,1397),set(146,1172),set(466,873),set(23,586),get(175),get(462),set(312,735),set(90,641),set(11,101),set(501,974),get(573),set(208,1495),set(518,1235),get(288),get(492),set(616,1094),get(352),set(15,402),set(305,607),get(603),set(640,1274),get(43),set(122,1271),set(86,70),set(532,109),set(325,143),get(587),get(207),set(559,1143),get(537),get(491),set(156,192),get(560),set(545,479),set(390,936),get(307),get(497),get(567),set(571,655),set(422,1050),get(35),get(162),get(585),get(173),set(198,1062),set(636,726),set(552,1048),set(424,1535),set(138,828),set(28,602),get(126),set(498,79),get(428),get(143),get(218),get(94),set(616,156),set(82,138),get(30),set(354,186),set(134,971),set(102,575),get(464),get(113),get(322),get(33),set(507,823),set(655,442),get(67),get(407),set(190,260),get(315),set(470,953),get(603),set(420,448),get(348),get(365),set(548,367),set(226,741),set(286,81),set(367,805),set(37,722),set(527,348),set(517,1407),get(177),set(119,300),set(25,1234),set(296,459),get(3),set(43,886),set(79,684),set(638,388),get(284),set(168,353),set(124,1171),set(89,436),get(596),set(280,1109),set(142,651),set(400,446),get(650),set(535,326),get(247),set(414,867),set(122,1421),get(428),set(538,1047),set(346,1441),set(481,1236),set(282,230),set(465,787),get(649),set(429,1120),set(218,772),set(291,116),set(359,1419),get(265),set(648,581),get(351),set(85,1099),set(16,1074),set(477,606),get(651),set(615,901),get(361),get(606),get(550),get(409),set(602,823),set(154,291),set(658,704),set(530,580),set(233,1396),set(403,891),set(555,839),set(47,460),set(458,347),get(599),get(67),set(597,493),set(217,868),set(290,63),get(9),get(398),get(311),get(313),get(454),get(369),set(636,81),get(114),get(256),get(432),get(125),get(145),set(584,540),get(390),get(313),set(382,497),get(512),get(273),set(552,755),set(367,1327),set(52,348),set(32,21),get(511),set(458,1496),set(583,611),set(528,134),set(253,1010),set(471,903),get(330),set(642,364),get(351),set(302,709),get(434),get(76),get(159),set(544,1492),get(104),get(560),get(228),get(266),get(187),set(66,693),set(483,1467),set(123,27),set(71,1221),set(319,587),set(653,1350),set(498,931),get(281),set(346,919),get(299),set(109,634),set(266,1291),set(207,1256),get(509),set(435,595),set(426,28),get(658),set(329,599),get(644),set(236,1470),set(13,1291),set(627,2),set(76,350),get(123),get(72),set(129,567),set(175,608),set(432,1279),get(87),set(291,1506),get(503),get(149),get(599),get(183),get(590),set(9,380),get(169),set(74,1133),set(220,1088),get(322),set(585,837),get(224),set(186,1353),get(444),set(161,1351),set(419,1064),set(584,1495),get(202),get(261),get(623),get(543),set(370,359),get(361),get(599),get(565),set(631,286),get(622),set(324,912),set(146,199),get(38),set(230,652),set(252,699),set(409,10),set(81,1161),set(623,996),get(103),set(149,721),get(442),get(58),set(525,1145),set(217,320),set(186,1525),get(35),get(224),set(111,1008),get(470),set(314,948),set(12,1435),set(373,605),get(531),get(622),set(187,687),set(570,986),set(516,97),set(187,1410),set(129,725),get(342),set(542,110),set(643,1295),get(427),set(423,1076),get(225),set(192,1497),get(45),set(568,691),get(460),set(154,1035),get(639),set(116,759),get(46),get(400),get(497),set(264,224),get(2),set(649,970),set(593,1501),set(345,376),get(514),set(663,1106),set(160,1147),get(568),get(564),get(166),get(323),set(32,1270),get(205),set(154,1458),set(20,1114),get(489),set(29,741),get(461),get(53),set(573,367),set(130,1388),get(575),get(480),get(463),get(247),set(576,804),get(310),set(81,251),get(323),get(126),get(168),set(80,289),set(6,514),get(245),set(334,1301),set(652,908),get(439),get(72),get(457),set(636,1006),set(69,647),set(224,811),set(216,587),set(44,676),set(398,70),set(370,1522),get(583),set(570,887),set(157,752),get(359),set(506,528),get(353),set(368,61),get(37),set(117,848),get(574),set(138,876),set(469,1353),set(297,107),set(201,137),get(519),get(544),get(180),set(132,1358),get(410),set(123,1494),get(520),set(20,1468),set(401,387),set(150,840),set(415,1127),set(489,62),set(553,1019),set(264,357),set(179,1520),set(604,1032),get(7),get(492),set(79,566),get(460),set(145,91),set(241,1293),get(306),get(364),get(391),set(104,565),set(242,412),set(553,599),set(377,416),get(278),get(631),get(596),set(2,1447),get(5),get(602),set(207,990),get(309),get(314),set(436,126),set(145,685),set(88,368),set(371,922),set(659,320),get(472),set(546,1164),set(52,797),set(481,226),set(315,898),get(590),get(162),set(589,975),set(204,305),set(114,353),get(186),get(52),set(222,62),get(377),set(266,147),set(651,540),set(371,96),set(183,1011),get(385),get(213),set(536,1017),get(131),get(574),get(414),set(490,243),set(370,53),set(640,1063),get(416),get(264),set(153,916),set(556,278),set(462,1122),get(457),get(637),get(20),set(176,601),get(140),get(357),set(560,330),set(548,1103),set(127,811),get(47),get(337),set(528,753),set(225,1089),set(58,1536),set(595,331),get(457),get(531),get(265),get(87),get(41),set(432,452),get(357),set(129,770),set(243,866),set(296,85),get(186),set(624,885),get(273),set(103,853),get(382),set(315,1006),get(218),set(567,605),get(51),get(317),set(583,1255),set(556,1254),get(311),set(325,1018),get(261),get(113),set(574,569),set(88,1214),set(422,746),set(374,934),get(11),set(248,484),set(383,90),set(209,195),get(394),set(122,1452),set(581,1415),set(524,811),set(59,933),set(642,1437),set(662,435),set(358,261),get(208),set(264,236),set(368,1368),get(58),set(146,475),get(62),set(452,523),get(210),get(105),set(89,1231),set(342,259),set(562,1426),set(448,657),get(612),set(561,669),set(344,1293),get(309),set(130,885),get(259),get(543),set(161,425),get(409),set(376,1513),set(142,396),get(594),set(508,928),set(457,1505),set(46,1252),set(64,1290),set(498,1224),set(30,123),get(354),set(50,349),set(358,637),set(625,233),set(258,117),set(567,308),set(222,735),set(333,54),set(361,451),set(547,188),set(145,250),set(120,667),set(464,694),get(119),get(596),get(150),set(582,524),set(212,794),get(33),get(480),get(199),set(33,444),get(224),get(81),get(84),get(72),get(622),get(461),set(617,26),get(456),set(262,351),get(599),get(244),get(108),get(184),set(92,347),get(492),set(160,1270),set(536,319),set(462,1499),set(329,1511),set(489,600),set(286,882),get(481),set(313,213),set(13,1),get(129),get(199),set(246,517),set(565,1239),set(160,617),get(100),set(519,119),set(5,450),set(118,1172),get(565),set(200,1160),set(198,104),set(499,481),set(359,520),set(120,956),get(71),get(233),set(524,362),get(631),get(75),get(449),get(651),get(42),set(640,1053),set(360,1303),get(309),set(77,341),set(228,1392),set(297,663),set(191,894),get(323),set(480,508),set(574,288),get(595),get(84),set(160,1243),set(249,780),set(447,1111),get(90),set(512,1037),get(92),get(51),set(515,540),set(643,938),get(112),set(617,104),set(246,612),set(376,777),set(665,166),set(592,613),set(180,1213),set(207,908),set(428,1197),get(150),set(430,527),set(509,1181),set(663,831),get(637),set(202,127),set(247,1434),get(414),set(2,721),get(178),set(484,818),set(579,1244),get(179),set(482,1129),get(206),set(346,1456),set(480,334),set(298,47),set(209,250),set(484,505),get(90),set(207,205),set(15,595),set(105,1326),get(245),get(446),set(380,276),set(533,1135),set(332,1165),set(196,1118),set(332,527),get(312),set(90,916),set(410,139),set(626,986),set(591,1506),get(102),set(481,856),set(89,209),set(120,1091),get(568),set(13,913),set(456,297),get(234),set(195,432),set(159,1486),get(535),set(250,737),set(91,29),set(399,1281),get(628),set(620,290),get(284),set(386,814),get(427),get(344),get(95),get(179),set(221,1033),set(197,1186),set(613,646),get(616),set(559,1399),set(371,1330),set(435,1530),set(483,132),set(372,403),get(578),set(56,1301),get(612),get(67),set(251,222),get(352),set(204,1280),set(150,15),get(296),set(619,397),set(339,1123),get(417),set(321,112),get(562),get(451),set(30,989),set(91,746),get(291),get(219),set(43,1059),set(526,553),set(274,241),set(162,172),set(234,746),get(457),get(333),set(650,856),get(541),get(647),set(93,1387),set(17,208),get(403),get(527),get(143),get(183),get(392),set(456,291),get(16),set(472,998),get(595),set(229,303),get(617),get(76),set(552,341),get(485),get(196),set(426,853),get(575),set(497,582),get(396),set(397,1531),get(154),set(203,767),set(630,838),set(173,1100),get(431),set(466,403),set(193,269),set(466,628),set(556,1054),get(400),set(372,1498),get(102),set(414,45),set(214,1474),set(213,553),set(142,520),set(507,531),get(337),set(15,1131),set(179,1415),set(328,1480),set(582,607),get(601),get(437),set(508,1446),get(342),get(267),set(125,148),get(331),set(463,1401),get(188),set(255,1405),set(549,1304),get(156),set(29,896),set(545,999),get(468),get(508),set(23,883),set(375,366),set(531,719),set(310,477),get(127),get(224),get(661),set(148,90),get(142),get(423),set(161,13),set(85,1301),get(232),set(114,101),set(565,506),get(441),get(621),get(300),set(577,1211),set(492,1351),get(258),set(610,1093),get(497),set(135,1309),get(417),set(648,449),set(429,90),set(239,1392),set(33,162),set(217,534),get(110),get(244),get(284),set(231,229),get(393),get(406),get(458),get(112),get(608),get(408),set(291,360),set(303,806),get(271),get(111),set(395,3),get(542),set(621,1153),get(257),get(11),get(141),set(308,851),get(252),get(170),set(517,649),set(397,200),get(537),get(547),set(220,704),set(36,8),set(113,1362),set(397,884),set(59,1328),get(332),get(449),get(220),set(558,1277),set(515,1505),set(468,993),get(659),set(570,212),get(659),set(624,103),set(233,1311),get(282),get(217),get(521),set(651,89),get(115),set(542,1203),set(513,1274),set(163,1402),get(562),set(171,752),set(269,1427),set(120,124),set(156,1412),set(659,273),set(27,430),get(187),set(171,1139),set(438,918),get(184),set(608,831),set(517,641),get(410),set(602,633),get(168),get(512),get(255),set(96,617),get(46),get(5),set(629,881),set(595,614),get(307),get(73),set(371,171),set(323,1193),set(246,216),set(500,1278),get(419),get(526),get(542),set(275,1418),set(407,598),set(564,873),set(11,1268),set(188,908),get(375),set(382,946),get(256),set(511,202),get(461),set(84,858),set(275,1036),set(116,1358),get(161),get(309),set(61,149),set(73,1181),get(235),get(629),set(91,1434),get(533),set(429,1331),get(627),set(80,801),set(202,104),set(503,199),get(365),get(477),get(40),get(201),get(354),get(381),set(193,1110),get(519),get(8),get(177),set(562,692),set(256,481),get(67),get(525),set(84,319),get(312),get(129),set(510,807),set(83,1526),set(41,1328),set(162,205),get(128),get(358),set(191,1433),set(191,558),set(440,214),set(321,947),set(640,672),set(417,1032),get(158),set(346,1019),set(139,718),set(613,616),get(385),set(173,421),set(38,590),set(519,524),get(572),get(8),get(39),get(369),get(588),get(214),get(466),get(56),get(114),set(65,381),set(614,1014),get(597),set(291,1491),get(664),set(390,837),get(375),set(447,203),set(599,982),get(559),set(48,1122),get(368),get(205),get(618),set(299,1357),set(170,968),get(451),set(36,1442),get(611),set(57,1433),set(595,942),get(52),get(445),get(91),get(351),set(603,826),get(280),get(261),set(86,627),set(412,534),set(238,941),set(575,570),set(23,1312),set(479,161),set(209,347),set(599,1252),set(591,336),set(338,970),get(139),get(184),set(462,418),get(246),set(142,383),get(533),set(77,851),set(25,1042),set(252,1470),get(160),set(154,754),set(482,565),set(156,125),set(653,126),get(119),set(48,1132),set(135,636),get(346),set(427,18),get(42),set(22,993),set(316,757),get(375),get(219),set(238,80),set(429,427),set(119,1245),set(180,1466),set(233,1399),set(571,1103),set(37,671),get(56),get(564),set(478,353),get(360),set(194,827),get(420),set(383,1446),set(207,1275),get(213),set(116,951),get(98),get(193),set(132,364),get(386),set(542,931),set(115,1445),get(333),get(28),set(65,979),set(522,1074),set(197,878),set(218,780),get(262),get(48),get(651),set(505,1529),set(343,184),set(284,715),set(579,1019),get(624),get(39),get(152),get(663),get(33),get(395),get(514),set(379,889),set(488,674),set(535,996),set(115,1316),set(405,1445),set(521,578),get(59),get(177),get(542),set(584,691),set(648,55),get(507),set(113,417),set(160,702),get(393),set(463,1249),get(152),get(300),set(443,1073),set(537,277),get(538),set(596,635),set(205,1285),get(531),set(533,149),set(204,1076),get(300),set(41,591),get(179),get(64),get(492),get(649),set(306,932),get(225),get(79),set(140,59),set(577,468),set(99,990),set(594,1398),get(489),set(636,585),get(346),get(323),set(560,531),set(417,697),set(176,292),get(407),set(635,1413),get(202),get(49),get(558),get(342),get(598),get(206),set(247,1013),set(655,425),get(72),set(196,1339),set(425,158),set(139,816),set(102,956),set(181,867),set(591,139),set(250,719),get(475),set(342,71),set(653,11),set(35,577),get(231),get(431),get(284),set(507,1235),get(26),get(60),get(229),get(423),get(614),set(332,359),set(407,140),get(105),set(20,636),set(74,739),set(544,1283),set(144,1273),set(215,295),get(3),set(453,873),set(614,132),set(249,10),get(649),set(33,376),set(188,787),set(590,1057),set(356,998),get(258),set(65,58),set(263,359),set(20,1166),set(319,885),set(373,612),set(432,764),set(84,523),get(463),get(523),set(8,196),get(454),get(83),get(561),set(76,384),set(115,1036),get(348),get(120),get(107),get(248),set(488,134),get(304),get(341),set(143,696),get(547),set(520,1042),set(368,1376),get(232),get(579),get(583),set(430,459),set(21,713),set(458,88),set(60,697),set(32,427),set(245,340),get(59),set(477,904),get(107),set(103,754),set(438,142),set(51,744),get(467),set(235,166),set(339,1219),set(621,1308),get(509),set(489,1091),get(183),set(108,95),set(492,1084),set(533,578),set(445,32),set(510,1488),set(347,965),get(68),get(470),set(382,941),set(429,1413),get(1),set(34,1428),set(292,1243),set(506,1137),get(305),set(6,64),get(115),get(420),set(45,292),get(218),set(642,412),set(165,1509),get(564),get(465),set(538,614),set(267,899),set(474,117),get(646),get(473),get(386),get(356),set(535,37),set(295,631),set(79,1414),set(612,1253),set(215,1352),set(21,1450),set(25,141),get(1),set(253,655),set(651,400),get(465),set(647,101))";
		while (operations.length() > 0) {
			char c = operations.charAt(0);
			if (c == 's') {
				operations = operations.substring(4); // skip (
				int idx = operations.indexOf(",");
				String a = operations.substring(0, idx);
				operations = operations.substring(idx + 1); // skip ,
				idx = operations.indexOf(")");
				String b = operations.substring(0, idx);
				cache.set(Integer.valueOf(a), Integer.valueOf(b));
			} else if (c == 'g') {
				operations = operations.substring(4);
				int idx = operations.indexOf(")");
				String a = operations.substring(0, idx);
				int b = cache.get(Integer.valueOf(a));
				System.out.println(b);
			} else {
				operations = operations.substring(1);
			}
		}
	}
}
