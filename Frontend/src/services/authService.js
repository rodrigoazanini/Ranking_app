const API_URL = "";

async function login(data){
    const response = await fetch(`${API_URL}/auth/login`, {
        method: "POST",
        body: JSON.stringify(data)
    })
    return await response.json();
}

async function register(data) {
    const response = await fetch(`${API_URL}/auth/register`, {
        method: "POST",
        body: JSON.stringify(data)
    })
    return await response.json();
}

export const authService = {
    login,
    register
}