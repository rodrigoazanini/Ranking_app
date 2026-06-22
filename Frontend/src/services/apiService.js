export const API_URL = "http://localhost:8091/api";

export function getHeaders() {
	return {
		"authorization": `Bearer ${localStorage.getItem("token")}`,
		"Content-Type": "application/json",
	};
}
