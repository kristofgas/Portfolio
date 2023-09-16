import express from "express";
import cors from 'cors';

import {customerRouter} from "./customers/customer.route.js";
import {itemRouter} from "./items/item.route.js";
const app = express();
const PORT = 8080;


// This is a built-in middleware function in Express. It parses incoming requests with JSON payloads.


// app.use(express.json());
// ;
app.use(cors())

// paths '/customer' are handled by customerRouter
app.use(customerRouter)
// app.use(orderRouter)
app.use(itemRouter)
// app.use(categoryRouter)



app.get("/", (req, res) => res.send("Server 3: Hello World!"));

// For invalid routes
app.get("*", (req, res) => {
  res.send("404! This is an invalid URL.");
});



app.listen(PORT, function (err) {
  if (err) console.log("Error in server setup");
  console.log("Server listening on Port", PORT);
});
