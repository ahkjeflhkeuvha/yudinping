import React from "react";
import style from "../styles/Button.module.css";

function Button({ text, type }) {
  let classes = {};

  const handleLogin = () => {};

  const selectUser = () => {};

  if (type === "login") {
    classes = {
      className: style.loginButton,
      onClick: () => handleLogin(),
    };
  } else if (type === "user-select") {
    classes = {
      className: style.userSelectButton,
      onClick: () => selectUser(),
    };
  }
  return <button {...classes}>{text}</button>;
}

export default Button;
