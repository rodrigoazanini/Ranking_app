import { API_URL, getHeaders } from "./apiService";

async function getReview(id) {
	const response = await fetch(`${API_URL}/reviews/${id}`);
	return await response.json();
}

async function getReviews(page = 0, size = 10) {
	const response = await fetch(`${API_URL}/reviews?page=${page}&size=${size}`);
	return await response.json();
}

async function getReviewsByUserId(userId) {
	const response = await fetch(`${API_URL}/reviews/u:${userId}/`);
	return await response.json();
}

async function createReview(data) {
	const response = await fetch(`${API_URL}/reviews`, {
		method: "POST",
		headers: getHeaders(),
		body: JSON.stringify(data),
	});
	return await response.json();
}

async function updateReview(id, data) {
	const response = await fetch(`${API_URL}/reviews/${id}`, {
		method: "PUT",
		headers: getHeaders(),
		body: JSON.stringify(data),
	});
	return await response.json();
}

async function deleteReview(id) {
	const response = await fetch(`${API_URL}/reviews/${id}`, {
		method: "DELETE",
		headers: getHeaders(),
	});
	if (response.status === 204) {
		return null;
	}
	return await response.json();
}

export const reviewService = {
	getReview,
	getReviews,
	createReview,
	updateReview,
	deleteReview,
	getReviewsByUserId,
};
