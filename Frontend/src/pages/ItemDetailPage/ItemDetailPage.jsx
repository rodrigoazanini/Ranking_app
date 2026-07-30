import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import styles from "./ItemDetailPage.module.css";
import Stars from "../../components/Stars/Stars";
import Btn from "../../components/Btn/Btn";
import ReviewOver from "../../components/ReviewOver/ReviewOver";
import { itemService } from "../../services/itemService";
import { reviewService } from "../../services/reviewService";
import { favoriteService } from "../../services/favoriteService";
import { getUser, getRole } from "../../services/apiService";

export default function ItemDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [item, setItem] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [submitted, setSubmitted] = useState(false);
  const [fav, setFav] = useState(false);
  const [favId, setFavId] = useState(null);
  const [copied, setCopied] = useState(false);
  const [showIncompletePopup, setShowIncompletePopup] = useState(false);

  const [newReview, setNewReview] = useState({
    comment: "",
    ranking: 0,
    price: "",
  });

  const role = getRole();
  const isAdmin = role === true;

  useEffect(() => {
    async function loadItem() {
      try {
        const response = await itemService.getItem(id);
        setItem(response ? response : null);

        if (!isAdmin) {
          const user = getUser();
          const result = await favoriteService.isFavorited(user.id, id);
          setFav(result.isFavorited);
          setFavId(result.favoriteId || null);
        }

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

  const isReviewIncomplete = () => {
    return (
      !newReview.comment.trim() ||
      !newReview.ranking ||
      newReview.price === "" ||
      newReview.price === null ||
      newReview.price === undefined
    );
  };

  const submitReview = async () => {
    if (isReviewIncomplete()) {
      setShowIncompletePopup(true);
      return;
    }

    try {
      const user = getUser();
const normalizedPrice = newReview.price.replace(",", ".");
const price = Number(parseFloat(normalizedPrice).toFixed(2));

      const data = {
        comment: newReview.comment,
        ranking: newReview.ranking,
        price: price,
        itemId: item.id,
        userId: user.id,
        date: new Date().toISOString().split("T")[0],
      };
      const created = await reviewService.createReview(data);
      setReviews((prev) => [created, ...prev]);

      const refreshed = await itemService.getItem(id);
      if (refreshed) setItem(refreshed);

      setNewReview({ comment: "", ranking: 0, price: "" });
      setSubmitted(true);
      setTimeout(() => setSubmitted(false), 3000);
    } catch (err) {
      console.error("Error creando reseña", err);
    }
  };

  const handleEditReview = () => {
    // Cierra el popup y deja los datos que ya escribió para que los complete
    setShowIncompletePopup(false);
  };

  const handleCancelReview = () => {
    // Cierra el popup y limpia el formulario, volviendo a la vista normal
    setShowIncompletePopup(false);
    setNewReview({ comment: "", ranking: 0, price: "" });
  };

  // --- Datos derivados para la sidebar ---
  const reviewsWithPrice = reviews.filter((r) => r.price);
  const avgPrice =
    reviewsWithPrice.length > 0
      ? reviewsWithPrice.reduce((sum, r) => sum + r.price, 0) / reviewsWithPrice.length
      : item.priceMin != null && item.priceMax != null
      ? (item.priceMin + item.priceMax) / 2
      : null;

  const rankingPosition = item.rankingPosition ?? null;

  const shareUrl = window.location.href;
  const shareText = `Mirá "${item.name}" en Ranking App`;

  const handleCopyLink = async () => {
    try {
      await navigator.clipboard.writeText(shareUrl);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    } catch (err) {
      console.error("No se pudo copiar el link:", err);
    }
  };

  return (
    <div className={styles.page}>

      {showIncompletePopup && (
        <div className={styles.modalOverlay}>
          <div className={styles.modal}>
            <p className={styles.modalText}>
              Algunos campos están vacíos, por favor complételos para publicar su reseña
            </p>
            <div className={styles.modalActions}>
              <button className={styles.modalBtnConfirm} onClick={handleEditReview}>
                Editar reseña
              </button>
              <button className={styles.modalBtnCancel} onClick={handleCancelReview}>
                Cancelar
              </button>
            </div>
          </div>
        </div>
      )}

      <button className={styles.backBtn} onClick={() => navigate("/")}>
        Volver
      </button>

      <div className={styles.layout}>
        {/* ---------- COLUMNA PRINCIPAL ---------- */}
        <div className={styles.main}>
          <div className={styles.productCard}>
            <div className={styles.imageWrapper}>
              <img src={item.imageUrl} alt={item.name} className={styles.image} />

              {!isAdmin && (
                <button className={styles.favBtn} onClick={toggleFavorite}>
                  {fav ? "❤️" : "🤍"}
                </button>
              )}
            </div>

            <div className={styles.productInfo}>
              <div className={styles.badges}>
                <span className={styles.badgeCategory}>
                  {item.categoryResponse?.name || "Sin categoría"}
                </span>
                <span className={styles.badgeBrand}>{item.brand}</span>
              </div>

              <h1 className={styles.productName}>{item.name}</h1>

              {item.reviews.length > 0 && item.rankingAvg != null ? (
                <div className={styles.ratingRow}>
                  <Stars value={item.rankingAvg} size={22} />
                  <span className={styles.ratingValue}>{item.rankingAvg.toFixed(1)}</span>
                  <span className={styles.ratingCount}>({item.reviews.length} reseñas)</span>
                </div>
              ) : (
                <p className={styles.noReviews}>¡Sé el primero en reseñarlo!</p>
              )}

              <div className={styles.priceBox}>
                <p className={styles.priceLabel}>RANGO DE PRECIO</p>
                <p className={styles.priceValue}>
                  ${item.priceMin?.toLocaleString()} – ${item.priceMax?.toLocaleString()}
                </p>
              </div>

              {item.description && (
                <p className={styles.description}>{item.description}</p>
              )}

              {!isAdmin && (
                <Btn variant="primary" onClick={toggleFavorite}>
                  {fav ? "❤️ En favoritos" : "🤍 Agregar a favoritos"}
                </Btn>
              )}
            </div>
          </div>

          {!isAdmin && (
            <div className={styles.reviewForm}>
              <h3 className={styles.reviewFormTitle}>Escribir una reseña</h3>

              <div className={styles.reviewFormGrid}>
                <div className={styles.fieldGroup}>
                  <p className={styles.fieldLabel}>Tu puntuación</p>
                  <Stars
                    value={newReview.ranking}
                    size={28}
                    interactive
                    onChange={(value) => setNewReview({ ...newReview, ranking: value })}
                  />
                </div>

    <div className={styles.fieldGroup}>
                  <label className={styles.fieldLabel}>Precio que pagaste ($)</label>
                  <input
                    type="text"
                    inputMode="decimal"
                    className={styles.priceInput}
                    placeholder="Ej: 650"
                    value={newReview.price}
                    onChange={(e) => {
                      let value = e.target.value.replace(/[^0-9.,]/g, "");

                      // Reemplaza coma por punto para trabajar internamente
                      value = value.replace(",", ".");

                      // Solo un punto decimal y hasta 2 decimales
                      if (/^\d*\.?\d{0,2}$/.test(value)) {
                        setNewReview({ ...newReview, price: value });
                      }
                    }}
                  />
                </div>

                <div className={`${styles.fieldGroup} ${styles.commentField}`}>
                  <label className={styles.fieldLabel}>Comentario</label>
                  <textarea
                    rows={3}
                    className={styles.textarea}
                    placeholder="Contanos tu experiencia con este producto..."
                    value={newReview.comment}
                    onChange={(e) => setNewReview({ ...newReview, comment: e.target.value })}
                  />
                </div>
              </div>

              {submitted && (
                <p className={styles.successMsg}>¡Reseña publicada con éxito!</p>
              )}

              <Btn variant="coral" onClick={submitReview}>
                Publicar reseña ➤
              </Btn>
            </div>
          )}

          <h3 className={styles.reviewsTitle}>Reseñas ({reviews.length})</h3>

          <div className={styles.reviewsList}>
            {reviews.length > 0 ? (
              reviews.map((review) => (
                <ReviewOver key={review.id} review={review} />
              ))
            ) : (
              <p>Aún no hay reseñas para este producto.</p>
            )}
          </div>
        </div>

        {/* ---------- SIDEBAR ---------- */}
        <aside className={styles.sidebar}>

          <div className={styles.statCard}>
            <div className={`${styles.statIcon} ${styles.statIconYellow}`}>🏷️</div>
            <div>
              <p className={styles.statLabel}>PRECIO PROMEDIO</p>
              <p className={styles.statValue}>
                {avgPrice != null
                  ? `$${avgPrice.toLocaleString(undefined, {
                      minimumFractionDigits: 1,
                      maximumFractionDigits: 2,
                    })}`
                  : "—"}
                              </p>
              <p className={styles.statSub}>Basado en reseñas de usuarios</p>
            </div>
          </div>

          <div className={styles.statCard}>
            <div className={`${styles.statIcon} ${styles.statIconBlue}`}>💬</div>
            <div>
              <p className={styles.statLabel}>CANTIDAD DE RESEÑAS</p>
              <p className={styles.statValue}>{reviews.length}</p>
              <p className={styles.statSub}>Reseñas verificadas</p>
            </div>
          </div>

          <div className={styles.shareCard}>
            <h4 className={styles.shareTitle}>Compartí este producto</h4>
            <p className={styles.shareSub}>
              Ayudá a otros usuarios a encontrar los mejores precios
            </p>
            <div className={styles.shareIcons}>
              <a
                className={`${styles.shareIcon} ${styles.shareWhatsapp}`}
                href={`https://wa.me/?text=${encodeURIComponent(shareText + " " + shareUrl)}`}
                target="_blank"
                rel="noreferrer"
                aria-label="Compartir por WhatsApp"
              >
                💬
              </a>
              <a
                className={`${styles.shareIcon} ${styles.shareFacebook}`}
                href={`https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(shareUrl)}`}
                target="_blank"
                rel="noreferrer"
                aria-label="Compartir en Facebook"
              >
                f
              </a>
              <a
                className={`${styles.shareIcon} ${styles.shareTwitter}`}
                href={`https://twitter.com/intent/tweet?text=${encodeURIComponent(shareText)}&url=${encodeURIComponent(shareUrl)}`}
                target="_blank"
                rel="noreferrer"
                aria-label="Compartir en X"
              >
                𝕏
              </a>
              <button
                className={`${styles.shareIcon} ${styles.shareLink}`}
                onClick={handleCopyLink}
                aria-label="Copiar enlace"
                type="button"
              >
                🔗
              </button>
            </div>
            {copied && <p className={styles.copiedMsg}>¡Enlace copiado!</p>}
          </div>
        </aside>
      </div>
    </div>
  );
}