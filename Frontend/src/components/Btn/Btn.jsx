import styles from "./Btn.module.css";

export default function Btn({ children, onClick, variant = "primary", className = "", disabled }) {
  return (
    <button onClick={onClick} disabled={disabled}
      className={`${styles.btn} ${styles[variant]} ${className}`}>
      {children}
    </button>
  );
}
