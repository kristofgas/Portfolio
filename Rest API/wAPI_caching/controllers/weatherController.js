const { fetchWeatherData } = require('../utils/weatherUtils');
const logger = require('../utils/logger');

const UNITS = {
  CELSIUS: 'metric',
  FAHRENHEIT: 'imperial',
  STANDARD: 'standard',
};

const handleClientError = (res, message) => {
  logger.warn(message);
  res.status(400).json({ error: message });
};

const handleServerError = (res, message) => {
  logger.error(message);
  res.status(500).json({ error: 'Internal server error!' });
};

const getWeatherSummary = async (req, res) => {
  try {
    const { unit, temperature, cities } = req.query;
    if (!unit || !Number(temperature) || !cities) {
      return handleClientError(res, 'Invalid parameters received for getWeatherSummary');
    }

    let tempUnit = UNITS[unit.toUpperCase()] || UNITS.STANDARD;
    const cityIds = cities.split(',');

    const weatherPromises = cityIds.map((cityId) => fetchWeatherData(cityId, tempUnit));
    const weatherData = await Promise.all(weatherPromises);

    const filteredCities = weatherData.filter(
      (cityData) => cityData.list.some((item) => item.main.temp > temperature)
    ).map((cityData) => ({ city: cityData.city.name }));

    logger.info('Successfully fetched filtered cities');
    res.json({ status: 'Success!', data: filteredCities });
  } catch (error) {
    handleServerError(res, `Error in getWeatherSummary: ${error}`);
  }
};

const getCityWeather = async (req, res) => {
  try {
    const { city_id } = req.params;
    if (!city_id) {
      return handleClientError(res, 'Invalid city_id parameter');
    }

    const cityWeather = await fetchWeatherData(city_id, UNITS.CELSIUS);

    if (!cityWeather || !cityWeather.list) {
      return handleClientError(res, `Data not found for city ID: ${city_id}`);
    }

    const cv = cityWeather.list.map((item) => ({
      date: item.dt_txt,
      temperature: item.main.temp,
    }));

    logger.info(`Successfully fetched weather for city ID: ${city_id}`);
    res.json({ status: 'Success!', data: cv });
  } catch (error) {
    handleServerError(res, `Error in getCityWeather: ${error}`);
  }
};

module.exports = {
  getWeatherSummary,
  getCityWeather,
};