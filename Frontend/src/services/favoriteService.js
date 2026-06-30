import { API_URL, getHeaders } from "./apiService";

async function getFavoritesByUserId(userId) {
	const response = await fetch(`${API_URL}/user-item-favorites/user/${userId}`, {
		headers: getHeaders(),
	});
	if (!response.ok) throw new Error("Error al cargar favoritos");
	return await response.json();
}

async function getFavorites() {
	const response = await fetch(`${API_URL}/user-item-favorites`, {
		headers: getHeaders(),
	});
	if (!response.ok) throw new Error("Error al cargar favoritos");
	return await response.json();
}

async function removeFavorite(favoriteId) {
	const response = await fetch(`${API_URL}/user-item-favorites/${favoriteId}`, {
		method: "DELETE",
		headers: getHeaders(),
	});
	if (!response.ok) throw new Error("Error al eliminar favorito");
	return response.status === 204 ? null : await response.json();
}

async function addFavorite(itemId, userId) {
  const response = await fetch(`${API_URL}/user-item-favorites`, {
    method: "POST",
    headers: getHeaders(),
    body: JSON.stringify({ itemId, userId }),
  });
	if (!response.ok) throw new Error("Error al agregar favorito");
	return await response.json();
}

async function isFavorited(userId, itemId) {
	const response = await fetch(`${API_URL}/favorites/check?userId=${userId}&itemId=${itemId}`, {
		headers: getHeaders(),
	});
	if (!response.ok) throw new Error("Error al verificar favorito");
	return await response.json();
}

export const favoriteService = {
	getFavoritesByUserId,
	getFavorites,
	removeFavorite,
	addFavorite,
	isFavorited,
};
