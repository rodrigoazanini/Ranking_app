import styles from "./ItemCard.module.css";
import Stars from "../Stars/Stars";

export default function ItemCard({ item, onClick }) {
  const hasReviews = item.rankingAvg != null;

  return (
    <div className={styles.card} onClick={() => onClick(item)}>
      <div className={styles.imageWrapper}>
        <img src={item.image} alt={item.name} className={styles.image} />
        <span className={styles.categoryBadge}>{item.category}</span>
      </div>
      <div className={styles.info}>
        <p className={styles.name}>{item.name.toUpperCase()}</p>
        {hasReviews ? (
          <>
            <Stars value={item.rankingAvg} size={14} />
            <p className={styles.price}>
              ${item.priceMin?.toLocaleString()} – ${item.priceMax?.toLocaleString()}
            </p>
          </>
        ) : (
          <p className={styles.firstReview}>¡Sé el primero en reseñarlo!</p>
        )}
      </div>
    </div>
  );
}
