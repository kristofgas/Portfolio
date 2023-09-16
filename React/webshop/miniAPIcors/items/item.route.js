// index.js
import express from 'express'
import {getAllItems, postItem, getItem,putItem, deleteItem,getItemsByCategory ,getAllCategories} from './items.controler.js'

export const itemRouter = express.Router();

// middleware specific to this route
itemRouter.use(express.json())

// route handlers
itemRouter.get("/items", getAllItems);
itemRouter.post("/items", postItem); //not in functional requirements

itemRouter.get("/items/:id", getItem);

itemRouter.put("/items/:id",putItem ); //not in functional requirements

itemRouter.delete("/items/:id", deleteItem); //not in functional requirements


itemRouter.get("/categories", getAllCategories);

itemRouter.get("/categories/:itemCategory", getItemsByCategory); //right now categories are 1 and 2