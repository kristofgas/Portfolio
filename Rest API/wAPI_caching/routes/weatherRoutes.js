const express = require('express');
const router = express.Router();
const weatherController = require('../controllers/weatherController');

// Route for getting weather summary
router.get('/summary', weatherController.getWeatherSummary);

// Route for getting weather of a specific city
router.get('/cities/:city_id', weatherController.getCityWeather);

module.exports = router;