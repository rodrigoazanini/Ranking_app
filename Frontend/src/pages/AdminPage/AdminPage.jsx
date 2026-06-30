import { useEffect, useState } from "react";
import styles from "./AdminPage.module.css";
import Btn from "../../components/Btn/Btn";
import { useNavigate } from 'react-router-dom';
import { itemService } from "../../services/itemService";

const SUGGESTED_OPTIONS = [
  { value: "",         label: "Todos" },
  { value: "approved", label: "Aprobado" },
  { value: "pending",  label: "En revisión" },
  { value: "own",      label: "Propio" },
];

const ENABLED_OPTIONS = [
  { value: "",      label: "Todos" },
  { value: "true",  label: "Activo" },
  { value: "false", label: "Inactivo" },
];

export function AdminPage() {
  const navigate = useNavigate();

  const [items, setItems]           = useState([]);
  const [page, setPageNumber]       = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading]       = useState(true);
  const pageSize = 10;

  // Filtros
  const [search,    setSearch]    = useState("");
  const [debouncedSearch, setDebouncedSearch] = useState("");
  const [brand,     setBrand]     = useState("");
  const [debouncedBrand, setDebouncedBrand] = useState("");
  const [category,  setCategory]  = useState("");
  const [debouncedCategory, setDebouncedCategory] = useState("");
  const [suggested, setSuggested] = useState("");
  const [enabled,   setEnabled]   = useState("");
  const [debounceTimer, setDebounceTimer] = useState(null);

  useEffect(() => {
    let isMounted = true;

    async function loadItems() {
      try {
        setLoading(true);

        const hasFilters = debouncedSearch || debouncedBrand || debouncedCategory || suggested || enabled;
        let response;

        if (hasFilters) {
          const filters = {
            query: debouncedSearch,
            brand: debouncedBrand || null,
            category: debouncedCategory || null,
            suggested: suggested === "" ? null : (suggested === "approved" || suggested === "pending" ? true : null),
            enabled: enabled === "" ? null : (enabled === "true" ? true : enabled === "false" ? false : null)
          };
          response = await itemService.searchItems("/items/search/filter", filters, page, pageSize);
        } else {
          response = await itemService.getItems(page, pageSize);
        }

        if (!isMounted) return;
        setItems(Array.isArray(response?.content) ? response.content : []);
        setTotalPages(response?.totalPages ?? 1);
      } catch (error) {
        console.error("No se pudo cargar los items", error);
        setItems([]);
        setTotalPages(1);
      } finally {
        if (isMounted) setLoading(false);
      }
    }

    loadItems();
    return () => { isMounted = false; };
  }, [page, debouncedSearch, debouncedBrand, debouncedCategory, suggested, enabled]);

  useEffect(() => {
    if (debounceTimer) {
      clearTimeout(debounceTimer);
    }

    if (search.trim().length > 0 && search.trim().length < 3) {
      return;
    }

    const timer = setTimeout(() => {
      setDebouncedSearch(search);
      setPageNumber(0);
    }, 500);

    setDebounceTimer(timer);

    return () => {
      if (timer) clearTimeout(timer);
    };
  }, [search]);

  useEffect(() => {
    if (debounceTimer) {
      clearTimeout(debounceTimer);
    }

    if (brand.trim().length > 0 && brand.trim().length < 3) {
      return;
    }

    const timer = setTimeout(() => {
      setDebouncedBrand(brand);
      setPageNumber(0);
    }, 500);

    setDebounceTimer(timer);

    return () => {
      if (timer) clearTimeout(timer);
    };
  }, [brand]);

  useEffect(() => {
    if (debounceTimer) {
      clearTimeout(debounceTimer);
    }

    if (category.trim().length > 0 && category.trim().length < 3) {
      return;
    }

    const timer = setTimeout(() => {
      setDebouncedCategory(category);
      setPageNumber(0);
    }, 500);

    setDebounceTimer(timer);

    return () => {
      if (timer) clearTimeout(timer);
    };
  }, [category]);

  const hasActiveFilters = debouncedSearch || debouncedBrand || debouncedCategory || suggested || enabled;

  const clearFilters = () => {
    setSearch(""); setDebouncedSearch("");
    setBrand(""); setDebouncedBrand("");
    setCategory(""); setDebouncedCategory("");
    setSuggested(""); setEnabled("");
  };

  const handleNew     = () => navigate("/items/create", { state: { fromAdmin: true } });
  const handleEdit    = (item) => navigate("/items/edit/" + item.id, { state: { fromAdmin: true } });
  const handleApprove = (id) => setItems((prev) => prev.map((item) => item.id === id ? { ...item, enabled: true } : item));
  const handleDelete  = (id) => setItems((prev) => prev.filter((item) => item.id !== id));


  return (
    <div className={styles.page}>
      <h1 className={styles.pageTitle}>Panel de administración</h1>

      <div className={styles.actions}>
        <Btn variant="coral" onClick={handleNew}>+ Nuevo producto</Btn>
      </div>

      <div className={styles.tableWrapper}>


        <div className={styles.tableTitleRow}>
          <p className={styles.tableTitle}>Todos los productos</p>
          <div className={styles.searchBox}>
            <input
              className={styles.searchInput}
              type="text"
              placeholder="🔍 Buscar producto..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
          </div>
        </div>

        <div className={styles.controlsRow}>
          <div className={styles.paginationRow}>
            <button
              className={styles.paginationBtn}
              disabled={page === 0}
              onClick={() => setPageNumber((prev) => Math.max(prev - 1, 0))}
            >
              Anterior
            </button>
            <span className={styles.paginationInfo}>Página {page + 1} de {totalPages}</span>
            <button
              className={styles.paginationBtn}
              disabled={page + 1 >= totalPages}
              onClick={() => setPageNumber((prev) => prev + 1)}
            >
              Siguiente
            </button>
          </div>

          <div className={styles.filtersRow}>
            <input
              className={styles.filterInput}
              type="text"
              placeholder="Marca"
              value={brand}
              onChange={(e) => setBrand(e.target.value)}
            />
            <input
              className={styles.filterInput}
              type="text"
              placeholder="Categoría"
              value={category}
              onChange={(e) => setCategory(e.target.value)}
            />
            <select
              className={styles.filterSelect}
              value={suggested}
              onChange={(e) => setSuggested(e.target.value)}
            >
              {SUGGESTED_OPTIONS.map((o) => (
                <option key={o.value} value={o.value}>{o.label}</option>
              ))}
            </select>
            <select
              className={styles.filterSelect}
              value={enabled}
              onChange={(e) => setEnabled(e.target.value)}
            >
              {ENABLED_OPTIONS.map((o) => (
                <option key={o.value} value={o.value}>{o.label}</option>
              ))}
            </select>
            {hasActiveFilters && (
              <button className={styles.clearBtn} onClick={clearFilters}>✕ Limpiar</button>
            )}
          </div>
        </div>


        {loading ? (
          <p className={styles.empty}>Cargando productos...</p>
        ) : items.length === 0 ? (
          <p className={styles.empty}>No hay productos que coincidan.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Categoría</th>
                <th>Activo</th>
                <th>Sugeridos</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {items.map((item) => (
                <tr key={item.id}>
                  <td>
                    <p className={styles.itemName}>{item.name}</p>
                    <p className={styles.itemBrand}>{item.brand}</p>
                  </td>
                  <td>{item.categoryResponse?.name || item.category || "Sin categoría"}</td>
                  <td>
                    <span className={`${styles.badge} ${item.enabled ? styles.badgeEnabled : styles.badgeDisabled}`}>
                      {item.enabled ? "Activo" : "Inactivo"}
                    </span>
                  </td>
                  <td>
                    <span className={`${styles.badge} ${item.suggested ? styles.badgeSuggested : styles.badgeOwn}`}>
                      {item.suggested ? "Sugerido" : "Propio"}
                    </span>
                  </td>
                  <td>
                    <div className={styles.rowActions}>
                      <button className={styles.editBtn} onClick={() => handleEdit(item)}>✏️ Editar</button>
                      {item.suggested && !item.enabled && (
                        <button className={styles.approveBtn} onClick={() => handleApprove(item.id)}>Aprobar</button>
                      )}
                      <button className={styles.deleteBtn} onClick={() => handleDelete(item.id)}>Eliminar</button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}
