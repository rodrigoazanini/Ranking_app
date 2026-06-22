import { API_URL, getHeaders } from "./apiService";

async function createCategory(data) {
	const response = await fetch(`${API_URL}/categories`, {
		method: "POST",
		headers: getHeaders(),
		body: JSON.stringify(data),
	});
	return await response.json();
}

async function getCategory(id) {
	const response = await fetch(`${API_URL}/categories/${id}`);
	return await response.json();
}

async function getCategories(page = 0, size = 10) {
	const response = await fetch(`${API_URL}/categories?page=${page}&size=${size}`);
	return await response.json();
}

async function updateCategory(id, data) {
	const response = await fetch(`${API_URL}/categories/${id}`, {
		method: "PUT",
		headers: getHeaders(),
		body: JSON.stringify(data),
	});
	return await response.json();
}

async function deleteCategory(id) {
	const response = await fetch(`${API_URL}/categories/${id}`, {
		method: "DELETE",
		headers: getHeaders(),
	});
	if (response.status === 204) {
		return null;
	}
	return await response.json();
}

export const categoryService = {
	getCategory,
	getCategories,
	createCategory,
	updateCategory,
	deleteCategory,
};
