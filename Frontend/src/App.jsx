
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { PublicRoute, PrivateRoute } from "./Routes.jsx";
import { Layout } from "./components/Layout/Layout.jsx";
import { ItemsPage } from "./pages/ItemsPage/ItemsPage.jsx";
import { AdminItemsPage } from "./pages/AdminItemsPage/AdminItemsPage.jsx";
import { LoginPage } from "./pages/LoginPage/LoginPage.jsx";
import { RegisterPage } from "./pages/RegisterPage/RegisterPage.jsx";
import { UserProfilePage } from "./pages/UserProfilePage/UserProfilePage.jsx";
import  HomePage  from "./pages/HomePage/HomePage.jsx";

export function App() {
	return (
		<BrowserRouter>
			<Routes>
				<Route element={<Layout />}>
					<Route path="/" element={<HomePage />} /> {/* para testing, luego se cambia a home page o algo asi */}

					<Route element={<PublicRoute />}>
						<Route path="/auth/login" element={<LoginPage />} />
						<Route path="/auth/register" element={<RegisterPage />} />
					</Route>

					<Route element={<PrivateRoute />}>
						<Route path="/items/create" element={<ItemsPage />} />
						<Route path="/items/suggest" element={<ItemsPage />} />
						<Route path="/items/edit/:id" element={<ItemsPage />} />
						<Route path="/profile" element={<UserProfilePage />} />
						<Route path="/admin/items" element={<AdminItemsPage />} />
					</Route>					

					<Route path="*" element={<Navigate to="/" replace />} />
				</Route>
			</Routes>
		</BrowserRouter>
	);
}
