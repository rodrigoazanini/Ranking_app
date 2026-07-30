import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./UserProfilePage.module.css";
import Stars from "../../components/Stars/Stars";
import { getUser } from "../../services/apiService";
import { userService } from "../../services/userService";
import { favoriteService } from "../../services/favoriteService";

export function UserProfilePage() {
  const navigate = useNavigate();

  const [activeTab, setActiveTab] = useState("reviews");
  const [user, setUser] = useState(null);
  const [myReviews, setMyReviews] = useState([]);
  const [favorites, setFavorites] = useState([]);

  const [confirmRemoveId, setConfirmRemoveId] = useState(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        const decodedUser = getUser();
        setUser(decodedUser);

        const profileData = await userService.getUserProfile();
        setMyReviews(profileData.reviews);
        setFavorites(profileData.favorites);
      } catch (err) {
        console.error("Error loading user data:", err);
      }
    };

    loadData();
  }, []);

  const handleRemoveClick = (itemId) => {
    setConfirmRemoveId(itemId);
  };

  const confirmRemove = async () => {
    try {
      await favoriteService.removeFavorite(confirmRemoveId);
      setFavorites((prev) => prev.filter((item) => item.id !== confirmRemoveId));
    } catch (err) {
      console.error(err);
    } finally {
      setConfirmRemoveId(null);
    }
  };

  const cancelRemove = () => {
    setConfirmRemoveId(null);
  };

  const goToItem = (item) => {
    if (item && item.id) {
      navigate(`/items/${item.id}`);
    }
  };

  return (
    <div>

      {confirmRemoveId !== null && (
        <div className={styles.modalOverlay}>
          <div className={styles.modal}>
            <p className={styles.modalText}>
              ¿Realmente querés quitar este producto de tus favoritos?
            </p>
            <div className={styles.modalActions}>
              <button className={styles.modalBtnConfirm} onClick={confirmRemove}>
                Sí
              </button>
              <button className={styles.modalBtnCancel} onClick={cancelRemove}>
                No
              </button>
            </div>
          </div>
        </div>
      )}

      <div className={styles.header}>
        <div className={styles.avatar}>👤</div>
        <div>
          <p className={styles.userName}>{user?.username ?? "Username"}</p>
          <p className={styles.userEmail}>{user?.email ?? "Username@Email"}</p>
        </div>
      </div>

      <div className={styles.tabs}>
        <button
          className={`${styles.tab} ${styles.tabReviews} ${activeTab === "reviews" ? styles.active : ""}`}
          onClick={() => setActiveTab("reviews")}
        >
          <span className={styles.tabIcon}>💬</span>
          Mis reseñas ({myReviews.length})
        </button>
        <button
          className={`${styles.tab} ${styles.tabFavorites} ${activeTab === "favorites" ? styles.active : ""}`}
          onClick={() => setActiveTab("favorites")}
        >
          <span className={styles.tabIcon}>⭐</span>
          Favoritos ({favorites.length})
        </button>
      </div>

      <div className={styles.contentPanel}>
        {/* Tab: Reseñas */}
        {activeTab === "reviews" && (
          <div className={styles.tabContent}>
            {myReviews.length === 0 ? (
              <div className={styles.empty}>
                <span className={styles.emptyIcon}>🤷‍♂️</span>
                Todavía no escribiste ninguna reseña.
              </div>
            ) : (
              myReviews.map((r) => (
                <div
                  key={r.id}
                  className={styles.reviewItem}
                  onClick={() => goToItem(r.item)}
                >
                  <img
                    src={r.item.imageUrl}
                    alt={r.item.name}
                    className={styles.reviewItemImage}
                  />
                  <div className={styles.reviewItemBody}>
                    <div className={styles.reviewItemMeta}>
                      <Stars value={r.ranking} size={16} />
                      <a
                        className={styles.reviewItemLink}
                        onClick={(e) => e.stopPropagation()}
                        href={`/items/${r.item.id}`}
                      >
                        Producto #{r.item.id}
                      </a>
                      <span className={styles.reviewItemDate}>{r.date}</span>
                    </div>
                    <p className={styles.reviewItemName}>{r.item.name}</p>
                    <p className={styles.reviewItemComment}>{r.comment}</p>
                    {r.price > 0 && (
                      <span className={styles.reviewItemPrice}>
                        Precio al momento de la reseña ${r.price.toLocaleString()}
                      </span>
                    )}
                  </div>
                  {r.item.rankingPosition && (
                    <span className={styles.reviewItemRank}>#{r.item.rankingPosition}</span>
                  )}
                </div>
              ))
            )}
          </div>
        )}

        {/* Tab: Favoritos */}
        {activeTab === "favorites" && (
          <div className={styles.tabContent}>
            {favorites.length === 0 ? (
              <div className={styles.empty}>
                <span className={styles.emptyIcon}>💔</span>
                Todavía no agregaste ningún favorito
              </div>
            ) : (
              <div className={styles.favGrid}>
                {favorites.map((fav) => (
                  <div key={fav.id} className={styles.favCard}>
                    <img
                      src={fav.item.imageUrl}
                      alt={fav.item.name}
                      className={styles.favImage}
                      onClick={() => goToItem(fav.item)}
                    />
                    <button
                      className={styles.removeFavBtn}
                      onClick={() => handleRemoveClick(fav.id)}
                      title="Eliminar de favoritos"
                    >
                      ✕
                    </button>
                    <div className={styles.favInfo} onClick={() => goToItem(fav.item)}>
                      <p className={styles.favName}>{fav.item.name}</p>
                      <p className={styles.favBrand}>{fav.item.brand}</p>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}