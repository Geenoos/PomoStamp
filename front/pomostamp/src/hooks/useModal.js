import * as React from "react";

function useModal() {
  const [modalOpen, setModalOpen] = React.useState(false);
  const toggle = () => setModalOpen(!modalOpen);
  return [modalOpen, toggle];
}

export default useModal;
