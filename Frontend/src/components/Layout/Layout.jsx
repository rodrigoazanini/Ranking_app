import "./Layout.module.css";
import { Outlet } from "react-router-dom";
import NavBar from "../NavBar/NavBar";
import Footer from "../Footer/Footer";

export function Layout() {
	return (
		<>
			<NavBar />
			<main className="mainWrapper">
				<Outlet />
			</main>
			<Footer />
		</>
	);
}
