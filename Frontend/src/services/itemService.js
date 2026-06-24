import { API_URL, getHeaders } from "./apiService";

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

async function getItems(page = 0, size = 10) {
	const response = await fetch(`${API_URL}/items?page=${page}&size=${size}`);
	return await response.json();
}

async function updateItem(id, data) {
	const response = await fetch(`${API_URL}/items/${id}`, {
		method: "PUT",
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
	if (response.status === 204) {
		return null;
	}
	return await response.json();
}



export const itemService = {
	getItem,
	getItems,
	createItem,
	updateItem,
	deleteItem
};
