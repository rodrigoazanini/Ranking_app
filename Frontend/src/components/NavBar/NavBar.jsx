import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import styles from "./NavBar.module.css";
import NavbarMenu from "./NavbarMenu";
import { getUser } from "../../services/apiService";

export default function Navbar({ searchQuery, setSearchQuery }) {


  const navigate = useNavigate();
  const [localSearch, setLocalSearch] = useState("");
  const currentSearch = searchQuery !== undefined ? searchQuery : localSearch;

  const [user, setUser] = useState(null)

  function handleBrandClick() {
    ;
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

  const location = useLocation()
  useEffect(() => {
    setUser(getUser());
  }, [location])

  return (
    <header className={styles.navbar}>
      <div className={styles.inner}>
        <button type="button" className={styles.brand} onClick={handleBrandClick}>
          <picture>
            <source srcSet="/images/stock/logo.avif" type="image/avif" />
            <img src="/images/stock/logo.png" alt="Logo" className={styles.logoImage} />
          </picture>
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
            user && (
              <p className={styles.welcomeMessage}>
                Hola, {user.username}
              </p>
            )
          }

          <NavbarMenu user={user} />
        </div>
      </div>
    </header>
  );
}