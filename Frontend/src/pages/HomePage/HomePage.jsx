import styles from "./HomePage.module.css";
import { MOCK_ITEMS } from "../../data/mockData";
import ItemCard from "../../components/ItemCard/ItemCard";
import Btn from "../../components/Btn/Btn";

export default function HomePage({ setPage, setSelectedItem, searchQuery }) {
  // TODO: reemplazar por → useEffect(() => api.get('/items').then(...), [])
  const filtered = MOCK_ITEMS.filter(
    (i) => i.enabled && (
      i.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      i.brand.toLowerCase().includes(searchQuery.toLowerCase())
    )
  );

  const topItems = MOCK_ITEMS
    .filter((i) => i.enabled && i.rankingAvg != null)
    .sort((a, b) => b.rankingAvg - a.rankingAvg)
    .slice(0, 3);

  const medalClass = [styles.medal1, styles.medal2, styles.medal3];

  const handleItemClick = (item) => { setSelectedItem(item); setPage("item"); };

  return (
    <div className={styles.page}>
      <div className={styles.hero}>
        <p className={styles.heroEyebrow}>BIENVENIDO A</p>
        <h1 className={styles.heroTitle}>Ranking App</h1>
        <p className={styles.heroSubtitle}>Descubrí, evaluá y compartí los mejores productos argentinos.</p>
        <Btn variant="ghost" onClick={() => setPage("suggest")}>Sugerir un producto</Btn>
      </div>

      <section className={styles.section}>
        <h2 className={styles.sectionTitle}>
          <span className={styles.topBadge}>TOP</span>
          PRODUCTOS MÁS POPULARES
        </h2>
        <div className={styles.topList}>
          {topItems.map((item, i) => (
            <div key={item.id} className={styles.medalWrapper}>
              <div className={`${styles.medal} ${medalClass[i]}`}>#{i+1}</div>
              <ItemCard item={item} onClick={handleItemClick} />
            </div>
          ))}
        </div>
      </section>

      <section className={styles.section}>
        <h2 className={styles.sectionTitle}>
          {searchQuery ? `Resultados para "${searchQuery}"` : "TODOS LOS PRODUCTOS"}
        </h2>
        {filtered.length === 0
          ? <p className={styles.empty}>No se encontraron productos 😕</p>
          : <div className={styles.grid}>{filtered.map((item) => <ItemCard key={item.id} item={item} onClick={handleItemClick} />)}</div>
        }
      </section>
    </div>
  );
}
