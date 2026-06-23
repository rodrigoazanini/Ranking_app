import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./NavBar.module.css";
import NavbarMenu from "./NavbarMenu";
import {jwtDecode} from "jwt-decode";

export default function Navbar({searchQuery, setSearchQuery}) {
  const token = localStorage.getItem("token");
  const decodedJwt = token ? jwtDecode(token) : null;

  const navigate = useNavigate();
  const [localSearch, setLocalSearch] = useState("");
  const currentSearch = searchQuery !== undefined ? searchQuery : localSearch;

  function handleBrandClick() {;
    navigate("/");
  }

  function handleSearchChange(event) {
    const nextValue = event.target.value;

    if (typeof setSearchQuery === "function") {
      setSearchQuery(nextValue);
    }

    if (searchQuery === undefined) {
      setLocalSearch(nextValue);
    }
  }

  return (
    <header className={styles.navbar}>
      <div className={styles.inner}>
        <button type="button" className={styles.brand} onClick={handleBrandClick}>
          <img src="../../assets/logo.webp" alt="Logo" className={styles.logoImage} /> 
          <span className={styles.logoText}>RANKING APP</span>
        </button>

        <div className={styles.actions}>
          <div className={styles.searchWrapper}>
            <input
              className={styles.searchInput}
              type="search"
              value={currentSearch}
              onChange={handleSearchChange}
              placeholder="Buscar productos"
            />
          </div>

          {
            decodedJwt && (
              <p className={styles.welcomeMessage}>
                Hola, {decodedJwt.username}
                </p>
            )
          }

          <NavbarMenu user={decodedJwt} />
        </div>
      </div>
    </header>
  );
}