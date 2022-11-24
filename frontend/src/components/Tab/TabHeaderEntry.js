export function TabHeaderEntry({ onClick, title, isActive }) {
  let className = "nav-link";
  if (isActive)
    className += " active";

  return (
    <li className="nav-item" onClick={onClick}>
      <span className={className}>{title}</span>
    </li>
  );
}

export default TabHeaderEntry;