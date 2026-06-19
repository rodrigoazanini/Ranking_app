const API_URL = "http://localhost:8091/api";

async function login(data){
    const response = await fetch(`${API_URL}/auth/login`, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    })
    return await response.json();
}

async function register(data) {
    const response = await fetch(`${API_URL}/users`, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    })
    return await response.json();
}

export const authService = {
    login,
    register
}