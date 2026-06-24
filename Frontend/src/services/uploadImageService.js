async function uploadImage(formData) {
	const response = await fetch(`${API_URL}/images/upload`, {
		method: "POST",
		headers: {
			...getHeaders(),
			"Content-Type": 'multipart/form-data'
		},
		body: formData,
	});
	return await response.json();
}

export const uploadImageService = {
	uploadImage
};