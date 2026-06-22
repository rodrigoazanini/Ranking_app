import styles from "./HomePage.module.css";
import ItemCard from "../../components/ItemCard/ItemCard";
import {mock_items} from "../../data/mockData";
import Btn from "../../components/Btn/Btn";

export default function HomePage({ setPage, setSelectedItem, searchQuery }) {
  // TODO: reemplazar por → useEffect(() => api.get('/items').then(...), [])

return (
  <div className={styles.homePage}>
    <div className={styles.itemsContainer}>

  <div className={styles.hero}>
      <div className={styles.heroTitle}>
        <h1>Ranking App</h1>
      </div>

      <div className={styles.heroSubtitle}>
        <p>Reseña tu producto favorito para que llegue al top!</p>
      </div>


      <img src="../../assets/HERO3.png" alt="Hero" className={styles.heroImage}
      />

  </div>


<div className={styles.gridContainer}>
      <div className={styles.grid}>
        {mock_items.map((item) => (
          <ItemCard
            key={item.id}
            item={item}
            onClick={(item) => console.log(item)}
          />
        ))}
      </div>
        </div>
    </div>
  </div>
);
}