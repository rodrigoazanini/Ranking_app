import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import styles from "./ItemDetailPage.module.css";
import Stars from "../../components/Stars/Stars";
import Btn from "../../components/Btn/Btn";
import ReviewOver from "../../components/ReviewOver/ReviewOver";
import { itemService } from "../../services/itemService";
import { reviewService } from "../../services/reviewService";
import { favoriteService } from "../../services/favoriteService";
import { getUser } from "../../services/apiService";

export default function ItemDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [item, setItem] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [submitted, setSubmitted] = useState(false);
  const [fav, setFav] = useState(false);
  const [favId, setFavId] = useState(null);

  const [newReview, setNewReview] = useState({
    comment: "",
    ranking: 0,
    price: "",
  });

  useEffect(() => {
    async function loadItem() {
      try {
        const response = await itemService.getItem(id);
        setItem(response ? response : null);

        const user = getUser();
        const result = await favoriteService.isFavorited(user.id, id);
        setFav(result.isFavorited);
        setFavId(result.favoriteId || null);

        const reviewsData = await reviewService.getReviewsByItemId(id);
        setReviews(reviewsData || []);
      } catch (err) {
        console.error("Failed to load item:", err);
        setItem(null);
      }
    }
    loadItem();
  }, [id]);

  if (!item) {
    return (
      <div className={styles.page}>
        <h2>Producto no encontrado</h2>
        <Btn variant="primary" onClick={() => navigate("/")}>
          Volver al inicio
        </Btn>
      </div>
    );
  }

  const toggleFavorite = async () => {
    try {
      const user = getUser();
      if (fav) {
        await favoriteService.removeFavorite(favId);
        setFavId(null);
      } else {
        const result = await favoriteService.addFavorite(item.id, user.id);
        setFavId(result.id);
      }
      setFav((prev) => !prev);
    } catch (err) {
      console.error("Error toggling favorite:", err);
    }
  };

  const submitReview = async () => {
    if (!newReview.comment || !newReview.ranking) return;

    try {
      const user = getUser();
      const data = {
        comment: newReview.comment,
        ranking: newReview.ranking,
        price: parseFloat(newReview.price) || 0,
        itemId: item.id,
        userId: user.id,
        date: new Date().toISOString().split("T")[0],
      };

      const created = await reviewService.createReview(data);
      setReviews((prev) => [created, ...prev]);

      const refreshed = await itemService.getItem(id);
      if (refreshed) setItem(refreshed);

      setNewReview({
        comment: "",
        ranking: 0,
        price: "",
      });

      setSubmitted(true);
      setTimeout(() => setSubmitted(false), 3000);
    } catch (err) {
      console.error("Error creating review:", err);
    }
  };

  return (
    <div className={styles.page}>
      <button className={styles.backBtn} onClick={() => navigate("/")}>
        Volver
      </button>

      <div className={styles.productCard}>
        <div className={styles.imageWrapper}>
          <img
            src={item.imageUrl}
            alt={item.name}
            className={styles.image}
          />

          <button className={styles.favBtn} onClick={toggleFavorite}>
            {fav ? "❤️" : "🤍"}
          </button>
        </div>

        <div className={styles.productInfo}>
          <div className={styles.badges}>
            <span className={styles.badgeCategory}>
              {item.categoryResponse?.name || "Sin categoría"}
            </span>

            <span className={styles.badgeBrand}>
              {item.brand}
            </span>
          </div>

          <h1 className={styles.productName}>{item.name}</h1>

          <div className={styles.priceBox}>
            <p className={styles.priceLabel}>RANGO DE PRECIO</p>

            <p className={styles.priceValue}>
              ${item.priceMin?.toLocaleString()} – $
              {item.priceMax?.toLocaleString()}
            </p>
          </div>

          {item.reviews.length > 0 && item.rankingAvg != null ? (
            <div className={styles.ratingRow}>
              <Stars value={item.rankingAvg} size={22} />

              <span className={styles.ratingValue}>
                {item.rankingAvg.toFixed(1)}
              </span>

              <span className={styles.ratingCount}>
                ({item.reviews.length} reseñas)
              </span>
            </div>
          ) : (
            <p className={styles.noReviews}>
              ¡Sé el primero en reseñarlo!
            </p>
          )}

          {item.description && (
            <p className={styles.description}>
              {item.description}
            </p>
          )}

          <Btn variant="primary" onClick={toggleFavorite}>
            {fav
              ? "❤️ En favoritos"
              : "🤍 Agregar a favoritos"}
          </Btn>
        </div>
      </div>


      <div className={styles.reviewForm}>
        <h3 className={styles.reviewFormTitle}>
          Escribir una reseña
        </h3>

        <div className={styles.fieldGroup}>
          <p className={styles.fieldLabel}>Tu puntuación</p>

          <Stars
            value={newReview.ranking}
            size={32}
            interactive
            onChange={(value) =>
              setNewReview({
                ...newReview,
                ranking: value,
              })
            }
          />
        </div>

        <div className={styles.fieldGroup}>
          <label className={styles.fieldLabel}>
            Precio que pagaste ($)
          </label>

          <input
            type="text"
            inputMode="numeric"
            className={styles.priceInput}
            placeholder="Ej: 650"
            value={newReview.price}
            onChange={(e) =>
              setNewReview({
                ...newReview,
                price: e.target.value,
              })
            }
          />
        </div>

        <div className={styles.fieldGroup}>
          <label className={styles.fieldLabel}>
            Comentario
          </label>

          <textarea
            rows={4}
            className={styles.textarea}
            placeholder="Escribí tu reseña acá"
            value={newReview.comment}
            onChange={(e) =>
              setNewReview({
                ...newReview,
                comment: e.target.value,
              })
            }
          />
        </div>

        {submitted && (
          <p className={styles.successMsg}>
            ¡Reseña publicada con éxito!
          </p>
        )}

        <Btn
          variant="coral"
          onClick={submitReview}
          disabled={
            !newReview.comment || !newReview.ranking
          }
        >
          Publicar reseña
        </Btn>
      </div>

      <h3 className={styles.reviewsTitle}>
        Reseñas ({reviews.length})
      </h3>

      <div className={styles.reviewsList}>
        {reviews.length > 0 ? (
          reviews.map((review) => (
            <ReviewOver
              key={review.id}
              review={review}
            />
          ))
        ) : (
          <p>Aún no hay reseñas para este producto.</p>
        )}
      </div>
    </div>
  );
}
