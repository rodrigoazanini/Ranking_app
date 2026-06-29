import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./UserProfilePage.module.css";
import Stars from "../../components/Stars/Stars";
import { API_URL, getHeaders, getUser } from "../../services/apiService";

export function UserProfilePage() {
  const navigate = useNavigate();

  const [activeTab, setActiveTab] = useState("reviews");
  const [user, setUser] = useState(null);
  const [myReviews, setMyReviews] = useState([]);
  const [favorites, setFavorites] = useState([]);

  const [confirmRemoveId, setConfirmRemoveId] = useState(null);

  useEffect(() => {
    fetchUser();
    fetchMyReviews();
    fetchFavorites();
  }, []);

  const fetchUser = async () => {
    try {
      const decoded = getUser();
      setUser(decoded);
    } catch (err) {
      console.error(err);
    }
  };

  const fetchMyReviews = async () => {
    try {
      const res = await fetch(`${API_URL}/reviews/my`, { headers: getHeaders() });
      if (!res.ok) throw new Error("Error al cargar reseñas");
      const data = await res.json();
      setMyReviews(data);
    } catch (err) {
      console.error(err);
    }
  };

  const fetchFavorites = async () => {
    try {
      const res = await fetch(`${API_URL}/favorites`, { headers: getHeaders() });
      if (!res.ok) throw new Error("Error al cargar favoritos");
      const data = await res.json();
      setFavorites(data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleRemoveClick = (itemId) => {
    setConfirmRemoveId(itemId);
  };

  const confirmRemove = async () => {
    try {
      const res = await fetch(`${API_URL}/favorites/${confirmRemoveId}`, {
        method: "DELETE",
        headers: getHeaders(),
      });
      if (!res.ok) throw new Error("Error al eliminar favorito");
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
    navigate(`/item/${item.id}`);
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
          <p className={styles.userName}>{user?.name ?? "Username"}</p>
          <p className={styles.userEmail}>{user?.email ?? "Username@Email"}</p>
        </div>
      </div>


      <div className={styles.tabs}>
        <button
          className={`${styles.tab} ${activeTab === "reviews" ? styles.active : ""}`}
          onClick={() => setActiveTab("reviews")}
        >
          Mis reseñas ({myReviews.length})
        </button>
        <button
          className={`${styles.tab} ${activeTab === "favorites" ? styles.active : ""}`}
          onClick={() => setActiveTab("favorites")}
        >
          Favoritos ({favorites.length})
        </button>
      </div>

      {/* Tab: Reseñas */}
      {activeTab === "reviews" && (
        <div className={styles.empty}>
          {myReviews.length === 0 ? (
            <div> 🤷‍♂️ Todavía no escribiste ninguna reseña.</div>
          ) : (
            myReviews.map((r) => (
              <div
                key={r.id}
                className={styles.reviewItem}
                onClick={() => goToItem(r.item)}
              >
                <img
                  src={r.item.image}
                  alt={r.item.name}
                  className={styles.reviewItemImage}
                />
                <div className={styles.reviewItemBody}>
                  <p className={styles.reviewItemName}>{r.item.name}</p>
                  <div className={styles.reviewItemMeta}>
                    <Stars value={r.ranking} size={14} />
                    {r.price > 0 && (
                      <span className={styles.reviewItemPrice}>
                        Precio al momento de la reseña ${r.price.toLocaleString()}
                      </span>
                    )}
                    <span className={styles.reviewItemDate}>{r.date}</span>
                  </div>
                  <p className={styles.reviewItemComment}>{r.comment}</p>
                </div>
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
			  Todavía no agregaste ningun favorito 
            </div>
          ) : (
            <div className={styles.favGrid}>
              {favorites.map((item) => (
                <div key={item.id} className={styles.favCard}>
                  <img
                    src={item.image}
                    alt={item.name}
                    className={styles.favImage}
                    onClick={() => goToItem(item)}
                  />
                  <button
                    className={styles.removeFavBtn}
                    onClick={() => handleRemoveClick(item.id)}  // ← abre modal
                    title="Eliminar de favoritos"
                  >
                    ✕
                  </button>
                  <div className={styles.favInfo} onClick={() => goToItem(item)}>
                    <p className={styles.favName}>{item.name}</p>
                    <p className={styles.favBrand}>{item.brand}</p>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

    </div>
  );
}
