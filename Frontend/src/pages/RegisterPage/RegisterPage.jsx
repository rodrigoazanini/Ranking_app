import styles from "./RegisterPage.module.css"
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { authService } from "../../services/authService";

export function RegisterPage() {
	const navigate = useNavigate();

	const [name, setName] = useState("");
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");

	const [error, setError] = useState(null);

	async function register(e) {
		e.preventDefault();
		setError(null);

		const data = await authService.register({ name, email, password });
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
				<h2>Crear una cuenta</h2>
				<p>Por favor, completa los datos</p>
			</header>

			<form onSubmit={register}>
				<input
					type="text"
					placeholder="Nombre"
					value={name}
					onChange={(e) => setName(e.target.value)}
				/>

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

				<input type="submit" value="Registrarme" />
			</form>

			<Link to="/auth/login">¿Ya tenes una cuenta? Ingresar</Link>
		</main>
	);
}