import { useState } from "react";
import "./index.css";

import Navbar from "./components/Navbar/Navbar";
import LoginPage from "./pages/LoginPage/LoginPage";
import HomePage from "./pages/HomePage/HomePage";
import ItemDetailPage from "./pages/ItemDetailPage/ItemDetailPage";
import SuggestItemPage from "./pages/SuggestItemPage/SuggestItemPage";
import NewItemPage from "./pages/NewItemPage/NewItemPage";
import EditItemPage from "./pages/EditItemPage/EditItemPage";

export default function App(){
	const [page, setPage] = useState("login");
	const [selectedItem, setSelectedItem] = useState(null);
	const [serchQuery, setSearchQuery] = useState("")}

	const showNav = page !== "login";
	
	return (
		<>
		{showNav && (
			<Navbar
				setPage={setpage}
				searchQuery={searchQuery}
				setSearchQuery={setSearchQuery}
			/>
		)}
	

{page === "login" && <LoginPage setPage={setPage} />}
{page === "home" && <HomePage 
		setPage={setPage} 	
		setSelectedItem={setSelectedItem} 
		searchQuery={searchQuery} />}	
{page === "item" && selectedItem && <ItemDetailPage 
	item={selectedItem}
	setPage={setPage} />}
{page === "suggest" && <SuggestItemPage setPage={setPage} />}
{page === "new" && <NewItemPage setPage={setPage} />}
{page === "edit" && selectedItem && <EditItemPage setPage={setPage} selectedItem={selectedItem} />}
</>	
);
