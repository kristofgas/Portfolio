# Weather API Report 



## Introduction 

  

The code implements a simple Node.js application using the Express framework to fetch weather information. The application uses Redis as an in-memory cache and integrates with the OpenWeatherMap API to provide real-time weather data. The purpose of this report is to provide an in-depth explanation of the code, its design decisions, and to address a critical issue related to Redis client connection errors, which is yet to be fixed. 
  

## Architecture Overview 

 The application serves as an integration layer between a mobile app and a third-party weather API, OpenWeatherMap. It is designed to address the rate-limiting concerns while serving a large number of users. This architecture is modular, leveraging the MVC pattern, and can be extended to meet future requirements. 

 

1. **Express Server (`app.js`)**: This is the entry point of the application that sets up the HTTP server and middleware. 

2. **Routes (`weatherRoutes.js`)**: Express Router is used to manage the application's routes. 

3. **Controller (`weatherController.js`)**: Houses the business logic of the application. 

4. **Utilities (`weatherUtils.js`)**: Utility functions for fetching weather data and handling Redis operations. 

5. **Redis Client (`redisClient.js`)**: Manages the Redis client and its connection status. 

6. **Configuration (`config.js`)**: Stores the API keys and other configurations. 

  

 

Though not an exact match, the architecture can be discussed in terms of the MVC design pattern, with specific adaptations made for a RESTful API service. 

  

#### Model 

  

In the context of a REST API, especially one serving as a middle layer, the "Model" can be thought of as the portion of the system that manages data and business rules. Although this application does not have a designated model layer, the `weatherUtils.js` can be seen as fulfilling this role. 

  

1. **Data Caching**: The utility functions in `weatherUtils.js` are responsible for caching data in Redis. This helps to alleviate the rate-limiting issue from the third-party API. 

   

2. **Data Retrieval**: Functions like `fetchWeatherData` either pull fresh data from the OpenWeatherMap API or return cached data from Redis, thus functioning as a kind of data model. 

  

3. **Data Transformation**: When data is fetched, it is transformed into a format that can be used to serve API endpoints, another task often associated with the model. 

  

#### View 

  

A REST API doesn't have a "View" in the traditional MVC sense, as it is not responsible for rendering user interfaces. However, in the API context, the closest analogy to a "View" would be the formatted JSON responses returned to the client.  

  

1. **JSON Formatting**: The JSON structure makes it easy for client applications to consume the API.  

  

2. **Data Presentation**: Even though there is no visual presentation, the structure of the JSON response is crucial as it determines how easy it is for client apps to parse and use the data. 

  

#### Controller 

  

The `weatherController.js` file serves as the Controller and is responsible for the application's business logic and data flow. 

  

1. **Input Validation**: Before processing, the controller validates user input such as unit, temperature, and cities. 

   

2. **Data Processing**: It uses utility functions from `weatherUtils.js` (the model) to fetch necessary data. It processes this data to fit the business requirements, e.g., filtering out cities based on temperature. 

  

3. **Error Handling**: The controller also deals with any errors that might occur during data fetching or processing and returns appropriate HTTP status codes and messages. 

  

#### Error Handling and Bootstrapping 

  

One of the issues I've encountered is with the Redis client disconnecting when an HTTP request is made, causing an 'Error: Redis client is not connected' error. 

  

1. **Redis Initialization**: The `redisClient.js` and the `bootstrapApp` function in `app.js` are designed to ensure that the Redis client is ready before the application starts accepting HTTP requests.  

  

2. **Error Handling in Utility Functions**: In `weatherUtils.js`, there are checks in place to see if the Redis client is connected before attempting to get or set data. 

 

 

 

## Detailed Code Explanation 

  

### Error Handling 

  

The middleware in `app.js` handles errors that occur during the processing of incoming HTTP requests. If any error occurs, a `500 Internal Server Error` is returned, and the error stack is logged to the console: 

  

```javascript 

app.use((err, req, res, next) => { 

  console.error(err.stack); 

  res.status(500).send('Something broke!'); 

}); 

``` 

  

### Bootstrapping 

  

Bootstrapping is done in the `app.js` file with the function `bootstrapApp()`. This function performs initial setup tasks like waiting for the Redis client to be ready before starting the HTTP server. The function `waitForClientToBeReady()` in `redisClient.js` is used to ensure that the Redis client is connected and ready before proceeding. 

  

```javascript 

async function bootstrapApp() { 

  await waitForClientToBeReady(); 

  app.listen(port, () => { 

    console.log(`Server is running on port ${port}`); 

  }); 

} 

``` 

  

### Redis Connection Issue 

  

The error message "Redis client is not connected" suggests that there's an issue with the Redis client's connection status when trying to get or set data in Redis. This is specifically happening in the utility functions `getAsync()` and `setAsync()` in `weatherUtils.js`. 

  

#### Attempts to Fix the Error 

  

1. **Connection Checks**: Implemented a variable `isClientReady` in `redisClient.js` to keep track of the Redis client’s connection status. Only proceed to use `get` or `set` if `isClientReady` is `true`. 

2. **Retry Mechanism**: A function `waitForClientToBeReady()` has been implemented, which waits until the Redis client is ready before starting the server. 

  

## Design Decisions 

  

1. **Modular Code**: The code is broken down into specific modules like routes, controllers, and utilities for better maintainability. 

2. **Environment Variables**: Used environment variables for storing sensitive information like API keys. 

3. **Caching**: Integrated Redis for caching weather data to reduce the number of calls to the OpenWeather API, thereby improving performance. 