import styles from "./HomePage.module.scss";
import { MOCK_ITEMS } from "../../data/mockData";
import ItemCard from "../../components/ItemCard/ItemCard";
import Btn from "../../components/Btn/Btn";
 

const filtered = MOCK_ITEMS.filter(
    (i) =>
      i.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      i.brand.toLowerCase().includes(searchQuery.toLowerCase())
  );

 const handleItemClick = (item) => {
    setSelectedItem(item);
    setPage("item");
  };

  <div className={styles.page}>
    
    <p > Bienvenido a</p>
    <p> Ranking App</p></div>

