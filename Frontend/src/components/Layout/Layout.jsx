import "./Layout.module.css";
import { Outlet } from "react-router-dom";
import NavBar from "../NavBar/NavBar";

export function Layout() {
	return (
		<>
			<NavBar />
			<main className="mainWrapper">
				<Outlet />
			</main>
		</>
	);
}
