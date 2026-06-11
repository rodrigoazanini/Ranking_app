import { useState } from "react";
import styles from "./Navbar.module.css";

export default function Navbar({ setPage, searchQuery, setSearchQuery, user }) {
  const [menuOpen, setMenuOpen] = useState(false);


  const userMenuItems = [
    { label: "Principal",      icon: "🏠", page: "home"    },
    { label: "Mi perfil",      icon: "👤", page: "profile" },
    { label: "Sugerir producto", icon: "💡", page: "suggest" },
    { label: "Cerrar sesión",  icon: "🚪", page: "login"   },
  ];

  const adminMenuItems = [
    { label: "Principal",      icon: "🏠", page: "home"   },
    { label: "Administración", icon: "⚙️", page: "admin"  },
    { label: "Cerrar sesión",  icon: "🚪", page: "login"  },
  ];

  const menuItems = user?.admin ? adminMenuItems : userMenuItems;

  return (
    <header className={styles.navbar}>
      <div className={styles.inner}>

        <div className={styles.logo} onClick={() => setPage("home")}>
          <span className={styles.logoIcon}>⭐</span>
          <span className={styles.logoText}>RANKING APP</span>
        </div>

        <div className={styles.searchWrapper}>
          <span className={styles.searchIcon}>🔍</span>
          <input
            className={styles.searchInput}
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Buscar productos..."
          />
        </div>

        <div className={styles.menuWrapper}>
          <button className={styles.hamburger} onClick={() => setMenuOpen((o) => !o)}>
            <span className={`${styles.bar} ${menuOpen ? styles.open : ""}`} />
            <span className={`${styles.bar} ${menuOpen ? styles.open : ""}`} />
            <span className={`${styles.bar} ${menuOpen ? styles.open : ""}`} />
          </button>

          {menuOpen && (
            <div className={styles.dropdown}>
              {menuItems.map((item, i) => (
                <button key={i} className={styles.menuItem}
                  onClick={() => { setPage(item.page); setMenuOpen(false); }}>
                  <span className={styles.menuIcon}>{item.icon}</span>
                  {item.label}
                </button>
              ))}
            </div>
          )}
        </div>

      </div>
    </header>
  );
}
