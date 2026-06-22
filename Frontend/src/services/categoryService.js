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
	const response = await fetch(`${API_URL}/categories/${id}`, {
		headers: getHeaders(),
	});
	return await response.json();
}

async function getCategories(page = 0, size = 10) {
	const response = await fetch(`${API_URL}/categories?page=${page}&size=${size}`, {
		headers: getHeaders(),
	});
	return await response.json();
}

async function getAllCategories() {
    const categories = [];
    let page = 0;
    let hasMore = true;

    while (hasMore) {
        const response = await getCategories(page, 100);
        categories.push(...(response.content ?? []));
        hasMore = response.last !== true;
        page += 1;
    }

    return categories;
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
	getAllCategories,
	createCategory,
	updateCategory,
	deleteCategory,
};
