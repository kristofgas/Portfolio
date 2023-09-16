import * as itemModel from "./items.model.js";

export async function getAllItems(req, res) {
    try {
        let allItems = await itemModel.getAll();
        res.json(allItems);
    } catch (error) {
      // res.statusMessage=
      res.status(400).send(error.message);
    }
  }

  export async function getItem (req, res) {
    try {
      let id = parseInt(req.params.id)
      let item = await itemModel.getByID(id);
      res.json(item);
    } catch (error) {
      // res.statusMessage=
      res.status(400).send(error.message);
    }
  }

  export async function getItemsByCategory(req, res) {
    try {
      let category = parseInt(req.params.itemCategory);
        let allItems = await itemModel.getByCategory(category);
        res.json(allItems);
    } catch (error) {
      // res.statusMessage=
      res.status(400).send(error.message);
    }
  }

  export async function getAllCategories(req, res) {
    try {
      let allCategories = await itemModel.getCategories();
      res.json(allCategories);
    } catch (error) {
      // res.statusMessage=
      res.status(400).send(error.message);
    }
  }








  
//NOT IN FUNCTIONAL REQUIREMENTS? :

export async function postItem(req, res) {
  try {
    let newItem = req.body;
    await itemModel.add(newItem);
    res.end()
  } catch (error) {
    // res.statusMessage=
    res.status(400).send(error.message);
  }
}

export async function putItem  (req, res) {
  try {
    let id = parseInt(req.params.id)
    let item = req.body;
    await itemModel.update(id, item);
    res.end();
  } catch (error) {
    // res.statusMessage=
    res.status(400).send(error.message);
  }
}

export async function deleteItem (req, res) {
  try {
    let id = parseInt(req.params.id)
    await itemModel.remove(id);
    res.end();
  } catch (error) {
    // res.statusMessage=
    res.status(400).send(error.message);
  }
}


