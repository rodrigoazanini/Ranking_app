import "./index.css";

import Navbar from "./components/Navbar/Navbar";
import LoginPage from "./pages/LoginPage/LoginPage";
import HomePage from "./pages/HomePage/HomePage";
import ItemDetailPage from "./pages/ItemDetailPage/ItemDetailPage";
import SuggestItemPage from "./pages/SuggestItemPage/SuggestItemPage";
import CreateItemPage from "./pages/CreateItemPage/CreateItemPage";
import EditItemPage from "./pages/EditItemPage/EditItemPage";
import AdminItemsPage from "./pages/AdminItemsPage/AdminItemsPage";
import UserProfilePage from "./pages/UserProfilePage/UserProfilePage";


export function App() {
	return (
		<>
			<BrowserRouter>
				<Routes>
					<Route element={<Layout />}>
						<Route element={<PublicRoute />}>
							<Route path="/" element={<HomePage />} />
							<Route path="/auth/login" element={<LoginPage />} />
							<Route path="/auth/register" element={<RegisterPage />} />
							<Route path="/items/details/:id" element={<ItemDetailPage />} />
						</Route>

						<Route element={<PrivateRoute />}>
							<Route path="/items/suggest" element={<SuggestItemPage />} />
							<Route path="/items/create" element={<CreateItemPage />} />
							<Route path="/items/edit/:id" element={<EditItemPage />} />
							<Route path="/admin/items" element={<AdminItemsPage />} />
							<Route path="/profile" element={<UserProfilePage />} />
						</Route>{/*ItemFormPage */}
					</Route>
				</Routes>
			</BrowserRouter>
			<ToggleThemeButton />
		</>
	);
}
