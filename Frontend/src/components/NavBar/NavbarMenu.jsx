import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import styles from "./NavbarMenu.module.css";

export default function NavbarMenu({ user = null }) {
  const navigate = useNavigate();
  const location = useLocation();
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => {
    setMenuOpen(false);
  }, [location.pathname]);

  const token = localStorage.getItem("token");

  const guestMenuItems = [
    { label: "Principal", to: "/" },
    { label: "Iniciar sesion", to: "/auth/login" },
    { label: "Registrarse", to: "/auth/register" },
  ];

  const userMenuItems = [
    { label: "Principal", to: "/" },
    { label: "Mi perfil", to: "/profile" },
    { label: "Sugerir producto", to: "/items/suggest" },
    { label: "Cerrar sesion", action: "logout" },
  ];

  const adminMenuItems = [
    { label: "Principal", to: "/" },
    { label: "Administracion", to: "/admin/items" },
    { label: "Cerrar sesion", action: "logout" },
  ];

  const menuItems = user?.admin ? adminMenuItems : token ? userMenuItems : guestMenuItems;

  function handleMenuItemClick(item) {
    setMenuOpen(false);

    if (item.action === "logout") {
      localStorage.removeItem("token");
      navigate("/auth/login");
      return;
    }

    if (item.to) {
      navigate(item.to);
    }
  }

  return (
    <div className={styles.menuWrapper}>
      <button
        type="button"
        className={styles.hamburger}
        aria-label="Abrir menu"
        aria-expanded={menuOpen}
        onClick={() => setMenuOpen((open) => !open)}
      >
        <span className={styles.bar} />
        <span className={styles.bar} />
        <span className={styles.bar} />
      </button>

      {menuOpen && (
        <div className={styles.dropdown}>
          {menuItems.map((item) => (
            <button
              key={item.label}
              type="button"
              className={`${styles.menuItem} ${item.action === "logout" ? styles.menuItemDanger : ""
                }`}
              onClick={() => handleMenuItemClick(item)}
            >
              <span className={styles.menuIcon} aria-hidden="true" />
              {item.label}
            </button>
          ))}
        </div>
      )}
    </div>
  );
}
