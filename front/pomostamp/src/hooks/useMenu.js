import * as React from "react";

function useMenuPopOver() {
  const [menuOpen, setMenuOpen] = React.useState(null);
  const openMenu = (event) => setMenuOpen(event.currentTarget);
  const closeMenu = () => setMenuOpen(null);
  return [menuOpen, openMenu, closeMenu];
}

export default useMenuPopOver;
