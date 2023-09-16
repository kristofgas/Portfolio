import * as fs from "fs/promises";
// const CUSTOMERS_FILE = "./customers/customers.json";
const DB_FILE = "./db.json";


// return all customer from file
export async function getAll() {
  try {
    let dbTxt = await fs.readFile(DB_FILE);
    let db = JSON.parse(dbTxt);
    return db.customers;
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
async function save(customers = []) {
  let dbTxt = await fs.readFile(DB_FILE);
  let db = JSON.parse(dbTxt);
  db.customers = customers;
  dbTxt = JSON.stringify(db, null, 2);
  await fs.writeFile(DB_FILE, dbTxt);
}

// test function for customer ID
function findCustomer(customerArray, Id) {
  return customerArray.findIndex(
    (currCustomer) => currCustomer.customerId === Id
  );
}

// get gustomer by ID
export async function getByID(customerId) {
  let customerArray = await getAll();
  let index = findCustomer(customerArray, customerId);
  if (index === -1)
    throw new Error(`Customer with ID:${customerId} doesn't exist`);
  else return customerArray[index];
}

export async function removeItemBasket(customerId, itemId) {
  let customerArray = await getAll();
  let index = findCustomer(customerArray, customerId); // findIndex
  let basketArray = customerArray[index].customerBasket
  let itemIndex = findItem(basketArray, itemId);
  if (index === -1)
    throw new Error(`Customer with ID:${customerId} doesn't exist`);
  if (itemIndex === -1)
    throw new Error(`Item with ID:${itemId} doesn't exist in the basket of Customer with ID:${customerId} `);
  else {
    basketArray.splice(itemIndex, 1);
    customerArray[index].customerBasket = basketArray;
    await save(customerArray);
  }
}

function findItem(basketArray, Id) {
  return basketArray.findIndex(
    (currBasket) => currBasket.itemId === Id
  );
}

export async function updateBasket(customerId, newOrder) {
  let customerArray = await getAll();
  let index = findCustomer(customerArray, customerId); // findIndex

  if (index === -1)
    throw new Error(`Customer with ID:${customerId} doesn't exist`);
  if (customerArray[index].customerBasket.filter(item => item.itemId === newOrder.itemId).length > 0) //hvis den allerede findes i kurven 
  {
    let newIndex = customerArray[index].customerBasket.findIndex(item => item.itemId === newOrder.itemId);
    customerArray[index].customerBasket[newIndex].itemQuantity += newOrder.itemQuantity;
    if (customerArray[index].customerBasket[newIndex].itemQuantity < 1){
      removeItemBasket(customerId,newOrder.itemId);
    }
    else{
    await save(customerArray);
    }
  }
  else {
    customerArray[index].customerBasket.push(newOrder);
    await save(customerArray);
  }
}







//NOT IN FUNCTIONAL REQUIREMENTS? :

// create a new customer
export async function add(newCustomer) {
  let customerArray = await getAll();
  if (findCustomer(customerArray, newCustomer.customerId) !== -1)
    throw new Error(
      `Customer with Id:${newCustomer.customerId} already exists`
    );
  customerArray.push(newCustomer);
  await save(customerArray);
}

// update existing customer
export async function update(customerId, customer) {
  let customerArray = await getAll();
  let index = findCustomer(customerArray, customerId); // findIndex
  if (index === -1)
    throw new Error(`Customer with ID:${customerId} doesn't exist`);
  else {
    customerArray[index] = customer;
    await save(customerArray);
  }
}

// delete existing customer
export async function remove(customerId) {
  let customerArray = await getAll();
  let index = findCustomer(customerArray, customerId); // findIndex
  if (index === -1)
    throw new Error(`Customer with ID:${customerId} doesn't exist`);
  else {
    customerArray.splice(index, 1); // remove customer from array
    await save(customerArray);
  }
}
