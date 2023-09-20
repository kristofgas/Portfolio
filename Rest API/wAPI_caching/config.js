require('dotenv').config();

module.exports = {
  openWeatherApiKey: process.env.OPEN_WEATHER_API_KEY,
  baseWeatherUrl: 'https://api.openweathermap.org/data/2.5/forecast',
};