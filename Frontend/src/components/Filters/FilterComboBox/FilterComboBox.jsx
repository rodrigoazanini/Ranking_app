import { useState } from "react";
import styles from "./FilterComboBox.module.css";

export default function FilterComboBox({ value, onChange, options, placeholder, className = "" }) {
  const [isOpen, setIsOpen] = useState(false);
  const [filterText, setFilterText] = useState("");

  const selectedLabel = options.find((o) => o.value === value)?.label || "";

  const filteredOptions = options.filter((o) =>
    o.label.toLowerCase().includes(filterText.toLowerCase())
  );

  const handleSelect = (optionValue) => {
    const option = options.find((o) => o.value === optionValue);
    onChange(optionValue);
    setFilterText(option.label);
    setIsOpen(false);
  };

  const handleInputChange = (e) => {
    setFilterText(e.target.value);
    setIsOpen(true);
  };

  const handleToggle = () => {
    setIsOpen((prev) => !prev);
  };

  return (
    <div className={`${styles.comboBox} ${className}`}>
      <div className={styles.comboBoxInput}>
        <input
          type="text"
          value={filterText}
          onChange={handleInputChange}
          onFocus={() => setIsOpen(true)}
          placeholder={placeholder}
          className={styles.comboBoxField}
        />
        <button
          type="button"
          className={styles.comboBoxToggle}
          onClick={handleToggle}
        >
          &#9660;
        </button>
      </div>
      {isOpen && filteredOptions.length > 0 && (
        <ul className={styles.comboBoxList}>
          {filteredOptions.map((o) => (
            <li
              key={o.value}
              className={`${styles.comboBoxItem} ${value === o.value ? styles.comboBoxItemActive : ""}`}
              onClick={() => handleSelect(o.value)}
            >
              {o.label}
            </li>
          ))}
        </ul>
      )}
      {isOpen && filteredOptions.length === 0 && (
        <div className={styles.comboBoxEmpty}>Sin resultados</div>
      )}
    </div>
  );
}
