const redis = require('redis');
const client = redis.createClient();
let isClientReady = false;

client.connect();

// Handling connection to Redis
client.on('connect', () => {
  console.log('Connected to Redis');
});

// Mark client as ready when Redis is ready
client.on('ready', () => {
  console.log('Redis client is now ready');
  isClientReady = true;
});

// Handling errors in Redis client
client.on('error', (err) => {
  console.log(`Redis Error: ${err}`);
});

// Wait until the Redis client is ready
const waitForClientToBeReady = () => {
  return new Promise((resolve) => {
    if (isClientReady) {
      return resolve();
    }
    const checkInterval = setInterval(() => {
      if (isClientReady) {
        clearInterval(checkInterval);
        return resolve();
      }
    }, 100);
  });
};

module.exports = { client, waitForClientToBeReady, isClientReady };