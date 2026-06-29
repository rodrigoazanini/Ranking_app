import { jwtDecode } from "jwt-decode";

export const API_URL = "http://localhost:8091/api";

export function getHeaders() {
	return {
		"authorization": `Bearer ${localStorage.getItem("token")}`,
		"Content-Type": "application/json",
	};
}

export function isTokenExpired(decoded) {
	if (!decoded || !decoded.exp) return true;
	const now = Math.floor(Date.now() / 1000);
	return decoded.exp < now;
}

export function clearToken() {
	localStorage.removeItem("token");
}

export function getUser() {
	const token = localStorage.getItem("token");
	if (!token) return null;
	try {
		const decoded = jwtDecode(token);
		if (isTokenExpired(decoded)) {
			clearToken();
			return null;
		}
		return decoded;
	} catch {
		clearToken();
		return null;
	}
}

export function getUserId() {
	const user = getUser();
	return user?.id || user?.userId || user?.sub;
}

export function getRole() {
	const user = getUser();
	return user?.role || user?.roles || user?.admin;
}
