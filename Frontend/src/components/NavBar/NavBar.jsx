import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import styles from "./NavBar.module.css";
import NavbarMenu from "./NavbarMenu";
import { getUser } from "../../services/apiService";
import { itemService } from "../../services/itemService";

export default function Navbar({ searchQuery, setSearchQuery }) {

  const navigate = useNavigate();
  const [localSearch, setLocalSearch] = useState("");
  const currentSearch = searchQuery !== undefined ? searchQuery : localSearch;
  const [searchResults, setSearchResults] = useState([]);
  const [showDropdown, setShowDropdown] = useState(false);
  const [debounceTimer, setDebounceTimer] = useState(null);

  const [user, setUser] = useState(null)

  function handleBrandClick() {
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

    // Clear previous timer
    if (debounceTimer) {
      clearTimeout(debounceTimer);
    }

    // Check minimum 3 characters
    if (nextValue.trim().length < 3) {
      setSearchResults([]);
      setShowDropdown(false);
      return;
    }

    // Debounce API call by 500ms
    const timer = setTimeout(async () => {
      try {
        const results = await itemService.searchItems('/items/search', { query: nextValue }, 0, 5);
        setSearchResults(results.content || []);
        setShowDropdown(true);
      } catch (error) {
        console.error('Search error:', error);
        console.log('Query sent:', nextValue);
        setSearchResults([]);
      }
    }, 500);

    setDebounceTimer(timer);
  }

  function handleItemClick(itemId) {
    navigate(`/items/${itemId}`);
    setShowDropdown(false);
    setLocalSearch("");
  }

  function goToAllItems() {
    const query = currentSearch.trim();
    if (!query) return;

    setShowDropdown(false);
    navigate(`/items?search=${encodeURIComponent(query)}`);
  }

  function handleSearchKeyDown(event) {
    if (event.key === "Enter") {
      event.preventDefault();
      goToAllItems();
    }
  }

  const location = useLocation()
  useEffect(() => {
    setUser(getUser());
    setShowDropdown(false);
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
            <button
              type="button"
              className={styles.searchIconBtn}
              onClick={goToAllItems}
              aria-label="Buscar"
            >
              🔍
            </button>
            <input
              className={styles.searchInput}
              type="search"
              value={currentSearch}
              onChange={handleSearchChange}
              onKeyDown={handleSearchKeyDown}
              placeholder="Buscar productos"
            />
            {showDropdown && searchResults.length > 0 && (
              <div className={styles.searchDropdown}>
                {searchResults.map((item) => (
                  <button
                    key={item.id}
                    className={styles.searchResultItem}
                    onClick={() => handleItemClick(item.id)}
                  >
                    <span className={styles.itemName}>{item.name}</span>
                    {item.rankingAvg && (
                      <span className={styles.itemRating}>⭐ {item.rankingAvg.toFixed(1)}</span>
                    )}
                  </button>
                ))}
              </div>
            )}
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