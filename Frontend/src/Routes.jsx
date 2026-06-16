import { Navigate, Outlet } from "react-router-dom";

export function PublicRoute() {
	const token = localStorage.getItem("token");
	if (token) return <Navigate to="/" />;
	return (
		<div className="mainWrapper">
			<Outlet />
		</div>
	)
}

export function PrivateRoute() {
	const token = localStorage.getItem("token");
	if (!token) return <Navigate to="/auth/login" />;
	return (
		<div className="mainWrapper">
			<Outlet />
		</div>
	)
}