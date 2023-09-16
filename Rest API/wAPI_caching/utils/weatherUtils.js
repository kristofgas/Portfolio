const axios = require('axios');
const { client, isClientReady } = require('../redisClient');

// Function to get value from Redis by key
const getAsync = (key) => {
  return new Promise((resolve, reject) => {
    if (client.connected && isClientReady) {
      client.get(key, (err, data) => {
        if (err) reject(err);
        resolve(data);
      });
    } else {
      console.error('Error in getAsync: Redis client is not connected');
      reject(new Error("Redis client is not connected"));
    }
  });
};

// Function to set value in Redis by key
const setAsync = async (key, expiration, value) => {
  return new Promise((resolve, reject) => {
    if (client.connected && isClientReady) {
      client.setex(key, expiration, value, (err) => {
        if (err) reject(err);
        resolve();
      });
    } else {
      console.error('Error in setAsync: Redis client is not connected');
      reject(new Error("Redis client is not connected"));
    }
  });
};

// Function to fetch weather data
const fetchWeatherData = async (cityId, unit, apiKey) => {
  try {
    const redisKey = `${cityId}-${unit}`;
    const cachedData = await getAsync(redisKey);

    if (cachedData) {
      return JSON.parse(cachedData);
    }

    const response = await axios.get(
      `https://api.openweathermap.org/data/2.5/forecast?id=${cityId}&units=${unit}&appid=${apiKey}`
    );

    await setAsync(redisKey, 3600, JSON.stringify(response.data));
    return response.data;

  } catch (error) {
    console.error('Error in fetchWeatherData:', error);
    throw error;
  }
};

module.exports = { fetchWeatherData };