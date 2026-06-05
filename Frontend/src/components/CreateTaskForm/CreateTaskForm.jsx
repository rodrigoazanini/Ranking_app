import styles from "./CreateTaskForm.module.css";
import { useState } from "react";

export function CreateTaskForm({ onCreate }) {
	const [title, setTitle] = useState("");

	async function createTask(event) {
		event.preventDefault();
		if (!title) return;
		onCreate({ title });
		setTitle("");
	}

	return (
		<form className={styles.form} onSubmit={createTask}>
			<input
				type="text"
				placeholder="Nueva tarea"
				value={title}
				onChange={(e) => setTitle(e.target.value)}
			/>

			<input type="submit" value="Crear" />
		</form>
	);
}
