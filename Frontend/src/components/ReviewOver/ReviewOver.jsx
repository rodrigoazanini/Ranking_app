import styles from "./ReviewOver.module.css";
import Stars from "../Stars/Stars";

export default function ReviewCard({ review }) {
  return (
    <div className={styles.card}>
      <div className={styles.header}>
        <div>
          <p className={styles.userName}>{review.user_name}</p>
          <Stars value={review.ranking} size={16} />
        </div>
        <div className={styles.meta}>
          {review.price > 0 && <p className={styles.price}>Pagó ${review.price.toLocaleString()}</p>}
          <p className={styles.date}>{review.date}</p>
        </div>
      </div>
      <p className={styles.comment}>{review.comment}</p>
    </div>
  );
}
