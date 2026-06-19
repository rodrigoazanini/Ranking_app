import styles from "./RegisterPage.module.css";
import Btn from "../../components/Btn/Btn";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { authService } from "../../services/authService";

export function RegisterPage() {
	const navigate = useNavigate();

	const [username, setUsername] = useState("");
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState(null);

	async function register(e) {
		e.preventDefault();
		setError(null);

		const data = await authService.register({ username, email, password });
		if (data?.id) {
			navigate("/auth/login");
		} else {
			setError(data?.error ?? data?.message ?? "No se pudo registrar el usuario");
		}
	}

	return (
		<main>
			<div className={styles.page}>
				<div className={styles.card}>
					<header>
						<div className={styles.UserIcon}>
							<svg
								xmlns="http://www.w3.org/2000/svg"
								width={140}
								height={140}
								viewBox="0 0 24 24"
								fill="currentColor"
								className="icon icon-tabler icons-tabler-filled icon-tabler-user"
							>
								<path stroke="none" d="M0 0h24v24H0z" fill="none" />
								<path d="M12 2a5 5 0 1 1 -5 5l.005 -.217a5 5 0 0 1 4.995 -4.783z" />
								<path d="M14 14a5 5 0 0 1 5 5v1a2 2 0 0 1 -2 2h-10a2 2 0 0 1 -2 -2v-1a5 5 0 0 1 5 -5h4z" />
							</svg>
						</div>

						<div className={styles.title}>
							<h2>Crear una cuenta</h2>
						</div>
					</header>

					<form className={styles.form} onSubmit={register}>
						<input
							type="text"
							placeholder="Nombre de usuario"
							value={username}
							onChange={(e) => setUsername(e.target.value)}
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

						<Btn className={styles.submitBtn} type="submit">
							REGISTRARME
						</Btn>

						<p>
							¿Ya tenes una cuenta?
							<Link to="/auth/login"> Ingresar</Link>
						</p>
					</form>
				</div>
			</div>
		</main>
	);
}
