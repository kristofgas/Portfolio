// /utils/weatherUtils.js

const axios = require('axios');
const { client } = require('../redisClient');
const config = require('../config');
const logger = require('./logger'); // Import the logger

const getAsync = async (key) => {
  const data = await client.get(key);
  return data;
};

const incrementApiCounter = async () => {
  await client.incr('api_counter');
};

const checkApiLimit = async () => {
  const counter = await client.get('api_counter');
  if (counter >= 10000) {
    throw new Error('API limit reached');
  }
};

const fetchWeatherData = async (cityId, unit) => {
  try {
    await checkApiLimit(); // Check the API limit before making a request

    const redisKey = `${cityId}-${unit}`;
    const cachedData = await getAsync(redisKey);

    if (cachedData) {
      logger.info(`Cache hit for city ID ${cityId} with unit ${unit}.`); // Log cache hit
      return JSON.parse(cachedData);
    }

    const url = `${config.baseWeatherUrl}?id=${cityId}&units=${unit}&appid=${config.openWeatherApiKey}`;
    const response = await axios.get(url);
    // 10800 is used, as data seems to be updated every 3 hours in the openWeatherApi
    await client.set(redisKey, JSON.stringify(response.data), 'EX', 3600);
    await incrementApiCounter(); // Increment the counter after a successful API call

    logger.info(`Fetched and cached weather data for city ID ${cityId} with unit ${unit}.`); // Log successful fetch and caching
    return response.data;
  } catch (error) {
    logger.error(`Error fetching weather data for city ID ${cityId}: ${error.message}`); // Log error
    throw error;
  }
};

module.exports = {
  fetchWeatherData,
};