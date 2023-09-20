// app.js

const express = require('express');
const weatherRoutes = require('./routes/weatherRoutes');
const { waitForClientToBeReady } = require('./redisClient');
const logger = require('./utils/logger');

const app = express();
const port = process.env.PORT || 3000;

let server;  // Declare server here

app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.use('/weather', weatherRoutes);

app.use((err, req, res, next) => {
  logger.error(err.stack);
  if (err.status && err.status >= 400 && err.status < 500) {
    res.status(err.status).send({ error: err.message });
  } else {
    res.status(500).send({ error: 'Internal Server Error' });
  }
});



async function bootstrapApp() {
  try {
    await waitForClientToBeReady();
    server = app.listen(port, () => {  // Assign the server object here
      logger.info(`Server is running on port ${port}`);
    });
  } catch (err) {
    logger.error('Failed to bootstrap app', err);
  }
}

if (process.env.NODE_ENV !== 'test') {  // Skip bootstrapping if in test environment
  bootstrapApp();
}

module.exports = { app, server };  // Export app and server