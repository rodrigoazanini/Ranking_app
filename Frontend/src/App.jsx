import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { LoginPage } from "./pages/LoginPage/LoginPage.jsx";
import { RegisterPage } from "./pages/RegisterPage/RegisterPage.jsx";
import { PublicRoute, PrivateRoute } from "./Routes.jsx";


export function App() {
	return (
		<>
			<BrowserRouter>
				<Routes>
					<Route element={<PublicRoute />}>
						<Route path="/auth/login" element={<LoginPage />} />
						<Route path="/auth/register" element={<RegisterPage />} />
					</Route>

					<Route element={<PrivateRoute />}>
						<Route path="/" element={<TasksPage />} />
					</Route>
				</Routes>
			</BrowserRouter>

			<ToggleThemeButton />
		</>
	);
}
