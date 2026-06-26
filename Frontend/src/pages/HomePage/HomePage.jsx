import styles from './HomePage.module.css';
import ItemCard from '../../components/ItemCard/ItemCard';
import { mock_items } from '../../data/mockData';
import Btn from '../../components/Btn/Btn';
import { useNavigate } from 'react-router-dom';
import {AdminPage} from '../AdminPage/AdminPage.jsx'

export default function HomePage({ searchQuery }) {
  // TODO: reemplazar por → useEffect(() => api.get('/items').then(...), [])
  const navigate = useNavigate();

  const top5Items = [...mock_items]
    .filter(item => item.rankingAvg !== null)
    .sort((a, b) => b.rankingAvg - a.rankingAvg)
    .slice(0, 5);

  return (
    <div className={styles.homePage}>
      <div className={styles.itemContainer}>

<div className={styles.heroContainer}>
      <img className={styles.heroBanner}src="../public/images/stock/BANNER.avif" alt="BannerHeroFondo" />
    <h1 className= {styles.heroTitle}>Bienvenido a Ranking App!</h1>
</div>

        {/* Tops*/}

        <div className={styles.topListContainer}>
          <div className={styles.topList}>
            <h1> ⭐Top 5 Productos </h1>
            {top5Items.map((item) => (
              <div key={item.id}>
                {item.name} - ⭐ {item.rankingAvg}
              </div>
            ))}
          </div>


          <div className={styles.topList}>
            <h1> 👤Top 5 Usuarios </h1>
            {top5Items.map((item) => (
              <div key={item.id}>
                {item.name} - ⭐ {item.rankingAvg}
              </div>
            ))}
          </div>
        </div>

        <picture>
          <source srcSet={"./images/stock/HERO3.avif"} type="image/avif" />
          <img
            src="./images/stock/HERO3.png"
            alt="Hero"
            className={styles.heroImage}
          />
        </picture>

        {/* <img
          src="./public/image/stock/HERO3.png"
          alt="Hero"
          className={styles.heroImage}
        /> */}

      </div>

      <div className={styles.gridContainer}>
        {mock_items.map(item => (
          <ItemCard
            key={item.id}
            item={item}
            onClick={item => navigate(`/item/${item.id}`)}
          />
        ))}
      </div>


      <picture>
        <source srcSet={"./images/stock/HERO2.avif"} type="image/avif" />
        <img
          src="./images/stock/HERO2.png"
          alt="Hero"
          className={styles.heroImage}
        />
      </picture>

    </div>
    
  );
}
