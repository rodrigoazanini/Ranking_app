import { Navigate, Outlet } from "react-router-dom";
import  NavBar  from "./components/NavBar/NavBar";
import  NavbarMenu  from "./components/NavBar/NavbarMenu";

export function PublicRoute() {
	const token = localStorage.getItem("token");
	if (token) return <Navigate to="/" />;
	return <div className="mainWrapper">
		<Outlet />
	</div>;
}

export function PrivateRoute() {
	const token = localStorage.getItem("token");
	if (!token) return <Navigate to="/auth/login" />;
	return (
		<>
			<NavBar />
			<div className="mainWrapper">
				<Outlet />
			</div>
		</>
	);
}
