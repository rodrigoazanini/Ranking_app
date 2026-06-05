import styles from "./LoginPage.module.css"
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { authService } from "../../services/authService";

export function LoginPage() {
	const navigate = useNavigate();

	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");

	const [error, setError] = useState(null);

	async function login(e) {
		e.preventDefault();
		setError(null);

		const data = await authService.login({ email, password });
		if (data.ok) {
			localStorage.setItem("token", data.token);
			navigate("/");
		} else {
			setError(data.error);
		}
	}

	return (
		<main>
			<header>
				<h2>Ingresa a tu cuenta</h2>
				<p>Por favor, completa los datos</p>
			</header>

			<form onSubmit={login}>
				<input
					type="email"
					placeholder="Email"
					value={email}
					onChange={(e) => setEmail(e.target.value)}
				/>
				
				<input
					type="password"
					placeholder="Contraseña"
					value={password}
					onChange={(e) => setPassword(e.target.value)}
				/>

				{error ? <p className={styles.errorMessage}>{error}</p> : null}

				<input type="submit" value="Ingresar" />
			</form>

			<Link to="/auth/register">¿Aun no tenes una cuenta? Registrarme</Link>
		</main>
	);
}
