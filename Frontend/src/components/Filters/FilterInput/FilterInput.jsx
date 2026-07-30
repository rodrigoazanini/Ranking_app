import styles from "./FilterInput.module.css";

export default function FilterInput({ value, onChange, placeholder, className = "" }) {
  return (
    <input
      className={`${styles.filterInput} ${className}`}
      type="text"
      placeholder={placeholder}
      value={value}
      onChange={(e) => onChange(e.target.value)}
    />
  );
}