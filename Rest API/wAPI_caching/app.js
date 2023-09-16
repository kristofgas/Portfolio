const express = require('express');
const weatherRoutes = require('./routes/weatherRoutes');
const { waitForClientToBeReady } = require('./redisClient');

const app = express();
const port = process.env.PORT || 3000;

// Middleware for parsing JSON and URL-encoded form data
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

// Middleware for handling errors and logging them
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).send('Something broke!');
});

// Mounting the weather routes
app.use('/weather', weatherRoutes);

// Bootstrap the application
async function bootstrapApp() {
  // Wait for the Redis client to be ready
  await waitForClientToBeReady();

  // Start the express server
  app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
  });
}

// Run the bootstrap process and catch any errors
bootstrapApp().catch((err) => console.error('Failed to bootstrap app', err));