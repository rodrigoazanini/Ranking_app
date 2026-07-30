import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./AllItemsPage.module.css";
import ItemCard from "../../components/ItemCard/ItemCard";
import FilterInput from "../../components/Filters/FilterInput/FilterInput";
import FilterComboBox from "../../components/Filters/FilterComboBox/FilterComboBox";
import { categoryService } from "../../services/categoryService";
import { useItems } from "../../hooks/useItems";

const PAGE_SIZE = 10;

export function AllItemsPage() {
  const navigate = useNavigate();

  const [page, setPage] = useState(0);

  const [filters, setFilters] = useState({
    search: "",
    brand: "",
    category: "",
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

  const { items, totalPages, loading } = useItems({
    page,
    pageSize: PAGE_SIZE,
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
      setPage(0);
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
      setPage(0);
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
      setPage(0);
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

  const hasActiveFilters = debouncedSearch || debouncedBrand || debouncedCategory;

  const clearFilters = () => {
    setFilters({
      search: "",
      brand: "",
      category: "",
    });
  };

  return (
    <div className={styles.page}>
      <div className={styles.gridContainer}>
        <h1 className={styles.title}>Todos los productos</h1>

        <div className={styles.filtersRow}>
          <FilterInput
            className={styles.filterInput}
            value={filters.search}
            onChange={(value) => updateFilter("search", value)}
            placeholder="🔍 Buscar producto..."
          />
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
          {hasActiveFilters && (
            <button className={styles.clearBtn} onClick={clearFilters}>✕ Limpiar</button>
          )}
        </div>

        {loading ? (
          <p className={styles.empty}>Cargando...</p>
        ) : items.length === 0 ? (
          <p className={styles.empty}>No hay productos disponibles.</p>
        ) : (
          <div className={styles.grid}>
            {items.map(item => (
              <ItemCard
                key={item.id}
                item={item}
                onClick={item => navigate(`/items/${item.id}`)}
              />
            ))}
          </div>
        )}
      </div>
      <div className={styles.pagination}>
        <button
          className={styles.pageBtn}
          disabled={page === 0}
          onClick={() => setPage(p => Math.max(p - 1, 0))}
        >
          Anterior
        </button>
        <span className={styles.pageInfo}>Página {page + 1} de {totalPages}</span>
        <button
          className={styles.pageBtn}
          disabled={page + 1 >= totalPages}
          onClick={() => setPage(p => p + 1)}
        >
          Siguiente
        </button>
      </div>
    </div>
  );
}
