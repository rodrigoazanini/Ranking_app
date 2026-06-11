export const C = {
  coral:     "#F05A6A",
  blue:      "#2196C9",
  blueDark:  "#1877A8",
  blueLight: "#4FC3F7",
  yellow:    "#F9C22E",
  white:     "#FFFFFF",
  offWhite:  "#F4F8FB",
  gray:      "#8A9BAE",
  grayLight: "#DDE7EF",
  dark:      "#1A2A36",
  cardBg:    "#FFFFFF",
  shadow:    "0 4px 24px rgba(33,150,201,0.13)",
};

export const globalStyle = `
  @import url('https://fonts.googleapis.com/css2?family=Nunito:wght@400;600;700;800;900&family=Baloo+2:wght@700;800&display=swap');
  { box-sizing: border-box; margin: 0; padding: 0; }
  body { font-family: 'Nunito', sans-serif; background: ${C.blue}; min-height: 100vh; }
  input, textarea, select { font-family: 'Nunito', sans-serif; }
  button { cursor: pointer; font-family: 'Nunito', sans-serif; }
  `;
