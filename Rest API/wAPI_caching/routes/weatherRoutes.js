const express = require('express');
const { getWeatherSummary, getCityWeather } = require('../controllers/weatherController');

const router = express.Router();

router.get('/summary', getWeatherSummary);
router.get('/cities/:city_id', getCityWeather);

module.exports = router;