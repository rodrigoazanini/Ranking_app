import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./AllItemsPage.module.css";
import ItemCard from "../../components/ItemCard/ItemCard";
import { itemService } from "../../services/itemService";

const PAGE_SIZE = 10;

export function AllItemsPage() {
  const navigate = useNavigate();

  const [items, setItems]           = useState([]);
  const [page, setPage]             = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading]       = useState(true);

  useEffect(() => {
    let isMounted = true;

    async function load() {
      try {
        setLoading(true);
        const res = await itemService.getItems(page, PAGE_SIZE);
        if (!isMounted) return;
        setItems(Array.isArray(res?.content) ? res.content : []);
        setTotalPages(res?.totalPages ?? 1);
      } catch (err) {
        console.error("Error al cargar productos:", err);
        setItems([]);
      } finally {
        if (isMounted) setLoading(false);
      }
    }

    load();
    return () => { isMounted = false; };
  }, [page]);

  return (
    <div className={styles.page}>
      <div className={styles.gridContainer}>
      <h1 className={styles.title}>Todos los productos</h1>

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
