import { useState } from "react";
import styles from "./Stars.module.css";

const YELLOW     = "#F9C22E";
const GRAY_LIGHT = "#9c9d9e";

export default function Stars({ value, size = 18, interactive = false, onChange }) {
  const [hovered, setHovered] = useState(0);

  return (
    <span className={styles.wrapper}>
      {[1,2,3,4,5].map((i) => {
        const filled = interactive ? (hovered || value) >= i : value >= i;
        const half   = !interactive && !filled && value >= i - 0.5;
        return (
          <svg key={i} width={size} height={size} viewBox="0 0 24 24"
            className={`${styles.star} ${interactive ? styles.interactive : ""}`}
            onMouseEnter={() => interactive && setHovered(i)}
            onMouseLeave={() => interactive && setHovered(0)}
            onClick={() => interactive && onChange && onChange(i)}>
            <defs>
              <linearGradient id={`half-${i}`}>
                <stop offset="50%" stopColor={YELLOW} />
                <stop offset="50%" stopColor={GRAY_LIGHT} />
              </linearGradient>
            </defs>
            <polygon
              points="12,2 15.09,8.26 22,9.27 17,14.14 18.18,21.02 12,17.77 5.82,21.02 7,14.14 2,9.27 8.91,8.26"
              fill={filled ? YELLOW : half ? `url(#half-${i})` : GRAY_LIGHT}
              stroke={filled || half ? YELLOW : GRAY_LIGHT}
              strokeWidth="0.5"
            />
          </svg>
        );
      })}
    </span>
  );
}
