import { useState } from "react";
import styles from "./UserProfilePage.module.css";
import Stars from "../../components/Stars/Stars";

const MOCK_USER       = { name: "María García", email: "maria@email.com" };
const MOCK_MY_REVIEWS = [
  { id:1, item:{ id:1, name:"Guaymallén Blanco", image:"https://http2.mlstatic.com/D_NQ_NP_714823-MLA52820341106_122022-O.webp" }, ranking:5, comment:"¡Riquísimo! Lo compro siempre que puedo.", price:650, date:"2024-05-10" },
  { id:2, item:{ id:3, name:"Copetin Amanecer",  image:"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1f7GgMLl4YrAFRqOV5p0nH0TYK0nFYP5cJA&s" }, ranking:4, comment:"Muy bueno para picar, crujiente y sabroso.", price:400, date:"2024-04-18" },
];
const MOCK_FAVORITES  = [
  { id:1, name:"Guaymallén Blanco",  brand:"Guaymallén", image:"https://http2.mlstatic.com/D_NQ_NP_714823-MLA52820341106_122022-O.webp" },
  { id:2, name:"Gallinita Misteriosa",brand:"Arcor",      image:"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTYwUBXhFvAcxTbBgz_gkk8kj3mXQcuY1AHw&s" },
  { id:5, name:"Bizcochos Canale",   brand:"Canale",     image:"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQg_93DQl1CU1qA0YkB80-XhP_ACxHH9pVwMg&s" },
];

export function UserProfilePage({ setPage, setSelectedItem }) {
  const [activeTab, setActiveTab] = useState("reviews");
  const [myReviews]               = useState(MOCK_MY_REVIEWS);
  const [favorites, setFavorites] = useState(MOCK_FAVORITES);

  const removeFavorite = (itemId) => {
	// TODO: api.delete(`/favorites/${itemId}`)
	setFavorites((prev) => prev.filter((f) => f.id !== itemId));
  };

  const goToItem = (item) => { setSelectedItem(item); setPage("item"); };

  return (
	<div className={styles.page}>
	  <div className={styles.header}>
		<div className={styles.avatar}>👤</div>
		<div>
		  <p className={styles.userName}>{MOCK_USER.name}</p>
		  <p className={styles.userEmail}>{MOCK_USER.email}</p>
		</div>
	  </div>

	  <div className={styles.tabs}>
		<button className={`${styles.tab} ${activeTab === "reviews"   ? styles.active : ""}`} onClick={() => setActiveTab("reviews")}>
		  📝 Mis reseñas ({myReviews.length})
		</button>
		<button className={`${styles.tab} ${activeTab === "favorites" ? styles.active : ""}`} onClick={() => setActiveTab("favorites")}>
		  ❤️ Favoritos ({favorites.length})
		</button>
	  </div>

	  {activeTab === "reviews" && (
		<div className={styles.tabContent}>
		  {myReviews.length === 0
			? <div className={styles.empty}><span className={styles.emptyIcon}>📝</span>Todavía no escribiste ninguna reseña.</div>
			: myReviews.map((r) => (
				<div key={r.id} className={styles.reviewItem} onClick={() => goToItem(r.item)}>
				  <img src={r.item.image} alt={r.item.name} className={styles.reviewItemImage} />
				  <div className={styles.reviewItemBody}>
					<p className={styles.reviewItemName}>{r.item.name}</p>
					<div className={styles.reviewItemMeta}>
					  <Stars value={r.ranking} size={14} />
					  {r.price > 0 && <span className={styles.reviewItemPrice}>Pagué ${r.price.toLocaleString()}</span>}
					  <span className={styles.reviewItemDate}>{r.date}</span>
					</div>
					<p className={styles.reviewItemComment}>{r.comment}</p>
				  </div>
				</div>
			  ))
		  }
		</div>
	  )}

	  {activeTab === "favorites" && (
		<div className={styles.tabContent}>
		  {favorites.length === 0
			? <div className={styles.empty}><span className={styles.emptyIcon}>❤️</span>No tenés productos favoritos todavía.</div>
			: <div className={styles.favGrid}>
				{favorites.map((item) => (
				  <div key={item.id} className={styles.favCard}>
					<img src={item.image} alt={item.name} className={styles.favImage} onClick={() => goToItem(item)} />
					<button className={styles.removeFavBtn} onClick={() => removeFavorite(item.id)} title="Quitar de favoritos">✕</button>
					<div className={styles.favInfo} onClick={() => goToItem(item)}>
					  <p className={styles.favName}>{item.name}</p>
					  <p className={styles.favBrand}>{item.brand}</p>
					</div>
				  </div>
				))}
			  </div>
		  }
		</div>
	  )}
	</div>
  );
}
