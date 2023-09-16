// index.js
import express from 'express'
import {getAllCustomers, postCustomer, getCustomer,putCustomer, deleteCustomer,getCustomerBasket,putCustomerBasket,deleteItemCustomerBasket } from './customers.controler.js'

export const customerRouter = express.Router();

// middleware specific to this route
customerRouter.use(express.json())

// route handlers
customerRouter.get("/customers", getAllCustomers); //not in functional requirements?
customerRouter.post("/customers", postCustomer);

customerRouter.get("/customers/:id", getCustomer); //not in functional requirements?

customerRouter.put("/customers/:id",putCustomer ); //not in functional requirements?

customerRouter.delete("/customers/:id", deleteCustomer); //not in functional requirements?



customerRouter.get("/customers/:id/basket", getCustomerBasket);

customerRouter.put("/customers/:id/basket", putCustomerBasket);

customerRouter.delete("/customers/:id/basket/:itemId", deleteItemCustomerBasket);
