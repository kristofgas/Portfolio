const request = require('supertest');
const { app, server } = require('../app'); // Import app and server from app.js
const { client, waitForClientToBeReady } = require('../redisClient'); // Import Redis client and waitForClientToBeReady function
const { getWeatherSummary, getCityWeather } = require('../controllers/weatherController');
const { fetchWeatherData } = require('../utils/weatherUtils');

// Mock fetchWeatherData
jest.mock('../utils/weatherUtils');

let mockRequest, mockResponse;

describe('All Tests', () => {

  // Make sure Redis client is ready before any tests are run
  let server;  // Declare server here for test scope

  beforeAll(async () => {
    await waitForClientToBeReady();
    if (process.env.NODE_ENV === 'test') {
      server = app.listen(4000, () => {  // Run the server on a different port for tests
        console.log('Test Server is running');
      });
    }
  });

  afterAll(() => {
    if (server) {
      server.close();  // Only attempt to close if the server was initialized
    }
    client.quit();
  });

  beforeEach(() => {
    // Clear all instances and calls to constructor and all methods
    jest.clearAllMocks();

    // Initialize mock request and response
    mockRequest = {
      query: { cities: '2618425', unit: 'Celsius', temperature: '10' },
    };
    mockResponse = {
      json: jest.fn(),
      status: jest.fn().mockReturnThis(),
    };
  });

  describe('Weather Controller', () => {

    test('should handle getWeatherSummary with valid parameters', async () => {
      fetchWeatherData.mockResolvedValue({
        list: [{ main: { temp: 25 } }],
        city: { name: 'Copenhagen' },
      });

      await getWeatherSummary(mockRequest, mockResponse);

      expect(mockResponse.json).toHaveBeenCalledWith({
        status: 'Success!',
        data: expect.arrayContaining([
          expect.objectContaining({
            city: 'Copenhagen',
          }),
        ]),
      });
    });

    test('should handle getWeatherSummary with invalid parameters', async () => {
      mockRequest.query.cities = null;
      await getWeatherSummary(mockRequest, mockResponse);
      expect(mockResponse.status).toHaveBeenCalledWith(400);
      expect(mockResponse.json).toHaveBeenCalledWith({ error: 'Invalid parameters received for getWeatherSummary' });

    });

    test('should handle error in getWeatherSummary gracefully', async () => {
      fetchWeatherData.mockRejectedValue(new Error('Failed to fetch data'));
      await getWeatherSummary(mockRequest, mockResponse);
      expect(mockResponse.status).toHaveBeenCalledWith(500);
      expect(mockResponse.json).toHaveBeenCalledWith({ error: 'Internal server error!' });
    });

    test('should handle getCityWeather with valid parameters', async () => {
      fetchWeatherData.mockResolvedValue({
        list: [{ main: { temp: 20 }, dt_txt: '2023-12-12 12:00:00' }],
        city: { name: 'Copenhagen' },
      });

      mockRequest.params = { city_id: '2618425' };

      await getCityWeather(mockRequest, mockResponse);

      expect(mockResponse.json).toHaveBeenCalledWith({
        status: 'Success!',
        data: expect.arrayContaining([
          expect.objectContaining({
            date: '2023-12-12 12:00:00',
            temperature: 20,
          }),
        ]),
      });
    });

  });

  describe('Weather Routes', () => {

    test('should respond to GET /weather/summary', async () => {
      fetchWeatherData.mockResolvedValue({
        list: [{ main: { temp: 20 } }],
        city: { name: 'Copenhagen' },
      });

      const response = await request(app).get('/weather/summary?cities=2618425&unit=Celsius&temperature=10');

      expect(response.status).toBe(200);
      expect(response.body).toMatchObject({
        status: 'Success!',
        data: expect.arrayContaining([
          expect.objectContaining({
            city: 'Copenhagen',
          }),
        ]),
      });
    });

    test('should respond to GET /weather/cities/:city_id', async () => {
      fetchWeatherData.mockResolvedValue({
        list: [{ main: { temp: 20 }, dt_txt: '2023-12-12 12:00:00' }],
        city: { name: 'Copenhagen' },
      });

      const response = await request(app).get('/weather/cities/2618425');

      expect(response.status).toBe(200);
      expect(response.body).toMatchObject({
        status: 'Success!',
        data: expect.arrayContaining([
          expect.objectContaining({
            date: '2023-12-12 12:00:00',
            temperature: 20,
          }),
        ]),
      });
    });

  });
});