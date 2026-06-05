const API_URL = "";

function getHeaders() {
	return {
		"authorization": `Bearer ${localStorage.getItem("token")}`,
	};
}

async function getTasks() {
	const response = await fetch(`${API_URL}/tasks`, {
		headers: getHeaders(),
	});
	return await response.json();
}

async function createTask(data) {
	const response = await fetch(`${API_URL}/tasks`, {
		method: "POST",
		headers: getHeaders(),
		body: JSON.stringify(data),
	});
	return await response.json();
}

async function updateTask(id, data) {
	const response = await fetch(`${API_URL}/tasks/${id}`, {
		method: "PATCH",
		headers: getHeaders(),
		body: JSON.stringify(data),
	});
	return await response.json();
}

async function deleteTask(id) {
	const response = await fetch(`${API_URL}/tasks/${id}`, {
		method: "DELETE",
		headers: getHeaders(),
	});
	return await response.json();
}

export const taskService = {
	getTasks,
	createTask,
	updateTask,
	deleteTask,
};
