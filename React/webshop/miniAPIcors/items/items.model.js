
import * as fs from "fs/promises";
// const ITEMS_FILE = "./items/items.json";
const DB_FILE = "./db.json";

// return all items from file
export async function getAll() {
  try {
    let dbTxt = await fs.readFile(DB_FILE);
    let db = JSON.parse(dbTxt);
    return db.items;
  } catch (err) {
    if (err.code === "ENOENT") {
      // file does not exits
      await save([]); // create a new file with ampty array
      return []; // return empty array
    } // // cannot handle this exception, so rethrow
    else throw err;
  }
}

// save array of customers to file
async function save(items = []) {
  let dbTxt = await fs.readFile(DB_FILE);
  let db = JSON.parse(dbTxt);
  db.items = items;
  dbTxt = JSON.stringify(db,null, 2);
  await fs.writeFile(DB_FILE, dbTxt);
}

// test function for customer ID
function findItem(itemArray, Id) {
  return itemArray.findIndex(
    (currItem) => currItem.itemId === Id
  );
}

// get gustomer by ID
export async function getByID(itemId) {
  let itemArray = await getAll();
  let index = findItem(itemArray, itemId);
  if (index === -1)
    throw new Error(`Item with ID:${itemId} doesn't exist`);
  else return itemArray[index];
}

function filterByCategory(itemArray, category) {
  return itemArray.filter(
    (currItem) => currItem.itemCategory === category
  );
}

export async function getByCategory(category) {
  let itemArray = await getAll();
  let categoryArray = filterByCategory(itemArray, category);
  if (categoryArray.length === 0)
    throw new Error(`Item with Category:${category} doesn't exist`);
  else return categoryArray;
}

export async function getCategories() {
  let itemArray = await getAll();
  return [...new Set(itemArray.map(item => item.itemCategory))] 
}









// NOT IN FUNCTIONAL REQUIREMENTS? :


// HOLD ØJE FOR MÅTTE IKKE RENAME
// create a new customer
export async function add(newItem) {
  let itemArray = await getAll();
  if (findItem(itemArray, newItem.itemId) !== -1 )
    throw new Error(
      `Item with Id:${newItem.itemId} already exists`
    );
  itemArray.push(newItem);
  await save(itemArray);
}

// update existing customer
export async function update(itemId, item) {
  let itemArray = await getAll();
  let index = findItem(itemArray, itemId); // findIndex
  if (index === -1)
    throw new Error(`Item with ID:${itemId} doesn't exist`);
  else {
    itemArray[index] = item;
    await save(itemArray);
  }
}

// delete existing customer
export async function remove(itemId) {
  let itemArray = await getAll();
  let index = findItem(itemArray, itemId); // findIndex
  if (index === -1)
    throw new Error(`Item with ID:${itemId} doesn't exist`);
  else {
    itemArray.splice(index, 1); // remove customer from array
    await save(itemArray);
  }
}
