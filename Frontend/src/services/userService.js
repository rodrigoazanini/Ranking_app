import { API_URL, getHeaders } from "./apiService";
//No se necesita CreateUser ya que es parte the authService.register

async function getUser(id) {
	const response = await fetch(`${API_URL}/users/${id}`);
	return await response.json();
}

async function getUsers(page = 0, size = 10) {
	const response = await fetch(`${API_URL}/users?page=${page}&size=${size}`);
	return await response.json();
}


async function getTopUsersByReviews(quantity = 10) {
	const response = await fetch(`${API_URL}/users/top/reviews?quantity=${quantity}`);
	return await response.json();
}

async function updateUser(id, data) {
	const response = await fetch(`${API_URL}/users/${id}`, {
		method: "PUT",
		headers: getHeaders(),
		body: JSON.stringify(data),
	});
	return await response.json();
}

async function updateUserAdmin(id, data) {
	const response = await fetch(`${API_URL}/admin/users/${id}`, {
		method: "PUT",
		headers: getHeaders(),
		body: JSON.stringify(data),
	});
	return await response.json();
}

async function deleteUser(id) {
	const response = await fetch(`${API_URL}/users/${id}`, {
		method: "DELETE",
		headers: getHeaders(),
	});
	if (response.status === 204) {
		return null;
	}
	return await response.json();
}

export const userService = {
	getUser,
	getUsers,
	getTopUsersByReviews,
	updateUser,
    updateUserAdmin,
	deleteUser,
};
