const API_URL = "http://localhost:8091/api";

function getHeaders() {
	return {
		"authorization": `Bearer ${localStorage.getItem("token")}`,
	};
}

async function createItem(data) {
	const response = await fetch(`${API_URL}/items`, {
		method: "POST",
		headers: getHeaders(),
		body: JSON.stringify(data),
	});
	return await response.json();
}

//getItem/Items es publico
async function getItem(id) {
	const response = await fetch(`${API_URL}/items/${id}`);
	return await response.json();
}

async function getItems() {
	const response = await fetch(`${API_URL}/items`);
	return await response.json();
}

async function updateItem(id, data) {
	const response = await fetch(`${API_URL}/items/${id}`, {
		method: "PATCH",
		headers: getHeaders(),
		body: JSON.stringify(data),
	});
	return await response.json();
}

async function deleteItem(id) {
	const response = await fetch(`${API_URL}/items/${id}`, {
		method: "DELETE",
		headers: getHeaders(),
	});
	return await response.json();
}

export const itemService = {
	getItem,
	createItem,
	updateItem,
	deleteItem,
};
