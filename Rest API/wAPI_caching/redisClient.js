// redisClient.js

const redis = require('redis');
const logger = require('./utils/logger');  // Import the logger utility
const client = redis.createClient();
let isClientReady = false;

client.connect();

// Handling connection to Redis
client.on('connect', () => {
  logger.info('Connected to Redis');  // Replaced console.log with logger
});

// Mark client as ready when Redis is ready
client.on('ready', () => {
  logger.info('Redis client is now ready');  // Replaced console.log with logger
  isClientReady = true;
});

// Handling errors in Redis client
client.on('error', (err) => {
  logger.error(`Redis Error: ${err}`);  // Replaced console.log with logger
});

// Wait until the Redis client is ready
const waitForClientToBeReady = (timeoutMs = 10000) => {
  return new Promise((resolve, reject) => {
    if (isClientReady) {
      return resolve();
    }

    const checkInterval = setInterval(() => {
      if (isClientReady) {
        clearInterval(checkInterval);
        clearTimeout(timeout);  // Clear the timeout
        return resolve();
      }
    }, 100);

    // Add a timeout to stop waiting after a certain time
    const timeout = setTimeout(() => {
      clearInterval(checkInterval);
      reject(new Error('Redis client failed to become ready within the timeout period'));
    }, timeoutMs);
  });
};

module.exports = { client, waitForClientToBeReady };