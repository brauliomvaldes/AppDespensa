-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 30-06-2019 a las 07:33:55
-- Versión del servidor: 10.3.15-MariaDB
-- Versión de PHP: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `despensa`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulos`
--

CREATE TABLE `articulos` (
  `serie` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `valor` int(11) NOT NULL,
  `fecha` varchar(10) NOT NULL,
  `categoria` tinyint(4) NOT NULL,
  `origen` tinyint(4) NOT NULL,
  `run` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `articulos`
--

INSERT INTO `articulos` (`serie`, `nombre`, `valor`, `fecha`, `categoria`, `origen`, `run`) VALUES
('7809558102661', 'endulzan te daily', 2800, '2019-6-25', 5, 5, '22222'),
('7802640403103', 'mayonesa', 2000, '2019-6-25', 5, 5, '22222'),
('4003274001625', 'pepinillos', 3500, '2019-6-25', 5, 6, '22222'),
('7809558102661', 'endulzante', 3500, '2019-6-29', 5, 5, '123456');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `cantidad` int(11) NOT NULL,
  `nombre` int(11) NOT NULL,
  `precio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id` tinyint(11) NOT NULL,
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `nombre`) VALUES
(1, 'Bebestibles'),
(2, 'Carnes'),
(3, 'Conservas'),
(4, 'Lacteos'),
(5, 'No peresibles'),
(6, 'Peresibles');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

CREATE TABLE `ciudad` (
  `id` tinyint(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ciudad`
--

INSERT INTO `ciudad` (`id`, `nombre`) VALUES
(1, 'Santiago');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comuna`
--

CREATE TABLE `comuna` (
  `id` tinyint(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `comuna`
--

INSERT INTO `comuna` (`id`, `nombre`) VALUES
(1, 'Cerrillos'),
(2, 'La Reina'),
(3, 'Pudahuel'),
(4, 'Cerro Navia'),
(5, 'Las Condes'),
(6, 'Quilicura'),
(7, 'Conchalí'),
(8, 'Lo Barnechea'),
(9, 'Quinta Normal'),
(10, 'El Bosque'),
(11, 'Lo Espejo'),
(12, 'Recoleta'),
(13, 'Estación Central'),
(14, 'Lo Prado'),
(15, 'Renca'),
(16, 'Huechuraba'),
(17, 'Macul'),
(18, 'San Joaquín'),
(19, 'Independencia'),
(20, 'Maipú'),
(21, 'San Miguel'),
(22, 'La Cisterna'),
(23, 'Ñunoa'),
(24, 'San Ramón'),
(25, 'La Florida'),
(26, 'Pedro Aguirre Cerda'),
(27, 'Santiago'),
(28, 'La Granja'),
(29, 'Peñalolén'),
(30, 'Vitacura'),
(31, 'La Pintana'),
(32, 'Providencia'),
(33, 'Padre Hurtado'),
(34, 'San Bernardo'),
(35, 'Puente Alto'),
(36, 'Pirque'),
(37, 'San José de Maipo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `origen`
--

CREATE TABLE `origen` (
  `id` tinyint(11) NOT NULL,
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `origen`
--

INSERT INTO `origen` (`id`, `nombre`) VALUES
(1, 'Lider'),
(2, 'Jumbo'),
(3, 'Santa Isabel'),
(4, 'Unimarc'),
(5, 'Tottus'),
(6, 'Monserrat'),
(7, 'Mayorista'),
(8, 'aCuenta'),
(9, 'Ekono');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `run` varchar(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `comuna` tinyint(4) NOT NULL,
  `ciudad` tinyint(4) NOT NULL,
  `edad` tinyint(4) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`run`, `nombre`, `direccion`, `comuna`, `ciudad`, `edad`, `email`, `password`) VALUES
('10412', 'braulio', 'particular cada 6', 6, 1, 37, 'bra', 'bra'),
('12345', 'brau', 'casa', 9, 1, 53, 'bra', 'bra'),
('10000', 'braulio', 'casa', 8, 1, 60, 'bra', 'bra'),
('22222', 'braulio', 'particular 777', 7, 1, 7, 'bra', 'bra'),
('10412135', 'braulio', 'mi casa', 1, 1, 7, 'bra', 'bra'),
('104121357', 'braulio', 'casa part', 13, 0, 47, 'bra', 'bra'),
('1111111', 'braulio', 'particular', 6, 1, 20, 'bra', 'brs'),
('11111116', 'braulio', 'particular', 6, 1, 20, 'bra', 'brs'),
('123456', 'braulio', 'particular', 7, 1, 48, 'bra', 'bra');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
