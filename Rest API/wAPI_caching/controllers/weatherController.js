const { fetchWeatherData } = require('../utils/weatherUtils');
const config = require('../config');

// Define the weather controller methods
const weatherController = {
  // Handle GET /weather/summary
  getWeatherSummary: async (req, res) => {
    try {
      const { unit, temperature, cities } = req.query;

      if (!unit || !temperature || !cities) {
        return res.status(400).json({ error: 'Invalid parameters' });
      }

      const cityIds = cities.split(',');

      // Fetch weather data for multiple cities
      const weatherPromises = cityIds.map((cityId) => {
        return fetchWeatherData(cityId, unit, config.openWeatherApiKey);
      });

      const weatherData = await Promise.all(weatherPromises);

      // Filter cities based on temperature
      const filteredCities = weatherData
        .filter((cityData) => cityData.list.some((item) => item.main.temp > temperature))
        .map((cityData) => {
          return {
            city: cityData.city.name,
            // temperatureUnit: unit,
            // temperature: temperature,
          };
        });

      return res.json({ status: 'Success!', data: filteredCities });

    } catch (error) {
      console.error('Error in getWeatherSummary:', error);
      return res.status(500).json({ error: 'Internal server error!' });
    }
  },

  // Handle GET /weather/cities/<city_id>
  getCityWeather: async (req, res) => {
    try {
      const { city_id } = req.params;
      const cityWeather = await fetchWeatherData(city_id, 'metric', config.openWeatherApiKey);
      const cv = cityWeather.list.map(item => {
        return {
          date: item.dt_txt,
          temperature: item.main.temp
        }
      });
      return res.json({ status: 'Success!', data: cv });

    } catch (error) {
      console.error('Error in getCityWeather:', error);
      return res.status(500).json({ error: 'Internal server error!' });
    }
  },
};

module.exports = weatherController;