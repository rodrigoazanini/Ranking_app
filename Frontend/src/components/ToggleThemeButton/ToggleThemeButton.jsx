import { useState } from "react";
import styles from "./ToggleThemeButton.module.css";

export function ToggleThemeButton() {
	const [dark, setDark] = useState(localStorage.getItem("dark") === "true");

	function toggle() {
		setDark((prev) => !prev);
		localStorage.setItem("dark", dark);

		if (dark) document.body.classList.add("dark");
		else document.body.classList.remove("dark");
	}

	return (
		<button className={styles.button} onClick={toggle}>
			{dark ? "☀️" : "🌙"}
		</button>
	);
}
