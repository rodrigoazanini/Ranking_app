import styles from "./NavBar.module.css";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

export function NavBar() {
	const navigate = useNavigate();
	const [user, setUser] = useState(null);

	useEffect(() => {
		const token = localStorage.getItem("token");
		if (!token) return;

		try {
			const decoded = jwtDecode(token);
			console.log(decoded);
			setUser(decoded);
		} catch (e) {
			console.log(e);
		}
	}, []);

	function logout() {
		localStorage.removeItem("token");
		navigate("/auth/login");
	}

	if (!user) return;

	return (
		<nav className={styles.navbar}>
			<p>👤 {user.name}</p>

			<button className={styles.logoutButton} onClick={logout}>
				Cerrar sesión
			</button>
		</nav>
	);
}
