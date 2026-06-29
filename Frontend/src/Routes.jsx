import { Navigate, Outlet } from "react-router-dom";
import { getUser } from "./services/apiService";

export function PublicRoute() {
	const user = getUser();
	if (user) return <Navigate to="/" />;
	return (
		<div className="mainWrapper">
			<Outlet />
		</div>
	)
}

export function PrivateRoute() {
	const user = getUser();
	if (!user) return <Navigate to="/auth/login" />;
	return (
		<div className="mainWrapper">
			<Outlet />
		</div>
	)
}