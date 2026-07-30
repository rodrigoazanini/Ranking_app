import { useEffect, useState } from "react";
import styles from "./AdminPage.module.css";
import Btn from "../../components/Btn/Btn";
import FilterInput from "../../components/Filters/FilterInput/FilterInput";
import FilterSelect from "../../components/Filters/FilterSelect/FilterSelect";
import FilterComboBox from "../../components/Filters/FilterComboBox/FilterComboBox";
import { useNavigate } from 'react-router-dom';
import { categoryService } from "../../services/categoryService";
import { useItems } from "../../hooks/useItems";
import { SUGGESTED_OPTIONS, ENABLED_OPTIONS } from "../../components/Filters/Options/Options";

export function AdminPage() {
  const navigate = useNavigate();

  const [page, setPageNumber]       = useState(0);
  const pageSize = 10;

  // Filtros
  const [filters, setFilters] = useState({
    search: "",
    brand: "",
    category: "",
    suggested: "",
    enabled: "",
  });
  const [categories, setCategories] = useState([]);
  const [debouncedSearch, setDebouncedSearch] = useState("");
  const [debouncedBrand, setDebouncedBrand] = useState("");
  const [debouncedCategory, setDebouncedCategory] = useState("");
  const [debounceTimer, setDebounceTimer] = useState(null);

  const updateFilter = (field, value) => {
    setFilters(prev => ({
      ...prev,
      [field]: value,
    }));
  };

  const { items, totalPages, loading, setItems } = useItems({
    page,
    pageSize,
    debouncedSearch,
    debouncedBrand,
    debouncedCategory,
    filters,
  });

  useEffect(() => {
    if (debounceTimer) {
      clearTimeout(debounceTimer);
    }

    if (filters.search.trim().length > 0 && filters.search.trim().length < 3) {
      return;
    }

    const timer = setTimeout(() => {
      setDebouncedSearch(filters.search);
      setPageNumber(0);
    }, 500);

    setDebounceTimer(timer);

    return () => {
      if (timer) clearTimeout(timer);
    };
  }, [filters.search]);

  useEffect(() => {
    if (debounceTimer) {
      clearTimeout(debounceTimer);
    }

    if (filters.brand.trim().length > 0 && filters.brand.trim().length < 3) {
      return;
    }

    const timer = setTimeout(() => {
      setDebouncedBrand(filters.brand);
      setPageNumber(0);
    }, 500);

    setDebounceTimer(timer);

    return () => {
      if (timer) clearTimeout(timer);
    };
  }, [filters.brand]);

  useEffect(() => {
    if (debounceTimer) {
      clearTimeout(debounceTimer);
    }

    if (filters.category.trim().length > 0 && filters.category.trim().length < 3) {
      return;
    }

    const timer = setTimeout(() => {
      setDebouncedCategory(filters.category);
      setPageNumber(0);
    }, 500);

    setDebounceTimer(timer);

    return () => {
      if (timer) clearTimeout(timer);
    };
  }, [filters.category]);

  useEffect(() => {
    async function loadCategories() {
      try {
        const data = await categoryService.getAllCategories();
        setCategories(data);
      } catch (error) {
        console.error("No se pudieron cargar las categorías", error);
      }
    }
    loadCategories();
  }, []);

  const hasActiveFilters = debouncedSearch || debouncedBrand || debouncedCategory || filters.suggested || filters.enabled;

  const clearFilters = () => {
    setFilters({
      search: "",
      brand: "",
      category: "",
      suggested: "",
      enabled: "",
    });
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
             <FilterInput
               className={styles.searchInput}
               value={filters.search}
               onChange={(value) => updateFilter("search", value)}
               placeholder="🔍 Buscar producto..."
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
           <FilterInput
               className={styles.filterInput}
               value={filters.brand}
               onChange={(value) => updateFilter("brand", value)}
               placeholder="Marca"
             />
              <FilterComboBox
                className={styles.filterInput}
                value={filters.category}
                onChange={(value) => updateFilter("category", value)}
                options={categories.map((c) => ({ value: c.name, label: c.name }))}
                placeholder="Categoría"
              />
             <FilterSelect
               className={styles.filterSelect}
               value={filters.suggested}
               onChange={(value) => updateFilter("suggested", value)}
               options={SUGGESTED_OPTIONS}
             />
             <FilterSelect
               className={styles.filterSelect}
               value={filters.enabled}
               onChange={(value) => updateFilter("enabled", value)}
               options={ENABLED_OPTIONS}
             />
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
