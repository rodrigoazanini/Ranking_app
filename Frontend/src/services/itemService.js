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

async function getLatestItems(page = 0, size = 10) {
	const response = await fetch(`${API_URL}/items/latest?page=${page}&size=${size}`);
	return await response.json();
}

async function getTopItemsByRanking(quantity = 10) {
	const response = await fetch(`${API_URL}/items/top/ranking?quantity=${quantity}`);
	return await response.json();
}

async function getTopItemsByReviews(quantity = 10) {
	const response = await fetch(`${API_URL}/items/top/reviews?quantity=${quantity}`);
	return await response.json();
}

async function getTopItemsByDate(quantity = 10, date) {
	const response = await fetch(`${API_URL}/items/top/date?quantity=${quantity}&date=${date}`);
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

async function searchItems(endpoint, filters = {}, page = 0, size = 5) {
	const params = buildParams(filters, page, size);
	const url = `${API_URL}${endpoint}?${params.toString()}`;
	console.log('Search URL:', url);
	console.log('Filters:', filters);

	const response = await fetch(url, {
		method: "GET"
	});

	if (!response.ok) {
		const errorData = await response.json();
		console.error('API Error:', errorData);
		throw new Error(`Request failed: ${response.status}`);
	}

	return response.json();
}

function buildParams(filters = {}, page = 0, size = 5) {
	const params = new URLSearchParams();

	Object.entries(filters).forEach(([key, value]) => {
		if (value !== undefined && value !== null && value !== '') {
			params.append(key, value);
		}
	});

	params.append('page', page);
	params.append('size', size);

	return params;
}

export const itemService = {
	getItem,
	getItems,
	getTopItemsByRanking,
	getTopItemsByReviews,
	getTopItemsByDate,
	getLatestItems,
	searchItems,
	createItem,
	updateItem,
	deleteItem
};
