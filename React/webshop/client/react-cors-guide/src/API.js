export async function getItems() {
	const response = await fetch(`http://localhost:8080/items`, {
		mode: "cors",
		method: "GET",
		headers: {
			Accept: "application/json",
			"Content-Type": "application/json",
			"Access-Control-Allow-Origin": "*",
		},
	});
	return response.json();
}

export async function getItem(itemId) {
	const response = await fetch(`http://localhost:8080/items/${itemId}`, {
		mode: "cors",
		method: "GET",
		headers: {
			Accept: "application/json",
			"Content-Type": "application/json",
			"Access-Control-Allow-Origin": "*",
		},
	});
	return response.json();
}

export async function getItemsCategory(categoryId) {
	const response = await fetch(
		`http://localhost:8080/categories/${categoryId}`,
		{
			mode: "cors",
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
				"Access-Control-Allow-Origin": "*",
			},
		}
	);
	return response.json();
}

export async function getBasket(customerId) {
	const response = await fetch(
		`http://localhost:8080/customers/${customerId}/basket/`,
		{
			mode: "cors",
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
				"Access-Control-Allow-Origin": "*",
			},
		}
	);
	return response.json();
}

export async function addItemToBasket(customerId, itemId, itemQuantity) {
	await fetch(`http://localhost:8080/customers/${customerId}/basket/`, {
		mode: "cors",
		method: "PUT",
		headers: {
			Accept: "application/json",
			"Content-Type": "application/json",
			"Access-Control-Allow-Origin": "*",
		},
		body: JSON.stringify({ itemId, itemQuantity }),
	});
}

export async function deleteItemFromBasket(customerId, itemId, itemQuantity) {
	await fetch(`http://localhost:8080/customers/${customerId}/basket/`, {
		mode: "cors",
		method: "PUT",
		headers: {
			Accept: "application/json",
			"Content-Type": "application/json",
			"Access-Control-Allow-Origin": "*",
		},
		body: JSON.stringify({ itemId, itemQuantity }),
	});
}

export async function getCustomer(customerId) {
	const response = await fetch(
		`http://localhost:8080/customers/${customerId}`,
		{
			mode: "cors",
			method: "GET",
			headers: {
				Accept: "application/json",
				"Content-Type": "application/json",
				"Access-Control-Allow-Origin": "*",
			},
		}
	);
	return response.json();
}

export async function createCustomer(customer) {
	await fetch(`http://localhost:8080/customers`, {
		mode: "cors",
		method: "POST",
		headers: {
			Accept: "application/json",
			"Content-Type": "application/json",
			"Access-Control-Allow-Origin": "*",
		},
		body: JSON.stringify({
			customerId: customer.id,
			customerFirstName: customer.firstName,
			customerLastName: customer.secondName,
			customerEmail: customer.email,
			customerBasket: customer.basket,
		}),
	});
}
export async function updateCustomer(customer) {
	await fetch(`http://localhost:8080/customers/${customer.id}`, {
		mode: "cors",
		method: "PUT",
		headers: {
			Accept: "application/json",
			"Content-Type": "application/json",
			"Access-Control-Allow-Origin": "*",
		},
		body: JSON.stringify({
			customerId: customer.id,
			customerFirstName: customer.firstName,
			customerLastName: customer.secondName,
			customerEmail: customer.email,
			customerBasket: customer.basket,
		}),
	});
}
