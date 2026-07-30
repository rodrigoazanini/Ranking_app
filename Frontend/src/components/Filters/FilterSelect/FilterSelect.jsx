import styles from "./FilterSelect.module.css";

export default function FilterSelect({ value, onChange, options, className = "" }) {
  return (
    <select
      className={`${styles.filterSelect} ${className}`}
      value={value}
      onChange={(e) => onChange(e.target.value)}
    >
      {options.map((o) => (
        <option key={o.value} value={o.value}>{o.label}</option>
      ))}
    </select>
  );
}