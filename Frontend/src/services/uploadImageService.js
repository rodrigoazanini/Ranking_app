import { API_URL, getHeaders } from "./apiService";

async function uploadImage(formData) {
	const response = await fetch(`${API_URL}/images/upload`, {
		method: "POST",
		headers: {
			authorization: `Bearer ${localStorage.getItem("token")}`,
		},
		body: formData,
	});

	try {
		if (!response.ok) {
			const errorText = await response.text();
			throw new Error(errorText || "Failed to upload image");
		}

		const responseText = await response.text();
		console.log(JSON.parse(responseText));
		return JSON.parse(responseText);
	} catch {
		return { imageUrl: null };
	}
}

export const uploadImageService = {
	uploadImage
};