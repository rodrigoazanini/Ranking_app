import styles from './HomePage.module.css';
import ItemCard from '../../components/ItemCard/ItemCard';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { itemService } from '../../services/itemService.js';
import { userService } from '../../services/userService.js';

export default function HomePage() {
  const navigate = useNavigate();

  const [topItemsByRanking, setTopItemsByRanking] = useState([]);
  const [topUsersByReviews, setTopUsersByReviews] = useState([]);
  const [lastItemsUploaded, setLastItemsUploaded] = useState([]);

  useEffect(() => {
    async function loadTops() {
      try {
        const rankingAmount = 5;
        const reviewsAmount = 5;
        const latestAmount = 5;
        const today = new Date().toISOString().slice(0, 10);

        const [rankingItems, rankedUsers, uploadedItems] = await Promise.all([
          itemService.getTopItemsByRanking(rankingAmount),
          userService.getTopUsersByReviews(reviewsAmount),
          itemService.getLatestItems(latestAmount),
          
        ]);

        setTopItemsByRanking(rankingItems);
        setTopUsersByReviews(rankedUsers);
        setLastItemsUploaded(Array.isArray(uploadedItems?.content) ? uploadedItems.content : []);
      } catch (error) {
        console.error('Error loading homepage data:', error);
      }
    }

    loadTops();
  }, []);

  return (
    <div className={styles.homePage}>
      <div className={styles.itemContainer}>

        <div className={styles.heroContainer}>
          <img className={styles.heroBanner} src="../public/images/stock/BANNER.webp" alt="BannerHeroFondo" />
          <p className={styles.heroTitle}>Bienvenido a Ranking App!</p>
        </div>

        {/* Tops*/}

        <div className={styles.topListContainer}>
          <div className={styles.topList}>
            <h1> ⭐Top 5 Productos </h1>
            {topItemsByRanking.map((item) => (
              <div key={item.id}>
                {item.name} - ⭐ {item.rankingAvg}
              </div>
            ))}
          </div>

          <div className={styles.topList}>
            <h1> 👤Top 5 Usuarios </h1>
            {topUsersByReviews.map((user) => (
              <div key={user.username}>
                ⭐ {user.username} - {user.reviewCount}
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

      </div>

      <div className={styles.gridContainer}>
        {lastItemsUploaded.map(item => (
          <ItemCard
            key={item.id}
            item={item}
            onClick={item => navigate(`/items/${item.id}`)}
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
